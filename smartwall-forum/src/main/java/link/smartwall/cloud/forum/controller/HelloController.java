package link.smartwall.cloud.forum.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import link.smartwall.cloud.forum.dao.ForumDao;

/**
 * Hello world!
 *
 */
@RestController
@ComponentScan
public class HelloController {
	@Autowired
	private ForumDao dao;

	@RequestMapping("/hello")
	public String index() {
		Sql sql = Sqls.create("select guid, caption from kygj_exam_e1");
		sql.setCallback(new SqlCallback() {

			@Override
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				while (rs.next()) {
					System.out.println(rs.getString("guid"));
					System.out.println(rs.getString("caption"));
				}

				return null;
			}
		});
		this.dao.getDao().execute(sql);

		return "Hello World";
	}
}