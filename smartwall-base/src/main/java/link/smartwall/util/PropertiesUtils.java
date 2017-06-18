package link.smartwall.util;

import java.util.Properties;

/**
 * @author <a herf="wangbo@jccatech.com">wangbo</a>
 * @version 1.0
 * @since 14-6-27，下午3:44
 */
public final class PropertiesUtils {
    /**
     * 构造函数
     */
    private PropertiesUtils() {}

    /**
     * @param key key
     * @param fileName fileName。相对于appHome的相对路径，包括文件名，且文件名必须以.properties结尾。
     * @return value
     */
    public static Object loadProperty(String key, String fileName) {
        Properties properties = loadProperty(fileName);

        return properties.get(key);
    }

    /**
     * @param fileName fileName。相对于appHome的相对路径，包括文件名，且文件名必须以.properties结尾。
     * @return value
     */
    public static Properties loadProperty(String fileName) {
        return PropertyLoadUtils.loadProperty(fileName);
    }
}
