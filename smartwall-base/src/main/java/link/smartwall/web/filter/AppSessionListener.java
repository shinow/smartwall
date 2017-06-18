package link.smartwall.web.filter;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session监听
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-7-17 lexloo
 *        </pre>
 */
public class AppSessionListener implements HttpSessionListener {
    /**
     * 日志记录
     */
    private static final Logger LOG = LoggerFactory.getLogger(AppSessionListener.class.getName());

    @Override
    public void sessionCreated(HttpSessionEvent evt) {
        HttpSession session = evt.getSession();
        ServletContext application = session.getServletContext();

        @SuppressWarnings("unchecked")
        Set<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute("sessions");

        if (sessions == null) {
            sessions = new HashSet<HttpSession>();

            application.setAttribute("sessions", sessions);
        }

        sessions.add(session);
        LOG.debug("创建session-连接数:" + sessions.size());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent evt) {
        HttpSession session = evt.getSession();
        // SessionUtils.logLogout(session);

        ServletContext application = session.getServletContext();
        @SuppressWarnings("unchecked")
        Set<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute("sessions");

        sessions.remove(session);
        LOG.debug("移除session-连接数:" + sessions.size());
    }
}
