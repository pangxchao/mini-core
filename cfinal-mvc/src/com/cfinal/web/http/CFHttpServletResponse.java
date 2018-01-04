/**
 * Created the com.cfinal.web.CFResponse.java
 * @created 2017年5月24日 下午5:32:27
 * @version 1.0.0
 */
package com.cfinal.web.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * response 返回码说明<br/>
 * <br/>
 * // 指示客户端可以继续<br/>
 * public static final int SC_CONTINUE = 100; <br/>
 * <br/>
 * // 指示服务器切换协议根据升级标题<br/>
 * public static final int SC_SWITCHING_PROTOCOLS = 101;<br/>
 * <br/>
 * // 指示请求成功了一般。<br/>
 * public static final int SC_OK = 200;<br/>
 * <br/>
 * // 指示请求成功,创造了一个新的服务器上的资源。<br/>
 * public static final int SC_CREATED = 201;<br/>
 * <br/>
 * // 表明一个请求被处理，但未能完成。<br/>
 * public static final int SC_ACCEPTED = 202;<br/>
 * // 指示客户端提出的元信息不是来自服务器。<br/>
 * public static final int SC_NON_AUTHORITATIVE_INFORMATION = 203;<br/>
 * <br/>
 * // 指示请求成功了但是没有新信息返回。<br/>
 * public static final int SC_NO_CONTENT = 204;<br/>
 * <br/>
 * // 指示代理人应当重置文档视图导致发送的请求。<br/>
 * public static final int SC_RESET_CONTENT = 205;<br/>
 * <br/>
 * // 说明服务器已经完成了部分GET请求的资源。一般断点续传用这个状态<br/>
 * public static final int SC_PARTIAL_CONTENT = 206;<br/>
 * <br/>
 * // 指示请求的资源对应的任何一个一组表示,每个都有自己的具体位置。<br/>
 * public static final int SC_MULTIPLE_CHOICES = 300;<br/>
 * <br/>
 * // 指示资源已经永久搬到一个新的位置,未来的引用应该使用一个新的URI请求。<br/>
 * public static final int SC_MOVED_PERMANENTLY = 301;<br/>
 * <br/>
 * // 指示资源暂时搬到另一个位置,但是,未来的引用应该仍然使用原来的URI来访问资源。<br/>
 * public static final int SC_MOVED_TEMPORARILY = 302;<br/>
 * <br/>
 * // 指示资源驻留暂时在一个不同的URI。<br/>
 * public static final int SC_FOUND = 302;<br/>
 * <br/>
 * // 表明此请求的响应可以被发现在一个不同的URI。<br/>
 * public static final int SC_SEE_OTHER = 303;<br/>
 * <br/>
 * // 表明一个条件GET操作发现,资源是可用的,而不是修改。<br/>
 * public static final int SC_NOT_MODIFIED = 304;<br/>
 * <br/>
 * // 指示请求的资源必须通过代理Locationfield给出的。<br/>
 * public static final int SC_USE_PROXY = 305;<br/>
 * <br/>
 * // 指示请求的资源驻留暂时在一个不同的URI<br/>
 * public static final int SC_TEMPORARY_REDIRECT = 307;<br/>
 * <br/>
 * // 指示客户端发送的请求是语法有误。<br/>
 * public static final int SC_BAD_REQUEST = 400;<br/>
 * <br/>
 * // 指示请求需要HTTP身份验证。<br/>
 * public static final int SC_UNAUTHORIZED = 401;<br/>
 * <br/>
 * // 保留以供将来使用。<br/>
 * public static final int SC_PAYMENT_REQUIRED = 402;<br/>
 * <br/>
 * // 指示服务器理解的请求,但拒绝履行它<br/>
 * public static final int SC_FORBIDDEN = 403;<br/>
 * <br/>
 * // 指示请求的资源不可用。<br/>
 * public static final int SC_NOT_FOUND = 404;<br/>
 * <br/>
 * // 表明该方法中指定的请求线是不允许资源识别由请求uri所指定资源。<br/>
 * public static final int SC_METHOD_NOT_ALLOWED = 405;<br/>
 * <br/>
 * // 表明该资源被请求只能生成响应实体具有内容特征不接受根据accept头信息发送的请求<br/>
 * public static final int SC_NOT_ACCEPTABLE = 406;<br/>
 * <br/>
 * // 指示客户机必须首先验证本身与代理。<br/>
 * public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;<br/>
 * <br/>
 * // 指示客户端没有产生一个请求的时间内服务器准备等<br/>
 * public static final int SC_REQUEST_TIMEOUT = 408;<br/>
 * <br/>
 * // 指示请求不能完成由于冲突与资源的当前状态<br/>
 * public static final int SC_CONFLICT = 409;<br/>
 * <br/>
 * // 指示资源不再是可用的服务器,没有转发地址是已知的。<br/>
 * public static final int SC_GONE = 410;<br/>
 * <br/>
 * // 指示请求不能处理没有定义内容长度。<br/>
 * public static final int SC_LENGTH_REQUIRED = 411;<br/>
 * <br/>
 * // 表明预处理在一个或多个请求头字段计算为false时在服务器上测试<br/>
 * public static final int SC_PRECONDITION_FAILED = 412;<br/>
 * <br/>
 * // 指示服务器拒绝处理请求,因为请求实体大于服务器是否愿意或者能够处理。<br/>
 * public static final int SC_REQUEST_ENTITY_TOO_LARGE = 413;<br/>
 * <br/>
 * // 指示服务器拒绝服务请求,因为要求通用长于服务器愿意解释。<br/>
 * public static final int SC_REQUEST_URI_TOO_LONG = 414;<br/>
 * <br/>
 * // 指示服务器拒绝服务请求,因为实体请求的格式不支持请求的资源请求的方法。<br/>
 * public static final int SC_UNSUPPORTED_MEDIA_TYPE = 415;<br/>
 * <br/>
 * // 指示服务器不能提供所请求的字节范围。<br/>
 * public static final int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;<br/>
 * <br/>
 * // 指示服务器无法满足预期给定的期望请求头<br/>
 * public static final int SC_EXPECTATION_FAILED = 417;<br/>
 * <br/>
 * // 表明一个错误在HTTP服务器,该情况阻止了完成请求。<br/>
 * public static final int SC_INTERNAL_SERVER_ERROR = 500;<br/>
 * <br/>
 * // 表示HTTP服务器不支持这个功能需要满足的要求。<br/>
 * public static final int SC_NOT_IMPLEMENTED = 501;<br/>
 * <br/>
 * // 表明HTTP服务器收到了一个无效回应从服务器提供咨询当充当代理或网关<br/>
 * public static final int SC_BAD_GATEWAY = 502;<br/>
 * <br/>
 * // 明HTTP服务器暂时超载,无法处理请求<br/>
 * public static final int SC_SERVICE_UNAVAILABLE = 503;<br/>
 * <br/>
 * // 指示服务器没有得到及时的响应,而代理从上游服务器作为网关或代理<br/>
 * public static final int SC_GATEWAY_TIMEOUT = 504;<br/>
 * <br/>
 * // 指示服务器不支持或拒绝支持HTTP协议版本,用于请求消息。<br/>
 * public static final int SC_HTTP_VERSION_NOT_SUPPORTED = 505;<br/>
 * <br/>
 * @author XChao
 */
