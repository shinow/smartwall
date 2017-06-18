package link.smartwall.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import link.smartwall.base.http.Constants;
import link.smartwall.base.http.UserManager;
import link.smartwall.base.http.WebUser;

/**
 * 
 * @author lexloo
 *
 */
public class CurrentUserFilter implements Filter {

    /**
     * 日期记录器
     */
    private static final Logger LOG = LoggerFactory.getLogger(CurrentUserFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpSession session = request.getSession(false);
        if (session != null) {
            WebUser webUser = (WebUser) session.getAttribute(Constants.SESSION_USER);
            UserManager.setCurrentUser(webUser);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterChain) throws ServletException {
        LOG.info("初始化当前用户过滤器");
    }

    @Override
    public void destroy() {
        LOG.info("释放当前用户过滤器");
    }
}
