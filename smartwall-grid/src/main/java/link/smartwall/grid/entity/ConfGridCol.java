package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.itfsm.basemodel.BaseEntity;

@Table("conf_grid_col")
public class ConfGridCol extends BaseEntity {

    /**
     * 默认宽度
     */
    public static final int DEFAULT_WIDTH = 200;
    /**
     * 默认导出
     */
    public static final int EXP_DEFAULT = 0;
    /**
     * 默认不导出
     */
    public static final int EXP_NO_DEFAULT = 1;
    /**
     * 永不导出
     */
    public static final int EXP_NO_ALWAYS = 2;

    /**
     * 当前导入id，存储到valueid中
     */
    public static final int ASSOCIATED_TYPE_VALUEID = 0;
    /**
     * 当前导入id，存储到clientid中
     */
    public static final int ASSOCIATED_TYPE_CLIENTID = 1;

    @Column("master_guid")
    private String masterGuid;
    @Column("code")
    private String code;
    @Column("caption")
    private String caption;
    @Column("align")
    private Align align;
    @Column("type")
    private String type;
    @Column("precesion")
    private int precesion;
    @Column("width")
    private int width;
    @Column("show_index")
    private int showIndex;
    @Column("visible")
    private boolean visible;
    @Column("export_type")
    private int exportType;
    @Column("imptable")
    private boolean imptable;
    @Column("imp_validator")
    private String impValidator;
    @Column("imp_order")
    private Integer impOrder;
    @Column("imp_ref_col")
    private String impRefCol;
    @Column("auto_add")
    private boolean autoAdd;
    @Column("imp_to_col")
    private String impToCol;
    @Column("imp_data_type")
    private String impDataType;
    @Column("value_set_func")
    private String valueSetFunc;
    /**
     * 在过滤面板中显示
     */
    @Column("show_in_filter")
    private boolean showInFilter;
    /**
     * 在超级查询中显示
     */
    @Column("show_in_super_filter")
    private boolean showInSuperFilter;

    /**
     * 过滤数据源，对下拉等有用
     */
    @Column("filter_data_source")
    private String filterDataSource;
    /**
     * 过滤显示时的编辑器
     */
    @Column("filter_editor")
    private String filterEditor;
    /**
     * 过滤显示时的操作符
     */
    @Column("filter_op")
    private String filterOp;

    @Column("import_type")
    private int importType;

    public String getMasterGuid() {
        return masterGuid;
    }

    public void setMasterGuid(String masterGuid) {
        this.masterGuid = masterGuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Align getAlign() {
        return align;
    }

    public void setAlign(Align align) {
        this.align = align;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrecesion() {
        return precesion;
    }

    public void setPrecesion(int precesion) {
        this.precesion = precesion;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getExportType() {
        return exportType;
    }

    public void setExportType(int exportType) {
        this.exportType = exportType;
    }

    public boolean isImptable() {
        return imptable;
    }

    public void setImptable(boolean imptable) {
        this.imptable = imptable;
    }

    public String getImpValidator() {
        return impValidator;
    }

    public void setImpValidator(String impValidator) {
        this.impValidator = impValidator;
    }

    public Integer getImpOrder() {
        return impOrder;
    }

    public void setImpOrder(Integer impOrder) {
        this.impOrder = impOrder;
    }

    public String getImpRefCol() {
        return impRefCol;
    }

    public void setImpRefCol(String impRefCol) {
        this.impRefCol = impRefCol;
    }

    public boolean isAutoAdd() {
        return autoAdd;
    }

    public void setAutoAdd(boolean autoAdd) {
        this.autoAdd = autoAdd;
    }

    public String getImpToCol() {
        return impToCol;
    }

    public void setImpToCol(String impToCol) {
        this.impToCol = impToCol;
    }

    public String getImpDataType() {
        return impDataType;
    }

    public void setImpDataType(String impDataType) {
        this.impDataType = impDataType;
    }

    public String getValueSetFunc() {
        return valueSetFunc;
    }

    public void setValueSetFunc(String valueSetFunc) {
        this.valueSetFunc = valueSetFunc;
    }

    public boolean isShowInFilter() {
        return showInFilter;
    }

    public void setShowInFilter(boolean showInFilter) {
        this.showInFilter = showInFilter;
    }

    public boolean isShowInSuperFilter() {
        return showInSuperFilter;
    }

    public void setShowInSuperFilter(boolean showInSuperFilter) {
        this.showInSuperFilter = showInSuperFilter;
    }

    public String getFilterDataSource() {
        return filterDataSource;
    }

    public void setFilterDataSource(String filterDataSource) {
        this.filterDataSource = filterDataSource;
    }

    public String getFilterEditor() {
        return filterEditor;
    }

    public void setFilterEditor(String filterEditor) {
        this.filterEditor = filterEditor;
    }

    public String getFilterOp() {
        return filterOp;
    }

    public void setFilterOp(String filterOp) {
        this.filterOp = filterOp;
    }

    public int getImportType() {
        return importType;
    }

    public void setImportType(int importType) {
        this.importType = importType;
    }
}
