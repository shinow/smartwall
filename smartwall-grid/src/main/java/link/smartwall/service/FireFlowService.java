package link.smartwall.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.itfsm.service.BaseService;

import link.smartwall.grid.entity.ConfFuncWorkFlow;

@IocBean(args = { "refer:dao" })
public class FireFlowService extends BaseService {

	public FireFlowService(Dao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	private static final String DEL_FLOW = "delete from T_FF_DF_PROCESS_REPOSITORY3 where guid = @guid";
	private static final String CONF_WEB_FORM = "select guid,code from CONF_WEB_FORM where data_is_deleted=0";
	private static final String CONF_MOBI_FORM = "select guid,code from CONF_MOBI_FORM where data_is_deleted=0";
	private static final String T_FF_DF_PROCESS_REPOSITORY3 = "select guid,display_name from T_FF_DF_PROCESS_REPOSITORY3 ";

	/**
	 * 获得流程对象
	 * 
	 * @param form
	 *            表单对象
	 * @return ConfMobiForm
	 */
	public Record getForm(String guid) {
		return dao().fetch("T_FF_DF_PROCESS_REPOSITORY3",
				Cnd.where("guid", "=", guid));
	}

	/**
	 * 删除流程
	 * 
	 * @param guid
	 */
	public void delFlow(String guid) {
		Sql sql = Sqls.create(DEL_FLOW);
		sql.params().set("guid", guid);
		sql.setCallback(Sqls.callback.record());
		dao().execute(sql);
	}

	/**
	 * 删除保存工作流功能配置
	 * @param confFuncWorkFlow
	 */
	public void saveOrUpdateFun(ConfFuncWorkFlow confFuncWorkFlow) {
		if (null != confFuncWorkFlow.getGuid()) {
			dao().updateIgnoreNull(confFuncWorkFlow);
		} else {
			confFuncWorkFlow.initValues();
			dao().insert(confFuncWorkFlow);
		}

	}

	/**
	 * 获取配置信息
	 * @return
	 */
	/*public Map<String, Object> getConfData() {
		Map<String, Object> result = new HashMap<String, Object>();
		Sql webConf = Sqls.create(CONF_WEB_FORM);
		Sql mobiConf = Sqls.create(CONF_MOBI_FORM);
		Sql processConf = Sqls.create(T_FF_DF_PROCESS_REPOSITORY3);
		webConf.setCallback(Sqls.callback.records());
		mobiConf.setCallback(Sqls.callback.records());
		processConf.setCallback(Sqls.callback.records());
		dao().execute(webConf);
		dao().execute(mobiConf);
		dao().execute(processConf);
		result.put("conf_web_form", webConf.getResult());
		result.put("conf_mobi_form", mobiConf.getResult());
		result.put("t_ff_df_process_repository3", processConf.getResult());
		return result;
	}*/

	/**
	 * 删除功能配置
	 * @param confFuncWorkFlow
	 * @return
	 */
	public int delFun(ConfFuncWorkFlow confFuncWorkFlow) {
		return dao().delete(confFuncWorkFlow);
	}
}
