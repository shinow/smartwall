/**
 * 天恒众航(2013) 
 */
package link.smartwall.basemodel;

import link.smartwall.base.api.IdName;

/**
 * 列分类
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
public enum ColumnCatalog {
    /**
     * 数据字段
     */
    FIELD,
    /**
     * 计算字段
     */
    CALCULATE;

    /**
     * 用于下拉列表填充
     */
    private static IdName[] idNames = {new IdName(FIELD, "数据字段"), new IdName(CALCULATE, "计算字段")};

    /**
     * 
     * @return idNames, 用于下拉列表
     */
    public static IdName[] getIdNames() {
        return idNames;
    }
}
