package com.mini.core.mvc.util;


import org.springframework.http.HttpStatus;


public interface ResponseCode {

    /**
     * <p>表示客户端可以继续。</p>
     */
    HttpStatus CONTINUE = HttpStatus.CONTINUE;

    /**
     * <p>服务器将遵从客户的请求转换到另外一种协议</p>
     */
    HttpStatus SWITCHING_PROTOCOLS = HttpStatus.SWITCHING_PROTOCOLS;

    /**
     * 由WebDAV（RFC 2518）扩展的状态码，代表处理将被继续执行
     */
    HttpStatus PROCESSING = HttpStatus.PROCESSING;

    /**
     * 在头部信息到达之前，用户可以开始预加载CSS和JavaScript文件
     */
    HttpStatus CHECKPOINT = HttpStatus.CHECKPOINT;

    /**
     * <p> 表示请求正常成功。 </p>
     */
    HttpStatus OK = HttpStatus.OK;

    /**
     * <p> 指示请求成功并在服务器上创建了新资源。 </p>
     */
    HttpStatus CREATED = HttpStatus.CREATED;

    /**
     * <p> 指示已接受请求正在处理，但未完成。 </p>
     */
    HttpStatus ACCEPTED = HttpStatus.ACCEPTED;

    /**
     * <p> 表示客户端提供的元信息不是来自服务器。  </p>
     */
    HttpStatus NON_AUTHORITATIVE_INFORMATION = HttpStatus.NON_AUTHORITATIVE_INFORMATION;

    /**
     * <p> 表示请求成功，但没有要返回的新信息。 </p>
     */
    HttpStatus NO_CONTENT = HttpStatus.NO_CONTENT;

    /**
     * <p> 指示代理应重置导致发送请求的文档视图。</p>
     */
    HttpStatus RESET_CONTENT = HttpStatus.RESET_CONTENT;

    /**
     * <p> 表示服务器已完成对资源的部分获取请求。 </p>
     */
    HttpStatus PARTIAL_CONTENT = HttpStatus.PARTIAL_CONTENT;

    HttpStatus MULTI_STATUS = HttpStatus.MULTI_STATUS;

    HttpStatus ALREADY_REPORTED = HttpStatus.ALREADY_REPORTED;

    HttpStatus IM_USED = HttpStatus.IM_USED;

    /**
     * <p> 指示请求的资源对应于一组表示中的任何一个，每个表示都有自己的特定位置。 </p>
     */
    HttpStatus MULTIPLE_CHOICES = HttpStatus.MULTIPLE_CHOICES;

    /**
     * <p> 指示资源已永久移动到新位置，并且将来的引用应在其请求中使用新的URI。 </p>
     */
    HttpStatus MOVED_PERMANENTLY = HttpStatus.MOVED_PERMANENTLY;


    /**
     * <p> 指示资源暂时位于不同的uri下。由于有时可能会更改重定向，</p>
     * <p> 客户端应继续使用请求URI来表示将来的请求（HTTP/1.1），因此建议使用此变量。</p>
     */
    HttpStatus FOUND = HttpStatus.FOUND;

    /**
     * <p> 指示资源已临时移动到另一个位置，但将来的引用仍应使用原始uri来访问资源。</p>
     * <p> 保留此定义是为了向后兼容。找到SC_FOUND 现在是首选定义。</p>
     */
    @Deprecated
    HttpStatus MOVED_TEMPORARILY = HttpStatus.MOVED_TEMPORARILY;


    /**
     * <p> 指示可以在不同的uri下找到对请求的响应。 </p>
     */
    HttpStatus SEE_OTHER = HttpStatus.SEE_OTHER;

    /**
     * <p> 指示条件获取操作发现资源可用且未修改。 </p>
     */
    HttpStatus NOT_MODIFIED = HttpStatus.NOT_MODIFIED;

    /**
     * <p> indicating that the requested resource
     * <em>MUST</em> be accessed through the proxy given by the
     * <code><em>Location</em></code> field. </p>
     */
    @Deprecated
    HttpStatus USE_PROXY = HttpStatus.USE_PROXY;

    /**
     * <p> 指示必须通过位置字段提供的代理访问请求的资源。</p>
     */
    HttpStatus TEMPORARY_REDIRECT = HttpStatus.TEMPORARY_REDIRECT;

    HttpStatus PERMANENT_REDIRECT = HttpStatus.PERMANENT_REDIRECT;

    /**
     * <p> 指示客户端发送的请求在语法上不正确。 </p>
     */
    HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    /**
     * <p> 指示请求需要http身份验证。 </p>
     */
    HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    /**
     * <p>保留以备将来使用。 </p>
     */
    HttpStatus PAYMENT_REQUIRED = HttpStatus.PAYMENT_REQUIRED;

    /**
     * <p> 表示服务器理解请求但拒绝执行。 </p>
     */
    HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;

    /**
     * <p> 表示请求的资源不可用。 </p>
     */
    HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    /**
     * <p> 指示由请求uri标识的资源不允许使用请求行中指定的方法。 </p>
     */
    HttpStatus METHOD_NOT_ALLOWED = HttpStatus.METHOD_NOT_ALLOWED;

    /**
     * <p> 指示由请求标识的资源只能根据请求中发送的接受头生成内容特征不可接受的响应实体。 </p>
     */
    HttpStatus NOT_ACCEPTABLE = HttpStatus.NOT_ACCEPTABLE;

    /**
     * <p> 表示客户机必须首先使用代理对自己进行身份验证。  </p>
     */
    HttpStatus PROXY_AUTHENTICATION_REQUIRED = HttpStatus.PROXY_AUTHENTICATION_REQUIRED;

