/**
 * 天恒众航（北京）科技股份公司(2015)
 */
package link.smartwall.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 反射辅助类
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年5月20日 lexloo * 建立
 *        </pre>
 */
public final class ReflectUtils {
    /**
     * 构造器
     */
    private ReflectUtils() {
        // --
    }

    private static Map<Class<?>, IConvert<?>> convertCache = new HashMap<>();
    static {
        convertCache.put(int.class, new IntegerConvert());
        convertCache.put(long.class, new LongConvert());
        convertCache.put(float.class, new FloatConvert());
        convertCache.put(double.class, new DoubleConvert());
        convertCache.put(boolean.class, new BooleanConvert());

        convertCache.put(String.class, new StringConvert());
        convertCache.put(Integer.class, new IntegerConvert());
        convertCache.put(Long.class, new LongConvert());
        convertCache.put(Float.class, new FloatConvert());
        convertCache.put(Double.class, new DoubleConvert());
        convertCache.put(Boolean.class, new BooleanConvert());
        convertCache.put(Date.class, new DateConvert());
        convertCache.put(BigDecimal.class, new BigDecimalConvert());
    }

    /**
     * 设置字段的值
     * 
     * @param obj 对象
     * @param field 字段名
     * @param value 字段值
     */
    public static void setValue(Object obj, String field, Object value) {
        try {
            Class<?> clazz = PropertyUtils.getPropertyType(obj, field);
            // 如果字段不存在，不进行赋值
            if (clazz == null) {
                return;
            }

            Object v = getTypeValue(clazz, value);

            PropertyUtils.setSimpleProperty(obj, field, v);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static Object getTypeValue(Class<?> clazz, Object value) {
        if (Enum.class.isAssignableFrom(clazz)) {
            return Enum.valueOf((Class) clazz, String.valueOf(value));
        }

        IConvert<?> c = convertCache.get(clazz);
        if (c == null) {
            return value;
        }
        
        return c.convert(value);
    }

    /**
     * 获取字段的值
     * 
     * @param obj 对象
     * @param field 字段名
     * @return 获取的值
     */
    public static Object getValue(Object obj, String field) {
        try {
            return PropertyUtils.getSimpleProperty(obj, field);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("获取属性值出错：" + field);
        }
    }
}
