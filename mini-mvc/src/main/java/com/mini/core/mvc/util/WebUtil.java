package com.mini.core.mvc.util;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.lang.String.format;

/**
 * WebUtil
 *
 * @author XChao
 */
public final class WebUtil {
    /**
     * 获取当前请求路径
     *
     * @param request request 对象
     * @return 例如：/index.jsp
     */
    public static String getRequestUri(@NotNull HttpServletRequest request) {
        final int length = request.getContextPath().length();
        return request.getRequestURI().substring(length);
    }

    /**
     * 获取当前请求路径
     *
     * @param request request 对象
     * @return 例如：index.jsp
     */
    public static String getRequestPath(@NotNull HttpServletRequest request) {
        final int length = request.getContextPath().length();
        return request.getRequestURI().substring(length + 1);
    }

    /**
     * 获取客户端IP地址
     *
     * @param request 请求数据
     * @return IP地址
     */
    public static String getIpAddress(@NotNull HttpServletRequest request) {
        return Optional.ofNullable(request.getRemoteAddr()).filter(it -> {
            return !it.isBlank(); //
        }).orElse("127.0.0.1");
    }

    /**
     * 是否为ajax请求
     *
     * @param request 请求数据
     * @return true-是
     */
    public static boolean isAjaxRequest(@NotNull HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
    }

    /**
     * 获取访问该项目的基础URL
     *
     * @param request request 对象
     * @return 如：http://localhost:8080
     */
    public static String getDomain(@NotNull HttpServletRequest request) {
        String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
        return format("%s://%s%s", request.getScheme(), request.getServerName(), port);
    }


    /**
     * 根据 Request 获取当前请求的绝对路径
     *
     * @param request HttpServletRequest 对象
     * @return 如：http://localhost:8080/user/login.htm
     */
    public static String getAbsoluteUrl(@NotNull HttpServletRequest request) {
        return getDomain(request) + request.getRequestURI();
    }
}