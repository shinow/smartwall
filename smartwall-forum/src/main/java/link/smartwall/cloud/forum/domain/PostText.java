package link.smartwall.cloud.forum.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_post_text")
public class PostText extends ForumBaseEntity {
	@Column(value = "post_guid")
	private String postGuid;

	@Column(value = "post_text")
	private String text;

	@Column(value = "post_subject")
	private String subject;

	public String getPostGuid() {
		return postGuid;
	}

	public void setPostGuid(String postGuid) {
		this.postGuid = postGuid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
