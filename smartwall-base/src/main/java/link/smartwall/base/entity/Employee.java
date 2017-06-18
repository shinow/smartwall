package link.smartwall.base.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.BaseEntity;

/**
 * 员工表entity
 * 
 * @author yty
 *
 */
@Table("sfa_employee")
public class Employee extends BaseEntity {
	@Column
	private String code;
	@Column
	private String name;
	@Column
	private String pwd;

	@Column("dept_guid")
	private String deptguid;

	@Column("position_guid")
	private String positionguid;
	@Column
	private String imsi;

	@Column("enroll_date")
	private Date enrolldate;

	@Column("quit_date")
	private Date quitdate;

	@Column("reg_date")
	private Date regdate;

	@Column("reg_status")
	private Integer regStatus;

	@Column("is_manage")
	private Integer ismanage;

	@Column("is_bound")
	private Integer isbound;
	@Column("bound_type")
	private String boundtype;
	@Column
	private String OS;
	@Column("app_version")
	private String appversion;
	@Column
	private String property;
	@Column
	private String mobile;
	@Column ("is_administrative")
	private Integer isadministrative;
	@Column ("bound_time")
	private Date boundTime;
	@Column ("screen_type")
	private String screenType;
	@Column("tenant_guid")
	private Integer tenantGuid;

   

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public Date getBoundTime() {
        return boundTime;
    }

    public void setBoundTime(Date boundTime) {
        this.boundTime = boundTime;
    }

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDeptguid() {
		return deptguid;
	}

	public void setDeptguid(String deptguid) {
		this.deptguid = deptguid;
	}

	public String getPositionguid() {
		return positionguid;
	}

	public Integer getTenantGuid() {
	    return tenantGuid;
	}

	public void setTenantGuid(Integer tenantGuid) {
	    this.tenantGuid = tenantGuid;
	}
	
	public void setPositionguid(String positionguid) {
		this.positionguid = positionguid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Date getEnrolldate() {
		return enrolldate;
	}

	public void setEnrolldate(Date enrolldate) {
		this.enrolldate = enrolldate;
	}

	public Date getQuitdate() {
		return quitdate;
	}

	public void setQuitdate(Date quitdate) {
		this.quitdate = quitdate;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Integer getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(Integer regStatus) {
		this.regStatus = regStatus;
	}

	public Integer getIsmanage() {
		return ismanage;
	}

	public void setIsmanage(Integer ismanage) {
		this.ismanage = ismanage;
	}

	public Integer getIsbound() {
		return isbound;
	}

	public void setIsbound(Integer isbound) {
		this.isbound = isbound;
	}

	public String getBoundtype() {
		return boundtype;
	}

	public void setBoundtype(String boundtype) {
		this.boundtype = boundtype;
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String oS) {
		OS = oS;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsadministrative() {
		return isadministrative;
	}

	public void setIsadministrative(Integer isadministrative) {
		this.isadministrative = isadministrative;
	}
}
