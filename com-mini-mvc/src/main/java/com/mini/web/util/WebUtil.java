
package com.mini.web.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

import static com.mini.util.StringUtil.equalsIgnoreCase;
import static com.mini.util.StringUtil.isBlank;
import static com.mini.util.TypeUtil.*;

/**
 * WebUtil
 * @author XChao
 */
public final class WebUtil {
    /**
     * 获取 ServletContext 的属性并转换成指定类型
     * @param context ServletContext 对象
     * @param name    属性名称
     * @param clazz   返回类型
     * @return 返回对象
     */
    public static <T> T getAttribute(ServletContext context, String name, Class<T> clazz) {
        return context == null ? null : clazz.cast(context.getAttribute(name));
    }

    /**
     * 设置 ServletContext 的属性
     * @param context ServletContext 对象
     * @param name    属性名称
     * @param value   属性值
     */
    public static void setAttribute(ServletContext context, String name, Object value) {
        if (context != null) context.setAttribute(name, value);
    }

    /**
     * 获取 ServletContext 初始化参数
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static String getInitParameter(ServletContext context, String name) {
        return context == null ? null : context.getInitParameter(name);

    }

    /**
     * 获取 ServletContext 初始化参数转换成 long 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static long getInitParameterToLongVal(ServletContext context, String name) {
        return context == null ? 0L : castToLongVal(context.getInitParameter(name));

    }

    /**
     * 获取 ServletContext 初始化参数转换成 Long 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Long getInitParameterToLong(ServletContext context, String name) {
        return context == null ? null : castToLong(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 int 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static int getInitParameterToIntVal(ServletContext context, String name) {
        return context == null ? 0 : castToIntVal(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 Integer 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Integer getInitParameterToInt(ServletContext context, String name) {
        return context == null ? null : castToInt(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 short 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static short getInitParameterToShortVal(ServletContext context, String name) {
        return context == null ? 0 : castToShortVal(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 Short 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Short getInitParameterToShort(ServletContext context, String name) {
        return context == null ? null : castToShort(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 byte 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static byte getInitParameterToByteVal(ServletContext context, String name) {
        return context == null ? 0 : castToByteVal(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 Byte 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Byte getInitParameterToByte(ServletContext context, String name) {
        return context == null ? null : castToByte(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 double 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static double getInitParameterToDoubleVal(ServletContext context, String name) {
        return context == null ? 0 : castToDoubleVal(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 Double 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Double getInitParameterToDouble(ServletContext context, String name) {
        return context == null ? null : castToDouble(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 float 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static float getInitParameterToFloatVal(ServletContext context, String name) {
        return context == null ? 0 : castToFloatVal(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 Float 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Float getInitParameterToFloat(ServletContext context, String name) {
        return context == null ? null : castToFloat(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 boolean 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static boolean getInitParameterToBoolVal(ServletContext context, String name) {
        return context != null && castToBoolVal(context.getInitParameter(name));
    }

    /**
     * 获取 ServletContext 初始化参数转换成 Boolean 类型
     * @param context ServletContext 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Boolean getInitParameterToBool(ServletContext context, String name) {
        return context == null ? null : castToBool(context.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static String getInitParameter(HttpServlet servlet, String name) {
        return servlet == null ? null : servlet.getInitParameter(name);

    }

    /**
     * 获取 HttpServlet 初始化参数转换成 long 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static long getInitParameterToLongVal(HttpServlet servlet, String name) {
        return servlet == null ? 0L : castToLongVal(servlet.getInitParameter(name));

    }

    /**
     * 获取 HttpServlet 初始化参数转换成 Long 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Long getInitParameterToLong(HttpServlet servlet, String name) {
        return servlet == null ? null : castToLong(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 int 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static int getInitParameterToIntVal(HttpServlet servlet, String name) {
        return servlet == null ? 0 : castToIntVal(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 Integer 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Integer getInitParameterToInt(HttpServlet servlet, String name) {
        return servlet == null ? null : castToInt(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 short 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static short getInitParameterToShortVal(HttpServlet servlet, String name) {
        return servlet == null ? 0 : castToShortVal(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 Short 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Short getInitParameterToShort(HttpServlet servlet, String name) {
        return servlet == null ? null : castToShort(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 byte 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static byte getInitParameterToByteVal(HttpServlet servlet, String name) {
        return servlet == null ? 0 : castToByteVal(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 Byte 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Byte getInitParameterToByte(HttpServlet servlet, String name) {
        return servlet == null ? null : castToByte(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 double 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static double getInitParameterToDoubleVal(HttpServlet servlet, String name) {
        return servlet == null ? 0 : castToDoubleVal(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 Double 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Double getInitParameterToDouble(HttpServlet servlet, String name) {
        return servlet == null ? null : castToDouble(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 float 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static float getInitParameterToFloatVal(HttpServlet servlet, String name) {
        return servlet == null ? 0 : castToFloatVal(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 Float 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Float getInitParameterToFloat(HttpServlet servlet, String name) {
        return servlet == null ? null : castToFloat(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 boolean 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static boolean getInitParameterToBoolVal(HttpServlet servlet, String name) {
        return servlet != null && castToBoolVal(servlet.getInitParameter(name));
    }

    /**
     * 获取 HttpServlet 初始化参数转换成 Boolean 类型
     * @param servlet HttpServlet 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Boolean getInitParameterToBool(HttpServlet servlet, String name) {
        return servlet == null ? null : castToBool(servlet.getInitParameter(name));
    }


    /**
     * 获取 HttpServletRequest 和属性并转换成指定类型
     * @param request HttpServletRequest 对象
     * @param name    属性名称
     * @param clazz   返回类型
     * @return 返回对象
     */
    public static <T> T getAttribute(HttpServletRequest request, String name, Class<T> clazz) {
        return request == null ? null : clazz.cast(request.getAttribute(name));
    }

