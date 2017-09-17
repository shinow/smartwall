package link.smartwall.cloud.forum.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_user_group")
public class UserGroup extends ForumBaseEntity {
	@Column(value = "user_guid")
	private String userGuid;

	@Column(value = "group_guid")
	private String groupGuid;

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getGroupGuid() {
		return groupGuid;
	}

	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
}
