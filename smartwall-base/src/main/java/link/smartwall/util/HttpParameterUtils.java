package link.smartwall.util;

import javax.servlet.http.HttpServletRequest;

import link.smartwall.base.http.Constants;
import link.smartwall.base.http.UserManager;
import link.smartwall.base.http.WebUser;
import link.smartwall.base.http.parameter.Parameters;
import link.smartwall.base.http.parameter.StandardParameters;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http协议，解析出对应的Parameter和StandardParameter
 * 
 * @version 1.0
 * @author <a href="ttan_style@sina.cn">ttan</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014年6月13日  ttan
 * </pre>
 */
public final class HttpParameterUtils {

    /**
     * 默认的构造方法
     */
    private HttpParameterUtils() {
        //
    }

    /**
     * 获取请求参数，增加企业Id与用户Id
     * 
     * @param req 请求
     * @return 参数串
     */
    public static Parameters getParamMap(HttpServletRequest req) {
        Parameters rtn = new Parameters();

        @SuppressWarnings({"rawtypes"})
        Enumeration em = req.getParameterNames();
        while (em.hasMoreElements()) {
            String paramName = StrUtils.c2str(em.nextElement(), "u");

            String paramValue = req.getParameter(paramName);

            // 后台处理参数都小写
            rtn.put(paramName.toLowerCase(), paramValue);
        }

        WebUser ac = (WebUser) req.getSession().getAttribute(Constants.SESSION_USER);
        if (ac != null) {
            rtn.put(StandardParameters.COMM_TENANT_ID, ac.getTenantId());
            rtn.put(StandardParameters.COMM_USER_ID, ac.getGuid());
        }

        return rtn;
    }

    /**
     * 获取基本请求参数
     * 
     * @param req 请求
     * @return 参数串
     */
    public static Parameters getBaseParamMap(HttpServletRequest req) {
        Parameters rtn = new Parameters();

        @SuppressWarnings({"rawtypes"})
        Enumeration em = req.getParameterNames();
        while (em.hasMoreElements()) {
            String paramName = StrUtils.c2str(em.nextElement(), "u");

            String paramValue = req.getParameter(paramName);

            // 后台处理参数都小写
            rtn.put(paramName.toLowerCase(), paramValue);
        }

        return rtn;
    }

    /**
     * 解析标准参数
     * 
     * @param parameters 参数列表
     * @param req 请求参数
     * @return 标准参数
     */
    public static StandardParameters parseStarndardParameters(Parameters parameters, HttpServletRequest req) {
        StandardParameters sp = new StandardParameters();

        WebUser wu = UserManager.getCurrentUser();
        sp.setTenantId(wu.getTenantId());
//        sp.setUserId(wu.getGuid());
        sp.setBusinessId(wu.getBusinessId());
        sp.setStaffid(wu.getStaffId());
        sp.setTimeSpan(0);
        // 后台数据录入
        sp.setClientType(StandardParameters.COMM_CLIENT_TYPE_WEB);

        return sp;
    }

    /**
     * 获取到标准参数
     * 
     * @return 标准参数
     */
    public static StandardParameters parseWebStarndardParameters() {
        StandardParameters sp = new StandardParameters();

        WebUser wu = UserManager.getCurrentUser();
        sp.setTenantId(wu.getTenantId());
//        sp.setUserId(wu.getId());
        sp.setBusinessId(wu.getBusinessId());
        sp.setStaffid(wu.getStaffId());
        sp.setTimeSpan(0);

        sp.setClientType(StandardParameters.COMM_CLIENT_TYPE_WEB);

        return sp;
    }

    /**
     * 获取所有参数Map
     * 
     * @param standardParamters 标准参数
     * @param parameters 加载参数
     * @return 所有参数
     */
    public static Map<String, Object> getAllParams(StandardParameters standardParamters, Parameters parameters) {
        Map<String, Object> rtn = new HashMap<String, Object>();

        rtn.putAll(standardParamters.getParameters());
        rtn.putAll(parameters.getParameters());

        return rtn;
    }
}
