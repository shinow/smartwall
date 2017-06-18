/**
 * 天恒众航（北京）科技有限公司
 */
package link.smartwall.grid.imp;

import link.smartwall.util.StrUtils;

/**
 * 信息导入配置
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-6-11 lexloo
 *          2015-07-17 lexloo 优化注释
 * </pre>
 */
public class ImpConfig {
    /**
     * 代码
     */
    private String code;
    /**
     * 业务模型代码
     */
    private String bizCode;
    /**
     * 标题
     */
    private String caption;
    /**
     * 验证信息,用于Name与Id转换
     */
    private String impValidator;
    /**
     * 验证字段
     */
    private String refCol;
    /**
     * 目的字段,对应模型的字段
     */
    private String toCol;
    /**
     * 是否自动添加到对应表,如果自动添加，则会在表中自动增加记录
     */
    private boolean autoAdd;
    /**
     * 导入数据的类型
     */
    private String impDataType;
    /**
     * 导入时，中间表关联字段
     */
    private String associatedTable;
    /**
     * 关联顺序，clientid,valueid 的顺序
     */
    private int associatedType;

    /**
     * 构造函数
     */
    public ImpConfig() {}

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return the impValidator
     */
    public String getImpValidator() {
        return impValidator;
    }

    /**
     * @param impValidator the impValidator to set
     */
    public void setImpValidator(String impValidator) {
        this.impValidator = impValidator;
    }

    /**
     * @return the refCol
     */
    public String getRefCol() {
        return StrUtils.isEmpty(refCol) ? "name" : refCol;
    }

    /**
     * @param refCol the refCol to set
     */
    public void setRefCol(String refCol) {
        this.refCol = refCol;
    }

    /**
     * @return the autoAdd
     */
    public boolean isAutoAdd() {
        return autoAdd;
    }

    /**
     * @param autoAdd the autoAdd to set
     */
    public void setAutoAdd(boolean autoAdd) {
        this.autoAdd = autoAdd;
    }

    /**
     * @return the toCol
     */
    public String getToCol() {
        return StrUtils.isEmpty(toCol) ? this.getCode() : toCol;
    }

    /**
     * @param toCol the toCol to set
     */
    public void setToCol(String toCol) {
        this.toCol = toCol;
    }

    /**
     * @return the impDataType
     */
    public String getImpDataType() {
        return impDataType;
    }

    /**
     * @param impDataType the impDataType to set
     */
    public void setImpDataType(String impDataType) {
        this.impDataType = impDataType;
    }

    /**
     * @return the bizCode
     */
    public String getBizCode() {
        return StrUtils.isEmpty(bizCode) ? code : bizCode;
    }

    /**
     * @param bizCode the bizCode to set
     */
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    /**
     * @return the associatedTable
     */
    public String getAssociatedTable() {
        return associatedTable;
    }

    /**
     * @param associatedTable the associatedTable to set
     */
    public void setAssociatedTable(String associatedTable) {
        this.associatedTable = associatedTable;
    }

    /**
     * @return the associatedType
     */
    public int getAssociatedType() {
        return associatedType;
    }

    /**
     * @param associatedType the associatedType to set
     */
    public void setAssociatedType(int associatedType) {
        this.associatedType = associatedType;
    }

}
