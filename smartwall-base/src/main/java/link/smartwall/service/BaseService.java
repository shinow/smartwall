/**
 * SmartWall(2015)
 */
package link.smartwall.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.base.api.Result;
import link.smartwall.basemodel.AbstractGuidObject;
import link.smartwall.basemodel.AbstractObject;
import link.smartwall.basemodel.BaseEntity;
import link.smartwall.exception.SfaBaseException;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象服务
 *
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since 外勤助手 4.0.0
 * <p/>
 * <pre>
 * 历史：
 *      2015年2月27日 lexloo * 建立
 *        </pre>
 */
@IocBean(args = {"refer:dao"})
public abstract class BaseService extends Service {

    /**
     *
     */
    private static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

    /**
     * 无参构造方法
     */
    public BaseService() {
        super();
    }

    /**
     * 构造函数
     */
    public BaseService(Dao dao) {
        super(dao);
    }

    /**
     * 插入对象
     *
     * @param ao 插入对象
     * @return 更新后的对象
     */
    public AbstractObject insert(AbstractObject ao) {
        return this.dao().insert(ao);
    }

    /**
     * 更新对象
     *
     * @param ao 待更新对象
     * @return 更新条数
     */
    public int update(AbstractObject ao) {
        return this.dao().update(ao);
    }

    /**
     * 删除对象
     *
     * @param ao 待删除的对象
     * @return 删除条数
     */
    public int delete(AbstractObject ao) {
        return this.dao().delete(ao);
    }

    /**
     * 根據主鍵Guid刪除對象
     */
    public int deleteByGuid(Class<?> t, String guid) {
        return this.dao().delete(t, guid);
    }

    /**
     * 保存或者更新baseEntity
     *
     * @param baseEntity baseEntity
     * @return result, json format
     */
    public Result saveOrUpdate(BaseEntity baseEntity) {
        try {
                Dao dao = dao();
            if (StringUtils.isBlank(baseEntity.getGuid())) {
                baseEntity.initCommonFields();
                baseEntity.beforeInsert(dao);
                baseEntity = dao.insert(baseEntity);
                baseEntity.afterInsert(dao);
            } else {
                baseEntity.updateModifyFields();
                BaseEntity baseEntityOld = getGuidObjectByGuid(baseEntity.getClass(), baseEntity.getGuid());
                baseEntity.beforeUpdate(dao, baseEntityOld);
                dao().update(baseEntity);
                baseEntity.afterUpdate(dao, baseEntityOld);
            }
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
            return Result.failureResult(e.getLocalizedMessage());
        }

        JSONObject result = new JSONObject();
        result.put("guid", baseEntity.getGuid());
        return Result.successResult(result);
    }

    /**
     * @param clazz clazz
     * @param guid  guid
     * @return result, json format
     */
    public Result getGuidObjectJsonByGuid(Class<? extends AbstractGuidObject> clazz, String guid) {
        try {
            return Result.successResult(JSON.toJSONString(getGuidObjectByGuid(clazz, guid)));
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
            return Result.failureResult(e.getLocalizedMessage());
        }
    }

    /**
     * @param clazz clazz
     * @param guid  guid
     * @return result, json format
     */
    public <T extends AbstractGuidObject> T getGuidObjectByGuid(Class<T> clazz, String guid) {
        T obj = dao().fetch(clazz, guid);
        if (obj == null) {
            throw new SfaBaseException("根据GUID：" + guid + " 未找到类：" + clazz.getName() + "对应的实体对象。");
        }
        return obj;
    }
}
