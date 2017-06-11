/**
 * NextVisual
 */
/**
 * NextVisual
 */
package link.smartwall.grid.controller;

/**
 * 列排序信息
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-9-12 lexloo
 * </pre>
 */
public class ColumnSortInfo {
    /**
     * 无排序
     */
    public static final int SORT_FLAG_NO = 0;
    /**
     * 升序
     */
    public static final int SORT_FLAG_ASC = 1;
    /**
     * 降序
     */
    public static final int SORT_FLAG_DESC = 2;
    /**
     * 列名
     */
    private String name;
    /**
     * 排序标识
     */
    private int flag;

    /**
     * 构造函数
     */
    public ColumnSortInfo() {

    }

    /**
     * 构造函数
     * 
     * @param name 名字
     * @param flag 标识
     */
    public ColumnSortInfo(String name, int flag) {
        this.name = name;
        this.flag = flag;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }
}
