package com.mini.web.util;

@SuppressWarnings("unused")
public interface IStatus {

    // Retain
    int CONTINUE = 100;
    int SWITCHING_PROTOCOLS = 101;

    // Success
    int OK = 200;
    int CREATED = 201;
    int ACCEPTED = 202;
    int NON_AUTHORITATIVE_INFORMATION = 203;
    int NO_CONTENT = 204;
    int RESET_CONTENT = 205;
    int PARTIAL_CONTENT = 206;

    // Redirection
    int MULTIPLE_CHOICES = 300;
    int MOVED_PERMANENTLY = 301;
    int MOVED_TEMPORARILY = 302;
    int FOUND = 302;
    int SEE_OTHER = 303;
    int NOT_MODIFIED = 304;
    int USE_PROXY = 305;
    int TEMPORARY_REDIRECT = 307;

    // Client Error
    int BAD_REQUEST = 400;
    int UNAUTHORIZED = 401;
    int PAYMENT_REQUIRED = 402;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int METHOD_NOT_ALLOWED = 405;
    int NOT_ACCEPTABLE = 406;
    int PROXY_AUTHENTICATION_REQUIRED = 407;
    int REQUEST_TIMEOUT = 408;
    int CONFLICT = 409;
    int GONE = 410;
    int LENGTH_REQUIRED = 411;
    int PRECONDITION_FAILED = 412;
    int REQUEST_ENTITY_TOO_LARGE = 413;
    int REQUEST_URI_TOO_LONG = 414;
    int UNSUPPORTED_MEDIA_TYPE = 415;
    int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    int EXPECTATION_FAILED = 417;

    // Server Error
    int INTERNAL_SERVER_ERROR = 500;
    int NOT_IMPLEMENTED = 501;
    int BAD_GATEWAY = 502;
    int SERVICE_UNAVAILABLE = 503;
    int GATEWAY_TIMEOUT = 504;
    int HTTP_VERSION_NOT_SUPPORTED = 505;

    /**
     * 普通类型错误码
     * <ul>
     *     <li>参数验证不通过</li>
     *     <li>程序一般异常</li>
     *     <li>其它普通错误</li>
     * </ul>
     */
    int NORMAL = 1000;

    /**
     * 用户未登录错误码
     * <ul>
     *     <li>用户未登录</li>
     *     <li>SESSION过期</li>
     *     <li>SESSION验证失败</li>
     * </ul>
     */
    int NO_LOGIN = 1100;

    /**
     * 用户没有该资源访问权限错误码
     * <ul>
     *     <li>没有访问该连接的权限</li>
     *     <li>没有访问该连接特定数据的权限</li>
     * </ul>
     */
    int NO_PERMISSION = 1200;


}
