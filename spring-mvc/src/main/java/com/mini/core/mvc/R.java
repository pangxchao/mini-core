package com.mini.core.mvc;

import com.mini.core.mvc.model.JsonModel;
import com.mini.core.mvc.model.PageModel;
import com.mini.core.mvc.model.StreamModel;
import org.springframework.core.DefaultParameterNameDiscoverer;

public final class R {
    /**
     * <h2>Error code (100)</h2>
     * 表示请求成功
     */
    public static final int SUCCESS = 0;

    /**
     * <h2>Status code (100)</h2>
     * 表示客户端可以继续。
     */
    public static final int CONTINUE = 100;

    /**
     * <h2>Status code (101)</h2>
     * 服务器将遵从客户的请求转换到另外一种协议
     */
    public static final int SWITCHING_PROTOCOLS = 101;

    /**
     * <h2> Status code (200) </h2>
     * 表示请求正常成功。
     */
    public static final int OK = 200;

    /**
     * <h2> Status code (201) </h2>
     * 指示请求成功并在服务器上创建了新资源。
     */
    public static final int CREATED = 201;

    /**
     * <h2> Status code (202) </h2>
     * 指示已接受请求正在处理，但未完成。
     */
    public static final int ACCEPTED = 202;

    /**
     * <h2> Status code (203) </h2>
     * 表示客户端提供的元信息不是来自服务器。
     */
    public static final int NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * <h2> Status code (204) </h2>
     * 表示请求成功，但没有要返回的新信息。
     */
    public static final int NO_CONTENT = 204;

    /**
     * <h2> Status code (205) </h2>
     * 指示代理应重置导致发送请求的文档视图。
     */
    public static final int RESET_CONTENT = 205;

    /**
     * <h2> Status code (206) </h2>
     * 表示服务器已完成对资源的部分获取请求。
     */
    public static final int PARTIAL_CONTENT = 206;

    /**
     * <h2> Status code (300) </h2>
     * 指示请求的资源对应于一组表示中的任何一个，每个表示都有自己的特定位置。
     */
    public static final int MULTIPLE_CHOICES = 300;

    /**
     * <h2> Status code (301) </h2>
     * 指示资源已永久移动到新位置，并且将来的引用应在其请求中使用新的URI。
     */
    public static final int MOVED_PERMANENTLY = 301;

    /**
     * <h2> Status code (302) </h2>
     * 指示资源已临时移动到另一个位置，但将来的引用仍应使用原始uri来访问资源。
     * 保留此定义是为了向后兼容。找到SC_FOUND 现在是首选定义。
     */
    public static final int MOVED_TEMPORARILY = 302;

    /**
     * <h2> Status code (302) </h2>
     * 指示资源暂时位于不同的uri下。由于有时可能会更改重定向，
     * 客户端应继续使用请求URI来表示将来的请求（HTTP/1.1），因此建议使用此变量。
     */
    public static final int FOUND = 302;

    /**
     * <h2> Status code (303) </h2>
     * 指示可以在不同的uri下找到对请求的响应。
     */
    public static final int SEE_OTHER = 303;

    /**
     * <h2> Status code (304) </h2>
     * 指示条件获取操作发现资源可用且未修改。
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * <h2> Status code (305) </h2>
     * indicating that the requested resource
     * *MUST* be accessed through the proxy given by the
     * `*Location*` field.
     */
    public static final int USE_PROXY = 305;

    /**
     * <h2> Status code (307) </h2>
     * 指示必须通过位置字段提供的代理访问请求的资源。
     */
    public static final int TEMPORARY_REDIRECT = 307;

    /**
     * <h2> Status code (400) </h2>
     * 指示客户端发送的请求在语法上不正确。
     */
    public static final int BAD_REQUEST = 400;

    /**
     * <h2> Status code (401) </h2>
     * 指示请求需要http身份验证。
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * <h2> Status code (402) </h2>
     * 保留以备将来使用。
     */
    public static final int PAYMENT_REQUIRED = 402;

