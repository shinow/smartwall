/**
 * 天恒众航(2013)
 */
package link.smartwall.basemodel;

import org.nutz.dao.Dao;

/**
 * 模型事件,用于模型保存时支持更多的处理
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-11-11 lexloo
 *        </pre>
 */
public interface IOprEvent {
    /**
     * 插入前执行
     * 
     * @param dao dao对象
     */
    void beforeInsert(Dao dao);

    /**
     * 插入后执行
     * 
     * @param dao dao对象
     */
    void afterInsert(Dao dao);

    /**
     * 更新前执行
     * 
     * @param dao dao对象
     * @param prevObj 更新前对象
     */
    void beforeUpdate(Dao dao, AbstractObject prevObj);

    /**
     * 更新后执行
     * 
     * @param dao dao对象
     * @param prevObj 更新前对象
     * 
     */
    void afterUpdate(Dao dao, AbstractObject prevObj);

    /**
     * 删除前执行
     * 
     * @param dao dao对象
     */
    void beforeDelete(Dao dao);

    /**
     * 删除后执行
     * 
     * @param dao dao对象
     */
    void afterDelete(Dao dao);
}
