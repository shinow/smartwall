package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

/**
 * 医学种类
 *
 */
@Table(value = "exam_medical_category")
public class Category {
	@Column(value = "guid")
	@Name
	@Prev(els = @EL("uuid(32)"))
	private String guid;

	@Column(value = "kind_guid")
	private String kindGuid;
	
	@Column(value = "name")
	private String name;

	@Column(value = "modify_time")
	private Date modifyTime;

	@Column(value = "modify_flag")
	private int modifyFlag;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

	public int getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(int modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getKindGuid() {
		return kindGuid;
	}

	public void setKindGuid(String kindGuid) {
		this.kindGuid = kindGuid;
	}
}
