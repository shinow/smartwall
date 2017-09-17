package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_topic")
public class Topic extends ForumBaseEntity{
	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getRepies_count() {
		return repies_count;
	}

	public void setRepies_count(int repies_count) {
		this.repies_count = repies_count;
	}

	public int getLastPostGuid() {
		return lastPostGuid;
	}

	public void setLastPostGuid(int lastPostGuid) {
		this.lastPostGuid = lastPostGuid;
	}

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
}
