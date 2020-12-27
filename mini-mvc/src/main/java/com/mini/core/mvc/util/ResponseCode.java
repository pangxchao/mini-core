package com.mini.core.mvc.util;

import com.mini.core.mvc.validation.ValidatorBuilder;

import static com.mini.core.mvc.validation.ValidatorUtil.message;
import static org.springframework.http.HttpStatus.*;

public final class ResponseCode {
    private ResponseCode() {
    }

    /**
     * <p>表示客户端可以继续。</p>
     */
    public static ValidatorBuilder continues() {
        return message(CONTINUE.getReasonPhrase()).code(CONTINUE.value()).status(CONTINUE);
    }

    public static ValidatorBuilder continues(String message) {
        return message(message).code(CONTINUE.value()).status(CONTINUE);
    }

    public static ValidatorBuilder continues(String message, Integer code) {
        return message(message).code(code).status(CONTINUE);
    }

    /**
     * <p>服务器将遵从客户的请求转换到另外一种协议</p>
     */
    public static ValidatorBuilder switchingProtocols() {
        return message(SWITCHING_PROTOCOLS.getReasonPhrase()).code(SWITCHING_PROTOCOLS.value()).status(SWITCHING_PROTOCOLS);
    }

    public static ValidatorBuilder switchingProtocols(String message) {
        return message(message).code(SWITCHING_PROTOCOLS.value()).status(SWITCHING_PROTOCOLS);
    }

    public static ValidatorBuilder switchingProtocols(String message, Integer code) {
        return message(message).code(code).status(SWITCHING_PROTOCOLS);
    }

    /**
     * 由WebDAV（RFC 2518）扩展的状态码，代表处理将被继续执行
     */
    public static ValidatorBuilder processing() {
        return message(PROCESSING.getReasonPhrase()).code(PROCESSING.value()).status(PROCESSING);
    }

    public static ValidatorBuilder processing(String message) {
        return message(message).code(PROCESSING.value()).status(PROCESSING);
    }

    public static ValidatorBuilder processing(String message, Integer code) {
        return message(message).code(code).status(PROCESSING);
    }

    /**
     * 在头部信息到达之前，用户可以开始预加载CSS和JavaScript文件
     */
    public static ValidatorBuilder checkpoint() {
        return message(CHECKPOINT.getReasonPhrase()).code(CHECKPOINT.value()).status(CHECKPOINT);
    }

    public static ValidatorBuilder checkpoint(String message) {
        return message(message).code(CHECKPOINT.value()).status(CHECKPOINT);
    }

    public static ValidatorBuilder checkpoint(String message, Integer code) {
        return message(message).code(code).status(CHECKPOINT);
    }

    /**
     * <p> 表示请求正常成功。 </p>
     */
    public static ValidatorBuilder ok() {
        return message(OK.getReasonPhrase()).code(OK.value()).status(OK);
    }

    public static ValidatorBuilder ok(String message) {
        return message(message).code(OK.value()).status(OK);
    }

    public static ValidatorBuilder ok(String message, Integer code) {
        return message(message).code(code).status(OK);
    }

    /**
     * <p> 指示请求成功并在服务器上创建了新资源。 </p>
     */
    public static ValidatorBuilder created() {
        return message(CREATED.getReasonPhrase()).code(CREATED.value()).status(CREATED);
    }

    public static ValidatorBuilder created(String message) {
        return message(message).code(CREATED.value()).status(CREATED);
    }

    public static ValidatorBuilder created(String message, Integer code) {
        return message(message).code(code).status(CREATED);
    }

    /**
     * <p> 指示已接受请求正在处理，但未完成。 </p>
     */
    public static ValidatorBuilder accepted() {
        return message(ACCEPTED.getReasonPhrase()).code(ACCEPTED.value()).status(ACCEPTED);
    }

    public static ValidatorBuilder accepted(String message) {
        return message(message).code(ACCEPTED.value()).status(ACCEPTED);
    }

    public static ValidatorBuilder accepted(String message, Integer code) {
        return message(message).code(code).status(ACCEPTED);
    }

    /**
     * <p> 表示客户端提供的元信息不是来自服务器。  </p>
     */
    public static ValidatorBuilder nonAuthoritativeInformation() {
        return message(NON_AUTHORITATIVE_INFORMATION.getReasonPhrase()).code(NON_AUTHORITATIVE_INFORMATION.value()).status(NON_AUTHORITATIVE_INFORMATION);
    }

    public static ValidatorBuilder nonAuthoritativeInformation(String message) {
        return message(message).code(NON_AUTHORITATIVE_INFORMATION.value()).status(NON_AUTHORITATIVE_INFORMATION);
    }

    public static ValidatorBuilder nonAuthoritativeInformation(String message, Integer code) {
        return message(message).code(code).status(NON_AUTHORITATIVE_INFORMATION);
    }

    /**
     * <p> 表示请求成功，但没有要返回的新信息。 </p>
     */
    public static ValidatorBuilder noContent() {
        return message(NO_CONTENT.getReasonPhrase()).code(NO_CONTENT.value()).status(NO_CONTENT);
    }

