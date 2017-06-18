package link.smartwall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author <a herf="wangbo@jccatech.com">wangbo</a>
 * @version 1.0
 * @since 2015-08-12，18:53
 */
public final class PropertyLoadUtils {
    /**
     * 构造函数
     */
    private PropertyLoadUtils() {}

    /**
     * @param key key
     * @param fileName fileName。相对于appHome的相对路径，包括文件名，且文件名必须以.properties结尾。
     * @return value
     * @throws java.io.IOException e
     */
    public static Object loadProperty(String key, String fileName) throws IOException {
        Properties properties = loadProperty(fileName);
        return properties.get(key);
    }

    /**
     * @param fileName fileName。相对于appHome的相对路径，包括文件名，且文件名必须以.properties结尾。
     * @return value
     * @throws java.io.IOException e
     */
    public static Properties loadProperty(String fileName) {
        try {
            String appHome = System.getenv("XSB_HOME");
            if (StrUtils.isEmpty(appHome)) {
                throw new IllegalStateException("找不到环境变量：XSB_HOME,无法读取配置文件：" + fileName);
            }
            String filePath = appHome + File.separator + fileName;
            Properties properties = new Properties();
            properties.load(new FileInputStream(filePath));
            return properties;
        } catch (Exception ex) {
            throw new IllegalStateException("加载配置'" + fileName + "'出错!");
        }
    }
}
