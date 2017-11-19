package link.smartwall.cloud.forum.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_forum")
public class Forum extends ForumBaseEntity {
	@Column(value = "category_guid")
	private String categoryGuid;

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

	public String getCategoryGuid() {
		return categoryGuid;
	}

	public void setCategoryGuid(String categoryGuid) {
		this.categoryGuid = categoryGuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getTopics() {
		return topics;
	}

	public void setTopics(int topics) {
		this.topics = topics;
	}

	public String getLastPostGuid() {
		return lastPostGuid;
	}

	public void setLastPostGuid(String lastPostGuid) {
		this.lastPostGuid = lastPostGuid;
	}
}
