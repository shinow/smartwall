package link.smartwall.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import link.smartwall.base.http.Constants;

/**
 * 企业登录过滤器.过滤地址为*.so：<br/>
 * 
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-4-6 lexloo
 *        </pre>
 */
public class TenantFilter implements Filter {

    /**
     * 日期记录器
     */
    private static final Logger LOG = LoggerFactory.getLogger(TenantFilter.class.getName());
    /**
     * 重定向地址
     */
    private String redirectURL = null;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getServletPath();

        String tenantCode = path.substring(1, path.indexOf("."));

        request.getSession(true).setAttribute(Constants.TENANT_CODE, tenantCode);
        request.getSession(true).setAttribute(Constants.SESSION_USER, null);
        servletRequest.getRequestDispatcher(redirectURL).forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOG.info("release tenant filter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("initialize tenant filter");

        redirectURL = filterConfig.getInitParameter("redirectURL");
    }
}
