/**
 * 天恒众航（北京）科技股份公司(2015)
 */
package link.smartwall.service;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.Service;

import link.smartwall.base.http.UserManager;

/**
 * 抽象服务
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年2月27日 lexloo * 建立
 * </pre>
 */
@IocBean(args = { "refer:dao" })
public class CheckService extends Service {
	/**
	 * 构造函数
	 */
	public CheckService(Dao dao) {
		super(dao);
	}

	/**
	 * 
	 * 查询编码是否存在
     * 需要锁，暂时使用synchronized 实现，待优化
     * @param code
     * @autho yty
     */
    public synchronized Boolean checkCode(String bizCode, Object code, Object guid) {
		int tenantId = 0;
		if (null != UserManager.getCurrentUser()){
			tenantId = UserManager.getCurrentUser().getTenantId();
		}

        if (null == code) {
            return true;
        }
        Sql sql = null;
		if ("sfa_tenant".equals(bizCode)) {
			sql = Sqls
					.create("select count(1) from $table where  code = @code");
			sql.vars().set("table", bizCode);
			sql.params().set("code", code);
		} else {
			sql = Sqls .create("select count(1) from $table where data_is_deleted = 0 and  tenant_id = @tenantId and code = @code and nvl( ' ' || guid,' ') <> @guid");
			sql.params().set("guid", " "+guid);
			sql.vars().set("table", bizCode);
			sql.params().set("tenantId", tenantId).set("code", code);
		}
		sql.setCallback(Sqls.callback.integer());
		dao().execute(sql);
        return (0 == sql.getInt());
    }

}
