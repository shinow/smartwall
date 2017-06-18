/**
 * SmartWall(2014)
 */
package link.smartwall.base.http;


/**
 * 用户管理
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤管家3.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014-3-12 lexloo
 * </pre>
 */
public final class UserManager {
    /**
     * 当前用户信息
     */
    private static final ThreadLocal<WebUser> CURRENT_USER = new ThreadLocal<WebUser>();

    /**
     * 构造函数
     */
    private UserManager() {
        // --
    }

    /**
     * 获取当前用户信息
     * 
     * @return 当前用户信息
     */
    public static WebUser getCurrentUser() {
        return (WebUser) CURRENT_USER.get();
    }

    /**
     * 设置当前用户信息
     * 
     * @param webUser 用户信息
     */
    public static void setCurrentUser(WebUser webUser) {
        CURRENT_USER.set(webUser);
    }
}
