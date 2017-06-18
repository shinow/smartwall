/**
 * SmartWall(2013)
 */
package link.smartwall.base.http.parameter;

import java.util.HashMap;
import java.util.Map;

import link.smartwall.util.StrUtils;

/**
 * 服务参数
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-2-21 lexloo
 * </pre>
 */
public class Parameters extends HashMap<String, Object> {

    /**
     * UID
     */
    private static final long serialVersionUID = 4032807895629807344L;

    /**
     * 读取Map中的参数一次，读取后删除,下次读取不到数据
     * 
     * @param key 参数键值
     * @return 读取值
     */
    public String readAndRemoveValue(String key) {
        String value = StrUtils.c2str(this.get(key));

        this.remove(key);

        return value;
    }

    /**
     * 获取字符串值
     * 
     * @param key 键值
     * @return 字符串值
     */
    public String getValueAsString(String key) {
        return StrUtils.c2str(this.get(key));
    }

    /**
     * 获取所有参数
     * 
     * @return 所有参数
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getParameters() {
        return (Map<String, Object>) this.clone();
    }

    /**
     * 获取URL附带参数，直接过滤字段，名称为"p_" + 字段名
     * 
     * @return 附带参数
     */
    public Map<String, String> getFieldParameters() {
        Map<String, String> result = new HashMap<String, String>();
        for (String key : this.keySet()) {
            if (key.startsWith("p_")) {
                result.put(key.substring(2), this.getValueAsString(key));
            }
        }

        return result;
    }
}
