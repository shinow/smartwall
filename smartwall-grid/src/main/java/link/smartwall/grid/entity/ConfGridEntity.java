package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.itfsm.basemodel.BaseEntity;

@Table("conf_grid")
public class ConfGridEntity extends BaseEntity {
    @Column("caption")
    private String caption;
    @Column("catalog")
    private String catalog;
    @Column("remark")
    private String remark;
    @Column("ds_type")
    private String dsType;
    @Column("ds_content")
    private String dsContent;
    @Column("show_filter")
    private boolean showFilter;

    /**
     * 所有输出字段
     */
    @Column("fields")
    protected String fields;
    @Column("sql_order_by")
    private String sqlOrderBy;
    @Column("rights_control")
    private boolean rightsControl;
    @Column("dept_control")
    private boolean deptControl;
    @Column("selectable")
    private boolean selectable;
    @Column("parameters")
    protected String parameters;
    @Column("vars")
    protected String vars;
    @Column("use_js_module")
    private String useJsModule;
    @Column("imp_template")
    private String impTemplate;
    @Column("exp_filename")
    private String expFileName;
    @Column("exp_col_lock")
    private String expColLock;
    @Column("exp_func")
    private String expFunc;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCatalog() {
        return catalog;
    }

    public boolean isShowFilter() {
        return showFilter;
    }

    public void setShowFilter(boolean showFilter) {
        this.showFilter = showFilter;
    }

    public boolean isDeptControl() {
        return deptControl;
    }

    public void setDeptControl(boolean deptControl) {
        this.deptControl = deptControl;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public String getDsContent() {
        return dsContent;
    }

    public void setDsContent(String dsContent) {
        this.dsContent = dsContent;
    }

    public String getSqlOrderBy() {
        return sqlOrderBy;
    }

    public void setSqlOrderBy(String sqlOrderBy) {
        this.sqlOrderBy = sqlOrderBy;
    }

    public boolean isRightsControl() {
        return rightsControl;
    }

    public void setRightsControl(boolean rightsControl) {
        this.rightsControl = rightsControl;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getVars() {
        return vars;
    }

    public void setVars(String vars) {
        this.vars = vars;
    }

    public String getUseJsModule() {
        return useJsModule;
    }

    public void setUseJsModule(String useJsModule) {
        this.useJsModule = useJsModule;
    }

    public String getImpTemplate() {
        return impTemplate;
    }

    public void setImpTemplate(String impTemplate) {
        this.impTemplate = impTemplate;
    }

    public String getExpFileName() {
        return expFileName;
    }

    public void setExpFileName(String expFileName) {
        this.expFileName = expFileName;
    }

    public String getExpColLock() {
        return expColLock;
    }

    public void setExpColLock(String expColLock) {
        this.expColLock = expColLock;
    }

    public String getExpFunc() {
        return expFunc;
    }

    public void setExpFunc(String expFunc) {
        this.expFunc = expFunc;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
