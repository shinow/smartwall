package link.smartwall.web.controller.grid.filter;

/**
 * Grid过滤器操作符:IN
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
public class InOperator implements IOperator {

    @Override
    public String getOperator() {
        return "in";
    }

    @Override
    public Object wrapperValue(String value, String type) {
        return value;
    }

}
