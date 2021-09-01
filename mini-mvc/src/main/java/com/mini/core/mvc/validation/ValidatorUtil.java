package com.mini.core.mvc.validation;

import com.mini.core.mvc.validation.ValidatorBuilder.ValidatorBuilderImpl;
import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;

/**
 * 自定义错误消息工具类
 *
 * @author pangchao
 */
public final class ValidatorUtil {
    /**
     * 设置HTTP状态码
     *
     * @param status HTTP状态码
     * @return 自定义消息构建器
     */
    @Nonnull
    public static ValidatorBuilder status(HttpStatus status) {
        return new ValidatorBuilderImpl().status(status)
                .message(status.getReasonPhrase());
    }

    /**
     * 设置消息内容
     * <p>
     * {message}表示取国际化消息属性文件名称
     * </p>
     *
     * @param message 消息内容
     * @return 自定义消息构建器
     */
    @Nonnull
    public static ValidatorBuilder message(String message) {
        return new ValidatorBuilderImpl().message(message);
    }

    /**
     * 设置专用错误码
     *
     * @param code 用错误码
     * @return 自定义消息构建器
     */
    @Nonnull
    public static ValidatorBuilder code(Integer code) {
        return new ValidatorBuilderImpl().code(code);
    }

    /**
     * 设置错误字段名称
     *
     * @param field 错误字段名称
     * @return 自定义消息构建器
     */
    @Nonnull
    public static ValidatorBuilder field(String field) {
        return new ValidatorBuilderImpl().field(field);
    }



    /**
     * <p>表示客户端可以继续。</p>
     */

    public static ValidatorBuilder continues(String message, Integer code) {
        return status(CONTINUE).message(message).code(code);
    }

    public static ValidatorBuilder continues(String message) {
        return status(CONTINUE).message(message);
    }

    public static ValidatorBuilder continues() {
        return status(CONTINUE);
    }

    /**
     * <p>服务器将遵从客户的请求转换到另外一种协议</p>
     */
    public static ValidatorBuilder switchingProtocols(String message, Integer code) {
        return status(SWITCHING_PROTOCOLS).message(message).code(code);
    }

    public static ValidatorBuilder switchingProtocols(String message) {
        return status(SWITCHING_PROTOCOLS).message(message);
    }

    public static ValidatorBuilder switchingProtocols() {
        return status(SWITCHING_PROTOCOLS);
    }

    /**
     * 由WebDAV（RFC 2518）扩展的状态码，代表处理将被继续执行
     */
    public static ValidatorBuilder processing(String message, Integer code) {
        return status(PROCESSING).message(message).code(code);
    }

    public static ValidatorBuilder processing(String message) {
        return status(PROCESSING).message(message);
    }

    public static ValidatorBuilder processing() {
        return status(PROCESSING);
    }

    /**
     * 在头部信息到达之前，用户可以开始预加载CSS和JavaScript文件
     */
    public static ValidatorBuilder checkpoint(String message, Integer code) {
        return status(CHECKPOINT).message(message).code(code);
    }

    public static ValidatorBuilder checkpoint(String message) {
        return status(CHECKPOINT).message(message);
    }

    public static ValidatorBuilder checkpoint() {
        return status(CHECKPOINT);
    }

    /**
     * <p> 表示请求正常成功。 </p>
     */

    public static ValidatorBuilder ok(String message, Integer code) {
        return status(OK).message(message).code(code);
    }


    public static ValidatorBuilder ok(String message) {
        return status(OK).message(message);
    }

    public static ValidatorBuilder ok() {
        return status(OK);
    }


    /**
     * <p> 指示请求成功并在服务器上创建了新资源。 </p>
     */
    public static ValidatorBuilder created(String message, Integer code) {
        return status(CREATED).message(message).code(code);
    }

    public static ValidatorBuilder created(String message) {
        return status(CREATED).message(message);
    }

