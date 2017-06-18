package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.BaseEntity;

@Table("conf_grid_filter")
public class ConfGridFilter extends BaseEntity {
    @Column("master_guid")
    private String masterGuid;
    @Column("field")
    private String field;
    @Column("caption")
    private String caption;
    @Column("editor")
    private String editor;
    @Column("datasource")
    private String datasource;
    @Column("operator")
    private String operator;
    @Column("show_index")
    private int showIndex;
    @Column
    private String type;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMasterGuid() {
        return masterGuid;
    }

    public void setMasterGuid(String masterGuid) {
        this.masterGuid = masterGuid;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }
}
