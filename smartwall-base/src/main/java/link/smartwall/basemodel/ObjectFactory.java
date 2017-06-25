/**
 * SmartWall(2013)
 */
package link.smartwall.basemodel;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础模型对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 *        </pre>
 */
public enum ObjectFactory {
    INSTANCE;

    private Map<String, Class<? extends AbstractObject>> objs = new HashMap<>();

    private ObjectFactory() {
        putObj("kygj_exam_zz", "link.smartwall.base.entity.KygjExamZZ");
        putObj("kygj_exam_e1", "link.smartwall.base.entity.KygjExamE1");
        putObj("kygj_exam_e2", "link.smartwall.base.entity.KygjExamE2");
        putObj("kygj_exam_s1", "link.smartwall.base.entity.KygjExamS1");
        putObj("kygj_exam_s2", "link.smartwall.base.entity.KygjExamS2");
        putObj("kygj_exam_s3", "link.smartwall.base.entity.KygjExamS3");
    }

    @SuppressWarnings("unchecked")
    private void putObj(String name, String className) {
        try {
            objs.put(name, (Class<? extends AbstractObject>) Class.forName(className));
        } catch (Exception e) {

        }
    }

    public AbstractObject getObjectByCode(String code) {
        try {
            Class<? extends AbstractObject> clazz = this.objs.get(code);
            if (clazz == null) {
                return null;
            }
            AbstractObject ao = clazz.newInstance();
            ao.initValues();

            return ao;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("类不存在！代码：" + code);
        }
    }

    public AbstractObject getObjectNoGuid(String code) {
        try {
            Class<? extends AbstractObject> clazz = this.objs.get(code);
            if (clazz == null) {
                return null;
            }
            AbstractObject ao = clazz.newInstance();

            return ao;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("类不存在！代码：" + code);
        }
    }

    public Class<?> getObjectClass(String code) {
        return this.objs.get(code);
    }
}
