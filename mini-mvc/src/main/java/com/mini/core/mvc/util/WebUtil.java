package com.mini.core.mvc.util;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.lang.String.format;

/**
 * WebUtil
 *
 * @author XChao
 */
public final class WebUtil {
    public static boolean isAjaxRequest(@NotNull HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request //
                .getHeader("x-requested-with"));
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
     * 获取访问当前项目请求的路径
     *
     * @param request request 对象
     * @return 路径
     */
    public static String getRequestPath(@NotNull HttpServletRequest request) {
        return Optional.ofNullable(request.getRequestURI()).map(it -> {
            return it.replaceFirst(request.getContextPath(), ""); //
        }).orElse("");
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
}