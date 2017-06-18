/**
 * 北京中航嘉城科技股份有限公司(2013) 
 */
package link.smartwall.base.http.parameter;

/**
 * 请求头信息
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-2-4 lexloo
 * </pre>
 */
public class Header {
    /**
     * 服务代码
     */
    public static final String H_SERVICE = "service";
    /**
     * 租户Id
     */
    public static final String H_TENANT_ID = "tenantId";
    /**
     * 终端号码
     */
    public static final String H_MOBILE = "mobile";
    /**
     * 版本
     */
    public static final String H_SERVICE_VERSION = "service version";
    /**
     * token
     */
    public static final String H_TOKEN = "token";
    /**
     * Client
     */
    public static final String H_CLIENT = "client";
    /**
     * host
     */
    public static final String H_HOST = "host";
    /**
     * userAgent
     */
    public static final String H_USER_AGENT = "userAgent";
    /**
     * 服务代码
     */
    private String service;
    /**
     * 租户Id
     */
    private int tenantId;
    /**
     * 终端号码
     */
    private String mobile;
    /**
     * 服务版本
     */
    private String serviceVersion;
    /**
     * 字符串，认证码，为了服务的安全，必须是认证用户才能调用，token由登录时取得
     */
    private String token;
    /**
     * 客户端信息
     */
    private String client;
    /**
     * 客户端地址
     */
    private String host;
    /**
     * 客户代理信息
     */
    private String userAgent;

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
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
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * @param serviceVersion the serviceVersion to set
     */
    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    /**
     * @return the serviceVersion
     */
    public String getServiceVersion() {
        return serviceVersion;
    }
}
