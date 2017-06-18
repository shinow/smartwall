/**
 * SmartWall(2015)
 */
package link.smartwall.service;

import java.util.List;

import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import link.smartwall.basemodel.AbstractObject;

/**
 * 实体相关服务
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @param <T> 实体对象
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年2月27日 lexloo * 建立
 *        </pre>
 */
@IocBean(args = {"refer:dao"})
public class BaseEntityService<T extends AbstractObject> extends IdEntityService<T> {
    /**
     * 获取对象列表
     * 
     * @param pageNumber 页码
     * @param pageSize 每页显示记录数
     * @param cnd 条件
     * @return 分页信息
     */
    public QueryResult getObjListByPager(Integer pageNumber, int pageSize, Condition cnd) {
        pageNumber = getPageNumber(pageNumber);

        Dao dao = this.dao();
        Pager pager = dao.createPager(pageNumber, pageSize);
        Class<T> entityType = getEntityClass();

        List<T> list = dao.query(entityType, cnd, pager);

        pager.setRecordCount(dao.count(entityType, cnd));

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
