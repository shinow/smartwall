/**
 * SmartWall(2013) 
 */
package link.smartwall.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据集实用类
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-4-1 lexloo
 * </pre>
 */
public final class ResultSetUtils {
    /**
     * 构造函数
     */
    private ResultSetUtils() {
        // 实用类
    }

    /**
     * 获取数据集所有字段,只返回小写名称
     * 
     * @param rs 数据集
     * @return 字段列表
     */
    public static List<String> getFieldList(ResultSet rs) {
        List<String> rtn = new ArrayList<String>();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1, size = rsmd.getColumnCount(); i <= size; i++) {
                rtn.add(rsmd.getColumnName(i).toLowerCase());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rtn;
    }
}
