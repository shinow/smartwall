/**
 * 天恒众航（北京）科技股份公司(2015)
 */
package link.smartwall.grid.controller;

import link.smartwall.grid.entity.ConfGridFilter;

/**
 * 包含下级过滤项
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年3月31日 lexloo * 建立
 *        </pre>
 */
public class IncludeFilterItem {
    /**
     * 包含下级过滤项
     */
    private ConfGridFilter item;
    /**
     * 包含下级过滤值
     */
    private int value;

    /**
     * 构造函数
     * 
     * @param item 项目
     * @param value 值
     */
    public IncludeFilterItem(ConfGridFilter item, int value) {
        this.item = item;
        this.value = value;
    }

    /**
     * 获取SQL
     * 
     * @return sql
     */
    public String getQuerySql() {
        throw new RuntimeException("请修改实现");
        // /* 按 表;Id;parentId */
        // String[] params = item.getSuffixValue().split(";");
        //
        // return "select "
        // + params[1]
        // + " from "
        // + params[0]
        // + " start with "
        // + params[1]
        // + "= "
        // + this.value
        // + " connect by nocycle prior "
        // + params[1]
        // + " = "
        // + params[2]
        // + " and tenantid = @tenantid";
    }

    /**
     * @return the item
     */
    public ConfGridFilter getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(ConfGridFilter item) {
        this.item = item;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
}
