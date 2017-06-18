package link.smartwall.service;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import link.smartwall.base.entity.ConfTreeData;
import link.smartwall.base.http.UserManager;
import link.smartwall.util.StrUtils;
@IocBean(args = { "refer:dao" })
public class TreeService extends BaseService {

	public TreeService(Dao dao) {
		super(dao);
	}
	@SuppressWarnings("unchecked")
	public List<Record> queryTree(String treeId, Map<String, String> map) {
		try {
			ConfTreeData ctd = dao().fetch(ConfTreeData.class,treeId);
			StringBuffer querySql = new StringBuffer("select " + ctd.getParams() + " from " + "("
					+ ctd.getSql() );
			querySql.append(" where 1 = 1 ");
			if(0 == ctd.getCheckTenantid()){
				querySql.append(" and tenant_id = "+UserManager.getCurrentUser().getTenantId());
			}
			if(0 == ctd.getCheckDeleted()){
				querySql.append(" and data_is_deleted = 0");
			}
			if (!StrUtils.isEmpty(ctd.getOrderBy())) {
				querySql.append(" order by " + ctd.getOrderBy());
			}
			querySql.append(")");
			Sql sql = Sqls.create(querySql.toString());
			if(null != map && !map.isEmpty()){
				for(String s : map.keySet()){
					sql.params().set(s, map.get(s));
				}
			}
			sql.setCallback(Sqls.callback.records());
     			dao().execute(sql);
			List<Record> list = (List<Record>) sql.getResult();
			if(null != ctd.getChildGuid()){
				list.addAll(queryTree(ctd.getChildGuid(),null));
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}
}
