package com.mini.core.mvc.util;


import com.mini.core.mvc.validation.ValidatorBuilder;
import com.mini.core.mvc.validation.ValidatorUtil;
import org.springframework.http.HttpStatus;


public interface ResponseCode {

    /**
     * <p>表示客户端可以继续。</p>
     */
    ValidatorBuilder CONTINUE = ValidatorUtil.status(HttpStatus.CONTINUE)
            .message(HttpStatus.CONTINUE.getReasonPhrase())
            .code(HttpStatus.CONTINUE.value());

    /**
     * <p>服务器将遵从客户的请求转换到另外一种协议</p>
     */
    ValidatorBuilder SWITCHING_PROTOCOLS = ValidatorUtil.status(HttpStatus.SWITCHING_PROTOCOLS)
            .message(HttpStatus.SWITCHING_PROTOCOLS.getReasonPhrase())
            .code(HttpStatus.SWITCHING_PROTOCOLS.value());

    /**
     * 由WebDAV（RFC 2518）扩展的状态码，代表处理将被继续执行
     */
    ValidatorBuilder PROCESSING = ValidatorUtil.status(HttpStatus.PROCESSING)
            .message(HttpStatus.PROCESSING.getReasonPhrase())
            .code(HttpStatus.PROCESSING.value());

    /**
     * 在头部信息到达之前，用户可以开始预加载CSS和JavaScript文件
     */
    ValidatorBuilder CHECKPOINT = ValidatorUtil.status(HttpStatus.CHECKPOINT)
            .message(HttpStatus.CHECKPOINT.getReasonPhrase())
            .code(HttpStatus.CHECKPOINT.value());

    /**
     * <p> 表示请求正常成功。 </p>
     */
    ValidatorBuilder OK = ValidatorUtil.status(HttpStatus.OK)
            .message(HttpStatus.OK.getReasonPhrase())
            .code(HttpStatus.OK.value());

    /**
     * <p> 指示请求成功并在服务器上创建了新资源。 </p>
     */
    ValidatorBuilder CREATED = ValidatorUtil.status(HttpStatus.CREATED)
            .message(HttpStatus.CREATED.getReasonPhrase())
            .code(HttpStatus.CREATED.value());

    /**
     * <p> 指示已接受请求正在处理，但未完成。 </p>
     */
    ValidatorBuilder ACCEPTED = ValidatorUtil.status(HttpStatus.ACCEPTED)
            .message(HttpStatus.ACCEPTED.getReasonPhrase())
            .code(HttpStatus.ACCEPTED.value());

