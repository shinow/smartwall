package link.smartwall.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式实用类
 *
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 *          <p/>
 *          <pre>
 *                            历史：
 *                            建立: 2017-02-04 lexloo
 *                                     </pre>
 */
public class RegxUtils {
    /**
     * 类似"{code}"字段提取
     */
    private static final Pattern REGX_PARAM = Pattern.compile("\\{(\\S+?)}");

    /**
     * 解析参数
     *
     * @param source 源字符串
     * @return 参数字符串
     */
    public static List<String> extractParams(String source) {
        List<String> fs = new ArrayList<String>();
        Matcher m = REGX_PARAM.matcher(source);
        while (m.find()) {
            fs.add(m.group(1));
        }

        return fs;
    }

    /**
     * 解析字符串表达式
     * <p>
     * 表达式形如：
     * 姓名：{name}, 性别：{sex}, 年龄：{age}
     * 解析后：
     * 姓名：王博, 性别：男, 年龄：18
     * </p>
     *
     * @param expression 表达式
     * @param dataValues {key: value}
     * @return 解析后的字符串
     */
    public static String parseExpression(String expression, Map<String, Object> dataValues) {
        List<String> params = RegxUtils.extractParams(expression);
        String result = expression;
        for (String param : params) {
            Object value = dataValues.get(param);
            if (value instanceof Date) {
                Date date = (Date) value;
                result = result.replaceAll("\\{" + param + "\\}", new SimpleDateFormat("yyyy-MM-dd").format(date));
            } else {
                result = result.replaceAll("\\{" + param + "\\}", value.toString());
            }
        }
        return result;
    }
}
