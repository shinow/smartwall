package link.smartwall.web.controller.grid.filter;

/**
 * Grid过滤器操作符
 * 
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since iTek SFA 5.0
 *        <p/>
 * 
 *        <pre>
 * 历史：
 * 建立: 2017-02-25 lexloo
 *        </pre>
 */
public interface IOperator {
    String EQUAL = "equal";
    String NOT_EQUAL = "notequal";
    String START_WITH = "startwith";
    String END_WITH = "endwith";
    String LIKE = "like";
    String GREATER = "greater";
    String GREATER_OR_EQUAL = "greaterorequal";
    String LESS = "less";
    String LESS_OR_EQUAL = "lessorequal";
    String IN = "in";
    String BETWEEN = "between";

    /**
     * 获取操作符
     * 
     * @return 操作符
     */
    String getOperator();

    /**
     * 数据包装
     * 
     * @param value 值
     * @param type 类型
     * 
     * @return 数据包装
     */
    Object wrapperValue(String value, String type);
}