    /**
     * <p> 表示客户端提供的元信息不是来自服务器。  </p>
     */
    ValidatorBuilder NON_AUTHORITATIVE_INFORMATION = ValidatorUtil.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
            .message(HttpStatus.NON_AUTHORITATIVE_INFORMATION.getReasonPhrase())
            .code(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
    /**
     * <p> 表示请求成功，但没有要返回的新信息。 </p>
     */
    ValidatorBuilder NO_CONTENT = ValidatorUtil.status(HttpStatus.NO_CONTENT)
            .message(HttpStatus.NO_CONTENT.getReasonPhrase())
            .code(HttpStatus.NO_CONTENT.value());

    /**
     * <p> 指示代理应重置导致发送请求的文档视图。</p>
     */
    ValidatorBuilder RESET_CONTENT = ValidatorUtil.status(HttpStatus.RESET_CONTENT)
            .message(HttpStatus.RESET_CONTENT.getReasonPhrase())
            .code(HttpStatus.RESET_CONTENT.value());

    /**
     * <p> 表示服务器已完成对资源的部分获取请求。 </p>
     */
    ValidatorBuilder PARTIAL_CONTENT = ValidatorUtil.status(HttpStatus.PARTIAL_CONTENT)
            .message(HttpStatus.PARTIAL_CONTENT.getReasonPhrase())
            .code(HttpStatus.PARTIAL_CONTENT.value());

    ValidatorBuilder MULTI_STATUS = ValidatorUtil.status(HttpStatus.MULTI_STATUS)
            .message(HttpStatus.MULTI_STATUS.getReasonPhrase())
            .code(HttpStatus.MULTI_STATUS.value());

    ValidatorBuilder ALREADY_REPORTED = ValidatorUtil.status(HttpStatus.ALREADY_REPORTED)
            .message(HttpStatus.ALREADY_REPORTED.getReasonPhrase())
            .code(HttpStatus.ALREADY_REPORTED.value());

    ValidatorBuilder IM_USED = ValidatorUtil.status(HttpStatus.IM_USED)
            .message(HttpStatus.IM_USED.getReasonPhrase())
            .code(HttpStatus.IM_USED.value());

    /**
     * <p> 指示请求的资源对应于一组表示中的任何一个，每个表示都有自己的特定位置。 </p>
     */
    ValidatorBuilder MULTIPLE_CHOICES = ValidatorUtil.status(HttpStatus.MULTIPLE_CHOICES)
            .message(HttpStatus.MULTIPLE_CHOICES.getReasonPhrase())
            .code(HttpStatus.MULTIPLE_CHOICES.value());

    /**
     * <p> 指示资源已永久移动到新位置，并且将来的引用应在其请求中使用新的URI。 </p>
     */
    ValidatorBuilder MOVED_PERMANENTLY = ValidatorUtil.status(HttpStatus.MOVED_PERMANENTLY)
            .message(HttpStatus.MOVED_PERMANENTLY.getReasonPhrase())
            .code(HttpStatus.MOVED_PERMANENTLY.value());


    /**
     * <p> 指示资源暂时位于不同的uri下。由于有时可能会更改重定向，</p>
     * <p> 客户端应继续使用请求URI来表示将来的请求（HTTP/1.1），因此建议使用此变量。</p>
     */
    ValidatorBuilder FOUND = ValidatorUtil.status(HttpStatus.FOUND)
            .message(HttpStatus.FOUND.getReasonPhrase())
            .code(HttpStatus.FOUND.value());

    /**
     * <p> 指示资源已临时移动到另一个位置，但将来的引用仍应使用原始uri来访问资源。</p>
     * <p> 保留此定义是为了向后兼容。找到SC_FOUND 现在是首选定义。</p>
     */
    @Deprecated
    ValidatorBuilder MOVED_TEMPORARILY = ValidatorUtil.status(HttpStatus.MOVED_TEMPORARILY)
            .message(HttpStatus.MOVED_TEMPORARILY.getReasonPhrase())
            .code(HttpStatus.MOVED_TEMPORARILY.value());


    /**
     * <p> 指示可以在不同的uri下找到对请求的响应。 </p>
     */
    ValidatorBuilder SEE_OTHER = ValidatorUtil.status(HttpStatus.SEE_OTHER)
            .message(HttpStatus.SEE_OTHER.getReasonPhrase())
            .code(HttpStatus.SEE_OTHER.value());
    /**
     * <p> 指示条件获取操作发现资源可用且未修改。 </p>
     */
    ValidatorBuilder NOT_MODIFIED = ValidatorUtil.status(HttpStatus.NOT_MODIFIED)
            .message(HttpStatus.NOT_MODIFIED.getReasonPhrase())
            .code(HttpStatus.NOT_MODIFIED.value());

    /**
     * <p> indicating that the requested resource
     * <em>MUST</em> be accessed through the proxy given by the
     * <code><em>Location</em></code> field. </p>
     */
    @Deprecated
    ValidatorBuilder USE_PROXY = ValidatorUtil.status(HttpStatus.USE_PROXY)
            .message(HttpStatus.USE_PROXY.getReasonPhrase())
            .code(HttpStatus.USE_PROXY.value());

    /**
     * <p> 指示必须通过位置字段提供的代理访问请求的资源。</p>
     */
    ValidatorBuilder TEMPORARY_REDIRECT = ValidatorUtil.status(HttpStatus.TEMPORARY_REDIRECT)
            .message(HttpStatus.TEMPORARY_REDIRECT.getReasonPhrase())
            .code(HttpStatus.TEMPORARY_REDIRECT.value());

    ValidatorBuilder PERMANENT_REDIRECT = ValidatorUtil.status(HttpStatus.PERMANENT_REDIRECT)
            .message(HttpStatus.PERMANENT_REDIRECT.getReasonPhrase())
            .code(HttpStatus.PERMANENT_REDIRECT.value());

    /**
     * <p> 指示客户端发送的请求在语法上不正确。 </p>
     */
    ValidatorBuilder BAD_REQUEST = ValidatorUtil.status(HttpStatus.BAD_REQUEST)
            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .code(HttpStatus.BAD_REQUEST.value());

    /**
     * <p> 指示请求需要http身份验证。 </p>
     */
    ValidatorBuilder UNAUTHORIZED = ValidatorUtil.status(HttpStatus.UNAUTHORIZED)
            .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .code(HttpStatus.UNAUTHORIZED.value());

    /**
     * <p>保留以备将来使用。 </p>
     */
    ValidatorBuilder PAYMENT_REQUIRED = ValidatorUtil.status(HttpStatus.PAYMENT_REQUIRED)
            .message(HttpStatus.PAYMENT_REQUIRED.getReasonPhrase())
            .code(HttpStatus.PAYMENT_REQUIRED.value());

    /**
     * <p> 表示服务器理解请求但拒绝执行。 </p>
     */
    ValidatorBuilder FORBIDDEN = ValidatorUtil.status(HttpStatus.FORBIDDEN)
            .message(HttpStatus.FORBIDDEN.getReasonPhrase())
            .code(HttpStatus.FORBIDDEN.value());

    /**
     * <p> 表示请求的资源不可用。 </p>
     */
    ValidatorBuilder NOT_FOUND = ValidatorUtil.status(HttpStatus.NOT_FOUND)
            .message(HttpStatus.NOT_FOUND.getReasonPhrase())
            .code(HttpStatus.NOT_FOUND.value());

    /**
     * <p> 指示由请求uri标识的资源不允许使用请求行中指定的方法。 </p>
     */
    ValidatorBuilder METHOD_NOT_ALLOWED = ValidatorUtil.status(HttpStatus.METHOD_NOT_ALLOWED)
            .message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
            .code(HttpStatus.METHOD_NOT_ALLOWED.value());

    /**
     * <p> 指示由请求标识的资源只能根据请求中发送的接受头生成内容特征不可接受的响应实体。 </p>
     */
    ValidatorBuilder NOT_ACCEPTABLE = ValidatorUtil.status(HttpStatus.NOT_ACCEPTABLE)
            .message(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
            .code(HttpStatus.NOT_ACCEPTABLE.value());

    /**
     * <p> 表示客户机必须首先使用代理对自己进行身份验证。  </p>
     */
    ValidatorBuilder PROXY_AUTHENTICATION_REQUIRED = ValidatorUtil.status(HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
            .message(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.getReasonPhrase())
            .code(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.value());

    /**
     * <p> 指示客户端在服务器准备等待的时间内未生成请求。  </p>
     */
    ValidatorBuilder REQUEST_TIMEOUT = ValidatorUtil.status(HttpStatus.REQUEST_TIMEOUT)
            .message(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
            .code(HttpStatus.REQUEST_TIMEOUT.value());

    /**
     * <p> 指示由于与资源的当前状态冲突而无法完成请求。 </p>
     */
    ValidatorBuilder CONFLICT = ValidatorUtil.status(HttpStatus.CONFLICT)
            .message(HttpStatus.CONFLICT.getReasonPhrase())
            .code(HttpStatus.CONFLICT.value());

    /**
     * <p> 表示资源在服务器上不再可用，并且不知道转发地址。这种情况应视为永久性的。 </p>
     */
    ValidatorBuilder GONE = ValidatorUtil.status(HttpStatus.GONE)
            .message(HttpStatus.GONE.getReasonPhrase())
            .code(HttpStatus.GONE.value());

    /**
     * <p> 表示没有定义“Content-Length”无法处理请求。 </p>
     */
    ValidatorBuilder LENGTH_REQUIRED = ValidatorUtil.status(HttpStatus.LENGTH_REQUIRED)
            .message(HttpStatus.LENGTH_REQUIRED.getReasonPhrase())
            .code(HttpStatus.LENGTH_REQUIRED.value());

    /**
     * <p> 指示一个或多个请求头字段中给定的前置条件在服务器上测试时计算为FALSE。 </p>
     */
    ValidatorBuilder PRECONDITION_FAILED = ValidatorUtil.status(HttpStatus.PRECONDITION_FAILED)
            .message(HttpStatus.PRECONDITION_FAILED.getReasonPhrase())
            .code(HttpStatus.PRECONDITION_FAILED.value());

    /**
     * <p> 表示服务器拒绝处理请求，因为请求实体大于服务器愿意或能够处理的实体。 </p>
     */
    ValidatorBuilder PAYLOAD_TOO_LARGE = ValidatorUtil.status(HttpStatus.PAYLOAD_TOO_LARGE)
            .message(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase())
            .code(HttpStatus.PAYLOAD_TOO_LARGE.value());

    @Deprecated
    ValidatorBuilder REQUEST_ENTITY_TOO_LARGE = ValidatorUtil.status(HttpStatus.REQUEST_ENTITY_TOO_LARGE)
            .message(HttpStatus.REQUEST_ENTITY_TOO_LARGE.getReasonPhrase())
            .code(HttpStatus.REQUEST_ENTITY_TOO_LARGE.value());

    /**
     * <p> 表示服务器拒绝服务请求，因为“Request-URI”比服务器愿意解释的要长。 </p>
     */
    ValidatorBuilder URI_TOO_LONG = ValidatorUtil.status(HttpStatus.URI_TOO_LONG)
            .message(HttpStatus.URI_TOO_LONG.getReasonPhrase())
            .code(HttpStatus.URI_TOO_LONG.value());
    @Deprecated
    ValidatorBuilder REQUEST_URI_TOO_LONG = ValidatorUtil.status(HttpStatus.REQUEST_URI_TOO_LONG)
            .message(HttpStatus.REQUEST_URI_TOO_LONG.getReasonPhrase())
            .code(HttpStatus.REQUEST_URI_TOO_LONG.value());

    /**
     * <p> 指示服务器拒绝为请求提供服务，因为请求实体的格式不受请求方法的请求资源支持。 </p>
     */
    ValidatorBuilder UNSUPPORTED_MEDIA_TYPE = ValidatorUtil.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .message(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
            .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());

    /**
     * <p> 表示服务器无法为请求的字节范围提供服务。 </p>
     */
    ValidatorBuilder REQUESTED_RANGE_NOT_SATISFIABLE = ValidatorUtil.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
            .message(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.getReasonPhrase())
            .code(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());

    /**
     * <p> 指示服务器无法满足Expect请求头中给定的期望。 </p>
     */
    ValidatorBuilder EXPECTATION_FAILED = ValidatorUtil.status(HttpStatus.EXPECTATION_FAILED)
            .message(HttpStatus.EXPECTATION_FAILED.getReasonPhrase())
            .code(HttpStatus.EXPECTATION_FAILED.value());

    ValidatorBuilder I_AM_A_TEAPOT = ValidatorUtil.status(HttpStatus.I_AM_A_TEAPOT)
            .message(HttpStatus.I_AM_A_TEAPOT.getReasonPhrase())
            .code(HttpStatus.I_AM_A_TEAPOT.value());

    @Deprecated
    ValidatorBuilder INSUFFICIENT_SPACE_ON_RESOURCE = ValidatorUtil.status(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE)
            .message(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE.getReasonPhrase())
            .code(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE.value());

    @Deprecated
    ValidatorBuilder METHOD_FAILURE = ValidatorUtil.status(HttpStatus.METHOD_FAILURE)
            .message(HttpStatus.METHOD_FAILURE.getReasonPhrase())
            .code(HttpStatus.METHOD_FAILURE.value());

    @Deprecated
    ValidatorBuilder DESTINATION_LOCKED = ValidatorUtil.status(HttpStatus.DESTINATION_LOCKED)
            .message(HttpStatus.DESTINATION_LOCKED.getReasonPhrase())
            .code(HttpStatus.DESTINATION_LOCKED.value());

    ValidatorBuilder UNPROCESSABLE_ENTITY = ValidatorUtil.status(HttpStatus.UNPROCESSABLE_ENTITY)
            .message(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
            .code(HttpStatus.UNPROCESSABLE_ENTITY.value());

    ValidatorBuilder LOCKED = ValidatorUtil.status(HttpStatus.LOCKED)
            .message(HttpStatus.LOCKED.getReasonPhrase())
            .code(HttpStatus.LOCKED.value());

    ValidatorBuilder FAILED_DEPENDENCY = ValidatorUtil.status(HttpStatus.FAILED_DEPENDENCY)
            .message(HttpStatus.FAILED_DEPENDENCY.getReasonPhrase())
            .code(HttpStatus.FAILED_DEPENDENCY.value());

    ValidatorBuilder TOO_EARLY = ValidatorUtil.status(HttpStatus.TOO_EARLY)
            .message(HttpStatus.TOO_EARLY.getReasonPhrase())
            .code(HttpStatus.TOO_EARLY.value());

    ValidatorBuilder UPGRADE_REQUIRED = ValidatorUtil.status(HttpStatus.UPGRADE_REQUIRED)
            .message(HttpStatus.UPGRADE_REQUIRED.getReasonPhrase())
            .code(HttpStatus.UPGRADE_REQUIRED.value());

    ValidatorBuilder PRECONDITION_REQUIRED = ValidatorUtil.status(HttpStatus.PRECONDITION_REQUIRED)
            .message(HttpStatus.PRECONDITION_REQUIRED.getReasonPhrase())
            .code(HttpStatus.PRECONDITION_REQUIRED.value());

    ValidatorBuilder TOO_MANY_REQUESTS = ValidatorUtil.status(HttpStatus.TOO_MANY_REQUESTS)
            .message(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase())
            .code(HttpStatus.TOO_MANY_REQUESTS.value());

    ValidatorBuilder REQUEST_HEADER_FIELDS_TOO_LARGE = ValidatorUtil.status(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE)
            .message(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.getReasonPhrase())
            .code(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.value());

    ValidatorBuilder UNAVAILABLE_FOR_LEGAL_REASONS = ValidatorUtil.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
            .message(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.getReasonPhrase())
            .code(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value());

    /**
     * <p> 指示HTTP服务器内部的错误，该错误阻止它完成请求。 </p>
     */
    ValidatorBuilder INTERNAL_SERVER_ERROR = ValidatorUtil.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value());

    /**
     * <p> 表示http服务器不支持完成请求所需的功能。 </p>
     */
    ValidatorBuilder NOT_IMPLEMENTED = ValidatorUtil.status(HttpStatus.NOT_IMPLEMENTED)
            .message(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase())
            .code(HttpStatus.NOT_IMPLEMENTED.value());

    /**
     * <p> 表示http服务器在充当代理或网关时从其所咨询的服务器接收到无效响应。 </p>
     */
    ValidatorBuilder BAD_GATEWAY = ValidatorUtil.status(HttpStatus.BAD_GATEWAY)
            .message(HttpStatus.BAD_GATEWAY.getReasonPhrase())
            .code(HttpStatus.BAD_GATEWAY.value());

    /**
     * <p> 表示http服务器暂时过载，无法处理该请求。 </p>
     */
    ValidatorBuilder SERVICE_UNAVAILABLE = ValidatorUtil.status(HttpStatus.SERVICE_UNAVAILABLE)
            .message(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
            .code(HttpStatus.SERVICE_UNAVAILABLE.value());

    /**
     * <p> 表示服务器在充当网关或代理时未收到来自上游服务器的及时响应。 </p>
     */
    ValidatorBuilder GATEWAY_TIMEOUT = ValidatorUtil.status(HttpStatus.GATEWAY_TIMEOUT)
            .message(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase())
            .code(HttpStatus.GATEWAY_TIMEOUT.value());

    /**
     * <p> 表示服务器不支持或拒绝支持请求消息中使用的http协议版本。 </p>
     */
    ValidatorBuilder HTTP_VERSION_NOT_SUPPORTED = ValidatorUtil.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
            .message(HttpStatus.HTTP_VERSION_NOT_SUPPORTED.getReasonPhrase())
            .code(HttpStatus.HTTP_VERSION_NOT_SUPPORTED.value());

    ValidatorBuilder VARIANT_ALSO_NEGOTIATES = ValidatorUtil.status(HttpStatus.VARIANT_ALSO_NEGOTIATES)
            .message(HttpStatus.VARIANT_ALSO_NEGOTIATES.getReasonPhrase())
            .code(HttpStatus.VARIANT_ALSO_NEGOTIATES.value());

    ValidatorBuilder INSUFFICIENT_STORAGE = ValidatorUtil.status(HttpStatus.INSUFFICIENT_STORAGE)
            .message(HttpStatus.INSUFFICIENT_STORAGE.getReasonPhrase())
            .code(HttpStatus.INSUFFICIENT_STORAGE.value());

    ValidatorBuilder LOOP_DETECTED = ValidatorUtil.status(HttpStatus.LOOP_DETECTED)
            .message(HttpStatus.LOOP_DETECTED.getReasonPhrase())
            .code(HttpStatus.LOOP_DETECTED.value());

    ValidatorBuilder BANDWIDTH_LIMIT_EXCEEDED = ValidatorUtil.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
            .message(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.getReasonPhrase())
            .code(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value());

    ValidatorBuilder NOT_EXTENDED = ValidatorUtil.status(HttpStatus.NOT_EXTENDED)
            .message(HttpStatus.NOT_EXTENDED.getReasonPhrase())
            .code(HttpStatus.NOT_EXTENDED.value());

    ValidatorBuilder NETWORK_AUTHENTICATION_REQUIRED = ValidatorUtil.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
            .message(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.getReasonPhrase())
            .code(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value());
}