    /**
     * 设置 HttpServletRequest 的属性
     * @param request HttpServletRequest 对象
     * @param name    属性名称
     * @param value   属性值
     */
    public static void setAttribute(HttpServletRequest request, String name, Object value) {
        if (request != null) request.setAttribute(name, value);
    }

    /**
     * 获取 HttpServlet 初始化参数
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static String getParameter(HttpServletRequest request, String name) {
        return request == null ? null : request.getParameter(name);

    }

    /**
     * 获取 HttpServletRequest 参数转换成 long 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static long getParameterToLongVal(HttpServletRequest request, String name) {
        return request == null ? 0L : castToLongVal(request.getParameter(name));

    }

    /**
     * 获取 HttpServletRequest 参数转换成 Long 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Long getParameterToLong(HttpServletRequest request, String name) {
        return request == null ? null : castToLong(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 int 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static int getParameterToIntVal(HttpServletRequest request, String name) {
        return request == null ? 0 : castToIntVal(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Integer 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Integer getParameterToInt(HttpServletRequest request, String name) {
        return request == null ? null : castToInt(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 short 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static short getParameterToShortVal(HttpServletRequest request, String name) {
        return request == null ? 0 : castToShortVal(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Short 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Short getParameterToShort(HttpServletRequest request, String name) {
        return request == null ? null : castToShort(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 byte 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static byte getParameterToByteVal(HttpServletRequest request, String name) {
        return request == null ? 0 : castToByteVal(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Byte 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Byte getParameterToByte(HttpServletRequest request, String name) {
        return request == null ? null : castToByte(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 double 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static double getParameterToDoubleVal(HttpServletRequest request, String name) {
        return request == null ? 0 : castToDoubleVal(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Double 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Double getParameterToDouble(HttpServletRequest request, String name) {
        return request == null ? null : castToDouble(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 float 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static float getParameterToFloatVal(HttpServletRequest request, String name) {
        return request == null ? 0 : castToFloatVal(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Float 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Float getParameterToFloat(HttpServletRequest request, String name) {
        return request == null ? null : castToFloat(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 boolean 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static boolean getParameterToBoolVal(HttpServletRequest request, String name) {
        return request != null && castToBoolVal(request.getParameter(name));
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Boolean 类型
     * @param request HttpServletRequest 对象
     * @param name    参数名称
     * @return 获取结果
     */
    public static Boolean getParameterToBool(HttpServletRequest request, String name) {
        return request == null ? null : castToBool(request.getParameter(name));
    }


    /**
     * 获取sessionId
     * @param session session 对象
     * @return session ID
     */
    public static String getSessionId(HttpSession session) {
        if (session != null) return session.getId();
        return UUID.randomUUID().toString();
    }

    /**
     * 获取 HttpSession 的属性并转换成指定类型
     * @param session HttpSession 对象
     * @param name    属性名称
     * @param clazz   返回类型
     * @return 返回对象
     */
    public static <T> T getAttribute(HttpSession session, String name, Class<T> clazz) {
        return session == null ? null : clazz.cast(session.getAttribute(name));
    }

    /**
     * 设置 HttpSession 的属性
     * @param session HttpSession 对象
     * @param name    属性名称
     * @param value   属性值
     */
    public static void setAttribute(HttpSession session, String name, Object value) {
        if (session != null) session.setAttribute(name, value);
    }

    /**
     * 获取访问该项目的基础URL
     * @param req request 对象
     * @return 如 http://localhost:80
     */
    public static String getDoMain(HttpServletRequest req) {
        return String.format("%s://%s:%d", req.getScheme(), //
                req.getServerName(), req.getServerPort());
    }

    /**
     * 获取客户端IP地址
     * @param request 请求数据
     * @return IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (isBlank(ip) || equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(ip) || equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isBlank(ip) || equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isBlank(ip) || equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("X-Real-IP");
        }
        if (equalsIgnoreCase(ip, "unknown")) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip == null ? "127.0.0.1" : ip;
    }
}