    public static ValidatorBuilder created() {
        return status(CREATED);
    }

    /**
     * <p> 指示已接受请求正在处理，但未完成。 </p>
     */
    public static ValidatorBuilder accepted(String message, Integer code) {
        return status(ACCEPTED).message(message).code(code);
    }

    public static ValidatorBuilder accepted(String message) {
        return status(ACCEPTED).message(message);
    }

    public static ValidatorBuilder accepted() {
        return status(ACCEPTED);
    }


    /**
     * <p> 表示客户端提供的元信息不是来自服务器。  </p>
     */
    public static ValidatorBuilder nonAuthoritativeInformation(String message, Integer code) {
        return status(NON_AUTHORITATIVE_INFORMATION).message(message).code(code);
    }

    public static ValidatorBuilder nonAuthoritativeInformation(String message) {
        return status(NON_AUTHORITATIVE_INFORMATION).message(message);
    }

    public static ValidatorBuilder nonAuthoritativeInformation() {
        return status(NON_AUTHORITATIVE_INFORMATION);
    }

    /**
     * <p> 表示请求成功，但没有要返回的新信息。 </p>
     */
    public static ValidatorBuilder noContent(String message, Integer code) {
        return status(NO_CONTENT).message(message).code(code);
    }

    public static ValidatorBuilder noContent(String message) {
        return status(NO_CONTENT).message(message);
    }

    public static ValidatorBuilder noContent() {
        return status(NO_CONTENT);
    }

    /**
     * <p> 指示代理应重置导致发送请求的文档视图。</p>
     */
    public static ValidatorBuilder resetContent(String message, Integer code) {
        return status(RESET_CONTENT).message(message).code(code);
    }

    public static ValidatorBuilder resetContent(String message) {
        return status(RESET_CONTENT).message(message);
    }

    public static ValidatorBuilder resetContent() {
        return status(RESET_CONTENT);
    }

    /**
     * <p> 表示服务器已完成对资源的部分获取请求。 </p>
     */
    public static ValidatorBuilder partialContent(String message, Integer code) {
        return status(PARTIAL_CONTENT).message(message).code(code);
    }

    public static ValidatorBuilder partialContent(String message) {
        return status(PARTIAL_CONTENT).message(message);
    }

    public static ValidatorBuilder partialContent() {
        return status(PARTIAL_CONTENT);
    }

    public static ValidatorBuilder multiStatus(String message, Integer code) {
        return status(MULTI_STATUS).message(message).code(code);
    }

    public static ValidatorBuilder multiStatus(String message) {
        return status(MULTI_STATUS).message(message);
    }

    public static ValidatorBuilder multiStatus() {
        return status(MULTI_STATUS);
    }

    public static ValidatorBuilder alreadyReported(String message, Integer code) {
        return status(ALREADY_REPORTED).message(message).code(code);
    }

    public static ValidatorBuilder alreadyReported(String message) {
        return status(ALREADY_REPORTED).message(message);
    }

    public static ValidatorBuilder alreadyReported() {
        return status(ALREADY_REPORTED);
    }

    public static ValidatorBuilder imUsed(String message, Integer code) {
        return status(IM_USED).message(message).code(code);
    }

    public static ValidatorBuilder imUsed(String message) {
        return status(IM_USED).message(message);
    }

    public static ValidatorBuilder imUsed() {
        return status(IM_USED);
    }

    /**
     * <p> 指示请求的资源对应于一组表示中的任何一个，每个表示都有自己的特定位置。 </p>
     */
    public static ValidatorBuilder multipleChoices(String message, Integer code) {
        return status(MULTIPLE_CHOICES).message(message).code(code);
    }

    public static ValidatorBuilder multipleChoices(String message) {
        return status(MULTIPLE_CHOICES).message(message);
    }

    public static ValidatorBuilder multipleChoices() {
        return status(MULTIPLE_CHOICES);
    }

