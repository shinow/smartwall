package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_forums")
public class Forums {
	@Column(value = "forums_guid")
	@Name
	private String guid;

	@Column(value = "categories_guid")
	private String categoriesGuid;

	@Column(value = "forum_name")
	private String name;

	@Column(value = "forum_desc")
	private String desc;

	@Column(value = "forum_order")
	private int order;

	@Column(value = "forum_topics")
	private int topics;

	@Column(value = "last_post_guid")
	private String lastPostGuid;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
