package link.smartwall.web.controller.grid;
///**
// * 天恒众航（北京）科技股份公司(2014)
// */
//package com.itfsm.web.controller.grid;
//
//import java.io.File;
//import java.io.FileOutputStream;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.nutz.dao.Dao;
//import org.nutz.ioc.loader.annotation.Inject;
//import org.nutz.ioc.loader.annotation.IocBean;
//import org.nutz.mvc.annotation.At;
//import org.nutz.mvc.annotation.Ok;
//import org.nutz.mvc.annotation.Param;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.itfsm.base.api.Result;
//import com.itfsm.base.http.UserManager;
//import com.itfsm.base.http.WebUser;
//import com.itfsm.base.http.parameter.Parameters;
//import com.itfsm.base.http.parameter.StandardParameters;
//import com.itfsm.config.GlobalStatic;
//import com.itfsm.config.tenant.parameter.TenantIndividuationParameterManager;
//import com.itfsm.grid.entity.ConfGrid;
//import com.itfsm.service.BaseService;
//import com.itfsm.service.grid.GridHelper;
//import com.itfsm.service.imp.ExcelToTable;
//import com.itfsm.service.imp.ImpDataObj;
//import com.itfsm.service.imp.ImpDataSaver;
//import com.itfsm.util.AbstractFileCopyUtils;
//import com.itfsm.util.HttpParameterUtils;
//import com.itfsm.util.StrUtils;
//import com.itfsm.util.fileupload.AttachmentHelper;
//
///**
// * Excel导入控制器
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since 外勤管家3.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2014年9月23日 lexloo
// *        </pre>
// */
//@IocBean(args = {"refer:dao"})
//public class XlsImpController extends BaseService {
//    /**
//     * 当前运行环境的字符编码
//     */
//    public static final String ENCODING = System.getProperties().getProperty("file.encoding");
//    public XlsImpController(Dao dao) {
//        super(dao);
//        // TODO Auto-generated constructor stub
//    }
//
//    @Inject
//    private GridHelper gridHelper;
//
//    @Inject
//    private ExcelToTable excelToTable;
//
//    @Inject
//    private ImpDataSaver impDataSaver;
//
//    /**
//     * 临时文件目录
//     */
//    public static final String TEMP_DIR = GlobalStatic.XSB_HOME_DATA_DIR + "temp" + File.separator;
//
//    /**
//     * TODO 最简单版本的导入功能  author yty create 2017.5.9
//     * @param model
//     * @param bizCode
//     * @return
//     */
//    @At("/excel/import2")
//    @Ok("fm:/excel/import.html")
//    public JSONObject uploadFile(@Param("model") String model,@Param("bizCode") String bizCode) {
//        JSONObject rtn = new JSONObject();
//
//        ConfGrid gridModel = gridHelper.getModel(model, UserManager.getCurrentUser().getTenantId());
//        rtn.put("model", model);
//        if (!StrUtils.isEmpty(gridModel.getImpTemplate())) {
//            rtn.put("template", this.getImpTemplateUrl(gridModel.getImpTemplate()));
//        }
//        rtn.put("bizCode", bizCode);
//        rtn.put("useJsModel", gridHelper.getModel(model, 0).getUseJsModule());
//        return rtn;
//    }
//
//    /**
//     * xls导入初始页面
//     * 
//     * @param model 需要导入的模型
//     * @param fast 使用快速导入方式
//     * @param tenantCode 企业tenantCode（企业定制）
//     * @return 参数
//     */
//    @At("/excel/import")
//    @Ok("fm:/excel/import.html")
//    public JSONObject uploadFile(@Param("model") String model,
//                                 @Param("fast") boolean fast,
//                                 @Param("tenantCode") String tenantCode) {
//        JSONObject rtn = new JSONObject();
//
//        ConfGrid gridModel = gridHelper.getModel(model, UserManager.getCurrentUser().getTenantId());
//        rtn.put("model", model);
//        if (!StrUtils.isEmpty(gridModel.getImpTemplate())) {
//            rtn.put("template", this.getImpTemplateUrl(gridModel.getImpTemplate()));
//        }
//        rtn.put("fast", fast);
//        rtn.put("tenantCode", tenantCode);
//        rtn.put("useJsModel", gridHelper.getModel(model, 0).getUseJsModule());
//
//        return rtn;
//    }
//
//    /**
//     * 获取配置文件URL
//     * 
//     * @param impTemplate 配置文件名
//     * @return 配置文件URL
//     */
//    private Object getImpTemplateUrl(String impTemplate) {
////        return "http://mobi.itfsm.com/datacache/documents/template/"
////               + TenantIndividuationParameterManager.INSTANCE.fillStringParams(impTemplate,
////                                                                               UserManager.getCurrentUser()
////                                                                                          .getTenantId(),
////                                                                               dao())
////               + ".xlsx";
//       // return "http://112.124.114.121:8305/sfa/oss/mobi/download.mvc?key=impTemplate/" + impTemplate +".xlsx";
//        //return "http://localhost:8787/sfa/oss/mobi/download.mvc?key=impTemplate/" + impTemplate +".xlsx";.
//        return "/sfa/oss/mobi/download.mvc?key=impTemplate/" + impTemplate +".xlsx";
//    }
//
//    /**
//     * 上传文件,加载预览信息
//     * 
//     * @param fileName 上传的文件名，在XSB_HOME\datacache\temp目录下
//     * @param model 模型对象
//     * @param fast 使用快速导入方式
//     * @param code 企业code（企业定制）
//     * @return 返回结果
//     */
//    @At("/excel/load_preview_info")
//    @Ok("json")
//    public JSONObject loadPreviewInfo(@Param("impFile") String fileName,
//                                      @Param("model") String model,
//                                      @Param("fast") boolean fast,
//                                      @Param("code") String code,@Param("bizCode") String bizCode) {
//        JSONObject rtn = new JSONObject();
//        WebUser wu = UserManager.getCurrentUser();
//
//        File file = new File(fileName);
//
//        if (!file.exists()) {
//            throw new IllegalStateException("上传文件发生出错");
//        }
//        excelToTable.setConfigs(null);
//        excelToTable.setModel(model);
//        rtn.put("tableInfo", excelToTable.transToTable(file));
//        rtn.put("cols", excelToTable.getCol());
//
//        return rtn;
//    }
//
//    /**
//     * 文件导入初始页面
//     * 
//     * @param code 导入程序代码
//     * @return 参数
//     */
//    @At("/file/import")
//    @Ok("fm:/excel/import.html")
//    public JSONObject imp(@Param("code") String code) {
//        JSONObject rtn = new JSONObject();
//        ConfGrid model = gridHelper.getModel(code, 0);
//        rtn.put("useJsModel", model.getUseJsModule());
//        rtn.put("code", code);
//        return rtn;
//    }
//
//    // /**
//    // * 上传文件,加载预览信息
//    // *
//    // * @param fileName 上传的文件名，在XSB_HOME\temp目录下
//    // * @param code 导入程序代码
//    // * @param param 附加参数
//    // * @return 返回结果
//    // */
//    // @At("/file/imp2db")
//    // @Ok("json")
//    // public Object imp2db(@Param("fileName") String fileName, @Param("code") String code, @Param("param") String
//    // param) {
//    //
//    // File file = new File(GlobalStatic.XSB_HOME_DATA_TMP_DIR + fileName);
//    // if (!file.exists()) {
//    // throw new IllegalStateException("上传文件发生出错");
//    // }
//    //
//    // IDataImport imp = DataImportFactory.getService(code);
//    // Result result = null;
//    // if (!StrUtils.isEmpty(param)) {
//    // result = imp.execute(file, param);
//    // } else {
//    // result = imp.execute(file);
//    // }
//    //
//    // return result;
//    // }
//
//    // /**
//    // * 文件导入初始页面
//    // *
//    // * @param code 导入程序代码
//    // * @param resp 响应参数
//    // */
//    // @At("/file/template")
//    // public void templateProvider(@Param("code") String code, HttpServletResponse resp) {
//    // IDataImport tpl = DataImportFactory.getService(code);
//    // tpl.templateProvider(resp);
//    // }
//
//    /**
//     * 数据导入操作
//     * 
//     * @param data data
//     * @param request request
//     * @return 导入
//     */
//    @At("/excel/do_import")
//    @Ok("json")
//    public Object excelImport(@Param("data") String data,HttpServletRequest request) {
//        WebUser wu = UserManager.getCurrentUser();
//        if (wu.isDemoUser()) {
//            return new Result(Result.FAILURE, "演示企业，操作无效");
//        }
//
//        // 参数列表
//        Parameters parameters = HttpParameterUtils.getParamMap(request);
//        StandardParameters standardParamters = HttpParameterUtils.parseStarndardParameters(parameters, request);
//
//        ImpDataObj impData = JSON.parseObject(data, ImpDataObj.class);
//        String result = "";
//        result = impDataSaver.importData(impData, standardParamters.getTenantId());
//
//        // if (StrUtils.isEmpty(result)) {
//        // // 写入审计信息
//        // auditOperateInfo(ModelOperationType.IMPORT, impData.getModel(), request, impData.getModel(), dao());
//        //
//        // return new Result();
//        // } else {
//        // return new Result(Result.FAILURE, result);
//        // }
//        return new Result(Result.FAILURE, result);
//    }
//
////    /**
////     * 添加审计信息
////     *
////     * @param operateType operateType
////     * @param operateModel operateModel
////     * @param request request
////     * @param operateTable operateTable
////     * @param dao dao
////     */
////     private void auditOperateInfo(ModelOperationType operateType,
////     String operateModel,
////     HttpServletRequest request,
////     String operateTable,
////     Dao dao) {
////
////     HttpSession session = request.getSession();
////     WebUser wu = (WebUser) session.getAttribute(Constants.SESSION_USER);
////
////     AuditOperationInfo auditOperationInfo = new AuditOperationInfo();
////     auditOperationInfo.setOperateModel(operateModel);
////     auditOperationInfo.setOperateType(operateType);
////     auditOperationInfo.setUserid(wu.getId());
////     auditOperationInfo.setUserName(wu.getCode());
////
////     auditOperationInfo.setOperateTable(operateTable);
////     auditOperationInfo.setTenantid(wu.getTenantId());
////
////     dao.insert(auditOperationInfo);
////     }
//
//    @At("/excel/upload")
//    public void uploadExcel (HttpServletRequest request, HttpServletResponse response) throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        request.setCharacterEncoding(ENCODING);
//        response.setCharacterEncoding(ENCODING);
//        String attachmentFileName = request.getParameter("name");
//        File basePathDir = new File(AttachmentHelper.DATA_CACHE_DIR + File.separator + "excel");
//        if (!basePathDir.exists()) {
//            basePathDir.mkdirs();
//        }
//        File logo = new File(basePathDir, attachmentFileName);
//        AbstractFileCopyUtils.copy(request.getInputStream(), new FileOutputStream(logo));
//
//        // OssUploader.uploadImage(UserManager.getCurrentUser().getTenantId(), request.getParameter("fileGuid") + File.separator + attachmentFileName, request.getInputStream());
//
//        jsonObject.put("url", basePathDir + File.separator + attachmentFileName);
//        jsonObject.put("filename", attachmentFileName);
//        response.getWriter().print(jsonObject.toJSONString());
//    }
//}
