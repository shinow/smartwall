package link.smartwall.basemodel;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Column;

import link.smartwall.base.http.UserManager;
import link.smartwall.base.http.WebUser;
import link.smartwall.util.StrUtils;

import java.util.Date;

/**
 * sfa基础实体类
 *
 * @author caisj
 */
public class BaseEntity extends AbstractGuidObject {
    @Column("tenant_id")
    private Integer tenantid;

    @Column("data_time")
    private Date dataTime;

    @Column("data_is_deleted")
    private int dataIsDeleted;

    @Column("modify_flag")
    private int modifyFlag;

    @Column("modify_time")
    private Date modifyTime;

    public Integer getTenantid() {
        return tenantid;
    }

    public void setTenantid(Integer tenantid) {
        this.tenantid = tenantid;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public int getDataIsDeleted() {
        return dataIsDeleted;
    }

    public void setDataIsDeleted(int dataIsDeleted) {
        this.dataIsDeleted = dataIsDeleted;
    }

    public int getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(int modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 对共有属性进行赋值
     * <p>
     * 对于其它需要在入库前初始化的属性，子类override该方法，进行赋值。
     * </p>
     */
    public void initCommonFields() {
        setGuid(StrUtils.uuid());
        setDataTime(new Date());
        setDataIsDeleted(0);
        setModifyFlag(1);
        setModifyTime(new Date());
        WebUser webUser = UserManager.getCurrentUser();
        if (webUser != null && (tenantid == null || tenantid == 0)) {
            setTenantid(webUser.getTenantId());
        }
    }

    /**
     * 修改modifyFlag和modifyTime两个字段
     */
    public void updateModifyFields() {
        modifyTime = new Date();
    }

    @Override
    public void beforeUpdate(Dao dao, AbstractObject prevObj) {
        this.tenantid = ((BaseEntity) prevObj).getTenantid();
        this.dataTime = ((BaseEntity) prevObj).getDataTime();
        this.modifyFlag = ((BaseEntity) prevObj).getModifyFlag() + 1;
    }
}
