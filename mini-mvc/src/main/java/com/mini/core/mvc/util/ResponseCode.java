package com.mini.core.mvc.util;

import com.mini.core.mvc.validation.ValidatorBuilder;
import org.springframework.http.HttpStatus;

import static com.mini.core.mvc.validation.ValidatorUtil.message;

public interface ResponseCode {

    /**
     * <p>表示客户端可以继续。</p>
     */
    default ValidatorBuilder CONTINUE() {
        return message(HttpStatus.CONTINUE.getReasonPhrase())
                .code(HttpStatus.CONTINUE.value())
                .status(HttpStatus.CONTINUE);
    }

    /**
     * <p>服务器将遵从客户的请求转换到另外一种协议</p>
     */
    default ValidatorBuilder SWITCHING_PROTOCOLS() {
        return message(HttpStatus.SWITCHING_PROTOCOLS.getReasonPhrase())
                .code(HttpStatus.SWITCHING_PROTOCOLS.value())
                .status(HttpStatus.SWITCHING_PROTOCOLS);
    }

    /**
     * 由WebDAV（RFC 2518）扩展的状态码，代表处理将被继续执行
     */
    default ValidatorBuilder PROCESSING() {
        return message(HttpStatus.PROCESSING.getReasonPhrase())
                .code(HttpStatus.PROCESSING.value())
                .status(HttpStatus.PROCESSING);
    }

    /**
     * 在头部信息到达之前，用户可以开始预加载CSS和JavaScript文件
     */
    default ValidatorBuilder CHECKPOINT() {
        return message(HttpStatus.CHECKPOINT.getReasonPhrase())
                .code(HttpStatus.CHECKPOINT.value())
                .status(HttpStatus.CHECKPOINT);
    }

    /**
     * <p> 表示请求正常成功。 </p>
     */
    default ValidatorBuilder OK() {
        return message(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK);
    }

    /**
     * <p> 指示请求成功并在服务器上创建了新资源。 </p>
     */
    default ValidatorBuilder CREATED() {
        return message(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED);
    }

    /**
     * <p> 指示已接受请求正在处理，但未完成。 </p>
     */
    default ValidatorBuilder ACCEPTED() {
        return message(HttpStatus.ACCEPTED.getReasonPhrase())
                .code(HttpStatus.ACCEPTED.value())
                .status(HttpStatus.ACCEPTED);
    }

