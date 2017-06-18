package link.smartwall.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import link.smartwall.util.StrUtils;

/**
 * 下拉框数据
 * 
 * @author caisj
 *
 */
@IocBean(args = { "refer:dao" })
public class DataService extends BaseService {

	public DataService(Dao dao) {
		super(dao);
	}
	
	/**
	 * 获取指定下拉框的content
	 * 
	 * @param code
	 * @return
	 */
	public Object getSelectContent(String code) {
		/*String textContent = code;
		textContent = textContent.trim();
		while (textContent.startsWith("　")) {// 这里判断是不是全角空格
			textContent = textContent.substring(1, textContent.length()).trim();
		}
		while (textContent.endsWith("　")) {
			textContent = textContent.substring(0, textContent.length() - 1)
					.trim();
		}*/
		boolean flag = true;
		if("process".endsWith(code)){
			flag=false;
		}
		Record record = dao().fetch("CONF_DATASET_SQL",
				Cnd.where("code", "=", code).and("data_is_deleted", "=", 0));
		return getSelectData(record,flag);
	}

	/**
	 * 获取指定下拉框数据
	 * 
	 * @param record
	 * @param flag 
	 * @return
	 */
	private Object getSelectData(Record record, boolean flag) {
		/**
		 * 数据源
		 */
		String content = record.getString("content");
		/**
		 * 输出字段
		 */
		String fields = record.getString("fields");
		/**
		 * 排序字段
		 */
		String order = record.getString("order_info");
		/**
		 * 输入参数
		 */
		String parameters = record.getString("parameters");
		/**
		 * 输入变量
		 */
		String vars = record.getString("vars");
		StringBuffer sql = new StringBuffer("select " + fields + " from " + "("
				+ content );
		if(flag){
			sql.append(" where t1.data_is_deleted=0 ) ");
		}else{
			sql.append(" ) ");
		}
		
		if (!StrUtils.isEmpty(parameters) && !StrUtils.isEmpty(vars)) {
			sql.append(" where " + parameters + "=" + vars + " order by "
					+ order);
		} else {
			sql.append(" order by " + order);
		}
		Sql dtSql = Sqls.create(sql.toString());
		dtSql.setCallback(Sqls.callback.records());
		dao().execute(dtSql);
		return dtSql.getResult();
	}

	
	

}
