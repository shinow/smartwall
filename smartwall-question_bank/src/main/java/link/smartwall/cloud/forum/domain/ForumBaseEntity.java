package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;

public class ForumBaseEntity {
	@Column(value = "guid")
	@Name
	private String guid;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

}
