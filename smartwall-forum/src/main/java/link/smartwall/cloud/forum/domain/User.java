package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_user")
public class User {
	@Column(value = "user_guid")
	@Name
	private String guid;

	@Column(value = "active")
	private boolean active;

	@Column(value = "username")
	private String name;

	@Column(value = "password")
	private String password;

	@Column(value = "last_in_date")
	private Date lastInDate;

	@Column(value = "last_out_date")
	private Date lastOutDate;

	@Column(value = "post_count")
	private int postCount;

	@Column(value = "user_level")
	private int userLevel;

	@Column(value = "rank_guid")
	private String rankGuid;

	@Column(value = "avatar")
	private String avatar;

	@Column(value = "gender")
	private String gender;
	
	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
