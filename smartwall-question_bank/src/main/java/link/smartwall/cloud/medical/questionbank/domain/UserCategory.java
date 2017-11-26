package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * 用户关注考试种类
 *
 */
@Table(value = "exam_medical_user_category")
public class UserCategory {
	@Name
	@Column(value = "user_guid")
	private String userGuid;

	@Column(value = "category_guid")
	private String categoryGuid;

	@Column(value = "modify_time")
	private Date modifyTime;

	@Column(value = "modify_flag")
	private int modifyFlag;

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getCategoryGuid() {
		return categoryGuid;
	}

	public void setCategoryGuid(String categoryGuid) {
		this.categoryGuid = categoryGuid;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(int modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

}
