package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_user")
public class User extends ForumBaseEntity {
	@Column(value = "active")
	private boolean active;

	@Column(value = "username")
	private String name;

	@Column(value = "password")
	private String password;

	@Column(value = "last_in_date")
	private Date lastInDate;

	@Column(value = "last_out_date")
	private Date lastOutDate;

	@Column(value = "post_count")
	private int postCount;

	@Column(value = "user_level")
	private int userLevel;

	@Column(value = "rank_guid")
	private String rankGuid;

	@Column(value = "avatar")
	private String avatar;

	@Column(value = "gender")
	private String gender;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastInDate() {
		return lastInDate;
	}

	public void setLastInDate(Date lastInDate) {
		this.lastInDate = lastInDate;
	}

	public Date getLastOutDate() {
		return lastOutDate;
	}

	public void setLastOutDate(Date lastOutDate) {
		this.lastOutDate = lastOutDate;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public String getRankGuid() {
		return rankGuid;
	}

	public void setRankGuid(String rankGuid) {
		this.rankGuid = rankGuid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
