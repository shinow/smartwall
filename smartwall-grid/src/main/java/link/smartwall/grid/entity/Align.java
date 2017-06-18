/**
 * SmartWall(2013) */
package link.smartwall.grid.entity;

import link.smartwall.base.api.IdName;

/**
 * 对齐
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 * <pre>
 * 历史：
 *      建立: 2013-1-11 lexloo
 * </pre>
 */
public enum Align {
    /**
     * 靠左
     */
    LEFT,
    /**
     * 居中
     */
    CENTER,
    /**
     * 靠右
     */
    RIGHT;

    /**
     * 用于下拉列表填充
     */
    private static IdName[] idNames = {new IdName(LEFT, "靠左"), new IdName(CENTER, "居中"), new IdName(RIGHT, "靠右")};

    /**
     * 
     * @return idNames, 用于下拉列表
     */
    public static IdName[] getIdNames() {
        return idNames;
    }
}
