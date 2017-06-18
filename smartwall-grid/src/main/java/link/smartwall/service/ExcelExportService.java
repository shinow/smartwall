package link.smartwall.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.loader.annotation.IocBean;

import link.smartwall.base.http.UserManager;
import link.smartwall.base.http.WebUser;
import link.smartwall.grid.entity.Align;
import link.smartwall.grid.entity.ConfGrid;
import link.smartwall.grid.entity.ConfGridCol;
import link.smartwall.service.BaseService;
import link.smartwall.util.NumberUtils;
import link.smartwall.util.StrUtils;

/**
 * 网格导出service
 * 
 * @version 1.0
 * @author <a herf="xiaojiaxingbj@163.com">xiaojiaxing</a>
 * @since 外勤管家3.0
 * 
 *        <pre>
 * 历史：
 *      建立: Oct 15, 2014 JiaXing
 *        </pre>
 */
@IocBean(args = {"refer:dao"})
public class ExcelExportService extends BaseService {
    public ExcelExportService(Dao dao) {
        super(dao);
        // TODO Auto-generated constructor stub
    }

    /**
     * 字母总数
     */
    private static final int LETTER_NUM = 26;
    /**
     * 字母表
     */
    private static final char[] DIGITS = {'A',
                                          'B',
                                          'C',
                                          'D',
                                          'E',
                                          'F',
                                          'G',
                                          'H',
                                          'I',
                                          'J',
                                          'K',
                                          'L',
                                          'M',
                                          'N',
                                          'O',
                                          'P',
                                          'Q',
                                          'R',
                                          'S',
                                          'T',
                                          'U',
                                          'V',
                                          'W',
                                          'X',
                                          'Y',
                                          'Z'};

    /**
     * 标题样式
     */
    private CellStyle formatHead;
    /**
     * 靠左样式
     */
    private CellStyle formatLeft;
    /**
     * 居中
     */
    private CellStyle formatCenter;
    /**
     * 靠右样式
     */
    private CellStyle formatRight;

    /**
     * 确保样式已经创建
     * 
     * @param wb wb
     */
    private void ensureCellStyles(Workbook wb) {
        DataFormat format = wb.createDataFormat();
        short fromatCode = format.getFormat("@");

        formatHead = wb.createCellStyle();
        formatHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        formatHead.setFillForegroundColor(new HSSFColor.LIGHT_ORANGE().getIndex());
        formatHead.setFillPattern(CellStyle.SOLID_FOREGROUND);
        setStyle(formatHead, fromatCode);

        formatLeft = wb.createCellStyle();
        formatLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        setStyle(formatLeft, fromatCode);

        formatCenter = wb.createCellStyle();
        formatCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        setStyle(formatCenter, fromatCode);

        formatRight = wb.createCellStyle();
        formatRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        setStyle(formatRight, fromatCode);
    }

    /**
     * 获取单元格样式
     * 
     * @param align 对齐方式
     * @return 单元格样式
     */
    private CellStyle getCellStyle(Align align) {
        switch (align) {
        case LEFT:
            return formatLeft;
        case CENTER:
            return formatCenter;
        default:
            return formatRight;
        }
    }

    /**
     * 设置上下左右边框
     * 
     * @param style 单元格样式
     * @param fromatCode formatCode
     */
    private void setStyle(CellStyle style, short fromatCode) {
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style.setDataFormat(fromatCode);

        style.setWrapText(true);
    }