    public static ValidatorBuilder noContent(String message) {
        return message(message).code(NO_CONTENT.value()).status(NO_CONTENT);
    }

    public static ValidatorBuilder noContent(String message, Integer code) {
        return message(message).code(code).status(NO_CONTENT);
    }

    /**
     * <p> 指示代理应重置导致发送请求的文档视图。</p>
     */
    public static ValidatorBuilder resetContent() {
        return message(RESET_CONTENT.getReasonPhrase()).code(RESET_CONTENT.value()).status(RESET_CONTENT);
    }

    public static ValidatorBuilder resetContent(String message) {
        return message(message).code(RESET_CONTENT.value()).status(RESET_CONTENT);
    }

    public static ValidatorBuilder resetContent(String message, Integer code) {
        return message(message).code(code).status(RESET_CONTENT);
    }

    /**
     * <p> 表示服务器已完成对资源的部分获取请求。 </p>
     */
    public static ValidatorBuilder partialContent() {
        return message(PARTIAL_CONTENT.getReasonPhrase()).code(PARTIAL_CONTENT.value()).status(PARTIAL_CONTENT);
    }

    public static ValidatorBuilder partialContent(String message) {
        return message(message).code(PARTIAL_CONTENT.value()).status(PARTIAL_CONTENT);
    }

    public static ValidatorBuilder partialContent(String message, Integer code) {
        return message(message).code(code).status(PARTIAL_CONTENT);
    }

    public static ValidatorBuilder multiStatus() {
        return message(MULTI_STATUS.getReasonPhrase()).code(MULTI_STATUS.value()).status(MULTI_STATUS);
    }

    public static ValidatorBuilder multiStatus(String message) {
        return message(message).code(MULTI_STATUS.value()).status(MULTI_STATUS);
    }

    public static ValidatorBuilder multiStatus(String message, Integer code) {
        return message(message).code(code).status(MULTI_STATUS);
    }

    public static ValidatorBuilder alreadyReported() {
        return message(ALREADY_REPORTED.getReasonPhrase()).code(ALREADY_REPORTED.value()).status(ALREADY_REPORTED);
    }

    public static ValidatorBuilder alreadyReported(String message) {
        return message(message).code(ALREADY_REPORTED.value()).status(ALREADY_REPORTED);
    }

    public static ValidatorBuilder alreadyReported(String message, Integer code) {
        return message(message).code(code).status(ALREADY_REPORTED);
    }

    public static ValidatorBuilder imUsed() {
        return message(IM_USED.getReasonPhrase()).code(IM_USED.value()).status(IM_USED);
    }

    public static ValidatorBuilder imUsed(String message) {
        return message(message).code(IM_USED.value()).status(IM_USED);
    }

    public static ValidatorBuilder imUsed(String message, Integer code) {
        return message(message).code(code).status(IM_USED);
    }

    /**
     * <p> 指示请求的资源对应于一组表示中的任何一个，每个表示都有自己的特定位置。 </p>
     */
    public static ValidatorBuilder multipleChoices() {
        return message(MULTIPLE_CHOICES.getReasonPhrase()).code(MULTIPLE_CHOICES.value()).status(MULTIPLE_CHOICES);
    }

    public static ValidatorBuilder multipleChoices(String message) {
        return message(message).code(MULTIPLE_CHOICES.value()).status(MULTIPLE_CHOICES);
    }

    public static ValidatorBuilder multipleChoices(String message, Integer code) {
        return message(message).code(code).status(MULTIPLE_CHOICES);
    }

    /**
     * <p> 指示资源已永久移动到新位置，并且将来的引用应在其请求中使用新的URI。 </p>
     */
    public static ValidatorBuilder movedPermanently() {
        return message(MOVED_PERMANENTLY.getReasonPhrase()).code(MOVED_PERMANENTLY.value()).status(MOVED_PERMANENTLY);
    }

    public static ValidatorBuilder movedPermanently(String message) {
        return message(message).code(MOVED_PERMANENTLY.value()).status(MOVED_PERMANENTLY);
    }

    public static ValidatorBuilder movedPermanently(String message, Integer code) {
        return message(message).code(code).status(MOVED_PERMANENTLY);
    }


    /**
     * <p> 指示资源暂时位于不同的uri下。由于有时可能会更改重定向，</p>
     * <p> 客户端应继续使用请求URI来表示将来的请求（HTTP/1.1），因此建议使用此变量。</p>
     */
    public static ValidatorBuilder found() {
        return message(FOUND.getReasonPhrase()).code(FOUND.value()).status(FOUND);
    }

    public static ValidatorBuilder found(String message) {
        return message(message).code(FOUND.value()).status(FOUND);
    }

    public static ValidatorBuilder found(String message, Integer code) {
        return message(message).code(code).status(FOUND);
    }

