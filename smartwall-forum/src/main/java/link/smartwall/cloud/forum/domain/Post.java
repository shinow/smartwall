package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_post")
public class Post {
	@Column(value = "post_guid")
	@Name
	private String guid;

	@Column(value = "topic_guid")
	private String topic;

	@Column(value = "forum_guid")
	private String forum;

	@Column(value = "user_guid")
	private String user;

	@Column(value = "post_time")
	private Date postTime;

	@Column(value = "post_edit_time")
	private Date editTime;

	@Column(value = "post_edit_count")
	private int editCount;

	@Column(value = "attach_count")
	private int attachCount;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
