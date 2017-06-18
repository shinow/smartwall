package link.smartwall.service.exam;

import java.util.Date;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import link.smartwall.service.BaseService;

@IocBean(args = { "refer:dao" }, name = "exam_service")
public class ExamService extends BaseService {

	public ExamService(Dao dao) {
		super(dao);
	}

	/**
	 * 保存试卷
	 * 
	 * @param type
	 *            试卷类型
	 * @param guid
	 *            guid
	 * @param value
	 *            值
	 */
	public void saveExam(String type, String guid, String value) {
		String sqlStr = "update kygj_exam_$type set content = @content, modify_flag = 2, modify_time=@modify_time where guid = @guid";
		Sql sql = Sqls.create(sqlStr);
		sql.setVar("type", type);
		sql.params().set("content", value).set("modify_time", new Date()).set("guid", guid);

		dao().execute(sql);
	}

	/**
	 * 获取试卷
	 * 
	 * @param type
	 *            试卷类型
	 * @param guid
	 * @return guid 值
	 */
	public Object getExam(String type, String guid) {
		String sqlStr = "select content from kygj_exam_$type where guid = @guid";
		Sql sql = Sqls.create(sqlStr);
		sql.setCallback(Sqls.callback.str());
		sql.setVar("type", type);
		sql.params().set("guid", guid);

		dao().execute(sql);

		return sql.getResult();
	}
}
