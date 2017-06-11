/**
 * 天恒众航（北京）科技股份公司(2014)
 */
package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * 广燕经销商用户权限表
 * 
 * @version 1.0
 * @author <a herf="xiaojiaxingbj@163.com">xiaojiaxing</a>
 * @since 外勤管家3.0
 * 
 *        <pre>
 * 历史：
 *      建立: Oct 15, 2014 JiaXing
 * </pre>
 */
@Table("jxs_gy_emp_rights")
public class GyEmpRights {

    /**
     * 员工GUID
     */
    @Column("emp_guid")
    private String empGuid;
    /**
     * 经销商GUID
     */
    @Column("agency_guid")
    private String agencyGuid;
    /**
     * 部门GUID
     */
    @Column("dept_guid")
    private String deptGuid;
    /**
     * 大区GUID
     */
    @Column("office_guid")
    private String officeGuid;
    /**
     * 业务组GUID
     */
    @Column("group_guid")
    private String groupGuid;
    /**
     * 岗位GUID
     */
    @Column("position_guid")
    private String positionGuid;
    /**
     * 状态
     */
    @Column("state")
    private int state;
    /**
     * 员工ID
     */
    @Column("emp_id")
    private int empId;
    /**
     * 经销商ID
     */
    @Column("agency_id")
    private int agencyId;
    /**
     * 部门ID
     */
    @Column("dept_id")
    private int deptId;
    /**
     * 大区ID
     */
    @Column("office_id")
    private int officeId;
    /**
     * 业务组ID
     */
    @Column("group_id")
    private int groupId;
    /**
     * 岗位ID
     */
    @Column("position_id")
    private int positionId;
    /**
     * 同步需要
     */
    @Column("sync_flag")
    private int syncFlag = 0;

    /**
     * @return the empGuid
     */
    public String getEmpGuid() {
        return empGuid;
    }

    /**
     * @param empGuid the empGuid to set
     */
    public void setEmpGuid(String empGuid) {
        this.empGuid = empGuid;
    }

    /**
     * @return the agencyGuid
     */
    public String getAgencyGuid() {
        return agencyGuid;
    }

    /**
     * @param agencyGuid the agencyGuid to set
     */
    public void setAgencyGuid(String agencyGuid) {
        this.agencyGuid = agencyGuid;
    }

    /**
     * @return the deptGuid
     */
    public String getDeptGuid() {
        return deptGuid;
    }

    /**
     * @param deptGuid the deptGuid to set
     */
    public void setDeptGuid(String deptGuid) {
        this.deptGuid = deptGuid;
    }

    /**
     * @return the officeGuid
     */
    public String getOfficeGuid() {
        return officeGuid;
    }

    /**
     * @param officeGuid the officeGuid to set
     */
    public void setOfficeGuid(String officeGuid) {
        this.officeGuid = officeGuid;
    }

    /**
     * @return the groupGuid
     */
    public String getGroupGuid() {
        return groupGuid;
    }

    /**
     * @param groupGuid the groupGuid to set
     */
    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    /**
     * @return the positionGuid
     */
    public String getPositionGuid() {
        return positionGuid;
    }

    /**
     * @param positionGuid the positionGuid to set
     */
    public void setPositionGuid(String positionGuid) {
        this.positionGuid = positionGuid;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the empId
     */
    public int getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @return the agencyId
     */
    public int getAgencyId() {
        return agencyId;
    }

    /**
     * @param agencyId the agencyId to set
     */
    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * @return the deptId
     */
    public int getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    /**
     * @return the officeId
     */
    public int getOfficeId() {
        return officeId;
    }

    /**
     * @param officeId the officeId to set
     */
    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
     * @return the syncFlag
     */
    public int getSyncFlag() {
        return syncFlag;
    }

    /**
     * @param syncFlag the syncFlag to set
     */
    public void setSyncFlag(int syncFlag) {
        this.syncFlag = syncFlag;
    }

}