    /**
     * 导出表格到Excel
     * 
     * @param cnd 条件
     * @param model 模型
     * @param values 输出字段
     * @param params 请求参数
     * @param resp 数据输出流
     */
    public void expXls(Cnd cnd,
                       final ConfGrid model,
                       String values,
                       Map<String, Object> params,
                       HttpServletResponse resp) {
        // try {
        // 解析所有的输出字段
        String[] fieldArr = values.split(";");
        final List<String> fieldList = Arrays.asList(fieldArr);

        // 解析SQL
        String sqlStr = model.getDsContent();
        if (model.isDeptControl()) {
            // TODO 需要增加企业参数
            sqlStr = "select t1.* from ("
                     + sqlStr
                     + ") t1 inner join v_role_dept_emp t2 "
                     + "on t1.deptid = t2.deptid and t2.userid = @userid";
        }

        Sql sql = Sqls.create("select * from ( " + sqlStr + ") $condition");
        sql.setCondition(cnd);

        String paramsStr = model.getParameters();
        if (!StrUtils.isEmpty(paramsStr)) {
            String[] paramArr = paramsStr.split(",");
            for (String param : paramArr) {
                sql.params().set(param, params.get(param));
            }
        }

        // Excel 输出相关
        final Workbook wb = new SXSSFWorkbook();
        ensureCellStyles(wb);
        final Sheet sheet = wb.createSheet(model.getCaption());
        if (StrUtils.isEmpty(model.getExpColLock())) {
            sheet.createFreezePane(0, 1);
        } else {
            String[] expParam = model.getExpColLock().split(",");
            sheet.createFreezePane(NumberUtils.c2int(expParam[0]), NumberUtils.c2int(expParam[1]));
        }
        sheet.setDisplayGridlines(false);
        expXlsHead(fieldList, model, sheet);

        WebUser webUser = UserManager.getCurrentUser();
        /* 是否导出清晰图 */
        final String useThumbnail = "false";
        // 循环输出到Excel
        SqlCallback scb = new SqlCallback() {
            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                try {
                    int rownum = 1;
                    Row row = null;
                    // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
                    Drawing patriarch = sheet.createDrawingPatriarch();
                    while (rs.next()) {
                        row = sheet.createRow(rownum);
                        row.setHeightInPoints((short) 20);
                        int col = 0;
                        for (ConfGridCol c : model.getColumns()) {
                            if (fieldList.contains(c.getCode())) {
                                switch (c.getType()) {
                                case "STRING":
                                    Cell cell = row.createCell(col);
                                    cell.setCellValue(rs.getString(c.getCode()));
                                    cell.setCellStyle(getCellStyle(c.getAlign()));
                                    break;
                                case "NUMBER":
                                    createIntCell(row, c, rs, col);
                                    break;
                                case "IMAGE":
                                    row.setHeightInPoints(120);
                                    sheet.setColumnWidth(col, 5000);
                                    String values = rs.getString(c.getCode());
                                    if (StrUtils.isEmpty(values)) {
                                        Cell imgCell = row.createCell(col);
                                        imgCell.setCellStyle(getCellStyle(c.getAlign()));
                                        imgCell.setCellValue("");
                                    } else {
                                        if (useThumbnail.equals("true")) {
                                            fillCell(wb, patriarch, row, col, values, "", getCellStyle(c.getAlign()));
                                        } else {
                                            fillCell(wb,
                                                     patriarch,
                                                     row,
                                                     col,
                                                     values,
                                                     "_thumbnail",
                                                     getCellStyle(c.getAlign()));
                                        }
                                    }
                                    break;
                                default:
                                    Cell defaultCell = row.createCell(col);
                                    defaultCell.setCellStyle(getCellStyle(c.getAlign()));
                                    defaultCell.setCellValue(rs.getString(c.getCode()));
                                }
                                col++;
                            }
                        }
                        rownum++;
                    }

                    String endChar = convertIntToABC(sheet.getRow(0).getPhysicalNumberOfCells() - 1);
                    String rangeAddress = "A1:" + endChar + "1";
                    sheet.setAutoFilter(CellRangeAddress.valueOf(rangeAddress));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        sql.setCallback(scb);
        dao().execute(sql);

        try {
            wb.write(resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换整数到字母代码 1-A,2-B
     * 
     * @param index 所有
     * @return 转换后字符串
     */
    public static String convertIntToABC(int index) {
        int pos = index;
        StringBuilder sb = new StringBuilder();

        if (pos == 0) {
            return "";
        }

        while (pos != 0) {
            int inPos = pos % LETTER_NUM;
            if (inPos == 0) {
                inPos = LETTER_NUM;
            }

            sb.insert(0, DIGITS[inPos - 1]);

            pos = (pos - inPos) / LETTER_NUM;
        }

        return sb.toString();
    }

    /**
     * 创建整型单元格
     * 
     * @param row row
     * @param c c
     * @param rs rs
     * @param col col
     * @throws SQLException 异常
     */
    private void createIntCell(Row row, ConfGridCol c, ResultSet rs, int col) throws SQLException {
        String fv = rs.getString(c.getCode());
        if (fv != null) {
            Cell intCell = row.createCell(col);
            intCell.setCellStyle(getCellStyle(c.getAlign()));

            double value = NumberUtils.c2double(fv, 0);
            if (c.getPrecesion() > 0) {
                intCell.setCellValue(NumberUtils.formatValue(value, c.getPrecesion()));
            } else {
                intCell.setCellValue(value);
            }
        }
    }

    /**
     * 导出标题
     * 
     * @param fieldList 字段列表
     * @param model 模型
     * @param sheet sheet
     */
    private void expXlsHead(final List<String> fieldList, final ConfGrid model, final Sheet sheet) {
        Row row = sheet.createRow(0);
        int i = 0;
        for (ConfGridCol col : model.getColumns()) {
            if (fieldList.contains(col.getCode())) {
                Cell cell = row.createCell(i);
                cell.setCellValue(col.getCaption());
                cell.setCellStyle(formatHead);
                row.setHeightInPoints((short) 20);
                if (col.getWidth() == 0) {
                    sheet.setColumnWidth(i, 4000);
                } else {
                    sheet.setColumnWidth(i, col.getWidth() * 40);
                }
                i++;
            }
        }
    }

    /**
     * 填充单元格
     * 
     * @param wb wb
     * @param patriarch patriarch
     * @param row 行
     * @param col 列
     * @param values 值
     * @param thumbnail 是否清晰原图后缀
     * @param style style
     * @throws IOException io异常
     */
    private void fillCell(Workbook wb,
                          Drawing patriarch,
                          Row row,
                          int col,
                          String values,
                          String thumbnail,
                          CellStyle style)
            throws IOException {
        CreationHelper helper = wb.getCreationHelper();
        ClientAnchor clientAnchor = helper.createClientAnchor();

        // TODO 图片输出有问题
        // if (Global.IS_DISTRIBUTED) {
        // byte[] data = this.getRemoteCachedThumbnailFileData(values, thumbnail);
        //
        // if (data != null && data.length > 0) {
        // int pictureIndex = wb.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG);
        //
        // // 设置图片大小，位置
        // clientAnchor = new XSSFClientAnchor(0,
        // 0,
        // 0,
        // 0,
        // (short) col,
        // row.getRowNum(),
        // (short) ((short) col + 1),
        // row.getRowNum() + 1);
        // clientAnchor.setAnchorType(1);
        //
        // patriarch.createPicture(clientAnchor, pictureIndex);
        // }
        // row.createCell(col).setCellStyle(style);
        // } else {
        // File img = this.createCachedThumbnailFile(values, thumbnail);
        // if (img.exists()) {
        // int pictureIndex =
        // wb.addPicture(IOUtils.toByteArray(new FileInputStream(img)), HSSFWorkbook.PICTURE_TYPE_JPEG);
        //
        // // 设置图片大小，位置
        // clientAnchor = new XSSFClientAnchor(0,
        // 0,
        // 0,
        // 0,
        // (short) col,
        // row.getRowNum(),
        // (short) ((short) col + 1),
        // row.getRowNum() + 1);
        // clientAnchor.setAnchorType(1);
        //
        // patriarch.createPicture(clientAnchor, pictureIndex);
        // }
        row.createCell(col).setCellStyle(style);
    }

    // /**
    // * 创建输出缩略图文件名
    // *
    // * @param values 缩略图id列表，逗号隔开
    // * @param thumbnail 是否清晰原图后缀
    // * @return 缩略图文件名
    // */
    // private File createCachedThumbnailFile(String values, String thumbnail) {
    // String[] ids = values.split(",");
    // String imgName = StrUtils.join(ids, "_");
    // if (imgName.length() > 200) {
    // imgName = imgName.substring(200);
    // }
    //
    // String cacheThumbnailFile = ImageUtils.IMAGE_CACHE_DIR + imgName + thumbnail + ".jpg";
    // File cachedF = new File(cacheThumbnailFile);
    //
    // if (!cachedF.exists()) {
    // // 如果不存在，则是不包括缩略文件，创建
    // List<File> imgFiles = new ArrayList<File>();
    // for (String id : ids) {
    // String ctf = ImageUtils.IMAGE_CACHE_DIR + id + thumbnail + ".jpg";
    // File f = new File(ctf);
    // if (f.exists()) {
    // imgFiles.add(f);
    // }
    // }
    //
    // if (!imgFiles.isEmpty()) {
    // ImageUtils.convert(cacheThumbnailFile, imgFiles);
    //
    // cachedF = new File(cacheThumbnailFile);
    // }
    // }
    //
    // return cachedF;
    // }

    // /**
    // * 从远程服务器获取缩略图数据
    // *
    // * @param values 信息
    // * @param thumbnail 缩略图后缀
    // * @return 图片字节信息
    // */
    // private byte[] getRemoteCachedThumbnailFileData(String values, String thumbnail) {
    // String dataURL = Global.FILE_SERVER_URL + "/image/joint?values=" + values + "&thumbnail=" + thumbnail;
    //
    // return HttpUtils.httpGetAsByteArray(dataURL);
    // }
}
