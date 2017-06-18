/**
 * SmartWall(2013)
 */
package link.smartwall.base.http;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Web用户对象，Session中存储
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-3-8 lexloo
 *        </pre>
 */
public class WebUser implements Serializable{
    /**
     * 用户Id
     */
    private int id;

    /**
     * GUID
     */
    private String guid;
    /**
     * 登录编号
     */
    private String code;
    /**
     * 登录的用户姓名
     */
    private String name;

    /**
     * 租户Id
     */
    private int tenantId;
    /**
     * 租户Guid
     */
    private String tenantGuid;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 部门Id
     */
    private int departmentId;
    /**
     * 所属部门
     */
    private String department;
    /**
     * 是否超级用户
     */
    private boolean superUser;
    /**
     * logo图像Id
     */
    private String logoId;
    /**
     * 对应的行业Id
     */
    private int businessId;
    /**
     * 岗位Id
     */
    private int positionId;
    /**
     * 员工Id
     */
    private int staffId;
    /**
     * 过期时间
     */
    private Date endTime;
    /**
     * 是否演示用户（演示企业）
     */
    private boolean isDemoUser;
    /**
     * 权限对象
     */
    private List<RightObj> rights = new ArrayList<RightObj>();
    /**
     * MD5 登陆密码
     */
    private String securityCode;

    /**
     * @return the securityCode
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * @param securityCode the securityCode to set
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * @param right the right to add
     */
    public void addRight(RightObj right) {
        this.rights.add(right);
    }

    /**
     * 是否包含指定权限
     * 
     * @param right 权限
     * @return 包含则为true
     */
    public boolean hasRight(RightObj right) {
        return this.rights.contains(right);
    }

    /**
     * @return the rights
     */
    public List<RightObj> getRights() {
        return rights == null ? Collections.<RightObj> emptyList() : rights;
    }

    /**
     * @return the tenantId
     */
    public int getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return the tenantName
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * @param tenantName the tenantName to set
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * @return the departmentId
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the superUser
     */
    public boolean isSuperUser() {
        return superUser;
    }

    /**
     * @param superUser the superUser to set
     */
    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the logoId
     */
    public String getLogoId() {
        return logoId;
    }

    /**
     * @param logoId the logoId to set
     */
    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    /**
     * @return the businessId
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     * @param businessId the businessId to set
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    /**
     * @return the staffId
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * @param staffId the staffId to set
     */
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return this.tenantName + ":" + this.code;
    }

    /**
     * @return the isDemoUser
     */
    public boolean isDemoUser() {
        return isDemoUser;
    }

    /**
     * @param isDemoUser the isDemoUser to set
     */
    public void setDemoUser(boolean isDemoUser) {
        this.isDemoUser = isDemoUser;
    }

    public String getTenantGuid() {
        return tenantGuid;
    }

    public void setTenantGuid(String tenantGuid) {
        this.tenantGuid = tenantGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

}
