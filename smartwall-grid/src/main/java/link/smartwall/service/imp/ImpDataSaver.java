package link.smartwall.service.imp;
///**
// * SmartWall(2013)
// */
//package com.itfsm.service.imp;
//
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.nutz.dao.Chain;
//import org.nutz.dao.Cnd;
//import org.nutz.dao.Dao;
//import org.nutz.dao.Sqls;
//import org.nutz.dao.entity.Record;
//import org.nutz.dao.sql.Sql;
//import org.nutz.ioc.loader.annotation.Inject;
//import org.nutz.ioc.loader.annotation.IocBean;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.itfsm.base.http.UserManager;
//import com.itfsm.base.http.parameter.StandardParameters;
//import com.itfsm.basemodel.AbstractObject;
//import com.itfsm.grid.entity.ConfGridCol;
//import com.itfsm.service.BaseService;
//import com.itfsm.service.ObjectManager;
//import com.itfsm.service.grid.GridHelper;
//import com.itfsm.util.DateUtils;
//import com.itfsm.util.NumberUtils;
//import com.itfsm.util.StrUtils;
//import com.itfsm.web.controller.grid.XlsImpController;
//
///**
// * 导入数据保存
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since  2.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2013-4-12 lexloo
// *        </pre>
// */
//@IocBean(args = {"refer:dao"})
//public class ImpDataSaver extends BaseService {
//    public ImpDataSaver(Dao dao) {
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
//    private ObjectManager objectManager;
//
//    /**
//     * 临时字典
//     */
//    protected Map<String, Map<String, Integer>> dicts = new HashMap<String, Map<String, Integer>>();
//
//    protected void extractConfigs(JSONArray fields, List<ImpConfig> configs, List<Integer> impCols) {
//        for (Object f : fields) {
//            JSONObject json = (JSONObject) f;
//
//            for (ImpConfig cfg : excelToTable.getConfigs()) {
//                String cc = json.getString("value");
//                if (!StrUtils.isEmpty(cc)) {
//                    if (cc.equals(cfg.getCode())) {
//                        configs.add(cfg);
//                        impCols.add(json.getInteger("index"));
//
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 加载字典
//     * 
//     * @param dictModel 验证器模型
//     * @param refCol 参照字段
//     * @param tenantId 企业Id
//     * @param dao 数据访问对象
//     * @return 字典对象
//     */
//    @SuppressWarnings("unchecked")
//    protected Map<String, Integer> loadDicts(String dictModel, String refCol, int tenantId, Dao dao) {
//        Map<String, Integer> dictMap = new HashMap<String, Integer>();
//
//        AbstractObject dict = objectManager.createObject(dictModel);
//
//        // 通过设置TenantId判断是否存在该字段
//        dict.setValue(StandardParameters.COMM_TENANT_ID, 1);
//        Integer testId = NumberUtils.c2int(dict.getValue(StandardParameters.COMM_TENANT_ID), 0);
//        Cnd cnd = null;
//        if (testId == 1) {
//            // Sys_dict_dt中，若无企业相关，tenantid默认为0
//            cnd = Cnd.where(Cnd.exps(StandardParameters.COMM_TENANT_ID, "=", tenantId)
//                               .or(StandardParameters.COMM_TENANT_ID, "=", 0));
//        }
//
//        List<AbstractObject> dictList = (List<AbstractObject>) dao.query(dict.getClass(), cnd);
//        for (AbstractObject ao : dictList) {
//            // 没有指定字段，则默认为id字段
//            dictMap.put(StrUtils.c2str(ao.getValue(refCol), ""), (int) ao.entityIdValue());
//        }
//
//        return dictMap;
//    }
//
////    public String importData(ImpDataObj obj, int tenantId) {
////        List<ImpConfig> configs = new ArrayList<ImpConfig>();
////        List<Integer> impCols = new ArrayList<Integer>();
////
////        // 抽取函数
////        JSONArray fields = obj.getFields();
////
////        extractConfigs(fields, configs, impCols);
////
////        // 获取网格对应model，当没有配置时，默认为grid的model
////        String bizModel = excelToTable.getGridBizModel(obj.getModel());
////        List<Integer> errorLine = new ArrayList<Integer>();
////
////        try (FileInputStream fis = new FileInputStream(obj.getFileName())) {
////            Workbook workBook = WorkbookFactory.create(fis);
////            Sheet sheet = workBook.getSheetAt(0);
////
////            for (int ri = sheet.getFirstRowNum() + 1, rsize = sheet.getLastRowNum(); ri <= rsize; ri++) {
////                Row row = sheet.getRow(ri);
////
////                Cell cell = row.getCell(0);
////                if (StrUtils.isEmpty(ExcelToTable.getStringCellValue(cell))) {
////                    // 第一列不能为空，否则视为无效数据
////                    continue;
////                }
////
////                AbstractObject ao = objectManager.createObject(bizModel);
////                ao.setValue("guid", StrUtils.uuid().toLowerCase());
////                ao.setValue("tenant_id", tenantId);
////                ao.setValue("modify_time", new Date());
////                ao.setValue("modify_flag", 1);
////                ao.setValue("data_is_deleted", 0);
////                // ao.setValue(StandardParameters.COMM_TENANT_ID, tenantId);
////                // 存储关联中间表的配置对象和ids
////                Map<ImpConfig, String> associatedImpConfigIdsMap = new HashMap<>();
////
////                for (int i = 0, size = configs.size(); i < size; i++) {
////                    int col = impCols.get(i);
////                    cell = row.getCell(col);
////                    ImpConfig cfg = configs.get(i);
////                    String value = ExcelToTable.getStringCellValue(cell);
////                    if (StrUtils.isEmpty(cfg.getImpValidator())) {
////                        if (StrUtils.isEmpty(cfg.getImpDataType())) {
////                            ao.setValue(cfg.getBizCode(), value);
////                        } else if (cfg.getImpDataType().equals("date")) {
////                            ao.setValue(cfg.getBizCode(), DateUtils.convertToDateShort2(value));
////                        } else if (cfg.getImpDataType().equals("date2")) {
////                            ao.setValue(cfg.getBizCode(), DateUtils.convertToDateShort3(value));
////                        }
////                    } else {
////                        // 无关联表
////                        if (StrUtils.isEmpty(cfg.getAssociatedTable())) {
////                            // 查找对应的主键
////                            Integer id = this.getDictId(value, cfg, tenantId, dao());
////                            ao.setValue(cfg.getToCol(), id);
////                        } else {
////                            // 导入时，有中间关联表时
////                            // 找到所有对应的ids
////                            String associatedIds = this.associatedIds(value, cfg, tenantId, dao());
////
////                            if (associatedIds != null) {
////                                ao.setValue(cfg.getToCol(), associatedIds);
////
////                                associatedImpConfigIdsMap.put(cfg, associatedIds);
////                            }
////                        }
////                    }
////                }
////
////                // 设置所有导入数据的DataType = 2
////                ao.setValue("datatype", 2);
////                try {
////                    ao.beforeInsert(dao());
////                    dao().insert(ao);
////                    ao.afterInsert(dao());
////
////                    // 往关联中间表中插入数据
////                    for (Map.Entry<ImpConfig, String> entry : associatedImpConfigIdsMap.entrySet()) {
////                        saveAssociatedConfig(ao.entityIdValue(), entry.getKey(), entry.getValue(), dao());
////                    }
////
////                } catch (Exception ex) {
////                    ex.printStackTrace();
////                    // 捕获导入的出错行
////                    errorLine.add(ri + 1);
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            // 如果是掌务通版本的导入门店
////            if ("jxs_store_info".equals(bizModel) && UserManager.getCurrentUser().getBusinessId() == 13) {
////                // 刷新门店业务员
////                Sql sql =
////                        Sqls.create("update store_info set allocmobiles = abbp.get_e_jxsstore_mobile(id), "
////                                    + "allocusers = abbp.get_e_jxsstore_user(id),need_refresh = 0 where need_refresh = 1");
////                dao().execute(sql);
////            }
////        }
////
////        String result = "";
////
////        if (!errorLine.isEmpty()) {
////            result = "导入过程有错误,行:[" + StrUtils.join(errorLine, ",") + "]不能导入!";
////        }
////
////        return result;
////    }
//    /**
//     * 存储关联中间表信息
//     * 
//     * @param entityIdValue 当前导入的id
//     * @param cfg 配置
//     * @param ids 关联ids
//     * @param dao dao
//     */
//    protected void saveAssociatedConfig(long entityIdValue, ImpConfig cfg, String ids, Dao dao) {
//        String associatedTable = cfg.getAssociatedTable();
//        int associatedType = cfg.getAssociatedType();
//        int tenantId = UserManager.getCurrentUser().getTenantId();
//
//        Sql insertAssociatedSql =
//                Sqls.create("insert into $table (id,clientid,valueid, tenantid) values($id,@clientid,@valueid, @tenantid)");
//
//        boolean hasData = ids.trim().length() == 0 ? false : true;
//
//        if (hasData) {
//            String[] idArray = ids.split(",");
//            for (String id : idArray) {
//                insertAssociatedSql.vars().set("table", associatedTable).set("id", associatedTable + "_s.nextval");
//
//                // 判断配置，当前导入的id基于clientid存储，还是valueid存储
//                if (associatedType == ConfGridCol.ASSOCIATED_TYPE_CLIENTID) {
//                    insertAssociatedSql.params().set("tenantid", tenantId).set("clientid", id).set("valueid",
//                                                                                                   entityIdValue);
//                } else if (associatedType == ConfGridCol.ASSOCIATED_TYPE_VALUEID) {
//                    insertAssociatedSql.params().set("tenantid", tenantId).set("clientid", entityIdValue).set("valueid",
//                                                                                                              id);
//                }
//
//                insertAssociatedSql.addBatch();
//            }
//
//            dao.execute(insertAssociatedSql);
//            // TODO 刷新配置信息
//            // Kernel.getInstance()
//            // .getEventBus()
//            // .fireKernelEvent(new RefreshConfigEvent(this, new RefreshConfigObj(associatedTable,
//            // associatedType,
//            // String.valueOf(entityIdValue),
//            // ids,
//            // UserManager.getCurrentUser()
//            // .getTenantId())));
//        }
//
//    }
//
//    /**
//     * 关联配置,返回所有关联的id，以逗号隔开
//     * 
//     * @param value 当前值
//     * @param cfg 配置
//     * @param tenantId 租户Id
//     * @param dao 数据访问对象
//     * @return 对应的id值
//     */
//   /* protected String associatedIds(String value, ImpConfig cfg, int tenantId, Dao dao) {
//        String associatedIds = "";
//
//        String fromTable = cfg.getImpValidator();
//        String fromColumn = cfg.getRefCol();
//
//        String sql = "select id from $table where $column in ("
//                     + StrUtils.assemblyString(value, ",")
//                     + ") and tenantid=@tenantid";
//
//        Sql queryIdsSql = Sqls.create(sql);
//        queryIdsSql.vars().set("table", fromTable).set("column", fromColumn);
//        queryIdsSql.params().set("tenantid", tenantId);
//        queryIdsSql.setCallback(Sqls.callback.longs());
//
//        dao.execute(queryIdsSql);
//
//        // 查询的id通过逗号分隔
//        associatedIds = StrUtils.join(queryIdsSql.getObject(Long[].class), ",");
//
//        return associatedIds;
//    }*/
//
//    /**
//     * 获取码表Id值
//     * 
//     * @param value 当前值
//     * @param cfg 配置
//     * @param tenantId 租户Id
//     * @param dao 数据访问对象
//     * @return 对应的id值
//     */
//    /*protected Integer getDictId(String value, ImpConfig cfg, int tenantId, Dao dao) {
//        // TODO 全部读还是每次读入一个值？需要考虑空间和效率
//        String dictModel = cfg.getImpValidator();
//        Map<String, Integer> dictMap = this.dicts.get(dictModel);
//        // 取出的不为null，为{} ttan
//        if (dictMap == null || dictMap.isEmpty()) {
//            dictMap = this.loadDicts(dictModel, cfg.getRefCol(), tenantId, dao);
//            this.dicts.put(cfg.getImpValidator(), dictMap);
//        }
//
//        Integer id = dictMap.get(value);
//        if (id == null && cfg.isAutoAdd() && !StrUtils.isEmpty(value)) {
//            try {
//                // 插入新记录
//                AbstractObject dict = objectManager.createObject(dictModel);
//                dict.setValue(cfg.getRefCol(), value);
//                dict.setValue(StandardParameters.COMM_TENANT_ID, tenantId);
//
//                dao.insert(dict);
//
//                id = (int) dict.entityIdValue();
//                dictMap.put(value, id);
//            } catch (Exception ex) {
//                try {
//                    // 有可能是操作的同一个表,像部门这类包括父子的，需要重新刷新，有可能会取得值
//                    dictMap = this.loadDicts(dictModel, cfg.getRefCol(), tenantId, dao);
//                    this.dicts.put(cfg.getImpValidator(), dictMap);
//
//                    return dictMap.get(value);
//                } catch (Exception ex1) {
//                    return null;
//                }
//            }
//        }
//
//        return id;
//    }*/
//
//    /**
//     * 数据导入
//     * 
//     * @param obj 数据对象
//     * @param tenantId 租户信息
//     * @return 出错行信息
//     */
//    // @Override
//    // public List<Integer> importDataFast(ImpDataObj obj, int tenantId) {
//    // ExcelToTable ett = new ExcelToTable(dao(), obj.getModel(), tenantId);
//    //
//    // // 抽取函数
//    // JSONArray fields = obj.getFields();
//    // List<ImpConfig> configs = new ArrayList<ImpConfig>();
//    // List<Integer> impCols = new ArrayList<Integer>();
//    // extractConfigs(ett, fields, configs, impCols);
//    //
//    // // 获取网格对应model，当没有配置时，默认为grid的model
//    // String bizModel = ett.getGridBizModel(obj.getModel());
//    // AbstractObject tmpAo = bomgr.createObject(bizModel);
//    //
//    // String sqlStr = "insert into " + tmpAo.entityTableName() + "(";
//    // for (int i = 0, size = configs.size(); i < size; i++) {
//    // ImpConfig cfg = configs.get(i);
//    // if (StrUtils.isEmpty(cfg.getImpValidator())) {
//    // sqlStr += cfg.getBizCode() + ",";
//    // } else {
//    // sqlStr += cfg.getToCol() + ",";
//    // }
//    // }
//    // sqlStr += "tenantid, datatype," + tmpAo.entityIdField() + ") values (";
//    // for (int i = 0, size = configs.size(); i < size; i++) {
//    // ImpConfig cfg = configs.get(i);
//    // if (StrUtils.isEmpty(cfg.getImpValidator())) {
//    // sqlStr += "@" + cfg.getBizCode() + ",";
//    // } else {
//    // sqlStr += cfg.getToCol() + ",";
//    // }
//    // }
//    // sqlStr += "@tenantid, @datatype, " + tmpAo.entityTableName() + "_s.nextval)";
//    //
//    // List<Integer> errorLine = new ArrayList<Integer>();
//    //
//    // try (FileInputStream fis = new FileInputStream(XlsImpController.TEMP_DIR + obj.getFileName())) {
//    // Sql sql = Sqls.create(sqlStr);
//    // Workbook workBook = WorkbookFactory.create(fis);
//    // Sheet sheet = workBook.getSheetAt(0);
//    //
//    // for (int ri = sheet.getFirstRowNum() + 1, rsize = sheet.getLastRowNum(); ri <= rsize; ri++) {
//    // Row row = sheet.getRow(ri);
//    // Cell cell = row.getCell(0);
//    //
//    // if (StrUtils.isEmpty(ExcelToTable.getStringCellValue(cell))) {
//    // // 第一列不能为空，否则视为无效数据
//    // continue;
//    // }
//    //
//    // sql.params().set(StandardParameters.COMM_TENANT_ID, tenantId);
//    //
//    // for (int i = 0, size = configs.size(); i < size; i++) {
//    // int col = impCols.get(i);
//    // cell = row.getCell(col);
//    // ImpConfig cfg = configs.get(i);
//    // String value = ExcelToTable.getStringCellValue(cell);
//    // if (StrUtils.isEmpty(cfg.getImpValidator())) {
//    // if (StrUtils.isEmpty(cfg.getImpDataType())) {
//    // sql.params().set(cfg.getBizCode(), value);
//    // } else if (cfg.getImpDataType().equals("date")) {
//    // sql.params().set(cfg.getBizCode(), DateUtils.convertToDateShort2(value));
//    // } else if (cfg.getImpDataType().equals("date2")) {
//    // sql.params().set(cfg.getBizCode(), DateUtils.convertToDateShort3(value));
//    // }
//    // } else {
//    // // 查找对应的主键
//    // Integer id = this.getDictId(value, cfg, tenantId, dao());
//    // sql.params().set(cfg.getToCol(), id);
//    // }
//    // }
//    //
//    // // 设置所有导入数据的DataType = 2
//    // sql.params().set("datatype", 2);
//    // sql.addBatch();
//    // }
//    //
//    // dao().execute(sql);
//    // } catch (Exception e) {
//    // e.printStackTrace();
//    // }
//    //
//    // return errorLine;
//    // }
//    /**
//     * 数据导入
//     * 
//     * @param obj 数据对象
//     * @param tenantId 租户信息
//     * @return 出错行信息
//     */
//    // public List<Integer> importDataEM(ImpDataObj obj, int tenantId) {
//    // ExcelToTable ett = new ExcelToTable(dao(), obj.getModel(), tenantId);
//    // // 抽取函数
//    // JSONArray fields = obj.getFields();
//    // List<ImpConfig> configs = new ArrayList<ImpConfig>();
//    // for (Object f : fields) {
//    // for (ImpConfig cfg : ett.getConfigs()) {
//    // if (cfg.getCode().equals(f)) {
//    // configs.add(cfg);
//    // break;
//    // }
//    // }
//    // }
//    // // 获取网格对应model，当没有配置时，默认为grid的model
//    // String bizModel = ett.getGridBizModel(obj.getModel());
//    //
//    // JSONArray data = obj.getData();
//    // List<Integer> errorLine = new ArrayList<Integer>();
//    //
//    // for (int r = 0, sizer = data.size(); r < sizer; r++) {
//    // AbstractObject ao = bomgr.createObject(bizModel);
//    // ao.setValue(StandardParameters.COMM_TENANT_ID, obj.getTenantId());
//    // JSONArray item = (JSONArray) data.get(r);
//    //
//    // // 存储关联中间表的配置对象和ids
//    // Map<ImpConfig, String> associatedImpConfigIdsMap = new HashMap<>();
//    //
//    // for (int i = 0, size = configs.size(); i < size; i++) {
//    // ImpConfig cfg = configs.get(i);
//    // String value = StrUtils.c2str(item.getString(i), "").trim();
//    // if (StrUtils.isEmpty(cfg.getImpValidator())) {
//    // if (StrUtils.isEmpty(cfg.getImpDataType())) {
//    // ao.setValue(cfg.getBizCode(), value);
//    // } else if (cfg.getImpDataType().equals("date")) {
//    // ao.setValue(cfg.getBizCode(), DateUtils.convertToDateShort2(value));
//    // } else if (cfg.getImpDataType().equals("date2")) {
//    // ao.setValue(cfg.getBizCode(), DateUtils.convertToDateShort3(value));
//    // }
//    // } else {
//    // if (StrUtils.isEmpty(cfg.getAssociatedTable())) {
//    // // 查找对应的主键
//    // Integer id = this.getDictId(value, cfg, obj.getTenantId(), dao());
//    // ao.setValue(cfg.getToCol(), id);
//    // } else {
//    // // 导入时，有中间关联表时
//    // // 找到所有对应的ids
//    // String associatedIds = this.associatedIds(value, cfg, obj.getTenantId(), dao());
//    // ao.setValue(cfg.getToCol(), associatedIds);
//    //
//    // associatedImpConfigIdsMap.put(cfg, associatedIds);
//    // }
//    // }
//    // }
//    //
//    // // 设置所有导入数据的DataType = 2
//    // ao.setValue("datatype", 2);
//    // try {
//    // ao.beforeInsert(dao());
//    // dao().insert(ao);
//    // ao.afterInsert(dao());
//    //
//    // // 往关联中间表中插入数据
//    // for (Map.Entry<ImpConfig, String> entry : associatedImpConfigIdsMap.entrySet()) {
//    // saveAssociatedConfig(ao.entityIdValue(), entry.getKey(), entry.getValue(), dao());
//    // }
//    // } catch (Exception ex) {
//    // ex.printStackTrace();
//    // // 捕获导入的出错行
//    // errorLine.add(r + 1);
//    // }
//    // }
//    //
//    // return errorLine;
//    // }
//
//
//    /**
//     * 最简单的导入功能,不考虑关联字段,不考虑使用entity新增。
//     * @param obj
//     * @param tenantId
//     * @return
//     */
//    public String importData(ImpDataObj obj, int tenantId) {
//
//        List<ImpConfig> configs = new ArrayList<ImpConfig>();
//        List<Integer> impCols = new ArrayList<Integer>();
//
//        // 抽取函数
//        JSONArray fields = obj.getFields();
//
//        extractConfigs(fields, configs, impCols);
//
//        // 获取网格对应model，当没有配置时，默认为grid的model
//        String bizModel = excelToTable.getGridBizModel(obj.getModel());
//        List<Integer> errorLine = new ArrayList<Integer>();
//
//        try (FileInputStream fis = new FileInputStream(obj.getFileName())) {
//            Workbook workBook = WorkbookFactory.create(fis);
//            Sheet sheet = workBook.getSheetAt(0);
//
//            for (int ri = sheet.getFirstRowNum() + 1, rsize = sheet.getLastRowNum(); ri <= rsize; ri++) {
//                Row row = sheet.getRow(ri);
//
//                Cell cell = row.getCell(0);
//                if (StrUtils.isEmpty(ExcelToTable.getStringCellValue(cell))) {
//                    // 第一列不能为空，否则视为无效数据
//                    continue;
//                }
//                /**
//                 * 暂时使用map来保存数据
//                 */
//                Map<String,Object> data = new HashMap<String,Object>();
//                String guid = StrUtils.uuid();
//                data.put("guid",guid);
//                data.put(".table",obj.getBizCode());
//                data.put("tenant_id",tenantId);
//                data.put("data_time",new Date());
//                data.put("modify_time",new Date());
//                data.put("modify_flag",1);
//                data.put("data_is_deleted",0);
//
//                for (int i = 0, size = configs.size(); i < size; i++) {
//                    int col = impCols.get(i);
//                    cell = row.getCell(col);
//                    ImpConfig cfg = configs.get(i);
//                    String value = ExcelToTable.getStringCellValue(cell);
//                    String refCol = cfg.getRefCol();
//                    String toCol = cfg.getToCol();
//
//                    Cnd cnd = Cnd.where("1", "=", "1");
//                    if (cfg.getImpValidator() == 0){
//                        cnd.and("tenant_id", "=", tenantId).and("data_is_deleted", "=", 0);
//                    }else if (cfg.getImpValidator() == 1){
//                        cnd.and("data_is_deleted", "=", 0);
//                    }
//
//                    if (cfg.getImportType() == 1){
//                        //如果是关联表,第一个为关联字段名，第二个字段为关联表
//                       /* refCol = cfg.getRefCol();
//                       toCol = cfg.getToCol();*/
//                        String[] cols = refCol.split(":");
//                        String colum = cols[0];
//                        String table = cols[1];
//                        Record record = dao().fetch(table, cnd.and(colum, "=", value));
//
//                        value = null!=record ? record.getString("guid") : null;
//                        putData(cfg, tenantId, data, value);
//                    } else if (cfg.getImportType() == 2){
//                        saveAssociated(tenantId, guid, value, refCol, cnd);
//
//                    }else {
//                        putData(cfg, tenantId, data, value);
//                    }
//
//                }
//                try {
//                   // Map<String,Object> data = new HashMap<String,Object>();
//                    /*for (Map.Entry entry : data.entrySet()){
//
//
//                    }*/
//                    dao().insert(data);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    // 捕获导入的出错行
//                    errorLine.add(ri + 1);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String result = "";
//
//        if (!errorLine.isEmpty()) {
//            result = "导入过程有错误,行:[" + StrUtils.join(errorLine, ",") + "]不能导入!";
//        }
//
//        return result;
//    }
//
//    private void saveAssociated(int tenantId, String guid, String value, String refCol, Cnd cnd) {
//        //如果是中间表，第一个为关联字段名，第二个为关联表，第三个为中间表，第四，五个为中间表的字段（emp_guid，role_guid）,且关联字段名多个用‘，’隔开
//        String[] cols = refCol.split(":");
//        String colum = cols[0];
//        String[] colums = colum.split(",");
//        String refTable = cols[1];
//        String middleTable = cols[2];
//        String field = cols[3];
//        String refField = cols[4];
//        Sql insertAssociatedSql =
//                Sqls.create("insert into $table ($id,$clientid,$valueid, tenantid) values($id,@clientid,@valueid, @tenantid)");
//        //中间表新增数据
//        for (String c : colums){
//            Record record = dao().fetch(refTable, cnd.and(c, "=", value));
//            /*insertAssociatedSql.setVar("table", middleTable);
//            insertAssociatedSql.setParam()*/
//            if (null != record){
//                String refGuid = record.getString("guid");
//                dao().insert(middleTable, Chain.make("guid", StrUtils.uuid()).add(field, guid).add(refField, refGuid).add("tenant_id", tenantId)
//                        .add("data_is_deleted", 0).add("data_time", new Date()).add("modify_flag", 1).add("modify_time", new Date()));
//            }
//
//        }
//    }
//
//    private void putData(ImpConfig cfg, int tenantId, Map<String,Object> data, String value) {
//        if (StrUtils.isEmpty(cfg.getImpDataType())) {
//            data.put(cfg.getCode(),value);
//        } else if (cfg.getImpDataType().equals("date")) {
//            data.put(cfg.getCode(),DateUtils.convertToDateShort2(value));
//        } else if (cfg.getImpDataType().equals("date2")) {
//            data.put(cfg.getCode(),DateUtils.convertToDateShort3(value));
//        }
//    }
//
//
//}
//
//
