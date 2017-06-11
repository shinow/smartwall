package link.smartwall.web.controller.grid.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Grid过滤器操作符工厂
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
public class OperatorFactory {
    private static Map<String, IOperator> operators = new HashMap<String, IOperator>();

    static {
        operators.put(IOperator.EQUAL, new EqualOperator());
        operators.put(IOperator.NOT_EQUAL, new NotEqualOperator());
        operators.put(IOperator.START_WITH, new StartWithOperator());
        operators.put(IOperator.END_WITH, new EndWithOperator());
        operators.put(IOperator.LIKE, new LikeOperator());
        operators.put(IOperator.GREATER, new GreaterOperator());
        operators.put(IOperator.GREATER_OR_EQUAL, new GreaterOrEqualOperator());
        operators.put(IOperator.LESS, new LessOperator());
        operators.put(IOperator.LESS_OR_EQUAL, new LessOrEqualOperator());
        operators.put(IOperator.IN, new InOperator());
        operators.put(IOperator.BETWEEN, new BetweenOperator());
    }

    /**
     * 通过代码获取操作符
     * 
     * @param code 代码
     * @return 操作符
     */
    public static IOperator getOperator(String code) {
        return operators.get(code);
    }
}
