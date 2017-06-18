package link.smartwall.grid.imp;
///**
// * SmartWall(2013)
// */
//package com.itfsm.grid.imp;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.nutz.dao.Dao;
//import org.nutz.dao.Sqls;
//import org.nutz.dao.sql.Sql;
//import org.nutz.dao.sql.SqlCallback;
//
//import com.alibaba.fastjson.JSONArray;
//import com.dcits.xsb.db.DbUtils;
//import com.itfsm.base.http.UserManager;
//import com.itfsm.base.util.StrUtils;
//import com.itfsm.config.tenant.individuation.TenantIndividuationParameterManager;
//import com.itfsm.grid.GridHelper;
//import com.itfsm.grid.entity.GridModel;
//import com.itfsm.util.NumberUtils;
//
///**
// * 读入Excel到Table HTML
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since  2.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2013-4-11 lexloo
// * </pre>
// */
//public class ExcelToTable {
//    /**
//     * 导入的列，需要根据企业自定义来
//     */
//    private static final String CFG_SQL =
//            "select t1.code, t1.biz_code, t1.caption, t1.impvalidator, t1.imprefcol, t1.imptocol, t1.autoadd,t1.impdatatype,"
//                    + " nvl2(t3.imporder,t3.imporder,t1.imporder) imporder,"
//                    + " nvl2(t3.imptable,t3.imptable,t1.imptable) imptable,"
//                    + " t1.associated_table,t1.associated_type "
//                    + " from cfg_grid_cols t1 "
//                    + " join  cfg_grid t2 on t1.masterid = t2.id "
//                    + " left join cfg_grid_cols_c t3 on t3.masterid = t1.id  and t3.tenantid=@tenantid"
//                    + " where  (t1.imptable = 1 or t3.imptable = 1 ) and t2.code = @model ORDER BY imporder";
//
//    /**
//     * 导入前显示，最多显示10行
//     */
//    private static final int MAX_SHOW_ROWS = 10;
//    /**
//     * 列总数
//     */
//    private int columnNum;
//    /**
//     * 对应的网格code
//     */
//    private String model;
//    /**
//     * 租户Id
//     */
//    private int tenantId;
//    /**
//     * 配置信息
//     */
//    private List<ImpConfig> configs;
//
//    /**
//     * 构造函数
//     * 
//     * @param model 模型信息
//     * @param tenantId 租户信息
//     */
//    public ExcelToTable(String model, int tenantId) {
//        this.model = model;
//        this.tenantId = tenantId;
//    }
//
//    /**
//     * 输出Excel到HTML格式字符串
//     * 
//     * @param fileName Excel文件
//     * @return HTML 格式字符串
//     */
//    public String transToTable(File fileName) {
//        try (FileInputStream fis = new FileInputStream(fileName)) {
//            Workbook workBook = WorkbookFactory.create(fis);
//            Sheet sheet = workBook.getSheetAt(0);
//
//            this.columnNum = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();
//            StringBuilder sb = new StringBuilder("<table>");
//            sb.append("<thead>");
//            sb.append(getSelect());
//            sb.append("</thead>");
//            sb.append("<tbody>");
//
//            for (int ri = sheet.getFirstRowNum(), rsize = Math.min(sheet.getLastRowNum(), MAX_SHOW_ROWS); ri <= rsize; ri++) {
//                Row row = sheet.getRow(ri);
//                Cell cell = row.getCell(0);
//                if (StrUtils.isEmpty(getStringCellValue(cell))) {
//                    // 第一列不能为空，否则视为无效数据
//                    continue;
//                }
//
//                sb.append("<tr>");
//                for (int ci = row.getFirstCellNum(), csize = this.columnNum; ci < csize; ci++) {
//                    Cell cc = row.getCell(ci);
//                    sb.append("<td>").append(getStringCellValue(cc)).append("</td>");
//                }
//                sb.append("</tr>");
//            }
//
//            sb.append("</tbody></table>");
//            return sb.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "数据读取失败";
//        }
//    }
//
//    /**
//     * 格式化器
//     */
//    public static final DecimalFormat DDF = new DecimalFormat("0");
//
//    /**
//     * 获取单元格数据内容为字符串类型的数据
//     * 
//     * @param cell Excel单元格
//     * @return String 单元格数据内容
//     */
//    public static String getStringCellValue(Cell cell) {
//        if (cell == null) {
//            return "";
//        }
//
//        String strCell = "";
//        switch (cell.getCellType()) {
//        case Cell.CELL_TYPE_STRING:
//            strCell = cell.getStringCellValue();
//            break;
//        case Cell.CELL_TYPE_NUMERIC:
//            strCell = DDF.format(cell.getNumericCellValue());
//            break;
//        case Cell.CELL_TYPE_BOOLEAN:
//            strCell = String.valueOf(cell.getBooleanCellValue());
//            break;
//        case Cell.CELL_TYPE_BLANK:
//            strCell = "";
//            break;
//        default:
//            strCell = "";
//            break;
//        }
//        if (strCell == null || strCell.equals("")) {
//            return "";
//        }
//
//        return strCell;
//    }
//
//    /**
//     * 生成表格 标题
//     * 
//     * @return 列信息
//     */
//    public Object getCol() {
//        List<ImpConfig> cfgs = this.getConfigs();
//        JSONArray fields = new JSONArray();
//        for (ImpConfig c : cfgs) {
//            fields.add(c.getCode());
//        }
//
//        return fields;
//    }
//
//    /**
//     * 生成表格
//     * 
//     * @return 选择信息
//     */
//    private String getSelect() {
//        List<ImpConfig> cfgs = this.getConfigs();
//        StringBuilder rtn = new StringBuilder();
//        StringBuffer select = new StringBuffer("<select><option></option>");
//        for (ImpConfig c : cfgs) {
//            select.append("<option value=\"")
//                  .append(c.getCode())
//                  .append("\">")
//                  .append(c.getCaption())
//                  .append("</option>");
//        }
//        select.append("</select>");
//
//        for (int i = 0; i < this.columnNum; i++) {
//            rtn.append("<th>").append(select).append("</th>");
//        }
//
//        return rtn.toString();
//    }
//
//    /**
//     * @return the configs
//     */
//    public List<ImpConfig> getConfigs() {
//        final TenantIndividuationParameterManager pm = TenantIndividuationParameterManager.INSTANCE;
//
//        if (this.configs == null) {
//            Sql sql = Sqls.create(CFG_SQL);
//            sql.params().set("model", model);
//            sql.params().set("tenantid", tenantId);
//            sql.setCallback(new SqlCallback() {
//                public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
//                    ExcelToTable.this.configs = new ArrayList<ImpConfig>();
//                    while (rs.next()) {
//                        // 企业网格自定义，控制其是否导入
//                        if (rs.getInt("imptable") == 1) {
//                            ImpConfig cfg = new ImpConfig();
//                            cfg.setCode(StrUtils.trim(rs.getString("code")));
//                            cfg.setBizCode(StrUtils.trim(rs.getString("biz_Code")));
//                            cfg.setCaption(StrUtils.trim(pm.fillStringParams(rs.getString("caption"), tenantId)));
//                            cfg.setImpValidator(StrUtils.trim(rs.getString("impvalidator")));
//                            cfg.setRefCol(StrUtils.trim(rs.getString("imprefcol")));
//                            cfg.setToCol(StrUtils.trim(rs.getString("imptocol")));
//                            cfg.setAutoAdd(rs.getBoolean("autoadd"));
//                            cfg.setImpDataType(StrUtils.trim(rs.getString("impdatatype")));
//                            cfg.setAssociatedTable(StrUtils.trim(rs.getString("associated_table")));
//                            cfg.setAssociatedType(NumberUtils.c2int(rs.getString("associated_type"), -1));
//
//                            ExcelToTable.this.configs.add(cfg);
//                        }
//                    }
//
//                    return null;
//                }
//            });
//            Dao dao = DbUtils.newDao();
//            dao.execute(sql);
//        }
//
//        return this.configs;
//    }
//
//    /**
//     * 获取网格所关联的业务模型 ;修改导入，仅当网格配置的model与业务模型model一致时，才能正确导入
//     * 
//     * @param model model
//     * @return 网格导入关联的业务模型
//     */
//    public String getGridBizModel(String model) {
//        GridModel gridModel = GridHelper.getModel(model, UserManager.getCurrentUser().getTenantId());
//
//        if (StrUtils.isEmpty(gridModel.getBizModel())) {
//            return model;
//        } else {
//            return gridModel.getBizModel();
//        }
//    }
//}
