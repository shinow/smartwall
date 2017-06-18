/**
 * 天恒众航(2013) 
 */
package link.smartwall.basemodel;

import link.smartwall.base.api.IdName;

/**
 * 列属性
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
public enum ColumnProperty {
    /**
     * 普通列
     */
    NORMAL,
    /**
     * 主键类，会映射为主键
     */
    ID,
    /**
     * 标识类，用于名称显示
     */
    CAPTION;

    /**
     * 用于下拉列表填充
     */
    private static IdName[] idNames = {new IdName(NORMAL, "普通字段"), new IdName(ID, "Id字段"), new IdName(CAPTION, "标识字段")};

    /**
     * 
     * @return idNames, 用于下拉列表
     */
    public static IdName[] getIdNames() {
        return idNames;
    }
}
