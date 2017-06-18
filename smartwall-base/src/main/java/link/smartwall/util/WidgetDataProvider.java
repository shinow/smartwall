package link.smartwall.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 组件数据工具类
 * 
 * @version 1.0
 * @author <a href="ttan_style@sina.cn">ttan</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2015年4月2日  ttan
 * </pre>
 */
public final class WidgetDataProvider {
    /**
     * 构造函数
     */
    private WidgetDataProvider() {
        // Utility class
    }

    /**
     * 组装自动补全数据集
     * 
     * @param querySql 查询sql 必须有id，name
     * @param paramMap 参数
     * @param dao dao
     * @return 组装自动补全数据集
     */
    public static Object autoCompleteFormatData(String querySql, Map<String, Object> paramMap, Dao dao) {
        final JSONArray items = new JSONArray();

        Sql sql = Sqls.create(querySql);
        for (String p : paramMap.keySet()) {
            sql.params().set(p, paramMap.get(p));
        }

        sql.setCallback(new SqlCallback() {
            @Override
            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                while (rs.next()) {
                    JSONObject item = new JSONObject();
                    item.put("id", rs.getInt("id"));
                    // 自动完成框需要label,不指定value时，label和value值一致
                    item.put("label", rs.getString("name"));
                    item.put("value", rs.getString("name"));

                    items.add(item);
                }

                return null;
            }
        });
        dao.execute(sql);

        return items;
    }

}
