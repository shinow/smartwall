/**
 * 天恒众航(2013)
 */
package link.smartwall.basemodel;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.AbstractObject;

/**
 * 模型列
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 * </pre>
 */
@Table("cfg_base_model_dt")
public class ModelColumn extends AbstractObject {
    /**
     * 主键Id
     */
    @Column
    @Id(auto = false)
    @Prev(@SQL("select cfg_base_model_dt_s.nextval from dual"))
    private int id;
    /**
     * 代码
     */
    @Column
    private String code;
    /**
     * 标签
     */
    @Column
    private String caption;
    /**
     * 字段名
     */
    @Column
    private String field;
    /**
     * 属性
     */
    @Column
    private ColumnProperty property = ColumnProperty.NORMAL;
    /**
     * 类型
     */
    @Column
    private ColumnType type = ColumnType.STRING;
    /**
     * 分类
     */
    @Column
    private ColumnCatalog catalog = ColumnCatalog.FIELD;
    /**
     * 对应模型Id
     */
    @Column
    private int masterId;
    /**
     * 是否只读
     */
    @Column("is_readonly")
    private boolean readonly;
    /**
     * 增加默认值
     */
    @Column("def_value")
    private String defValue;
    /**
     * 是否必填
     */
    @Column("is_required")
    private boolean required;
   
    /**
     * @return the required
     */
    public boolean getRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

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
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the property
     */
    public ColumnProperty getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(ColumnProperty property) {
        this.property = property;
    }

    /**
     * @return the type
     */
    public ColumnType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ColumnType type) {
        this.type = type;
    }

    /**
     * @return the catalog
     */
    public ColumnCatalog getCatalog() {
        return catalog;
    }

    /**
     * @param catalog the catalog to set
     */
    public void setCatalog(ColumnCatalog catalog) {
        this.catalog = catalog;
    }

    /**
     * @param masterId the masterId to set
     */
    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    /**
     * @return the masterId
     */
    public int getMasterId() {
        return masterId;
    }

    /**
     * @return the readonly
     */
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * @param readonly the readonly to set
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * @return the defValue
     */
    public String getDefValue() {
        return defValue;
    }

    /**
     * @param defValue the defValue to set
     */
    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }
}