    /**
     * <p> 指示资源已临时移动到另一个位置，但将来的引用仍应使用原始uri来访问资源。</p>
     * <p> 保留此定义是为了向后兼容。找到SC_FOUND 现在是首选定义。</p>
     */
    @Deprecated
    public static ValidatorBuilder movedTemporarily() {
        return message(MOVED_TEMPORARILY.getReasonPhrase()).code(MOVED_TEMPORARILY.value()).status(MOVED_TEMPORARILY);
    }


    /**
     * <p> 指示可以在不同的uri下找到对请求的响应。 </p>
     */
    public static ValidatorBuilder seeOther() {
        return message(SEE_OTHER.getReasonPhrase()).code(SEE_OTHER.value()).status(SEE_OTHER);
    }

    public static ValidatorBuilder seeOther(String message) {
        return message(message).code(SEE_OTHER.value()).status(SEE_OTHER);
    }

    public static ValidatorBuilder seeOther(String message, Integer code) {
        return message(message).code(code).status(SEE_OTHER);
    }

    /**
     * <p> 指示条件获取操作发现资源可用且未修改。 </p>
     */
    public static ValidatorBuilder notModified() {
        return message(NOT_MODIFIED.getReasonPhrase()).code(NOT_MODIFIED.value()).status(NOT_MODIFIED);
    }

    public static ValidatorBuilder notModified(String message) {
        return message(message).code(NOT_MODIFIED.value()).status(NOT_MODIFIED);
    }

    public static ValidatorBuilder notModified(String message, Integer code) {
        return message(message).code(code).status(NOT_MODIFIED);
    }

    /**
     * <p> indicating that the requested resource
     * <em>MUST</em> be accessed through the proxy given by the
     * <code><em>Location</em></code> field. </p>
     */
    @Deprecated
    public static ValidatorBuilder useProxy() {
        return message(USE_PROXY.getReasonPhrase()).code(USE_PROXY.value()).status(USE_PROXY);
    }

    /**
     * <p> 指示必须通过位置字段提供的代理访问请求的资源。</p>
     */
    public static ValidatorBuilder temporaryRedirect() {
        return message(TEMPORARY_REDIRECT.getReasonPhrase()).code(TEMPORARY_REDIRECT.value()).status(TEMPORARY_REDIRECT);
    }

    public static ValidatorBuilder temporaryRedirect(String message) {
        return message(message).code(TEMPORARY_REDIRECT.value()).status(TEMPORARY_REDIRECT);
    }

    public static ValidatorBuilder temporaryRedirect(String message, Integer code) {
        return message(message).code(code).status(TEMPORARY_REDIRECT);
    }

    public static ValidatorBuilder permanentRedirect() {
        return message(PERMANENT_REDIRECT.getReasonPhrase()).code(PERMANENT_REDIRECT.value()).status(PERMANENT_REDIRECT);
    }

    public static ValidatorBuilder permanentRedirect(String message) {
        return message(message).code(PERMANENT_REDIRECT.value()).status(PERMANENT_REDIRECT);
    }

    public static ValidatorBuilder permanentRedirect(String message, Integer code) {
        return message(message).code(code).status(PERMANENT_REDIRECT);
    }

    /**
     * <p> 指示客户端发送的请求在语法上不正确。 </p>
     */
    public static ValidatorBuilder badRequest() {
        return message(BAD_REQUEST.getReasonPhrase()).code(BAD_REQUEST.value()).status(BAD_REQUEST);
    }

    public static ValidatorBuilder badRequest(String message) {
        return message(message).code(BAD_REQUEST.value()).status(BAD_REQUEST);
    }

    public static ValidatorBuilder badRequest(String message, Integer code) {
        return message(message).code(code).status(BAD_REQUEST);
    }

    /**
     * <p> 指示请求需要http身份验证。 </p>
     */
    public static ValidatorBuilder unauthorized() {
        return message(UNAUTHORIZED.getReasonPhrase()).code(UNAUTHORIZED.value()).status(UNAUTHORIZED);
    }

    public static ValidatorBuilder unauthorized(String message) {
        return message(message).code(UNAUTHORIZED.value()).status(UNAUTHORIZED);
    }

    public static ValidatorBuilder unauthorized(String message, Integer code) {
        return message(message).code(code).status(UNAUTHORIZED);
    }

    /**
     * <p>保留以备将来使用。 </p>
     */
    public static ValidatorBuilder paymentRequired() {
        return message(PAYMENT_REQUIRED.getReasonPhrase()).code(PAYMENT_REQUIRED.value()).status(PAYMENT_REQUIRED);
    }

    public static ValidatorBuilder paymentRequired(String message) {
        return message(message).code(PAYMENT_REQUIRED.value()).status(PAYMENT_REQUIRED);
    }

    public static ValidatorBuilder paymentRequired(String message, Integer code) {
        return message(message).code(code).status(PAYMENT_REQUIRED);
    }

    /**
     * <p> 表示服务器理解请求但拒绝执行。 </p>
     */
    public static ValidatorBuilder forbidden() {
        return message(FORBIDDEN.getReasonPhrase()).code(FORBIDDEN.value()).status(FORBIDDEN);
    }

