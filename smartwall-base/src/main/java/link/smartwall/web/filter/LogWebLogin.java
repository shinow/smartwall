/**
 * SmartWall(2013)
 */
/**
 * SmartWall(2013)
 */
package link.smartwall.web.filter;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

/**
 * 后台登陆日志对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-7-17 lexloo
 * </pre>
 */
@Table("log_web_login")
public class LogWebLogin {
    /**
     * 主键Id
     */
    @Column
    @Id(auto = false)
    @Prev(@SQL("select log_web_login_s.nextval from dual"))
    private int id;
    /**
     * 业务员Id
     */
    @Column
    private int userId;
    /**
     * 租户Id
     */
    @Column
    private int tenantId;
    /**
     * 登录时间
     */
    @Column
    private Date loginTime = new Date();

    /**
     * IP地址
     */
    @Column("ip_addr")
    private String ip;

    /**
     * 退出时间
     */
    @Column("logoutTime")
    private Date logoutTime;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the loginTime
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime the loginTime to set
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the logoutTime
     */
    public Date getLogoutTime() {
        return logoutTime;
    }

    /**
     * @param logoutTime the logoutTime to set
     */
    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }
}
