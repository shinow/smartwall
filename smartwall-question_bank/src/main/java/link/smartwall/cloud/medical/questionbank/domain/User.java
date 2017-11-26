package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

/**
 * 用户
 *
 */
@Table(value = "exam_medical_user")
public class User {
	@Column(value = "guid")
	@Name
	@Prev(els = @EL("uuid(32)"))
	private String guid;

	@Column(value = "mobile")
	private String subjectGuid;

	@Column(value = "pwd")
	private String name;

	@Column(value = "data_time")
	private Date modifyTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getSubjectGuid() {
		return subjectGuid;
	}

	public void setSubjectGuid(String subjectGuid) {
		this.subjectGuid = subjectGuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
