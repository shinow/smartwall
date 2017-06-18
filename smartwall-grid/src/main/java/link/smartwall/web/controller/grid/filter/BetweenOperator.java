package link.smartwall.web.controller.grid.filter;

import java.util.Date;

import link.smartwall.util.DateUtils;

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
public class BetweenOperator implements IOperator {

    @Override
    public String getOperator() {
        return "between";
    }

    @Override
    public Object wrapperValue(String value, String type) {
        return value;
    }

    public Object wrapperStartValue(String value, String type) {
        if ("DATE".equals(type)) {
            if (value == null) {
                return new Date();
            }

            return DateUtils.convertToDateShort2(value);
        } else if ("DATETIME".equals(type)) {
            if (value == null) {
                return new Date();
            }

            return DateUtils.converToDateTime(value);
        }

        return value;
    }

    public Object wrapperEndValue(String value, String type) {
        if ("DATE".equals(type)) {
            if (value == null) {
                return new Date();
            }

            return DateUtils.converToDateTime(value + " 23:59:59");
        } else if ("DATETIME".equals(type)) {
            if (value == null) {
                return new Date();
            }

            return DateUtils.converToDateTime(value);
        }

        return value;
    }
}
