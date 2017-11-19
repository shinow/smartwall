package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_post")
public class Post extends ForumBaseEntity {
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public int getEditCount() {
		return editCount;
	}

	public void setEditCount(int editCount) {
		this.editCount = editCount;
	}

	public int getAttachCount() {
		return attachCount;
	}

	public void setAttachCount(int attachCount) {
		this.attachCount = attachCount;
	}
}