    /**
     * <p> 指示客户端在服务器准备等待的时间内未生成请求。  </p>
     */
    HttpStatus REQUEST_TIMEOUT = HttpStatus.REQUEST_TIMEOUT;

    /**
     * <p> 指示由于与资源的当前状态冲突而无法完成请求。 </p>
     */
    HttpStatus CONFLICT = HttpStatus.CONFLICT;

    /**
     * <p> 表示资源在服务器上不再可用，并且不知道转发地址。这种情况应视为永久性的。 </p>
     */
    HttpStatus GONE = HttpStatus.GONE;

    /**
     * <p> 表示没有定义“Content-Length”无法处理请求。 </p>
     */
    HttpStatus LENGTH_REQUIRED = HttpStatus.LENGTH_REQUIRED;

    /**
     * <p> 指示一个或多个请求头字段中给定的前置条件在服务器上测试时计算为FALSE。 </p>
     */
    HttpStatus PRECONDITION_FAILED = HttpStatus.PRECONDITION_FAILED;

    /**
     * <p> 表示服务器拒绝处理请求，因为请求实体大于服务器愿意或能够处理的实体。 </p>
     */
    HttpStatus PAYLOAD_TOO_LARGE = HttpStatus.PAYLOAD_TOO_LARGE;

    @Deprecated
    HttpStatus REQUEST_ENTITY_TOO_LARGE = HttpStatus.REQUEST_ENTITY_TOO_LARGE;

    /**
     * <p> 表示服务器拒绝服务请求，因为“Request-URI”比服务器愿意解释的要长。 </p>
     */
    HttpStatus URI_TOO_LONG = HttpStatus.URI_TOO_LONG;
    @Deprecated
    HttpStatus REQUEST_URI_TOO_LONG = HttpStatus.REQUEST_URI_TOO_LONG;

    /**
     * <p> 指示服务器拒绝为请求提供服务，因为请求实体的格式不受请求方法的请求资源支持。 </p>
     */
    HttpStatus UNSUPPORTED_MEDIA_TYPE = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    /**
     * <p> 表示服务器无法为请求的字节范围提供服务。 </p>
     */
    HttpStatus REQUESTED_RANGE_NOT_SATISFIABLE = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE;

    /**
     * <p> 指示服务器无法满足Expect请求头中给定的期望。 </p>
     */
    HttpStatus EXPECTATION_FAILED = HttpStatus.EXPECTATION_FAILED;

    HttpStatus I_AM_A_TEAPOT = HttpStatus.I_AM_A_TEAPOT;

    @Deprecated
    HttpStatus INSUFFICIENT_SPACE_ON_RESOURCE = HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE;

    @Deprecated
    HttpStatus METHOD_FAILURE = HttpStatus.METHOD_FAILURE;

    @Deprecated
    HttpStatus DESTINATION_LOCKED = HttpStatus.DESTINATION_LOCKED;

    HttpStatus UNPROCESSABLE_ENTITY = HttpStatus.UNPROCESSABLE_ENTITY;

    HttpStatus LOCKED = HttpStatus.LOCKED;

    HttpStatus FAILED_DEPENDENCY = HttpStatus.FAILED_DEPENDENCY;

    HttpStatus TOO_EARLY = HttpStatus.TOO_EARLY;

    HttpStatus UPGRADE_REQUIRED = HttpStatus.UPGRADE_REQUIRED;

    HttpStatus PRECONDITION_REQUIRED = HttpStatus.PRECONDITION_REQUIRED;

    HttpStatus TOO_MANY_REQUESTS = HttpStatus.TOO_MANY_REQUESTS;

    HttpStatus REQUEST_HEADER_FIELDS_TOO_LARGE = HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE;

    HttpStatus UNAVAILABLE_FOR_LEGAL_REASONS = HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS;

    /**
     * <p> 指示HTTP服务器内部的错误，该错误阻止它完成请求。 </p>
     */
    HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * <p> 表示http服务器不支持完成请求所需的功能。 </p>
     */
    HttpStatus NOT_IMPLEMENTED = HttpStatus.NOT_IMPLEMENTED;

    /**
     * <p> 表示http服务器在充当代理或网关时从其所咨询的服务器接收到无效响应。 </p>
     */
    HttpStatus BAD_GATEWAY = HttpStatus.BAD_GATEWAY;

    /**
     * <p> 表示http服务器暂时过载，无法处理该请求。 </p>
     */
    HttpStatus SERVICE_UNAVAILABLE = HttpStatus.SERVICE_UNAVAILABLE;

    /**
     * <p> 表示服务器在充当网关或代理时未收到来自上游服务器的及时响应。 </p>
     */
    HttpStatus GATEWAY_TIMEOUT = HttpStatus.GATEWAY_TIMEOUT;

    /**
     * <p> 表示服务器不支持或拒绝支持请求消息中使用的http协议版本。 </p>
     */
    HttpStatus HTTP_VERSION_NOT_SUPPORTED = HttpStatus.HTTP_VERSION_NOT_SUPPORTED;

    HttpStatus VARIANT_ALSO_NEGOTIATES = HttpStatus.VARIANT_ALSO_NEGOTIATES;

    HttpStatus INSUFFICIENT_STORAGE = HttpStatus.INSUFFICIENT_STORAGE;

    HttpStatus LOOP_DETECTED = HttpStatus.LOOP_DETECTED;

    HttpStatus BANDWIDTH_LIMIT_EXCEEDED = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;

    HttpStatus NOT_EXTENDED = HttpStatus.NOT_EXTENDED;

    HttpStatus NETWORK_AUTHENTICATION_REQUIRED = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;

}