    /**
     * <p> 指示资源已永久移动到新位置，并且将来的引用应在其请求中使用新的URI。 </p>
     */
    public static ValidatorBuilder movedPermanently(String message, Integer code) {
        return status(MOVED_PERMANENTLY).message(message).code(code);
    }

    public static ValidatorBuilder movedPermanently(String message) {
        return status(MOVED_PERMANENTLY).message(message);
    }

    public static ValidatorBuilder movedPermanently() {
        return status(MOVED_PERMANENTLY);
    }

    /**
     * <p> 指示资源暂时位于不同的uri下。由于有时可能会更改重定向，</p>
     * <p> 客户端应继续使用请求URI来表示将来的请求（HTTP/1.1），因此建议使用此变量。</p>
     */
    public static ValidatorBuilder found(String message, Integer code) {
        return status(FOUND).message(message).code(code);
    }

    public static ValidatorBuilder found(String message) {
        return status(FOUND).message(message);
    }

    public static ValidatorBuilder found() {
        return status(FOUND);
    }


    /**
     * <p> 指示资源已临时移动到另一个位置，但将来的引用仍应使用原始uri来访问资源。</p>
     * <p> 保留此定义是为了向后兼容。找到SC_FOUND 现在是首选定义。</p>
     */
    @Deprecated
    public static ValidatorBuilder movedTemporarily() {
        return status(MOVED_TEMPORARILY);
    }

    /**
     * <p> 指示可以在不同的uri下找到对请求的响应。 </p>
     */
    public static ValidatorBuilder seeOther(String message, Integer code) {
        return status(SEE_OTHER).message(message).code(code);
    }

    public static ValidatorBuilder seeOther(String message) {
        return status(SEE_OTHER).message(message);
    }

    public static ValidatorBuilder seeOther() {
        return status(SEE_OTHER);
    }

    /**
     * <p> 指示条件获取操作发现资源可用且未修改。 </p>
     */
    public static ValidatorBuilder notModified(String message, Integer code) {
        return status(NOT_MODIFIED).message(message).code(code);
    }

    public static ValidatorBuilder notModified(String message) {
        return status(NOT_MODIFIED).message(message);
    }

    public static ValidatorBuilder notModified() {
        return status(NOT_MODIFIED);
    }

    /**
     * <p> indicating that the requested resource
     * <em>MUST</em> be accessed through the proxy given by the
     * <code><em>Location</em></code> field. </p>
     */
    @Deprecated
    public static ValidatorBuilder useProxy() {
        return status(USE_PROXY);
    }

    /**
     * <p> 指示必须通过位置字段提供的代理访问请求的资源。</p>
     */
    public static ValidatorBuilder temporaryRedirect(String message, Integer code) {
        return status(TEMPORARY_REDIRECT).message(message).code(code);
    }

    public static ValidatorBuilder temporaryRedirect(String message) {
        return status(TEMPORARY_REDIRECT).message(message);
    }

    public static ValidatorBuilder temporaryRedirect() {
        return status(TEMPORARY_REDIRECT);
    }

    public static ValidatorBuilder permanentRedirect(String message, Integer code) {
        return status(PERMANENT_REDIRECT).message(message).code(code);
    }

    public static ValidatorBuilder permanentRedirect(String message) {
        return status(PERMANENT_REDIRECT).message(message);
    }

    public static ValidatorBuilder permanentRedirect() {
        return status(PERMANENT_REDIRECT);
    }


    /**
     * <p> 指示客户端发送的请求在语法上不正确。 </p>
     */
    public static ValidatorBuilder badRequest(String message, Integer code) {
        return status(BAD_REQUEST).message(message).code(code);
    }

    public static ValidatorBuilder badRequest(String message) {
        return status(BAD_REQUEST).message(message);
    }

    public static ValidatorBuilder badRequest() {
        return status(BAD_REQUEST);
    }