public class CFHttpServletResponse implements HttpServletResponse {

	protected HttpServletResponse httpServletResponse;

	public CFHttpServletResponse(HttpServletResponse response) {
		this.httpServletResponse = response;
	}

	protected final HttpServletResponse getResponse() {
		return this.httpServletResponse;
	}

	@Override
	public void flushBuffer() throws IOException {
		this.httpServletResponse.flushBuffer();
	}

	@Override
	public int getBufferSize() {
		return this.httpServletResponse.getBufferSize();
	}

	@Override
	public String getCharacterEncoding() {
		return this.httpServletResponse.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return this.httpServletResponse.getContentType();
	}

	@Override
	public Locale getLocale() {
		return this.httpServletResponse.getLocale();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return this.httpServletResponse.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return this.httpServletResponse.getWriter();
	}

	@Override
	public boolean isCommitted() {
		return this.httpServletResponse.isCommitted();
	}

	@Override
	public void reset() {
		this.httpServletResponse.reset();
	}

	@Override
	public void resetBuffer() {
		this.httpServletResponse.resetBuffer();
	}

	@Override
	public void setBufferSize(int arg0) {
		this.httpServletResponse.setBufferSize(arg0);
	}

	@Override
	public void setCharacterEncoding(String arg0) {
		this.httpServletResponse.setCharacterEncoding(arg0);
	}

