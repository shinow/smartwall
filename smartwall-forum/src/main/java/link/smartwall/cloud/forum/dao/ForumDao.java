package link.smartwall.cloud.forum.dao;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 */
@Configuration
@Component("dao")
public class ForumDao extends NutDao implements Dao {
	@Autowired
	public void setDruidDataSource(DataSource druidDataSource) {
		setDataSource(druidDataSource);
	}
	
	// public void run(ConnCallback callback) {
	// Connection con = DataSourceUtils.getConnection(druidDataSource);
	// try {
	// callback.invoke(con);
	// } catch (Exception e) {
	// if (e instanceof RuntimeException)
	// throw (RuntimeException) e;
	// else
	// throw new RuntimeException(e);
	// } finally {
	// DataSourceUtils.releaseConnection(con, druidDataSource);
	// }
	// }
}