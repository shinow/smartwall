package link.smartwall.grid.entity;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Table;

@Table("conf_grid")
public class ConfGrid extends ConfGridEntity {
    /**
     * 所有列
     */
    private List<ConfGridCol> columns;
    /**
     * 所有Action
     */
    private List<ConfGridAction> actions;
    /**
     * 所有filters
     */
    private List<ConfGridFilter> filters;

    public List<ConfGridCol> getColumns() {
        return columns;
    }

    public void setColumns(List<ConfGridCol> columns) {
        this.columns = columns;
    }

    public List<ConfGridAction> getActions() {
        return actions;
    }

    public void setActions(List<ConfGridAction> actions) {
        this.actions = actions;
    }

    public List<ConfGridFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<ConfGridFilter> filters) {
        this.filters = filters;
    }

    public String[] getParameterArray() {
        if (this.parameters == null) {
            return new String[0];
        } else {
            return this.parameters.trim().split(",");
        }
    }

    public String[] getVarArray() {
        if (this.vars == null) {
            return new String[0];
        } else {
            return this.vars.trim().split(",");
        }
    }

    /**
     * 获取配置的字段列表
     * 
     * @return 字段列表
     */
    public String[] getFieldArr() {
        return this.fields.split(",");
    }

    /**
     * 更新列
     * 
     * @param dao dao
     */
    public void updateColumns(Dao dao) {
        this.columns = dao.query(ConfGridCol.class, Cnd.where("master_guid", "=", this.getGuid()).and("data_is_deleted", "=", 0).asc("showIndex"));
    }

    /**
     * 更新操作
     * 
     * @param dao dao
     */
    public void updateActions(Dao dao) {
        this.actions = dao.query(ConfGridAction.class, Cnd.where("master_guid", "=", this.getGuid()).and("data_is_deleted", "=", 0).asc("showIndex"));
    }

    /**
     * 更新过滤
     * 
     * @param dao dao
     */
    public void updateFilters(Dao dao) {
        this.filters = dao.query(ConfGridFilter.class, Cnd.where("master_guid", "=", this.getGuid()).and("data_is_deleted", "=", 0).asc("showIndex"));
    }
}