    /**
     * <p> 指示请求需要http身份验证。 </p>
     */
    public static ValidatorBuilder unauthorized(String message, Integer code) {
        return status(UNAUTHORIZED).message(message).code(code);
    }

    public static ValidatorBuilder unauthorized(String message) {
        return status(UNAUTHORIZED).message(message);
    }

    public static ValidatorBuilder unauthorized() {
        return status(UNAUTHORIZED);
    }

    /**
     * <p>保留以备将来使用。 </p>
     */
    public static ValidatorBuilder paymentRequired(String message, Integer code) {
        return status(PAYMENT_REQUIRED).message(message).code(code);
    }

    public static ValidatorBuilder paymentRequired(String message) {
        return status(PAYMENT_REQUIRED).message(message);
    }

    public static ValidatorBuilder paymentRequired() {
        return status(PAYMENT_REQUIRED);
    }

    /**
     * <p> 表示服务器理解请求但拒绝执行。 </p>
     */
    public static ValidatorBuilder forbidden(String message, Integer code) {
        return status(FORBIDDEN).message(message).code(code);
    }

    public static ValidatorBuilder forbidden(String message) {
        return status(FORBIDDEN).message(message);
    }

    public static ValidatorBuilder forbidden() {
        return status(FORBIDDEN);
    }


    /**
     * <p> 表示请求的资源不可用。 </p>
     */
    public static ValidatorBuilder notFound(String message, Integer code) {
        return status(NOT_FOUND).message(message).code(code);
    }

    public static ValidatorBuilder notFound(String message) {
        return status(NOT_FOUND).message(message);
    }

    public static ValidatorBuilder notFound() {
        return status(NOT_FOUND);
    }

    /**
     * <p> 指示由请求uri标识的资源不允许使用请求行中指定的方法。 </p>
     */
    public static ValidatorBuilder methodNotAllowed(String message, Integer code) {
        return status(METHOD_NOT_ALLOWED).message(message).code(code);
    }

    public static ValidatorBuilder methodNotAllowed(String message) {
        return status(METHOD_NOT_ALLOWED).message(message);
    }

    public static ValidatorBuilder methodNotAllowed() {
        return status(METHOD_NOT_ALLOWED);
    }

    /**
     * <p> 指示由请求标识的资源只能根据请求中发送的接受头生成内容特征不可接受的响应实体。 </p>
     */
    public static ValidatorBuilder notAcceptable(String message, Integer code) {
        return status(NOT_ACCEPTABLE).message(message).code(code);
    }

    public static ValidatorBuilder notAcceptable(String message) {
        return status(NOT_ACCEPTABLE).message(message);
    }

    public static ValidatorBuilder notAcceptable() {
        return status(NOT_ACCEPTABLE);
    }

    /**
     * <p> 表示客户机必须首先使用代理对自己进行身份验证。  </p>
     */
    public static ValidatorBuilder proxyAuthenticationRequired(String message, Integer code) {
        return status(PROXY_AUTHENTICATION_REQUIRED).message(message).code(code);
    }

    public static ValidatorBuilder proxyAuthenticationRequired(String message) {
        return status(PROXY_AUTHENTICATION_REQUIRED).message(message);
    }

    public static ValidatorBuilder proxyAuthenticationRequired() {
        return status(PROXY_AUTHENTICATION_REQUIRED);
    }

    /**
     * <p> 指示客户端在服务器准备等待的时间内未生成请求。  </p>
     */
    public static ValidatorBuilder requestTimeout(String message, Integer code) {
        return status(REQUEST_TIMEOUT).message(message).code(code);
    }

    public static ValidatorBuilder requestTimeout(String message) {
        return status(REQUEST_TIMEOUT).message(message);
    }

    public static ValidatorBuilder requestTimeout() {
        return status(REQUEST_TIMEOUT);
    }

    /**
     * <p> 指示由于与资源的当前状态冲突而无法完成请求。 </p>
     */
    public static ValidatorBuilder conflict(String message, Integer code) {
        return status(CONFLICT).message(message).code(code);
    }