	@Override
	public void setContentLength(int arg0) {
		this.httpServletResponse.setContentLength(arg0);
	}

	@Override
	public void setContentLengthLong(long arg0) {
		this.httpServletResponse.setContentLengthLong(arg0);
	}

	@Override
	public void setContentType(String arg0) {
		this.httpServletResponse.setContentType(arg0);
	}

	@Override
	public void setLocale(Locale arg0) {
		this.httpServletResponse.setLocale(arg0);
	}

	@Override
	public void addCookie(Cookie arg0) {
		this.httpServletResponse.addCookie(arg0);
	}

	@Override
	public void addDateHeader(String arg0, long arg1) {
		this.httpServletResponse.addDateHeader(arg0, arg1);
	}

	@Override
	public void addHeader(String arg0, String arg1) {
		this.httpServletResponse.addHeader(arg0, arg1);
	}

	@Override
	public void addIntHeader(String arg0, int arg1) {
		this.httpServletResponse.addIntHeader(arg0, arg1);
	}

	@Override
	public boolean containsHeader(String arg0) {
		return this.httpServletResponse.containsHeader(arg0);
	}

	@Override
	public String encodeRedirectURL(String arg0) {
		return this.httpServletResponse.encodeRedirectURL(arg0);
	}

	@Override
	@Deprecated
	public String encodeRedirectUrl(String arg0) {
		return this.httpServletResponse.encodeRedirectUrl(arg0);
	}

	@Override
	public String encodeURL(String arg0) {
		return this.httpServletResponse.encodeURL(arg0);
	}

	@Override
	@Deprecated
	public String encodeUrl(String arg0) {
		return this.httpServletResponse.encodeUrl(arg0);
	}

	@Override
	public String getHeader(String arg0) {
		return this.httpServletResponse.getHeader(arg0);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return this.httpServletResponse.getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		return this.httpServletResponse.getHeaders(arg0);
	}

	@Override
	public int getStatus() {
		return this.httpServletResponse.getStatus();
	}

	@Override
	public void sendError(int arg0) throws IOException {
		this.httpServletResponse.sendError(arg0);
	}

	@Override
	public void sendError(int arg0, String arg1) throws IOException {
		this.httpServletResponse.sendError(arg0, arg1);
	}

	@Override
	public void sendRedirect(String arg0) throws IOException {
		this.httpServletResponse.sendRedirect(arg0);
	}

	@Override
	public void setDateHeader(String arg0, long arg1) {
		this.httpServletResponse.setDateHeader(arg0, arg1);
	}

	@Override
	public void setHeader(String arg0, String arg1) {
		this.httpServletResponse.setHeader(arg0, arg1);
	}

	@Override
	public void setIntHeader(String arg0, int arg1) {
		this.httpServletResponse.setIntHeader(arg0, arg1);
	}

	@Override
	public void setStatus(int arg0) {
		this.httpServletResponse.setStatus(arg0);
	}

	@Override
	@Deprecated
	public void setStatus(int arg0, String arg1) {
		this.httpServletResponse.setStatus(arg0, arg1);
	}

}
