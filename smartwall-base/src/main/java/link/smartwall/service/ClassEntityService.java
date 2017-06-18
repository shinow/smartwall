/**
 * SmartWall(2015)
 */
package link.smartwall.service;

import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Each;
import org.nutz.lang.Lang;

/**
 * 类实体服务
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年2月27
 *      日 lexloo * 建立
 *        </pre>
 */
@IocBean(args = {"refer:dao"})
public class ClassEntityService extends BaseService {
    /**
     * 构造函数
     */
    public ClassEntityService(Dao dao) {
        super(dao);
    }

    /**
     * 清除数据
     * 
     * @param entityClass 实体类
     * @param cnd 条件
     * @return 清除条数
     */
    public int clear(Class<?> entityClass, Condition cnd) {
        return dao().clear(entityClass, cnd);
    }

    /**
     * 
     * @param entityClass 实体类
     * @return 结果
     */
    public int clear(Class<?> entityClass) {
        return dao().clear(entityClass, null);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param cnd 条件
     * @param pager 分页对象
     * @param <T> 类泛型
     * @return 结果
     */
    public <T> List<T> query(Class<T> entityClass, Condition cnd, Pager pager) {
        return dao().query(entityClass, cnd, pager);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param cnd 条件
     * @param pager 分页对象
     * @param callback 回调
     * @param <T> 类泛型
     * @return 结果
     */
    public <T> int each(Class<T> entityClass, Condition cnd, Pager pager, Each<T> callback) {
        return dao().each(entityClass, cnd, pager, callback);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param cnd 条件
     * @return 结果
     */
    public int count(Class<?> entityClass, Condition cnd) {
        return dao().count(entityClass, cnd);
    }

    /**
     * 
     * @param entityClass 实体类
     * @return 结果
     */
    public int count(Class<?> entityClass) {
        return dao().count(entityClass);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param id 主键值
     * @param <T> 类泛型
     * @return 结果
     */
    public <T> T fetch(Class<T> entityClass, long id) {
        return dao().fetch(entityClass, id);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param name name值
     * @param <T> 类泛型
     * @return 结果
     */
    public <T> T fetch(Class<T> entityClass, String name) {
        return dao().fetch(entityClass, name);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param cnd 条件
     * @param <T> 类泛型
     * @return 结果
     */
    public <T> T fetch(Class<T> entityClass, Condition cnd) {
        return dao().fetch(entityClass, cnd);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param chain chain
     * @param cnd 条件
     */
    public void update(Class<?> entityClass, Chain chain, Condition cnd) {
        dao().update(entityClass, chain, cnd);
    }

    /**
     * 
     * @param entityClass 实体类
     * @param regex relations
     * @param chain chain
     * @param cnd 条件
     */
    public void updateRelation(Class<?> entityClass, String regex, Chain chain, Condition cnd) {
        dao().updateRelation(entityClass, regex, chain, cnd);
    }

    /**
     * 获取对象列表
     * 
     * @param entityClass 实体类
     * @param pageNumber 页码
     * @param pageSize 每页显示记录数
     * @param cnd 条件
     * @return 分页信息
     */
    public QueryResult getObjListByPager(Class<?> entityClass, Integer pageNumber, int pageSize, Condition cnd) {
        pageNumber = getPageNumber(pageNumber);

        Dao dao = this.dao();
        Pager pager = dao.createPager(pageNumber, pageSize);

        List<?> list = dao.query(entityClass, cnd, pager);
        pager.setRecordCount(dao.count(entityClass, cnd));

        return new QueryResult(list, pager);
    }

    /**
     * 获取页码
     * 
     * @param pageNumber 页码
     * @return 页面
     */
    private int getPageNumber(Integer pageNumber) {
        return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
    }
}