    public static ValidatorBuilder conflict(String message) {
        return status(CONFLICT).message(message);
    }

    public static ValidatorBuilder conflict() {
        return status(CONFLICT);
    }

    /**
     * <p> 表示资源在服务器上不再可用，并且不知道转发地址。这种情况应视为永久性的。 </p>
     */
    public static ValidatorBuilder gone(String message, Integer code) {
        return status(GONE).message(message).code(code);
    }

    public static ValidatorBuilder gone(String message) {
        return status(GONE).message(message);
    }

    public static ValidatorBuilder gone() {
        return status(GONE);
    }

    /**
     * <p> 表示没有定义“Content-Length”无法处理请求。 </p>
     */
    public static ValidatorBuilder lengthRequired(String message, Integer code) {
        return status(LENGTH_REQUIRED).message(message).code(code);
    }

    public static ValidatorBuilder lengthRequired(String message) {
        return status(LENGTH_REQUIRED).message(message);
    }

    public static ValidatorBuilder lengthRequired() {
        return status(LENGTH_REQUIRED);
    }

    /**
     * <p> 指示一个或多个请求头字段中给定的前置条件在服务器上测试时计算为FALSE。 </p>
     */
    public static ValidatorBuilder preconditionFailed(String message, Integer code) {
        return status(PRECONDITION_FAILED).message(message).code(code);
    }

    public static ValidatorBuilder preconditionFailed(String message) {
        return status(PRECONDITION_FAILED).message(message);
    }

    public static ValidatorBuilder preconditionFailed() {
        return status(PRECONDITION_FAILED);
    }

    /**
     * <p> 表示服务器拒绝处理请求，因为请求实体大于服务器愿意或能够处理的实体。 </p>
     */
    public static ValidatorBuilder payloadTooLarge(String message, Integer code) {
        return status(PAYLOAD_TOO_LARGE).message(message).code(code);
    }

    public static ValidatorBuilder payloadTooLarge(String message) {
        return status(PAYLOAD_TOO_LARGE).message(message);
    }

    public static ValidatorBuilder payloadTooLarge() {
        return status(PAYLOAD_TOO_LARGE);
    }

    @Deprecated
    public static ValidatorBuilder requestEntityTooLarge() {
        return status(REQUEST_ENTITY_TOO_LARGE);
    }

    /**
     * <p> 表示服务器拒绝服务请求，因为“Request-URI”比服务器愿意解释的要长。 </p>
     */
    public static ValidatorBuilder uriTooLong(String message, Integer code) {
        return status(URI_TOO_LONG).message(message).code(code);
    }

    public static ValidatorBuilder uriTooLong(String message) {
        return status(URI_TOO_LONG).message(message);
    }

    public static ValidatorBuilder uriTooLong() {
        return status(URI_TOO_LONG);
    }


    @Deprecated
    public static ValidatorBuilder requestUriTooLong() {
        return status(REQUEST_URI_TOO_LONG);
    }

    /**
     * <p> 指示服务器拒绝为请求提供服务，因为请求实体的格式不受请求方法的请求资源支持。 </p>
     */
    public static ValidatorBuilder unsupportedMediaType(String message, Integer code) {
        return status(UNSUPPORTED_MEDIA_TYPE).message(message).code(code);
    }

    public static ValidatorBuilder unsupportedMediaType(String message) {
        return status(UNSUPPORTED_MEDIA_TYPE).message(message);
    }

