package link.smartwall.base.entity;

import org.nutz.dao.entity.annotation.Column;

import link.smartwall.basemodel.BaseEntity;

public class ConfTreeData extends BaseEntity{
	@Column
	private String code;
	@Column
	private String name;
	@Column
	private String params;
	@Column
	private String sql;
	@Column
	private int version;
	@Column
	private String remark;
	@Column("order_by")
	private String orderBy;
	@Column("icon_skin")
	private String iconSkin;
	@Column("child_guid")
	private String childGuid;
	@Column("check_tenantid")
	private int checkTenantid;
	@Column("check_deleted")
	private int checkDeleted;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	public int getCheckTenantid() {
		return checkTenantid;
	}
	public void setCheckTenantid(int checkTenantid) {
		this.checkTenantid = checkTenantid;
	}
	public String getChildGuid() {
		return childGuid;
	}
	public void setChildGuid(String childGuid) {
		this.childGuid = childGuid;
	}
	public int getCheckDeleted() {
		return checkDeleted;
	}
	public void setCheckDeleted(int checkDeleted) {
		this.checkDeleted = checkDeleted;
	}
	
	
}
