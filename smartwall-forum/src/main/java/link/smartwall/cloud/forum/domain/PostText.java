package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_post_text")
public class PostText {
	@Column(value = "post_guid")
	@Name
	private String guid;
	
	@Column(value = "post_text")
	private String text;

	@Column(value = "post_subject")
	private String subject;
	

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