    /**
     * <h2> Status code (403) </h2>
     * 表示服务器理解请求但拒绝执行。
     */
    public static final int FORBIDDEN = 403;

    /**
     * <h2> Status code (404) </h2>
     * 表示请求的资源不可用。
     */
    public static final int NOT_FOUND = 404;

    /**
     * <h2> Status code (405) </h2>
     * 指示由请求uri标识的资源不允许使用请求行中指定的方法。
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * <h2> Status code (406) </h2>
     * 指示由请求标识的资源只能根据请求中发送的接受头生成内容特征不可接受的响应实体。
     */
    public static final int NOT_ACCEPTABLE = 406;

    /**
     * <h2> Status code (407) </h2>
     * 表示客户机必须首先使用代理对自己进行身份验证。
     */
    public static final int PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * <h2> Status code (408) </h2>
     * 指示客户端在服务器准备等待的时间内未生成请求。
     */
    public static final int REQUEST_TIMEOUT = 408;

    /**
     * <h2> Status code (409) </h2>
     * 指示由于与资源的当前状态冲突而无法完成请求。
     */
    public static final int CONFLICT = 409;

    /**
     * <h2> Status code (410) </h2>
     * 表示资源在服务器上不再可用，并且不知道转发地址。这种情况应视为永久性的。
     */
    public static final int GONE = 410;

    /**
     * <h2> Status code (411) </h2>
     * 表示没有定义“Content-Length”无法处理请求。
     */
    public static final int LENGTH_REQUIRED = 411;

    /**
     * <h2> Status code (412) </h2>
     * 指示一个或多个请求头字段中给定的前置条件在服务器上测试时计算为FALSE。
     */
    public static final int PRECONDITION_FAILED = 412;

    /**
     * <h2> Status code (413) </h2>
     * 表示服务器拒绝处理请求，因为请求实体大于服务器愿意或能够处理的实体。
     */
    public static final int REQUEST_ENTITY_TOO_LARGE = 413;

    /**
     * <h2> Status code (414) </h2>
     * 表示服务器拒绝服务请求，因为“Request-URI”比服务器愿意解释的要长。
     */
    public static final int REQUEST_URI_TOO_LONG = 414;

    /**
     * <h2> Status code (415) </h2>
     * 指示服务器拒绝为请求提供服务，因为请求实体的格式不受请求方法的请求资源支持。
     */
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * <h2> Status code (416) </h2>
     * 表示服务器无法为请求的字节范围提供服务。
     */
    public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * <h2> Status code (417) </h2>
     * 指示服务器无法满足Expect请求头中给定的期望。
     */
    public static final int EXPECTATION_FAILED = 417;

    /**
     * <h2> Status code (500) </h2>
     * 指示HTTP服务器内部的错误，该错误阻止它完成请求。
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * <h2> Status code (501) </h2>
     * 表示http服务器不支持完成请求所需的功能。
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * <h2> Status code (502) </h2>
     * 表示http服务器在充当代理或网关时从其所咨询的服务器接收到无效响应。
     */
    public static final int BAD_GATEWAY = 502;

    /**
     * <h2> Status code (503) </h2>
     * 表示http服务器暂时过载，无法处理该请求。
     */
    public static final int SERVICE_UNAVAILABLE = 503;

    /**
     * <h2> Status code (504) </h2>
     * 表示服务器在充当网关或代理时未收到来自上游服务器的及时响应。
     */
    public static final int GATEWAY_TIMEOUT = 504;

    /**
     * <h2> Status code (505) </h2>
     * 表示服务器不支持或拒绝支持请求消息中使用的http协议版本。
     */
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;


    public static PageModel page() {
        return new PageModel();
    }


    public static JsonModel json() {
        return new JsonModel();
    }


    public static StreamModel stream() {
        return new StreamModel();
    }
}