/**
 * 北京中航嘉城科技股份有限公司(2013)
 */
package link.smartwall.base.http.parameter;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求标准参数，每个请求必须包含
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-2-4 lexloo
 *        </pre>
 */
public class StandardParameters {
    /**
     * 服务代码
     */
    public static final String H_SERVICE = "_service_";
    /**
     * 租户Id
     */
    public static final String H_TENANT_ID = "_tenantid_";
    /**
     * 终端号码
     */
    public static final String H_USER_ID = "_userid_";
    // /**
    // * 版本
    // */
    // public static final String H_SERVICE_VERSION = "service version";

    /**
     * 员工Id
     */
    public static final String H_STAFF_ID = "_staffid_";
    /**
     * token
     */
    public static final String H_TOKEN = "_token_";
    /**
     * 提交时间间隔
     */
    public static final String H_TIMESPAN = "_timespan_";
    /**
     * 通用tenantId
     */
    public static final String COMM_TENANT_ID = "tenant_id";
    /**
     * 通用用户Id
     */
    public static final String COMM_USER_ID = "userid";
    /**
     * 通用用户Id
     */
    public static final String COMM_EMP_ID = "emp_id";
    /**
     * 对象属性用户Id
     */
    public static final String COMM_PROP_EMP_ID = "empid";
    /**
     * 行业Id
     */
    public static final String BUSINESS_ID = "businessid";
    /**
     * 通用员工
     */
    public static final String COMM_STAFF_ID = "staffid";
    /**
     * 数据上报类型 1：后台，2：手机端
     */
    public static final String COMM_CLIENT_TYPE = "clientType";
    /**
     * 离线上报时间间隔
     */
    public static final String COMM_TIME_SPANE = "timeSpan";
    /**
     * 部门Id
     */
    public static final String COMM_DPET_ID = "deptid";
    /**
     * 数据上报类型 1：后台
     */
    public static final int COMM_CLIENT_TYPE_WEB = 1;
    /**
     * 数据上报类型 2：手机端
     */
    public static final int COMM_CLIENT_TYPE_MOBILE = 2;
    /**
     * 是否要修改userid（true/false）， 默认为true此时修改empid userid propempid; false时不修改
     */
    public static final String USE_EMPID = "_use_empid_";

    /**
     * 服务代码
     */
    private String service;
    /**
     * 租户Id
     */
    private int tenantId;
    /**
     * 用户Id
     */
    private String userGuid;
    /**
     * 服务版本
     */
    // private String serviceVersion;
    /**
     * 字符串，认证码，为了服务的安全，必须是认证用户才能调用，token由登录时取得
     */
    private String token;
    /**
     * 行业Id
     */
    private int businessId;
    /**
     * 员工Id
     */
    private int staffid;
    /**
     * 数据提交间隔，用于处理缓存数据的日期，如果为0，使用当前时间
     */
    private long timeSpan;
    /**
     * 客户端类型
     */
    private int clientType;
    /**
     * 是否要修改userid（true/false）， 默认为true此时修改empid userid propempid; false时不修改
     */
    private boolean useEmpid;

    /**
     * @return the useEmpid
     */
    public boolean isUseEmpid() {
        return useEmpid;
    }

    /**
     * @param useEmpid the useEmpid to set
     */
    public void setUseEmpid(boolean useEmpid) {
        this.useEmpid = useEmpid;
    }

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
     * @return the userGuid
     */
    public String getUserGuid() {
        return userGuid;
    }

    /**
     * @param userGuid the userGuid to set
     */
    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    /**
     * 
     * @return 行业Id
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     * 设置行业Id
     * 
     * @param businessId 行业Id
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取所有(外部可能使用的参数，TenantId，UserId)参数
     * 
     * @return 参数Map
     */
    public Map<String, Object> getParameters() {
        Map<String, Object> rtn = new HashMap<String, Object>();

        rtn.put(StandardParameters.H_TENANT_ID, this.getTenantId());
        rtn.put(StandardParameters.H_USER_ID, this.getUserGuid());
        rtn.put(StandardParameters.COMM_TENANT_ID, this.getTenantId());
        rtn.put(StandardParameters.COMM_USER_ID, this.getUserGuid());
        rtn.put(StandardParameters.BUSINESS_ID, this.getBusinessId());
        rtn.put(StandardParameters.COMM_CLIENT_TYPE, this.getClientType());
        rtn.put(StandardParameters.USE_EMPID, this.isUseEmpid());

        return rtn;
    }

    /**
     * @return the staffid
     */
    public int getStaffid() {
        return staffid;
    }

    /**
     * @param staffid the staffid to set
     */
    public void setStaffid(int staffid) {
        this.staffid = staffid;
    }

    /**
     * @return the timeSpan
     */
    public long getTimeSpan() {
        return timeSpan;
    }

    /**
     * @param timeSpan the timeSpan to set
     */
    public void setTimeSpan(long timeSpan) {
        this.timeSpan = timeSpan;
    }

    /**
     * @return the clientType
     */
    public int getClientType() {
        return clientType;
    }

    /**
     * @param clientType the clientType to set
     */
    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

}
