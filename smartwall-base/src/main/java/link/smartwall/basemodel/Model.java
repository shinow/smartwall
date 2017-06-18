/**
 * SmartWall(2013)
 */
package link.smartwall.basemodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.AbstractObject;

/**
 * 基础模型
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 * </pre>
 */
@Table("cfg_base_model")
public class Model extends AbstractObject {
    /**
     * 主键Id
     */
    @Column
    @Id(auto = false)
    @Prev(@SQL("select cfg_base_model_s.nextval from dual"))
    private int id;
    /**
     * 代码
     */
    @Column
    @Name
    private String code;
    /**
     * 备注
     */
    @Column
    private String remark;
    /**
     * 表名
     */
    @Column
    private String tableName;
    /**
     * 对应的视图
     */
    @Column
    private String viewName;
    /**
     * 是否使用已存在类
     */
    @Column
    private boolean useExistObj;
    /**
     * 已存在对象类名
     */
    @Column
    private String objClass;
    /**
     * 分类
     */
    @Column
    private String catalog;
    /**
     * 创建者
     */
    @Column
    private String creator;
    /**
     * 创建时间
     */
    @Column
    private Date createDate = new Date();
    /**
     * 更新者
     */
    @Column
    private String updator;
    /**
     * 更新时间
     */
    @Column
    private Date updateDate = new Date();

    /**
     * 是否版本控制
     */
    @Column
    private boolean versionCheck;

    /**
     * 版本控制类型
     */
    @Column
    private String versionType;

    /**
     * 版本key
     */
    @Column
    private String versionKey;

    /**
     * beforeupdate
     */
    @Column
    private String beforeUpdate;

    /**
     * afterupdate
     */
    @Column
    private String afterUpdate;

    /**
     * beforeinsert
     */
    @Column
    private String beforeInsert;

    /**
     * afterinsert
     */
    @Column
    private String afterInsert;

    /**
     * beforedelete
     */
    @Column
    private String beforeDelete;

    /**
     * afterdelete
     */
    @Column
    private String afterDelete;

    /**
     * 所有列
     */
    @Many(target = ModelColumn.class, field = "masterId")
    private List<ModelColumn> columns = new ArrayList<ModelColumn>();

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
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param useExistObj the useExistObj to set
     */
    public void setUseExistObj(boolean useExistObj) {
        this.useExistObj = useExistObj;
    }

    /**
     * @return the useExistObj
     */
    public boolean isUseExistObj() {
        return useExistObj;
    }

    /**
     * @param objClass the objClass to set
     */
    public void setObjClass(String objClass) {
        this.objClass = objClass;
    }

    /**
     * @return the objClass
     */
    public String getObjClass() {
        return objClass;
    }

    /**
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updator
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * @param updator the updator to set
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }

    /**
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return 所有列
     */
    public List<ModelColumn> getColumns() {
        return this.columns;
    }

    /**
     * 清空列表
     */
    public void clearColumns() {
        this.columns.clear();
    }

    /**
     * 添加列
     * 
     * @param column 需要添加的列
     */
    public void addColumn(ModelColumn column) {
        if (this.columns == null) {
            this.columns = new ArrayList<ModelColumn>();
        }

        this.columns.add(column);
    }

    /**
     * @param catalog the catalog to set
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    /**
     * @return the catalog
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * @return the viewName
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * @param viewName the viewName to set
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    /**
     * @return the versionCheck
     */
    public boolean isVersionCheck() {
        return versionCheck;
    }

    /**
     * @param versionCheck the versionCheck to set
     */
    public void setVersionCheck(boolean versionCheck) {
        this.versionCheck = versionCheck;
    }

    /**
     * @return the versionType
     */
    public String getVersionType() {
        return versionType;
    }

    /**
     * @param versionType the versionType to set
     */
    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    /**
     * @return the versionKey
     */
    public String getVersionKey() {
        return versionKey;
    }

    /**
     * @param versionKey the versionKey to set
     */
    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    /**
     * @return the beforeUpdate
     */
    public String getBeforeUpdate() {
        return beforeUpdate;
    }

    /**
     * @param beforeUpdate the beforeUpdate to set
     */
    public void setBeforeUpdate(String beforeUpdate) {
        this.beforeUpdate = beforeUpdate;
    }

    /**
     * @return the afterUpdate
     */
    public String getAfterUpdate() {
        return afterUpdate;
    }

    /**
     * @param afterUpdate the afterUpdate to set
     */
    public void setAfterUpdate(String afterUpdate) {
        this.afterUpdate = afterUpdate;
    }

    /**
     * @return the beforeInsert
     */
    public String getBeforeInsert() {
        return beforeInsert;
    }

    /**
     * @param beforeInsert the beforeInsert to set
     */
    public void setBeforeInsert(String beforeInsert) {
        this.beforeInsert = beforeInsert;
    }

    /**
     * @return the afterInsert
     */
    public String getAfterInsert() {
        return afterInsert;
    }

    /**
     * @param afterInsert the afterInsert to set
     */
    public void setAfterInsert(String afterInsert) {
        this.afterInsert = afterInsert;
    }

    /**
     * @return the beforeDelete
     */
    public String getBeforeDelete() {
        return beforeDelete;
    }

    /**
     * @param beforeDelete the beforeDelete to set
     */
    public void setBeforeDelete(String beforeDelete) {
        this.beforeDelete = beforeDelete;
    }

    /**
     * @return the afterDelete
     */
    public String getAfterDelete() {
        return afterDelete;
    }

    /**
     * @param afterDelete the afterDelete to set
     */
    public void setAfterDelete(String afterDelete) {
        this.afterDelete = afterDelete;
    }
}
