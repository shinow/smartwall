package link.smartwall.base.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.AbstractObject;

import java.util.Date;

/**
 * TODO
 * Created by yty on 2017/3/21 ${time}.
 */
@Table("sfa_log_user_last_pos")
public class LogUserLastPos extends AbstractObject {
    @Column("emp_guid")
    @Name
    private String empGuid;
    @Column("datatype")
    private Integer dataType;
    @Column("sourceid")
    private Integer sourceId;
    @Column
    private double longitude;
    @Column
    private double latitude;
    @Column("emp_enclosure_status")
    private Integer empEnclosureStatus;
    @Column
    private String address;
    @Column("tenant_id")
    private Integer tenantId;
    @Column("data_time")
    private Date dataTime;
    @Column("data_is_deleted")
    private Integer dataIsDeleted;
    @Column("modify_flag")
    private Integer modifyFlag;
    @Column("modify_time")
    private Date modifyTime;

    public String getEmpGuid() {
        return empGuid;
    }

    public void setEmpGuid(String empGuid) {
        this.empGuid = empGuid;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Integer getEmpEnclosureStatus() {
        return empEnclosureStatus;
    }

    public void setEmpEnclosureStatus(Integer empEnclosureStatus) {
        this.empEnclosureStatus = empEnclosureStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getDataIsDeleted() {
        return dataIsDeleted;
    }

    public void setDataIsDeleted(Integer dataIsDeleted) {
        this.dataIsDeleted = dataIsDeleted;
    }

    public Integer getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(Integer modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