    public static ValidatorBuilder forbidden(String message) {
        return message(message).code(FORBIDDEN.value()).status(FORBIDDEN);
    }

    public static ValidatorBuilder forbidden(String message, Integer code) {
        return message(message).code(code).status(FORBIDDEN);
    }

    /**
     * <p> 表示请求的资源不可用。 </p>
     */
    public static ValidatorBuilder notFound() {
        return message(NOT_FOUND.getReasonPhrase()).code(NOT_FOUND.value()).status(NOT_FOUND);
    }

    public static ValidatorBuilder notFound(String message) {
        return message(message).code(NOT_FOUND.value()).status(NOT_FOUND);
    }

    public static ValidatorBuilder notFound(String message, Integer code) {
        return message(message).code(code).status(NOT_FOUND);
    }

    /**
     * <p> 指示由请求uri标识的资源不允许使用请求行中指定的方法。 </p>
     */
    public static ValidatorBuilder methodNotAllowed() {
        return message(METHOD_NOT_ALLOWED.getReasonPhrase()).code(METHOD_NOT_ALLOWED.value()).status(METHOD_NOT_ALLOWED);
    }

    public static ValidatorBuilder methodNotAllowed(String message) {
        return message(message).code(METHOD_NOT_ALLOWED.value()).status(METHOD_NOT_ALLOWED);
    }

    public static ValidatorBuilder methodNotAllowed(String message, Integer code) {
        return message(message).code(code).status(METHOD_NOT_ALLOWED);
    }

    /**
     * <p> 指示由请求标识的资源只能根据请求中发送的接受头生成内容特征不可接受的响应实体。 </p>
     */
    public static ValidatorBuilder notAcceptable() {
        return message(NOT_ACCEPTABLE.getReasonPhrase()).code(NOT_ACCEPTABLE.value()).status(NOT_ACCEPTABLE);
    }

    public static ValidatorBuilder notAcceptable(String message) {
        return message(message).code(NOT_ACCEPTABLE.value()).status(NOT_ACCEPTABLE);
    }

    public static ValidatorBuilder notAcceptable(String message, Integer code) {
        return message(message).code(code).status(NOT_ACCEPTABLE);
    }

    /**
     * <p> 表示客户机必须首先使用代理对自己进行身份验证。  </p>
     */
    public static ValidatorBuilder proxyAuthenticationRequired() {
        return message(PROXY_AUTHENTICATION_REQUIRED.getReasonPhrase()).code(PROXY_AUTHENTICATION_REQUIRED.value()).status(PROXY_AUTHENTICATION_REQUIRED);
    }

    public static ValidatorBuilder proxyAuthenticationRequired(String message) {
        return message(message).code(PROXY_AUTHENTICATION_REQUIRED.value()).status(PROXY_AUTHENTICATION_REQUIRED);
    }

    public static ValidatorBuilder proxyAuthenticationRequired(String message, Integer code) {
        return message(message).code(code).status(PROXY_AUTHENTICATION_REQUIRED);
    }

    /**
     * <p> 指示客户端在服务器准备等待的时间内未生成请求。  </p>
     */
    public static ValidatorBuilder requestTimeout() {
        return message(REQUEST_TIMEOUT.getReasonPhrase()).code(REQUEST_TIMEOUT.value()).status(REQUEST_TIMEOUT);
    }

    public static ValidatorBuilder requestTimeout(String message) {
        return message(message).code(REQUEST_TIMEOUT.value()).status(REQUEST_TIMEOUT);
    }

    public static ValidatorBuilder requestTimeout(String message, Integer code) {
        return message(message).code(code).status(REQUEST_TIMEOUT);
    }

    /**
     * <p> 指示由于与资源的当前状态冲突而无法完成请求。 </p>
     */
    public static ValidatorBuilder conflict() {
        return message(CONFLICT.getReasonPhrase()).code(CONFLICT.value()).status(CONFLICT);
    }

    public static ValidatorBuilder conflict(String message) {
        return message(message).code(CONFLICT.value()).status(CONFLICT);
    }

    public static ValidatorBuilder conflict(String message, Integer code) {
        return message(message).code(code).status(CONFLICT);
    }

    /**
     * <p> 表示资源在服务器上不再可用，并且不知道转发地址。这种情况应视为永久性的。 </p>
     */
    public static ValidatorBuilder gone() {
        return message(GONE.getReasonPhrase()).code(GONE.value()).status(GONE);
    }

    public static ValidatorBuilder gone(String message) {
        return message(message).code(GONE.value()).status(GONE);
    }

    public static ValidatorBuilder gone(String message, Integer code) {
        return message(message).code(code).status(GONE);
    }

    /**
     * <p> 表示没有定义“Content-Length”无法处理请求。 </p>
     */
    public static ValidatorBuilder lengthRequired() {
        return message(LENGTH_REQUIRED.getReasonPhrase()).code(LENGTH_REQUIRED.value()).status(LENGTH_REQUIRED);
    }

