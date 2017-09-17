package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_topic")
public class Topic {
	@Column(value = "topic_guid")
	@Name
	private String guid;
	
	@Column(value = "forum_guid")
	private String forum;
	
	@Column(value = "topic_title")
	private String title;
	
	@Column(value = "user_guid")
	private String user;
	
	@Column(value = "topic_time")
	private Date createTime;
	
	@Column(value = "view_count")
	private int viewCount;
	
	@Column(value = "replies_count")
	private int repies_count;
	
	@Column(value = "last_post_guid")
	private int lastPostGuid;
	

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
