package link.smartwall.service.exam;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import link.smartwall.service.BaseService;

@IocBean(args = { "refer:dao" }, name = "exam_service")
public class ExamService extends BaseService {

	public ExamService(Dao dao) {
		super(dao);
	}
}