    public static ValidatorBuilder lengthRequired(String message) {
        return message(message).code(LENGTH_REQUIRED.value()).status(LENGTH_REQUIRED);
    }

    public static ValidatorBuilder lengthRequired(String message, Integer code) {
        return message(message).code(code).status(LENGTH_REQUIRED);
    }

    /**
     * <p> 指示一个或多个请求头字段中给定的前置条件在服务器上测试时计算为FALSE。 </p>
     */
    public static ValidatorBuilder preconditionFailed() {
        return message(PRECONDITION_FAILED.getReasonPhrase()).code(PRECONDITION_FAILED.value()).status(PRECONDITION_FAILED);
    }

    public static ValidatorBuilder preconditionFailed(String message) {
        return message(message).code(PRECONDITION_FAILED.value()).status(PRECONDITION_FAILED);
    }

    public static ValidatorBuilder preconditionFailed(String message, Integer code) {
        return message(message).code(code).status(PRECONDITION_FAILED);
    }

    /**
     * <p> 表示服务器拒绝处理请求，因为请求实体大于服务器愿意或能够处理的实体。 </p>
     */
    public static ValidatorBuilder payloadTooLarge() {
        return message(PAYLOAD_TOO_LARGE.getReasonPhrase()).code(PAYLOAD_TOO_LARGE.value()).status(PAYLOAD_TOO_LARGE);
    }

    public static ValidatorBuilder payloadTooLarge(String message) {
        return message(message).code(PAYLOAD_TOO_LARGE.value()).status(PAYLOAD_TOO_LARGE);
    }

    public static ValidatorBuilder payloadTooLarge(String message, Integer code) {
        return message(message).code(code).status(PAYLOAD_TOO_LARGE);
    }

    @Deprecated
    public static ValidatorBuilder requestEntityTooLarge() {
        return message(REQUEST_ENTITY_TOO_LARGE.getReasonPhrase()).code(REQUEST_ENTITY_TOO_LARGE.value()).status(REQUEST_ENTITY_TOO_LARGE);
    }

    /**
     * <p> 表示服务器拒绝服务请求，因为“Request-URI”比服务器愿意解释的要长。 </p>
     */
    public static ValidatorBuilder uriTooLong() {
        return message(URI_TOO_LONG.getReasonPhrase()).code(URI_TOO_LONG.value()).status(URI_TOO_LONG);
    }

    public static ValidatorBuilder uriTooLong(String message) {
        return message(message).code(URI_TOO_LONG.value()).status(URI_TOO_LONG);
    }

    public static ValidatorBuilder uriTooLong(String message, Integer code) {
        return message(message).code(code).status(URI_TOO_LONG);
    }

    @Deprecated
    public static ValidatorBuilder requestUriTooLong() {
        return message(REQUEST_URI_TOO_LONG.getReasonPhrase()).code(REQUEST_URI_TOO_LONG.value()).status(REQUEST_URI_TOO_LONG);
    }

    /**
     * <p> 指示服务器拒绝为请求提供服务，因为请求实体的格式不受请求方法的请求资源支持。 </p>
     */
    public static ValidatorBuilder unsupportedMediaType() {
        return message(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()).code(UNSUPPORTED_MEDIA_TYPE.value()).status(UNSUPPORTED_MEDIA_TYPE);
    }

    public static ValidatorBuilder unsupportedMediaType(String message) {
        return message(message).code(UNSUPPORTED_MEDIA_TYPE.value()).status(UNSUPPORTED_MEDIA_TYPE);
    }

