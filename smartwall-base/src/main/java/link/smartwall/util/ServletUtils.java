/**
 * CellSoft 2010
 */
package link.smartwall.util;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端访问Servlet辅助类
 * 
 * @author lexloo
 * @version 1.0 2010-8-26
 * @since 销售宝 2.0
 */
public final class ServletUtils {
    /**
     * 默认缓冲区大小
     */
    private static final int DEF_BUFFER_SIZE = 1024;

    /**
     * constructor
     */
    private ServletUtils() {
        // Utility class
    }

    /**
     * 参数转化为Map
     * 
     * @param request 请求
     * @return 参数Map
     */
    public static Map<String, Object> parametersToMap(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        @SuppressWarnings("rawtypes")
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();

            String[] values = request.getParameterValues(name);

            if (values.length == 1) { // 处理单值
                String value = values[0];

                if (!StrUtils.isEmpty(value)) {
                    result.put(name, value);
                }
            }
        }

        return result;
    }

    /**
     * 参数转化为Map,名称小写
     * 
     * @param request 请求
     * @return 参数Map
     */
    public static Map<String, Object> parametersToMap2(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        @SuppressWarnings("rawtypes")
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();

            String[] values = request.getParameterValues(name);

            if (values.length == 1) { // 处理单值
                String value = values[0];

                if (!StrUtils.isEmpty(value)) {
                    result.put(name.toLowerCase(), value);
                }
            }
        }

        return result;
    }

    /**
     * 获取Servlet请求文档流字符串
     * 
     * @param request 请求
     * @return 文档流字符串
     */
    public static String getInputDocString(HttpServletRequest request) {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = new BufferedInputStream(request.getInputStream());
            os = new ByteArrayOutputStream(DEF_BUFFER_SIZE);
            byte[] data = new byte[DEF_BUFFER_SIZE];
            int n = -1;
            while ((n = is.read(data)) != -1) {
                os.write(data, 0, n);
            }

            os.flush();
            return new String(os.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    /**
     * 从Servlet读取字符串
     * 
     * @param servletURL Servlet地址
     * @param method 访问方法: GET、POST
     * @return servlet返回字符串
     */
    public static String readStringFromServlet(String servletURL, String method) {
        try {
            URL url = new URL(servletURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (StrUtils.isEmpty(method)) {
                con.setRequestMethod("GET");
            } else {
                con.setRequestMethod(method);
            }

            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = con.getInputStream();

            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            String result = baos.toString();
            baos.close();
            is.close();

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * 处理查询参数中的 '&',如果XML中包含查询地址必须转码
     * 
     * @param queryString 查询地址字符串
     * @return 解码后的字符串
     */
    public static String escapeQueryString(String queryString) {
        return queryString.replaceAll("&", "&amp;");
    }

    /**
     * 获取应用的上下文
     * 
     * @param request 请求
     * @return 应用上下文
     */
    public static String getServerPath(HttpServletRequest request) {
        StringBuffer appPath = new StringBuffer("http://");
        appPath.append(request.getLocalAddr() + ":");
        appPath.append(request.getLocalPort());
        appPath.append(request.getContextPath());
        appPath.append("/");
        return appPath.toString();
    }

    /**
     * 获取request中指定参数的参数值。
     * 
     * @param request http request
     * @param paramName 参数名
     * @param defaultValue 默认值
     * @return 参数值。或者默认值
     */
    public static String getParameter(HttpServletRequest request, String paramName, String defaultValue) {
        String value = request.getParameter(paramName);
        if (StrUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 获取客户端Ip
     * 
     * @param request 请求
     * @return 客户端Ip
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