    /**
     * <p> 表示客户端提供的元信息不是来自服务器。  </p>
     */
    default ValidatorBuilder NON_AUTHORITATIVE_INFORMATION() {
        return message(HttpStatus.NON_AUTHORITATIVE_INFORMATION.getReasonPhrase())
                .code(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value())
                .status(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    /**
     * <p> 表示请求成功，但没有要返回的新信息。 </p>
     */
    default ValidatorBuilder NO_CONTENT() {
        return message(HttpStatus.NO_CONTENT.getReasonPhrase())
                .code(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT);
    }

    /**
     * <p> 指示代理应重置导致发送请求的文档视图。</p>
     */
    default ValidatorBuilder RESET_CONTENT() {
        return message(HttpStatus.RESET_CONTENT.getReasonPhrase())
                .code(HttpStatus.RESET_CONTENT.value())
                .status(HttpStatus.RESET_CONTENT);
    }

    /**
     * <p> 表示服务器已完成对资源的部分获取请求。 </p>
     */
    default ValidatorBuilder PARTIAL_CONTENT() {
        return message(HttpStatus.PARTIAL_CONTENT.getReasonPhrase())
                .code(HttpStatus.PARTIAL_CONTENT.value())
                .status(HttpStatus.PARTIAL_CONTENT);
    }

    default ValidatorBuilder MULTI_STATUS() {
        return message(HttpStatus.MULTI_STATUS.getReasonPhrase())
                .code(HttpStatus.MULTI_STATUS.value())
                .status(HttpStatus.MULTI_STATUS);
    }

    default ValidatorBuilder ALREADY_REPORTED() {
        return message(HttpStatus.ALREADY_REPORTED.getReasonPhrase())
                .code(HttpStatus.ALREADY_REPORTED.value())
                .status(HttpStatus.ALREADY_REPORTED);
    }

    default ValidatorBuilder IM_USED() {
        return message(HttpStatus.IM_USED.getReasonPhrase())
                .code(HttpStatus.IM_USED.value())
                .status(HttpStatus.IM_USED);
    }

    /**
     * <p> 指示请求的资源对应于一组表示中的任何一个，每个表示都有自己的特定位置。 </p>
     */
    default ValidatorBuilder MULTIPLE_CHOICES() {
        return message(HttpStatus.MULTIPLE_CHOICES.getReasonPhrase())
                .code(HttpStatus.MULTIPLE_CHOICES.value())
                .status(HttpStatus.MULTIPLE_CHOICES);
    }

    /**
     * <p> 指示资源已永久移动到新位置，并且将来的引用应在其请求中使用新的URI。 </p>
     */
    default ValidatorBuilder MOVED_PERMANENTLY() {
        return message(HttpStatus.MOVED_PERMANENTLY.getReasonPhrase())
                .code(HttpStatus.MOVED_PERMANENTLY.value())
                .status(HttpStatus.MOVED_PERMANENTLY);
    }


    /**
     * <p> 指示资源暂时位于不同的uri下。由于有时可能会更改重定向，</p>
     * <p> 客户端应继续使用请求URI来表示将来的请求（HTTP/1.1），因此建议使用此变量。</p>
     */
    default ValidatorBuilder FOUND() {
        return message(HttpStatus.FOUND.getReasonPhrase())
                .code(HttpStatus.FOUND.value())
                .status(HttpStatus.FOUND);
    }

    /**
     * <p> 指示资源已临时移动到另一个位置，但将来的引用仍应使用原始uri来访问资源。</p>
     * <p> 保留此定义是为了向后兼容。找到SC_FOUND 现在是首选定义。</p>
     */
    @Deprecated
    default ValidatorBuilder MOVED_TEMPORARILY() {
        return message(HttpStatus.MOVED_TEMPORARILY.getReasonPhrase())
                .code(HttpStatus.MOVED_TEMPORARILY.value())
                .status(HttpStatus.MOVED_TEMPORARILY);
    }


    /**
     * <p> 指示可以在不同的uri下找到对请求的响应。 </p>
     */
    default ValidatorBuilder SEE_OTHER() {
        return message(HttpStatus.SEE_OTHER.getReasonPhrase())
                .code(HttpStatus.SEE_OTHER.value())
                .status(HttpStatus.SEE_OTHER);
    }

    /**
     * <p> 指示条件获取操作发现资源可用且未修改。 </p>
     */
    default ValidatorBuilder NOT_MODIFIED() {
        return message(HttpStatus.NOT_MODIFIED.getReasonPhrase())
                .code(HttpStatus.NOT_MODIFIED.value())
                .status(HttpStatus.NOT_MODIFIED);
    }

    /**
     * <p> indicating that the requested resource
     * <em>MUST</em> be accessed through the proxy given by the
     * <code><em>Location</em></code> field. </p>
     */
    @Deprecated
    default ValidatorBuilder USE_PROXY() {
        return message(HttpStatus.USE_PROXY.getReasonPhrase())
                .code(HttpStatus.USE_PROXY.value())
                .status(HttpStatus.USE_PROXY);
    }

    /**
     * <p> 指示必须通过位置字段提供的代理访问请求的资源。</p>
     */
    default ValidatorBuilder TEMPORARY_REDIRECT() {
        return message(HttpStatus.TEMPORARY_REDIRECT.getReasonPhrase())
                .code(HttpStatus.TEMPORARY_REDIRECT.value())
                .status(HttpStatus.TEMPORARY_REDIRECT);
    }

    default ValidatorBuilder PERMANENT_REDIRECT() {
        return message(HttpStatus.PERMANENT_REDIRECT.getReasonPhrase())
                .code(HttpStatus.PERMANENT_REDIRECT.value())
                .status(HttpStatus.PERMANENT_REDIRECT);
    }

    /**
     * <p> 指示客户端发送的请求在语法上不正确。 </p>
     */
    default ValidatorBuilder BAD_REQUEST() {
        return message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST);
    }

    /**
     * <p> 指示请求需要http身份验证。 </p>
     */
    default ValidatorBuilder UNAUTHORIZED() {
        return message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED);
    }

