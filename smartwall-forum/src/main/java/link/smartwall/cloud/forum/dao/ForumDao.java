package link.smartwall.cloud.forum.dao;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Hello world!
 *
 */
@Service
public class ForumDao {
	@Autowired
	private DataSource dataSource;

	public Dao getDao() {
		return new NutDao(dataSource);
	}
}