package link.smartwall.base.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
* Title: TenantWebModules
* Description:TODO 
* Company: $
* author: yty 
* date:2017年1月11日 上午9:27:39
 */
@Table("conf_tenant_web_modules")
public class TenantWebModules {
	@Column("tenant_id")
	private int tenantId;
	@Column("module_guid")
	private String moduleGuid;
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public String getModuleGuid() {
		return moduleGuid;
	}
	public void setModuleGuid(String moduleGuid) {
		this.moduleGuid = moduleGuid;
	}
	
}
