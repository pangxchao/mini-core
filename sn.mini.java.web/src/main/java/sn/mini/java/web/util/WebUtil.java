
package sn.mini.java.web.util;

import sn.mini.java.util.digest.MD5Util;
import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.util.lang.TypeUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * WebUtil
 * @author XChao
 */
public class WebUtil {

    /**
     * 获取sessionId
     * @param session session 对象
     * @return session ID
     */
    public static String getSessionId(HttpSession session) {
        return session == null ? MD5Util.encode(UUID.randomUUID().toString()) : session.getId();
    }

    /**
     * 获取访问该项目的基础URL
     * @param request request 对象
     * @return 如 http://localhost:80
     */
    public static String getDoMain(HttpServletRequest request) {
        return String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtil.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtil.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtil.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    public static String getParameter(ServletContext context, String name) {
        return context == null ? null : context.getInitParameter(name);
    }

    public static Long getParameterToLong(ServletContext context, String name) {
        return TypeUtil.castToLong(getParameter(context, name));
    }

    public static Integer getParameterToInt(ServletContext context, String name) {
        return TypeUtil.castToInt(getParameter(context, name));
    }

    public static Double getParameterToDouble(ServletContext context, String name) {
        return TypeUtil.castToDouble(getParameter(context, name));
    }

    public static Float getParameterToFloat(ServletContext context, String name) {
        return TypeUtil.castToFloat(getParameter(context, name));
    }

    public static long getParameterToLongVal(ServletContext context, String name) {
        return TypeUtil.castToLongValue(getParameter(context, name));
    }

    public static int getParameterToIntVal(ServletContext context, String name) {
        return TypeUtil.castToIntValue(getParameter(context, name));
    }

    public static double getParameterToDoubleVal(ServletContext context, String name) {
        return TypeUtil.castToDoubleValue(getParameter(context, name));
    }

    public static float getParameterToFloatVal(ServletContext context, String name) {
        return TypeUtil.castToFloatValue(getParameter(context, name));
    }

    //////////////////////////////////////////////////////////////////

    public static String getParameter(HttpServlet servlet, String name) {
        return servlet == null ? null : servlet.getInitParameter(name);
    }

    public static Long getParameterToLong(HttpServlet servlet, String name) {
        return TypeUtil.castToLong(getParameter(servlet, name));
    }

    public static Integer getParameterToInt(HttpServlet servlet, String name) {
        return TypeUtil.castToInt(getParameter(servlet, name));
    }

    public static Double getParameterToDouble(HttpServlet servlet, String name) {
        return TypeUtil.castToDouble(getParameter(servlet, name));
    }

    public static Float getParameterToFloat(HttpServlet servlet, String name) {
        return TypeUtil.castToFloat(getParameter(servlet, name));
    }

    public static long getParameterToLongVal(HttpServlet servlet, String name) {
        return TypeUtil.castToLongValue(getParameter(servlet, name));
    }

    public static int getParameterToIntVal(HttpServlet servlet, String name) {
        return TypeUtil.castToIntValue(getParameter(servlet, name));
    }

    public static double getParameterToDoubleVal(HttpServlet servlet, String name) {
        return TypeUtil.castToDoubleValue(getParameter(servlet, name));
    }

    public static float getParameterToFloatVal(HttpServlet servlet, String name) {
        return TypeUtil.castToFloatValue(getParameter(servlet, name));
    }

    //////////////////////////////////////////////////////////////////

    public static String getParameter(HttpServletRequest request, String name) {
        return request == null ? null : request.getParameter(name);
    }

    public static Long getParameterToLong(HttpServletRequest request, String name) {
        return TypeUtil.castToLong(getParameter(request, name));
    }

    public static Integer getParameterToInt(HttpServletRequest request, String name) {
        return TypeUtil.castToInt(getParameter(request, name));
    }

    public static Double getParameterToDouble(HttpServletRequest request, String name) {
        return TypeUtil.castToDouble(getParameter(request, name));
    }

    public static Float getParameterToFloat(HttpServletRequest request, String name) {
        return TypeUtil.castToFloat(getParameter(request, name));
    }

    public static long getParameterToLongVal(HttpServletRequest request, String name) {
        return TypeUtil.castToLongValue(getParameter(request, name));
    }

    public static int getParameterToIntVal(HttpServletRequest request, String name) {
        return TypeUtil.castToIntValue(getParameter(request, name));
    }

    public static double getParameterToDoubleVal(HttpServletRequest request, String name) {
        return TypeUtil.castToDoubleValue(getParameter(request, name));
    }

    public static float getParameterToFloatVal(HttpServletRequest request, String name) {
        return TypeUtil.castToFloatValue(getParameter(request, name));
    }

    //////////////////////////////////////////////////////////////////

    public static void setAttribute(ServletContext context, String name, Object value) {
        if (context != null) context.setAttribute(name, value);
    }

    public static void setAttribute(HttpSession session, String name, Object value) {
        if (session != null) session.setAttribute(name, value);
    }

    public static void setAttribute(HttpServletRequest request, String name, Object value) {
        if (request != null) request.setAttribute(name, value);
    }

    public static <T> T getAttribute(ServletContext context, String name, Class<T> clazz) {
        return context == null ? null : clazz.cast(context.getAttribute(name));
    }

    public static <T> T getAttribute(HttpSession session, String name, Class<T> clazz) {
        return session == null ? null : clazz.cast(session.getAttribute(name));
    }

    public static <T> T getAttribute(HttpServletRequest request, String name, Class<T> clazz) {
        return request == null ? null : clazz.cast(request.getAttribute(name));
    }
}
