/**
 * 北京中航嘉城科技股份有限公司(2013)
 */
package link.smartwall.base.http;

/**
 * WEB应用常量
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-3-8 lexloo
 * </pre>
 */
public interface Constants {
    /**
     *  SESSION-子站点
     */
    String SESSION_WEB_SITE = "site";
    /**
     * SESSION常量-用户信息
     */
    String SESSION_USER = "user";
    /**
     * SESSION-权限信息
     */
    String SESSION_RIGHTS = "rights";
    /**
     * 租户代码
     */
    String TENANT_CODE = "tenantCode";
    /**
     * 行业Id
     */
    String BUSINESS_ID = "businessId";
    /**
     * 系统主题
     */
    String THEME = "theme";
    /**
     * 登录记录Id，用于记录退出时间
     */
    String LOGIN_ID = "loginid";

    /**
     * 
     * 暂时用于处理checkstyle错误，需要删掉
     */
    void empty();
}
