package link.smartwall.cloud.forum.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_banlist")
public class BanList extends ForumBaseEntity {
	@Column(value = "user_guid")
	private String userGuid;

	@Column(value = "reason")
	private String reason;

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
