/**
 * 天恒众航（北京）科技股份公司(2015)
 */
package link.smartwall.web.control.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.util.StrUtils;

/**
 * 网格选择组件配置实体
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2016年2月16日 lexloo * 建立
 * </pre>
 */
@Table("cfg_ctl_grid_selector")
public class GridSelectorEntity {
    /**
     * 主键Id
     */
    @Column
    @Id(auto = false)
    @Prev(@SQL("select cfg_ctl_grid_selector_s.nextval from dual"))
    private int id;
    /**
     * 代码
     */
    @Column
    @Name
    private String code;
    /**
     * 网格配置信息
     */
    @Column(value = "conf_content")
    private String confContent;

    /**
     * SQL配置
     */
    @Column(value = "sql_content")
    private String sqlContent;
    /**
     * 授权表
     */
    @Column(value = "auth_table")
    private String authTable;

    /**
     * 过滤字段，以“，”号隔开
     */
    @Column(value = "filter_fields")
    private String filterFields;
    /**
     * 是否树
     */
    @Column(value = "is_tree")
    private boolean tree;
    /**
     * 树数据
     */
    @Column(value = "tree_data")
    private String treeData;
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
     * @return the confContent
     */
    public String getConfContent() {
        return confContent;
    }

    /**
     * @param confContent the confContent to set
     */
    public void setConfContent(String confContent) {
        this.confContent = confContent;
    }

    /**
     * @return the sqlContent
     */
    public String getSqlContent() {
        return sqlContent;
    }

    /**
     * @param sqlContent the sqlContent to set
     */
    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    /**
     * @return the authTable
     */
    public String getAuthTable() {
        return authTable;
    }

    /**
     * @param authTable the authTable to set
     */
    public void setAuthTable(String authTable) {
        this.authTable = authTable;
    }

    /**
     * @return the filterFields
     */
    public String getFilterFields() {
        return filterFields;
    }

    /**
     * @param filterFields the filterFields to set
     */
    public void setFilterFields(String filterFields) {
        this.filterFields = filterFields;
    }

    /**
     * @return the filter field array
     */
    public String[] getFilterFieldArr() {
        if (StrUtils.isEmpty(this.filterFields)) {
            return new String[0];
        } else {
            return this.filterFields.replace(" ", "").split(",");
        }
    }

    /**
     * @return the tree
     */
    public boolean isTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(boolean tree) {
        this.tree = tree;
    }

    /**
     * @return the treeData
     */
    public String getTreeData() {
        return treeData;
    }

    /**
     * @param treeData the treeData to set
     */
    public void setTreeData(String treeData) {
        this.treeData = treeData;
    }
}
