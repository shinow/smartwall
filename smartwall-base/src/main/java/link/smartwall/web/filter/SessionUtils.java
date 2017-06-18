package link.smartwall.web.filter;
///**
// * SmartWall(2015)
// */
//package com.itfsm.web.filter;
//
//import java.util.Date;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.nutz.dao.Chain;
//import org.nutz.dao.Cnd;
//import org.nutz.dao.Dao;
//
//import com.dcits.xsb.db.DbUtils;
//import com.itfsm.base.http.Constants;
//import com.itfsm.util.NumberUtils;
//import com.itfsm.util.ServletUtils;
//
///**
// * Session操作实用类
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since 外勤助手 4.0.0
// * 
// *        <pre>
// * 历史：
// *      2015年12月16日 lexloo * 建立
// * </pre>
// */
//public final class SessionUtils {
//
//    /**
//     * constructor
//     */
//    private SessionUtils() {
//        // Utility class
//    }
//
//    /**
//     * 记录登出信息
//     * 
//     * @param session session信息
//     */
//    public static void logLogout(HttpSession session) {
//        int loginId = NumberUtils.c2int(session.getAttribute(Constants.LOGIN_ID), 0);
//        if (loginId > 0) {
//            Dao dao = DbUtils.newDao();
//            dao.update(LogWebLogin.class, Chain.make("logoutTime", new Date()), Cnd.where("id", "=", loginId));
//        }
//
//        session.removeAttribute(Constants.LOGIN_ID);
//        session.removeAttribute(Constants.SESSION_USER);
//    }
//
//    /**
//     * 记录登入信息
//     * 
//     * @param tenantId 企业id
//     * @param userId 用户Id
//     * @param request 请求
//     * @param dao 数据访问对象
//     */
//    public static void logLogin(int tenantId, int userId, HttpServletRequest request, Dao dao) {
//        LogWebLogin lml = new LogWebLogin();
//        lml.setIp(ServletUtils.getClientIp(request));
//        lml.setUserId(userId);
//        lml.setTenantId(tenantId);
//        dao.insert(lml);
//
//        request.getSession().setAttribute(Constants.LOGIN_ID, lml.getId());
//    }
//}
