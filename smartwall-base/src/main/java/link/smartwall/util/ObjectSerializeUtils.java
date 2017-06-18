package link.smartwall.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对象序列化工具类
 * 
 * @version 1.0
 * @author <a href="ttan_style@sina.cn">ttan</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014-5-5  ttan
 * </pre>
 */
public final class ObjectSerializeUtils {
    /**
     * 默认构造器
     */
    private ObjectSerializeUtils() {
        // 空实现
    }

    /**
     * 对象序列化
     * 
     * @param object object
     * @return 序列化字节数组
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);

            return bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 反序列化生成对象
     * 
     * @param bytes 字节数组
     * @return 返回对象
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
