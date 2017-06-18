/**
 * NextVisual
 */
package link.smartwall.basemodel;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
 * 数据改变信息,用于数据持久化
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014-7-9 lexloo
 * </pre>
 */
@Table("log_data_change")
public class DataChangeLogInfo {
    /**
     * 修改的表名
     */
    @Column("log_table")
    private String table;
    /**
     * 修改者名称
     */
    @Column("log_user")
    private String userName;
    /**
     * 修改这用户Id
     */
    @Column("log_userid")
    private int userId;
    /**
     * 企业Id
     */
    @Column
    private int tenantId;
    /**
     * 修改时间
     */
    @Column("change_time")
    private Date changeTime = new Date();
    /**
     * 日志信息
     */
    @Column("log_info")
    private String logInfo;

    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * @return the changeTime
     */
    public Date getChangeTime() {
        return changeTime;
    }

    /**
     * @param changeTime the changeTime to set
     */
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * @return the logInfo
     */
    public String getLogInfo() {
        return logInfo;
    }

    /**
     * @param logInfo the logInfo to set
     */
    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

}
