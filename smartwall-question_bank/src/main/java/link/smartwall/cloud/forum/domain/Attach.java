package link.smartwall.cloud.forum.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_attach")
public class Attach extends ForumBaseEntity {
	@Column(value = "post_guid")
	private String postGuid;

	@Column(value = "user_guid")
	private String userGuid;

	public String getPostGuid() {
		return postGuid;
	}

	public void setPostGuid(String postGuid) {
		this.postGuid = postGuid;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
}