    public static ValidatorBuilder unsupportedMediaType() {
        return status(UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * <p> 表示服务器无法为请求的字节范围提供服务。 </p>
     */
    public static ValidatorBuilder requestedRangeNotSatisfiable(String message, Integer code) {
        return status(REQUESTED_RANGE_NOT_SATISFIABLE).message(message).code(code);
    }

    public static ValidatorBuilder requestedRangeNotSatisfiable(String message) {
        return status(REQUESTED_RANGE_NOT_SATISFIABLE).message(message);
    }

    public static ValidatorBuilder requestedRangeNotSatisfiable() {
        return status(REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    /**
     * <p> 指示服务器无法满足Expect请求头中给定的期望。 </p>
     */
    public static ValidatorBuilder expectationFailed(String message, Integer code) {
        return status(EXPECTATION_FAILED).message(message).code(code);
    }

    public static ValidatorBuilder expectationFailed(String message) {
        return status(EXPECTATION_FAILED).message(message);
    }

    public static ValidatorBuilder expectationFailed() {
        return status(EXPECTATION_FAILED);
    }

    public static ValidatorBuilder iAmATeapot(String message, Integer code) {
        return status(I_AM_A_TEAPOT).message(message).code(code);
    }

    public static ValidatorBuilder iAmATeapot(String message) {
        return status(I_AM_A_TEAPOT).message(message);
    }

    public static ValidatorBuilder iAmATeapot() {
        return status(I_AM_A_TEAPOT);
    }

    @Deprecated
    public static ValidatorBuilder insufficientSpaceOnResource() {
        return status(INSUFFICIENT_SPACE_ON_RESOURCE);
    }

    @Deprecated
    public static ValidatorBuilder methodFailure() {
        return status(METHOD_FAILURE);
    }

    @Deprecated
    public static ValidatorBuilder destinationLocked() {
        return status(DESTINATION_LOCKED);
    }

    public static ValidatorBuilder unprocessableEntity(String message, Integer code) {
        return status(UNPROCESSABLE_ENTITY).message(message).code(code);
    }

    public static ValidatorBuilder unprocessableEntity(String message) {
        return status(UNPROCESSABLE_ENTITY).message(message);
    }

    public static ValidatorBuilder unprocessableEntity() {
        return status(UNPROCESSABLE_ENTITY);
    }

    public static ValidatorBuilder locked(String message, Integer code) {
        return status(LOCKED).message(message).code(code);
    }

    public static ValidatorBuilder locked(String message) {
        return status(LOCKED).message(message);
    }

    public static ValidatorBuilder locked() {
        return status(LOCKED);
    }

    public static ValidatorBuilder failedDependency(String message, Integer code) {
        return status(FAILED_DEPENDENCY).message(message).code(code);
    }

    public static ValidatorBuilder failedDependency(String message) {
        return status(FAILED_DEPENDENCY).message(message);
    }

    public static ValidatorBuilder failedDependency() {
        return status(FAILED_DEPENDENCY);
    }

    public static ValidatorBuilder tooEarly(String message, Integer code) {
        return status(TOO_EARLY).message(message).code(code);
    }

    public static ValidatorBuilder tooEarly(String message) {
        return status(TOO_EARLY).message(message);
    }

    public static ValidatorBuilder tooEarly() {
        return status(TOO_EARLY);
    }

    public static ValidatorBuilder upgradeRequired(String message, Integer code) {
        return status(UPGRADE_REQUIRED).message(message).code(code);
    }

    public static ValidatorBuilder upgradeRequired(String message) {
        return status(UPGRADE_REQUIRED).message(message);
    }

    public static ValidatorBuilder upgradeRequired() {
        return status(UPGRADE_REQUIRED);
    }

    public static ValidatorBuilder preconditionRequired(String message, Integer code) {
        return status(PRECONDITION_REQUIRED).message(message).code(code);
    }

    public static ValidatorBuilder preconditionRequired(String message) {
        return status(PRECONDITION_REQUIRED).message(message);
    }

    public static ValidatorBuilder preconditionRequired() {
        return status(PRECONDITION_REQUIRED);
    }

    public static ValidatorBuilder tooManyRequests(String message, Integer code) {
        return status(TOO_MANY_REQUESTS).message(message).code(code);
    }

    public static ValidatorBuilder tooManyRequests(String message) {
        return status(TOO_MANY_REQUESTS).message(message);
    }

    public static ValidatorBuilder tooManyRequests() {
        return status(TOO_MANY_REQUESTS);
    }

    public static ValidatorBuilder requestHeaderFieldsTooLarge(String message, Integer code) {
        return status(REQUEST_HEADER_FIELDS_TOO_LARGE).message(message).code(code);
    }

    public static ValidatorBuilder requestHeaderFieldsTooLarge(String message) {
        return status(REQUEST_HEADER_FIELDS_TOO_LARGE).message(message);
    }

    public static ValidatorBuilder requestHeaderFieldsTooLarge() {
        return status(REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    public static ValidatorBuilder unavailableForLegalReasons(String message, Integer code) {
        return status(UNAVAILABLE_FOR_LEGAL_REASONS).message(message).code(code);
    }

    public static ValidatorBuilder unavailableForLegalReasons(String message) {
        return status(UNAVAILABLE_FOR_LEGAL_REASONS).message(message);
    }

    public static ValidatorBuilder unavailableForLegalReasons() {
        return status(UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    /**
     * <p> 指示HTTP服务器内部的错误，该错误阻止它完成请求。 </p>
     */
    public static ValidatorBuilder internalServerError(String message, Integer code) {
        return status(INTERNAL_SERVER_ERROR).message(message).code(code);
    }

    public static ValidatorBuilder internalServerError(String message) {
        return status(INTERNAL_SERVER_ERROR).message(message);
    }

    public static ValidatorBuilder internalServerError() {
        return status(INTERNAL_SERVER_ERROR);
    }

    /**
     * <p> 表示http服务器不支持完成请求所需的功能。 </p>
     */
    public static ValidatorBuilder notImplemented(String message, Integer code) {
        return status(NOT_IMPLEMENTED).message(message).code(code);
    }

    public static ValidatorBuilder notImplemented(String message) {
        return status(NOT_IMPLEMENTED).message(message);
    }

    public static ValidatorBuilder notImplemented() {
        return status(NOT_IMPLEMENTED);
    }

    /**
     * <p> 表示http服务器在充当代理或网关时从其所咨询的服务器接收到无效响应。 </p>
     */
    public static ValidatorBuilder badGateway(String message, Integer code) {
        return status(BAD_GATEWAY).message(message).code(code);
    }

    public static ValidatorBuilder badGateway(String message) {
        return status(BAD_GATEWAY).message(message);
    }

    public static ValidatorBuilder badGateway() {
        return status(BAD_GATEWAY);
    }

    /**
     * <p> 表示http服务器暂时过载，无法处理该请求。 </p>
     */
    public static ValidatorBuilder serviceUnavailable(String message, Integer code) {
        return status(SERVICE_UNAVAILABLE).message(message).code(code);
    }

    public static ValidatorBuilder serviceUnavailable(String message) {
        return status(SERVICE_UNAVAILABLE).message(message);
    }

    public static ValidatorBuilder serviceUnavailable() {
        return status(SERVICE_UNAVAILABLE);
    }

    /**
     * <p> 表示服务器在充当网关或代理时未收到来自上游服务器的及时响应。 </p>
     */
    public static ValidatorBuilder gatewayTimeout(String message, Integer code) {
        return status(GATEWAY_TIMEOUT).message(message).code(code);
    }

    public static ValidatorBuilder gatewayTimeout(String message) {
        return status(GATEWAY_TIMEOUT).message(message);
    }

    public static ValidatorBuilder gatewayTimeout() {
        return status(GATEWAY_TIMEOUT);
    }

    /**
     * <p> 表示服务器不支持或拒绝支持请求消息中使用的http协议版本。 </p>
     */
    public static ValidatorBuilder httpVersionNotSupported(String message, Integer code) {
        return status(HTTP_VERSION_NOT_SUPPORTED).message(message).code(code);
    }

    public static ValidatorBuilder httpVersionNotSupported(String message) {
        return status(HTTP_VERSION_NOT_SUPPORTED).message(message);
    }

    public static ValidatorBuilder httpVersionNotSupported() {
        return status(HTTP_VERSION_NOT_SUPPORTED);
    }

    public static ValidatorBuilder variantAlsoNegotiates(String message, Integer code) {
        return status(VARIANT_ALSO_NEGOTIATES).message(message).code(code);
    }

    public static ValidatorBuilder variantAlsoNegotiates(String message) {
        return status(VARIANT_ALSO_NEGOTIATES).message(message);
    }

    public static ValidatorBuilder variantAlsoNegotiates() {
        return status(VARIANT_ALSO_NEGOTIATES);
    }

    public static ValidatorBuilder insufficientStorage(String message, Integer code) {
        return status(INSUFFICIENT_STORAGE).message(message).code(code);
    }

    public static ValidatorBuilder insufficientStorage(String message) {
        return status(INSUFFICIENT_STORAGE).message(message);
    }

    public static ValidatorBuilder insufficientStorage() {
        return status(INSUFFICIENT_STORAGE);
    }

    public static ValidatorBuilder loopDetected(String message, Integer code) {
        return status(LOOP_DETECTED).message(message).code(code);
    }

    public static ValidatorBuilder loopDetected(String message) {
        return status(LOOP_DETECTED).message(message);
    }

    public static ValidatorBuilder loopDetected() {
        return status(LOOP_DETECTED);
    }

    public static ValidatorBuilder bandwidthLimitExceeded(String message, Integer code) {
        return status(BANDWIDTH_LIMIT_EXCEEDED).message(message).code(code);
    }

    public static ValidatorBuilder bandwidthLimitExceeded(String message) {
        return status(BANDWIDTH_LIMIT_EXCEEDED).message(message);
    }

    public static ValidatorBuilder bandwidthLimitExceeded() {
        return status(BANDWIDTH_LIMIT_EXCEEDED);
    }

    public static ValidatorBuilder notExtended(String message, Integer code) {
        return status(NOT_EXTENDED).message(message).code(code);
    }

    public static ValidatorBuilder notExtended(String message) {
        return status(NOT_EXTENDED).message(message);
    }

    public static ValidatorBuilder notExtended() {
        return status(NOT_EXTENDED);
    }

    public static ValidatorBuilder networkAuthenticationRequired(String message, Integer code) {
        return status(NETWORK_AUTHENTICATION_REQUIRED).message(message).code(code);
    }

    public static ValidatorBuilder networkAuthenticationRequired(String message) {
        return status(NETWORK_AUTHENTICATION_REQUIRED).message(message);
    }

    public static ValidatorBuilder networkAuthenticationRequired() {
        return status(NETWORK_AUTHENTICATION_REQUIRED);
    }

    // ---------------------------------------------------------------------
    // -----------------下面是自定义的快捷错误消息------------------------------
    // ---------------------------------------------------------------------

    public static ValidatorBuilder noLogin(String message, Integer code) {
        return unauthorized(message, code);
    }

    public static ValidatorBuilder noLogin(String message) {
        return unauthorized(message);
    }

    public static ValidatorBuilder noLogin() {
        return noLogin("No Login");
    }

    public static ValidatorBuilder noPermission(String message, Integer code) {
        return forbidden(message, code);
    }

    public static ValidatorBuilder noPermission(String message) {
        return forbidden(message);
    }

    public static ValidatorBuilder noPermission() {
        return noPermission("No Permission");
    }

    public static ValidatorBuilder serverError(String message, Integer code) {
        return internalServerError(message, code);
    }

    public static ValidatorBuilder serverError(String message) {
        return internalServerError(message);
    }

    public static ValidatorBuilder serverError() {
        return serverError("Server Error");
    }
}