    /**
     * <p>保留以备将来使用。 </p>
     */
    default ValidatorBuilder PAYMENT_REQUIRED() {
        return message(HttpStatus.PAYMENT_REQUIRED.getReasonPhrase())
                .code(HttpStatus.PAYMENT_REQUIRED.value())
                .status(HttpStatus.PAYMENT_REQUIRED);
    }

    /**
     * <p> 表示服务器理解请求但拒绝执行。 </p>
     */
    default ValidatorBuilder FORBIDDEN() {
        return message(HttpStatus.FORBIDDEN.getReasonPhrase())
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN);
    }

    /**
     * <p> 表示请求的资源不可用。 </p>
     */
    default ValidatorBuilder NOT_FOUND() {
        return message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND);
    }

    /**
     * <p> 指示由请求uri标识的资源不允许使用请求行中指定的方法。 </p>
     */
    default ValidatorBuilder METHOD_NOT_ALLOWED() {
        return message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                .status(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * <p> 指示由请求标识的资源只能根据请求中发送的接受头生成内容特征不可接受的响应实体。 </p>
     */
    default ValidatorBuilder NOT_ACCEPTABLE() {
        return message(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase())
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .status(HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * <p> 表示客户机必须首先使用代理对自己进行身份验证。  </p>
     */
    default ValidatorBuilder PROXY_AUTHENTICATION_REQUIRED() {
        return message(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.getReasonPhrase())
                .code(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.value())
                .status(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    /**
     * <p> 指示客户端在服务器准备等待的时间内未生成请求。  </p>
     */
    default ValidatorBuilder REQUEST_TIMEOUT() {
        return message(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
                .code(HttpStatus.REQUEST_TIMEOUT.value())
                .status(HttpStatus.REQUEST_TIMEOUT);
    }

    /**
     * <p> 指示由于与资源的当前状态冲突而无法完成请求。 </p>
     */
    default ValidatorBuilder CONFLICT() {
        return message(HttpStatus.CONFLICT.getReasonPhrase())
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT);
    }

    /**
     * <p> 表示资源在服务器上不再可用，并且不知道转发地址。这种情况应视为永久性的。 </p>
     */
    default ValidatorBuilder GONE() {
        return message(HttpStatus.GONE.getReasonPhrase())
                .code(HttpStatus.GONE.value())
                .status(HttpStatus.GONE);
    }

    /**
     * <p> 表示没有定义“Content-Length”无法处理请求。 </p>
     */
    default ValidatorBuilder LENGTH_REQUIRED() {
        return message(HttpStatus.LENGTH_REQUIRED.getReasonPhrase())
                .code(HttpStatus.LENGTH_REQUIRED.value())
                .status(HttpStatus.LENGTH_REQUIRED);
    }

    /**
     * <p> 指示一个或多个请求头字段中给定的前置条件在服务器上测试时计算为FALSE。 </p>
     */
    default ValidatorBuilder PRECONDITION_FAILED() {
        return message(HttpStatus.PRECONDITION_FAILED.getReasonPhrase())
                .code(HttpStatus.PRECONDITION_FAILED.value())
                .status(HttpStatus.PRECONDITION_FAILED);
    }

    /**
     * <p> 表示服务器拒绝处理请求，因为请求实体大于服务器愿意或能够处理的实体。 </p>
     */
    default ValidatorBuilder PAYLOAD_TOO_LARGE() {
        return message(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase())
                .code(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .status(HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @Deprecated
    default ValidatorBuilder REQUEST_ENTITY_TOO_LARGE() {
        return message(HttpStatus.REQUEST_ENTITY_TOO_LARGE.getReasonPhrase())
                .code(HttpStatus.REQUEST_ENTITY_TOO_LARGE.value())
                .status(HttpStatus.REQUEST_ENTITY_TOO_LARGE);
    }

    /**
     * <p> 表示服务器拒绝服务请求，因为“Request-URI”比服务器愿意解释的要长。 </p>
     */
    default ValidatorBuilder URI_TOO_LONG() {
        return message(HttpStatus.URI_TOO_LONG.getReasonPhrase())
                .code(HttpStatus.URI_TOO_LONG.value())
                .status(HttpStatus.URI_TOO_LONG);
    }

    @Deprecated
    default ValidatorBuilder REQUEST_URI_TOO_LONG() {
        return message(HttpStatus.REQUEST_URI_TOO_LONG.getReasonPhrase())
                .code(HttpStatus.REQUEST_URI_TOO_LONG.value())
                .status(HttpStatus.REQUEST_URI_TOO_LONG);
    }

    /**
     * <p> 指示服务器拒绝为请求提供服务，因为请求实体的格式不受请求方法的请求资源支持。 </p>
     */
    default ValidatorBuilder UNSUPPORTED_MEDIA_TYPE() {
        return message(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * <p> 表示服务器无法为请求的字节范围提供服务。 </p>
     */
    default ValidatorBuilder REQUESTED_RANGE_NOT_SATISFIABLE() {
        return message(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.getReasonPhrase())
                .code(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value())
                .status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    /**
     * <p> 指示服务器无法满足Expect请求头中给定的期望。 </p>
     */
    default ValidatorBuilder EXPECTATION_FAILED() {
        return message(HttpStatus.EXPECTATION_FAILED.getReasonPhrase())
                .code(HttpStatus.EXPECTATION_FAILED.value())
                .status(HttpStatus.EXPECTATION_FAILED);
    }

    default ValidatorBuilder I_AM_A_TEAPOT() {
        return message(HttpStatus.I_AM_A_TEAPOT.getReasonPhrase())
                .code(HttpStatus.I_AM_A_TEAPOT.value())
                .status(HttpStatus.I_AM_A_TEAPOT);
    }

    @Deprecated
    default ValidatorBuilder INSUFFICIENT_SPACE_ON_RESOURCE() {
        return message(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE.getReasonPhrase())
                .code(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE.value())
                .status(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE);
    }

    @Deprecated
    default ValidatorBuilder METHOD_FAILURE() {
        return message(HttpStatus.METHOD_FAILURE.getReasonPhrase())
                .code(HttpStatus.METHOD_FAILURE.value())
                .status(HttpStatus.METHOD_FAILURE);
    }

    @Deprecated
    default ValidatorBuilder DESTINATION_LOCKED() {
        return message(HttpStatus.DESTINATION_LOCKED.getReasonPhrase())
                .code(HttpStatus.DESTINATION_LOCKED.value())
                .status(HttpStatus.DESTINATION_LOCKED);
    }

    default ValidatorBuilder UNPROCESSABLE_ENTITY() {
        return message(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    default ValidatorBuilder LOCKED() {
        return message(HttpStatus.LOCKED.getReasonPhrase())
                .code(HttpStatus.LOCKED.value())
                .status(HttpStatus.LOCKED);
    }

    default ValidatorBuilder FAILED_DEPENDENCY() {
        return message(HttpStatus.FAILED_DEPENDENCY.getReasonPhrase())
                .code(HttpStatus.FAILED_DEPENDENCY.value())
                .status(HttpStatus.FAILED_DEPENDENCY);
    }

    default ValidatorBuilder TOO_EARLY() {
        return message(HttpStatus.TOO_EARLY.getReasonPhrase())
                .code(HttpStatus.TOO_EARLY.value())
                .status(HttpStatus.TOO_EARLY);
    }

    default ValidatorBuilder UPGRADE_REQUIRED() {
        return message(HttpStatus.UPGRADE_REQUIRED.getReasonPhrase())
                .code(HttpStatus.UPGRADE_REQUIRED.value())
                .status(HttpStatus.UPGRADE_REQUIRED);
    }

    default ValidatorBuilder PRECONDITION_REQUIRED() {
        return message(HttpStatus.PRECONDITION_REQUIRED.getReasonPhrase())
                .code(HttpStatus.PRECONDITION_REQUIRED.value())
                .status(HttpStatus.PRECONDITION_REQUIRED);
    }

    default ValidatorBuilder TOO_MANY_REQUESTS() {
        return message(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase())
                .code(HttpStatus.TOO_MANY_REQUESTS.value())
                .status(HttpStatus.TOO_MANY_REQUESTS);
    }

    default ValidatorBuilder REQUEST_HEADER_FIELDS_TOO_LARGE() {
        return message(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.getReasonPhrase())
                .code(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.value())
                .status(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    default ValidatorBuilder UNAVAILABLE_FOR_LEGAL_REASONS() {
        return message(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.getReasonPhrase())
                .code(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value())
                .status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    /**
     * <p> 指示HTTP服务器内部的错误，该错误阻止它完成请求。 </p>
     */
    default ValidatorBuilder INTERNAL_SERVER_ERROR() {
        return message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * <p> 表示http服务器不支持完成请求所需的功能。 </p>
     */
    default ValidatorBuilder NOT_IMPLEMENTED() {
        return message(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase())
                .code(HttpStatus.NOT_IMPLEMENTED.value())
                .status(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * <p> 表示http服务器在充当代理或网关时从其所咨询的服务器接收到无效响应。 </p>
     */
    default ValidatorBuilder BAD_GATEWAY() {
        return message(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .code(HttpStatus.BAD_GATEWAY.value())
                .status(HttpStatus.BAD_GATEWAY);
    }

    /**
     * <p> 表示http服务器暂时过载，无法处理该请求。 </p>
     */
    default ValidatorBuilder SERVICE_UNAVAILABLE() {
        return message(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .status(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * <p> 表示服务器在充当网关或代理时未收到来自上游服务器的及时响应。 </p>
     */
    default ValidatorBuilder GATEWAY_TIMEOUT() {
        return message(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase())
                .code(HttpStatus.GATEWAY_TIMEOUT.value())
                .status(HttpStatus.GATEWAY_TIMEOUT);
    }

    /**
     * <p> 表示服务器不支持或拒绝支持请求消息中使用的http协议版本。 </p>
     */
    default ValidatorBuilder HTTP_VERSION_NOT_SUPPORTED() {
        return message(HttpStatus.HTTP_VERSION_NOT_SUPPORTED.getReasonPhrase())
                .code(HttpStatus.HTTP_VERSION_NOT_SUPPORTED.value())
                .status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
    }

    default ValidatorBuilder VARIANT_ALSO_NEGOTIATES() {
        return message(HttpStatus.VARIANT_ALSO_NEGOTIATES.getReasonPhrase())
                .code(HttpStatus.VARIANT_ALSO_NEGOTIATES.value())
                .status(HttpStatus.VARIANT_ALSO_NEGOTIATES);
    }

    default ValidatorBuilder INSUFFICIENT_STORAGE() {
        return message(HttpStatus.INSUFFICIENT_STORAGE.getReasonPhrase())
                .code(HttpStatus.INSUFFICIENT_STORAGE.value())
                .status(HttpStatus.INSUFFICIENT_STORAGE);
    }

    default ValidatorBuilder LOOP_DETECTED() {
        return message(HttpStatus.LOOP_DETECTED.getReasonPhrase())
                .code(HttpStatus.LOOP_DETECTED.value())
                .status(HttpStatus.LOOP_DETECTED);
    }

    default ValidatorBuilder BANDWIDTH_LIMIT_EXCEEDED() {
        return message(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.getReasonPhrase())
                .code(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value())
                .status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }

    default ValidatorBuilder NOT_EXTENDED() {
        return message(HttpStatus.NOT_EXTENDED.getReasonPhrase())
                .code(HttpStatus.NOT_EXTENDED.value())
                .status(HttpStatus.NOT_EXTENDED);
    }

    default ValidatorBuilder NETWORK_AUTHENTICATION_REQUIRED() {
        return message(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.getReasonPhrase())
                .code(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value())
                .status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
    }
}
