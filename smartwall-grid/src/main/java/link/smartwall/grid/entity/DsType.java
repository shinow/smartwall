/**
 * SmartWall(2013) */
package link.smartwall.grid.entity;

import link.smartwall.base.api.IdName;

/**
 * 数据源类型
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 * <pre>
 * 历史：
 *      建立: 2013-1-14 lexloo
 * </pre>
 */
public enum DsType {
    /**
     * SQL配置
     */
    SQL,
    /**
     * SQL配置代码，SQL配置在数据库中
     */
    SQLCODE;
    /**
     * 用于下拉列表填充
     */
    private static IdName[] idNames = {new IdName(SQL, "SQL配置"), new IdName(SQLCODE, "SQL配置代码")};

    /**
     * 
     * @return idNames, 用于下拉列表
     */
    public static IdName[] getIdNames() {
        return idNames;
    }
}