    public static ValidatorBuilder unsupportedMediaType(String message, Integer code) {
        return message(message).code(code).status(UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * <p> 表示服务器无法为请求的字节范围提供服务。 </p>
     */
    public static ValidatorBuilder requestedRangeNotSatisfiable() {
        return message(REQUESTED_RANGE_NOT_SATISFIABLE.getReasonPhrase()).code(REQUESTED_RANGE_NOT_SATISFIABLE.value()).status(REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    public static ValidatorBuilder requestedRangeNotSatisfiable(String message) {
        return message(message).code(REQUESTED_RANGE_NOT_SATISFIABLE.value()).status(REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    public static ValidatorBuilder requestedRangeNotSatisfiable(String message, Integer code) {
        return message(message).code(code).status(REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    /**
     * <p> 指示服务器无法满足Expect请求头中给定的期望。 </p>
     */
    public static ValidatorBuilder expectationFailed() {
        return message(EXPECTATION_FAILED.getReasonPhrase()).code(EXPECTATION_FAILED.value()).status(EXPECTATION_FAILED);
    }

    public static ValidatorBuilder expectationFailed(String message) {
        return message(message).code(EXPECTATION_FAILED.value()).status(EXPECTATION_FAILED);
    }

    public static ValidatorBuilder expectationFailed(String message, Integer code) {
        return message(message).code(code).status(EXPECTATION_FAILED);
    }

    public static ValidatorBuilder iAmATeapot() {
        return message(I_AM_A_TEAPOT.getReasonPhrase()).code(I_AM_A_TEAPOT.value()).status(I_AM_A_TEAPOT);
    }

    public static ValidatorBuilder iAmATeapot(String message) {
        return message(message).code(I_AM_A_TEAPOT.value()).status(I_AM_A_TEAPOT);
    }

    public static ValidatorBuilder iAmATeapot(String message, Integer code) {
        return message(message).code(code).status(I_AM_A_TEAPOT);
    }

    @Deprecated
    public static ValidatorBuilder insufficientSpaceOnResource() {
        return message(INSUFFICIENT_SPACE_ON_RESOURCE.getReasonPhrase()).code(INSUFFICIENT_SPACE_ON_RESOURCE.value()).status(INSUFFICIENT_SPACE_ON_RESOURCE);
    }

    @Deprecated
    public static ValidatorBuilder methodFailure() {
        return message(METHOD_FAILURE.getReasonPhrase()).code(METHOD_FAILURE.value()).status(METHOD_FAILURE);
    }

    @Deprecated
    public static ValidatorBuilder destinationLocked() {
        return message(DESTINATION_LOCKED.getReasonPhrase()).code(DESTINATION_LOCKED.value()).status(DESTINATION_LOCKED);
    }

    public static ValidatorBuilder unprocessableEntity() {
        return message(UNPROCESSABLE_ENTITY.getReasonPhrase()).code(UNPROCESSABLE_ENTITY.value()).status(UNPROCESSABLE_ENTITY);
    }

    public static ValidatorBuilder unprocessableEntity(String message) {
        return message(message).code(UNPROCESSABLE_ENTITY.value()).status(UNPROCESSABLE_ENTITY);
    }

    public static ValidatorBuilder unprocessableEntity(String message, Integer code) {
        return message(message).code(code).status(UNPROCESSABLE_ENTITY);
    }

    public static ValidatorBuilder locked() {
        return message(LOCKED.getReasonPhrase()).code(LOCKED.value()).status(LOCKED);
    }

    public static ValidatorBuilder locked(String message) {
        return message(message).code(LOCKED.value()).status(LOCKED);
    }

    public static ValidatorBuilder locked(String message, Integer code) {
        return message(message).code(code).status(LOCKED);
    }

    public static ValidatorBuilder failedDependency() {
        return message(FAILED_DEPENDENCY.getReasonPhrase()).code(FAILED_DEPENDENCY.value()).status(FAILED_DEPENDENCY);
    }

    public static ValidatorBuilder failedDependency(String message) {
        return message(message).code(FAILED_DEPENDENCY.value()).status(FAILED_DEPENDENCY);
    }

    public static ValidatorBuilder failedDependency(String message, Integer code) {
        return message(message).code(code).status(FAILED_DEPENDENCY);
    }

    public static ValidatorBuilder tooEarly() {
        return message(TOO_EARLY.getReasonPhrase()).code(TOO_EARLY.value()).status(TOO_EARLY);
    }

    public static ValidatorBuilder tooEarly(String message) {
        return message(message).code(TOO_EARLY.value()).status(TOO_EARLY);
    }

    public static ValidatorBuilder tooEarly(String message, Integer code) {
        return message(message).code(code).status(TOO_EARLY);
    }

    public static ValidatorBuilder upgradeRequired() {
        return message(UPGRADE_REQUIRED.getReasonPhrase()).code(UPGRADE_REQUIRED.value()).status(UPGRADE_REQUIRED);
    }

    public static ValidatorBuilder upgradeRequired(String message) {
        return message(message).code(UPGRADE_REQUIRED.value()).status(UPGRADE_REQUIRED);
    }

    public static ValidatorBuilder upgradeRequired(String message, Integer code) {
        return message(message).code(code).status(UPGRADE_REQUIRED);
    }

    public static ValidatorBuilder preconditionRequired() {
        return message(PRECONDITION_REQUIRED.getReasonPhrase()).code(PRECONDITION_REQUIRED.value()).status(PRECONDITION_REQUIRED);
    }

    public static ValidatorBuilder preconditionRequired(String message) {
        return message(message).code(PRECONDITION_REQUIRED.value()).status(PRECONDITION_REQUIRED);
    }

    public static ValidatorBuilder preconditionRequired(String message, Integer code) {
        return message(message).code(code).status(PRECONDITION_REQUIRED);
    }

    public static ValidatorBuilder tooManyRequests() {
        return message(TOO_MANY_REQUESTS.getReasonPhrase()).code(TOO_MANY_REQUESTS.value()).status(TOO_MANY_REQUESTS);
    }

    public static ValidatorBuilder tooManyRequests(String message) {
        return message(message).code(TOO_MANY_REQUESTS.value()).status(TOO_MANY_REQUESTS);
    }

    public static ValidatorBuilder tooManyRequests(String message, Integer code) {
        return message(message).code(code).status(TOO_MANY_REQUESTS);
    }

    public static ValidatorBuilder requestHeaderFieldsTooLarge() {
        return message(REQUEST_HEADER_FIELDS_TOO_LARGE.getReasonPhrase()).code(REQUEST_HEADER_FIELDS_TOO_LARGE.value()).status(REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    public static ValidatorBuilder requestHeaderFieldsTooLarge(String message) {
        return message(message).code(REQUEST_HEADER_FIELDS_TOO_LARGE.value()).status(REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    public static ValidatorBuilder requestHeaderFieldsTooLarge(String message, Integer code) {
        return message(message).code(code).status(REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    public static ValidatorBuilder unavailableForLegalReasons() {
        return message(UNAVAILABLE_FOR_LEGAL_REASONS.getReasonPhrase()).code(UNAVAILABLE_FOR_LEGAL_REASONS.value()).status(UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    public static ValidatorBuilder unavailableForLegalReasons(String message) {
        return message(message).code(UNAVAILABLE_FOR_LEGAL_REASONS.value()).status(UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    public static ValidatorBuilder unavailableForLegalReasons(String message, Integer code) {
        return message(message).code(code).status(UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    /**
     * <p> 指示HTTP服务器内部的错误，该错误阻止它完成请求。 </p>
     */
    public static ValidatorBuilder internalServerError() {
        return message(INTERNAL_SERVER_ERROR.getReasonPhrase()).code(INTERNAL_SERVER_ERROR.value()).status(INTERNAL_SERVER_ERROR);
    }

    public static ValidatorBuilder internalServerError(String message) {
        return message(message).code(INTERNAL_SERVER_ERROR.value()).status(INTERNAL_SERVER_ERROR);
    }

    public static ValidatorBuilder internalServerError(String message, Integer code) {
        return message(message).code(code).status(INTERNAL_SERVER_ERROR);
    }

    /**
     * <p> 表示http服务器不支持完成请求所需的功能。 </p>
     */
    public static ValidatorBuilder notImplemented() {
        return message(NOT_IMPLEMENTED.getReasonPhrase()).code(NOT_IMPLEMENTED.value()).status(NOT_IMPLEMENTED);
    }

    public static ValidatorBuilder notImplemented(String message) {
        return message(message).code(NOT_IMPLEMENTED.value()).status(NOT_IMPLEMENTED);
    }

    public static ValidatorBuilder notImplemented(String message, Integer code) {
        return message(message).code(code).status(NOT_IMPLEMENTED);
    }

    /**
     * <p> 表示http服务器在充当代理或网关时从其所咨询的服务器接收到无效响应。 </p>
     */
    public static ValidatorBuilder badGateway() {
        return message(BAD_GATEWAY.getReasonPhrase()).code(BAD_GATEWAY.value()).status(BAD_GATEWAY);
    }

    public static ValidatorBuilder badGateway(String message) {
        return message(message).code(BAD_GATEWAY.value()).status(BAD_GATEWAY);
    }

    public static ValidatorBuilder badGateway(String message, Integer code) {
        return message(message).code(code).status(BAD_GATEWAY);
    }

    /**
     * <p> 表示http服务器暂时过载，无法处理该请求。 </p>
     */
    public static ValidatorBuilder serviceUnavailable() {
        return message(SERVICE_UNAVAILABLE.getReasonPhrase()).code(SERVICE_UNAVAILABLE.value()).status(SERVICE_UNAVAILABLE);
    }

    public static ValidatorBuilder serviceUnavailable(String message) {
        return message(message).code(SERVICE_UNAVAILABLE.value()).status(SERVICE_UNAVAILABLE);
    }

    public static ValidatorBuilder serviceUnavailable(String message, Integer code) {
        return message(message).code(code).status(SERVICE_UNAVAILABLE);
    }

    /**
     * <p> 表示服务器在充当网关或代理时未收到来自上游服务器的及时响应。 </p>
     */
    public static ValidatorBuilder gatewayTimeout() {
        return message(GATEWAY_TIMEOUT.getReasonPhrase()).code(GATEWAY_TIMEOUT.value()).status(GATEWAY_TIMEOUT);
    }

    public static ValidatorBuilder gatewayTimeout(String message) {
        return message(message).code(GATEWAY_TIMEOUT.value()).status(GATEWAY_TIMEOUT);
    }

    public static ValidatorBuilder gatewayTimeout(String message, Integer code) {
        return message(message).code(code).status(GATEWAY_TIMEOUT);
    }

    /**
     * <p> 表示服务器不支持或拒绝支持请求消息中使用的http协议版本。 </p>
     */
    public static ValidatorBuilder httpVersionNotSupported() {
        return message(HTTP_VERSION_NOT_SUPPORTED.getReasonPhrase()).code(HTTP_VERSION_NOT_SUPPORTED.value()).status(HTTP_VERSION_NOT_SUPPORTED);
    }

    public static ValidatorBuilder httpVersionNotSupported(String message) {
        return message(message).code(HTTP_VERSION_NOT_SUPPORTED.value()).status(HTTP_VERSION_NOT_SUPPORTED);
    }

    public static ValidatorBuilder httpVersionNotSupported(String message, Integer code) {
        return message(message).code(code).status(HTTP_VERSION_NOT_SUPPORTED);
    }

    public static ValidatorBuilder variantAlsoNegotiates() {
        return message(VARIANT_ALSO_NEGOTIATES.getReasonPhrase()).code(VARIANT_ALSO_NEGOTIATES.value()).status(VARIANT_ALSO_NEGOTIATES);
    }

    public static ValidatorBuilder variantAlsoNegotiates(String message) {
        return message(message).code(VARIANT_ALSO_NEGOTIATES.value()).status(VARIANT_ALSO_NEGOTIATES);
    }

    public static ValidatorBuilder variantAlsoNegotiates(String message, Integer code) {
        return message(message).code(code).status(VARIANT_ALSO_NEGOTIATES);
    }

    public static ValidatorBuilder insufficientStorage() {
        return message(INSUFFICIENT_STORAGE.getReasonPhrase()).code(INSUFFICIENT_STORAGE.value()).status(INSUFFICIENT_STORAGE);
    }

    public static ValidatorBuilder insufficientStorage(String message) {
        return message(message).code(INSUFFICIENT_STORAGE.value()).status(INSUFFICIENT_STORAGE);
    }

    public static ValidatorBuilder insufficientStorage(String message, Integer code) {
        return message(message).code(code).status(INSUFFICIENT_STORAGE);
    }

    public static ValidatorBuilder loopDetected() {
        return message(LOOP_DETECTED.getReasonPhrase()).code(LOOP_DETECTED.value()).status(LOOP_DETECTED);
    }

    public static ValidatorBuilder loopDetected(String message) {
        return message(message).code(LOOP_DETECTED.value()).status(LOOP_DETECTED);
    }

    public static ValidatorBuilder loopDetected(String message, Integer code) {
        return message(message).code(code).status(LOOP_DETECTED);
    }

    public static ValidatorBuilder bandwidthLimitExceeded() {
        return message(BANDWIDTH_LIMIT_EXCEEDED.getReasonPhrase()).code(BANDWIDTH_LIMIT_EXCEEDED.value()).status(BANDWIDTH_LIMIT_EXCEEDED);
    }

    public static ValidatorBuilder bandwidthLimitExceeded(String message) {
        return message(message).code(BANDWIDTH_LIMIT_EXCEEDED.value()).status(BANDWIDTH_LIMIT_EXCEEDED);
    }

    public static ValidatorBuilder bandwidthLimitExceeded(String message, Integer code) {
        return message(message).code(code).status(BANDWIDTH_LIMIT_EXCEEDED);
    }

    public static ValidatorBuilder notExtended() {
        return message(NOT_EXTENDED.getReasonPhrase()).code(NOT_EXTENDED.value()).status(NOT_EXTENDED);
    }

    public static ValidatorBuilder notExtended(String message) {
        return message(message).code(NOT_EXTENDED.value()).status(NOT_EXTENDED);
    }

    public static ValidatorBuilder notExtended(String message, Integer code) {
        return message(message).code(code).status(NOT_EXTENDED);
    }

    public static ValidatorBuilder networkAuthenticationRequired() {
        return message(NETWORK_AUTHENTICATION_REQUIRED.getReasonPhrase()).code(NETWORK_AUTHENTICATION_REQUIRED.value()).status(NETWORK_AUTHENTICATION_REQUIRED);
    }

    public static ValidatorBuilder networkAuthenticationRequired(String message) {
        return message(message).code(NETWORK_AUTHENTICATION_REQUIRED.value()).status(NETWORK_AUTHENTICATION_REQUIRED);
    }

    public static ValidatorBuilder networkAuthenticationRequired(String message, Integer code) {
        return message(message).code(code).status(NETWORK_AUTHENTICATION_REQUIRED);
    }

    // ---------------------------------------------------------------------
    // -----------------下面是自定义的快捷错误消息------------------------------
    // ---------------------------------------------------------------------

    public static ValidatorBuilder BadRequest() {
        return badRequest("{Bad.Request}");
    }

    public static ValidatorBuilder BadRequest(String message) {
        return badRequest(message);
    }

    public static ValidatorBuilder BadRequest(String message, Integer code) {
        return badRequest(message, code);
    }

    public static ValidatorBuilder NoLogin() {
        return unauthorized("{No.Login}");
    }

    public static ValidatorBuilder NoLogin(String message) {
        return unauthorized(message);
    }

    public static ValidatorBuilder NoLogin(String message, Integer code) {
        return unauthorized(message, code);
    }

    public static ValidatorBuilder NoPermission() {
        return forbidden("{No.Permission}");
    }

    public static ValidatorBuilder NoPermission(String message) {
        return forbidden(message);
    }

    public static ValidatorBuilder NoPermission(String message, Integer code) {
        return forbidden(message, code);
    }

    public static ValidatorBuilder ServerError() {
        return internalServerError("{Server.Error}");
    }

    public static ValidatorBuilder ServerError(String message) {
        return forbidden(message);
    }

    public static ValidatorBuilder ServerError(String message, Integer code) {
        return forbidden(message, code);
    }

}
