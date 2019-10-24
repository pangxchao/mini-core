package com.mini.web.model;

import com.mini.util.StringUtil;
import com.mini.web.util.WebUtil;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;

import static com.mini.util.StringUtil.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED;

/**
 * <h3>1xx. Retain</h3>
 * <ul>
 * <li>100 Continue 初始的请求已经接受，客户应当继续发送请求的其余部分。（HTTP 1.1新）</li>
 * <li>101 Switching Protocols 服务器将遵从客户的请求转换到另外一种协议（HTTP 1.1新）</li>
 * </ul>
 * <br/>
 * <h3>2xx. Successful</h3>
 * <ul>
 * <li>200 OK 一切正常，对GET和POST请求的应答文档跟在后面。</li>
 * <li>201 Created 服务器已经创建了文档，Location头给出了它的URL。</li>
 * <li>202 Accepted 已经接受请求，但处理尚未完成。</li>
 * <li>203 Non-Authoritative Information 文档已经正常地返回，但一些应答头可能不正确，因为使用的是文档的拷贝（HTTP 1.1新）。</li>
 * <li>204 No Content 没有新文档，浏览器应该继续显示原来的文档。如果用户定期地刷新页面，而Servlet可以确定用户文档足够新，这个状态代码是很有用的。</li>
 * <li>205 Reset Content 没有新的内容，但浏览器应该重置它所显示的内容。用来强制浏览器清除表单输入内容（HTTP 1.1新）。</li>
 * <li>206 Partial Content 客户发送了一个带有Range头的GET请求，服务器完成了它（HTTP 1.1新）。</li>
 * </ul>
 * <br/>
 * <h3>3xx. Redirection</h3>
 * <ul>
 * <li>300 Multiple Choices 客户请求的文档可以在多个位置找到，这些位置已经在返回的文档内列出。如果服务器要提出优先选择，则应该在Location应答头指明。</li>
 * <li>301 Moved Permanently 客户请求的文档在其他地方，新的URL在Location头中给出，浏览器应该自动地访问新的URL。</li>
 * <li>302 Found 类似于301，但新的URL应该被视为临时性的替代，而不是永久性的。注意，在HTTP1.0中对应的状态信息是“Moved Temporatily”。
 * 出现该状态代码时，浏览器能够自动访问新的URL，因此它是一个很有用的状态代码。
 * 注意这个状态代码有时候可以和301替换使用。例如，如果浏览器错误地请求http://host/~user（缺少了后面的斜杠），有的服务器返回301，有的则返回302。
 * 严格地说，我们只能假定只有当原来的请求是GET时浏览器才会自动重定向。请参见307。</li>
 * <li>303 See Other 类似于301/302，不同之处在于，如果原来的请求是POST，Location头指定的重定向目标文档应该通过GET提取（HTTP 1.1新）。</li>
 * <li>304 Not Modified
 * 客户端有缓冲的文档并发出了一个条件性的请求（一般是提供If-Modified-Since头表示客户只想比指定日期更新的文档）。服务器告诉客户，原来缓冲的文档还可以继续使用。</li>
 * <li>305 Use Proxy 客户请求的文档应该通过Location头所指明的代理服务器提取（HTTP 1.1新）。</li>
 * <li>307 Temporary Redirect
 * 和302（Found）相同。许多浏览器会错误地响应302应答进行重定向，即使原来的请求是POST，即使它实际上只能在POST请求的应答是303时 才能重定向。 由于这个原因，HTTP 1
 * .1新增了307，以便更加清除地区分几个状态代码：当出现303应答时，浏览器可以跟随重定向的GET和POST请求； 如果是307应答，则浏览器只 能跟随对GET请求的重定向。（HTTP
 * 1.1新）</li>
 * </ul>
 * <br/>
 * <h3>4xx. Client Error</h3>
 * <ul>
 * <li>400 Bad Request 请求出现语法错误。</li>
 * <li>401 Unauthorized
 * 客户试图未经授权访问受密码保护的页面。应答中会包含一个WWW-Authenticate头，浏览器据此显示用户名字/密码对话框，然后在填写合适的Authorization头后再次发出请求。</li>
 * <li>403 Forbidden 资源不可用。服务器理解客户的请求，但拒绝处理它。通常由于服务器上文件或目录的权限设置导致。</li>
 * <li>404 Not Found 无法找到指定位置的资源。这也是一个常用的应答。</li>
 * <li>405 Method Not Allowed 请求方法（GET、POST、HEAD、Delete、PUT、TRACE等）对指定的资源不适用。（HTTP 1.1新）</li>
 * <li>406 Not Acceptable 指定的资源已经找到，但它的MIME类型和客户在Accpet头中所指定的不兼容。（HTTP 1.1新）</li>
 * <li>407 Proxy Authentication Required 类似于401，表示客户必须先经过代理服务器的授权。（HTTP 1.1新）</li>
 * <li>408 Request Timeout 在服务器许可的等待时间内，客户一直没有发出任何请求。客户可以在以后重复同一请求。（HTTP 1.1新）</li>
 * <li>409 Conflict 通常和PUT请求有关。由于请求和资源的当前状态相冲突，因此请求不能成功。（HTTP 1.1新）</li>
 * <li>410 Gone
 * 所请求的文档已经不再可用，而且服务器不知道应该重定向到哪一个地址。它和404的不同在于，返回410表示文档永久地离开了指定的位置，而404表示由于未知的原因文档不可用。（HTTP
 * 1.1新）</li>
 * <li>411 Length Required 服务器不能处理请求，除非客户发送一个Content-Length头。（HTTP 1.1新）</li>
 * <li>412 Precondition Failed 请求头中指定的一些前提条件失败。（HTTP 1.1新）</li>
 * <li>413 Request Entity Too Large
 * 目标文档的大小超过服务器当前愿意处理的大小。如果服务器认为自己能够稍后再处理该请求，则应该提供一个Retry-After头。（HTTP 1.1新）</li>
 * <li>414 Request URI Too Long URI太长。（HTTP 1.1新）</li>
 * <li>416 Requested Range Not Satisfiable 服务器不能满足客户在请求中指定的Range头。（HTTP 1.1新）</li>
 * </ul>
 * <br/>
 * <h3>5xx. Server Error</h3>
 * <ul>
 * <li>500 Internal Server Error 服务器遇到了意料不到的情况，不能完成客户的请求。</li>
 * <li>501 Not Implemented 服务器不支持实现请求所需要的功能。例如，客户发出了一个服务器不支持的PUT请求。</li>
 * <li>502 Bad Gateway 服务器作为网关或者代理时，为了完成请求访问下一个服务器，但该服务器返回了非法的应答。</li>
 * <li>503 Service Unavailable
 * 服务器由于维护或者负载过重未能应答。例如，Servlet可能在数据库连接池已满的情况下返回503。服务器返回503时可以提供一个Retry-After头。</li>
 * <li>504 Gateway Timeout 由作为代理或网关的服务器使用，表示不能及时地从远程服务器获得应答。（HTTP 1.1新）</li>
 * <li>505 HTTP Version Not Supported 服务器不支持请求中所指明的HTTP版本。（HTTP 1.1新）</li>
 * </ul>
 * <br/>
 * @author xchao
 */
public abstract class IModel<T extends IModel> implements Serializable {
    private static final long serialVersionUID = -8709093093109721059L;
    private int status = HttpServletResponse.SC_OK;
    private String contentType, viewPath, message;
    private long lastModified = -1;
    private String eTag;

    public IModel() {
    }

    public IModel(String contentType) {
        setContentType(contentType);
    }

    protected abstract T model();

    // 获取错误码
    public final int getStatus() {
        if (status <= 0) {
            return 200;
        }
        return status;
    }

    // 获取错误消息
    @Nonnull
    public final String getMessage() {
        return def(message, "");
    }

    // 获取内容类型
    public final String getContentType() {
        return contentType;
    }

    // 设置错误码
    public final T setStatus(int status) {
        this.status = status;
        return model();
    }

    // 设置错误消息
    public final T setMessage(String message) {
        this.message = message;
        return model();
    }

    // 设置返回视图路径，可以重定向和转发
    public T setViewPath(@Nonnull String viewPath) {
        this.viewPath = viewPath;
        return model();
    }

    // 设置页面返回内容的类型
    public final T setContentType(@Nonnull String contentType) {
        this.contentType = contentType;
        return model();
    }

    // 设置资源最后修改时间
    public final T setLastModified(long lastModified) {
        this.lastModified = lastModified;
        return model();
    }

    // 设置资源最后修改时间
    public final T setLastModified(@Nonnull Date lastModified) {
        this.lastModified = lastModified.getTime();
        return model();
    }

    // 设置Response头部ETag信息

    public final T setETag(@Nonnull String eTag) {
        this.eTag = eTag;
        return model();
    }

    /**
     * 提交渲染页面
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public final void onSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        // 错误码处理和返回数据格式处理
        response.setContentType(contentType);
        response.setStatus(this.getStatus());

        // 验证返回码是否错误，并发送错误信息
        if (getStatus() < 200 || this.getStatus() >= 300) {
            IModel.this.onError(request, response);
            return;
        }

        // 请求转发处理
        if (viewPath != null && startsWith(viewPath = viewPath.trim(), "f:")) {
            WebUtil.forward(viewPath.substring(2), request, response);
            return;
        }

        // 重定向处理
        if (viewPath != null && startsWith(viewPath = viewPath.trim(), "r:")) {
            WebUtil.sendRedirect(viewPath.substring(2), request, response);
            return;
        }

        // 处理缓存情况
        if (this.useModifiedOrNoneMatch(request, response)) {
            response.sendError(SC_NOT_MODIFIED);
            return;
        }
        // 处理具体数据
        IModel.this.onSubmit(request, response, viewPath);
    }

    /**
     * 出错时的处理方式
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    protected void onError(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        try (Writer writer = response.getWriter()) {
            response.setStatus(this.getStatus());
            writer.write(this.getMessage());
            writer.flush();
        }
    }

    protected abstract void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error;

    // 判断该请求资源是否没有修改过(直接使用缓存返回页面)
    private boolean useModifiedOrNoneMatch(HttpServletRequest request, HttpServletResponse response) {
        // If-Modified与Etag都未设置，表示该请求不支持缓存
        if (lastModified < 0 && StringUtil.isBlank(eTag)) {
            return false;
        }

        // 返回 If-Modified 与 Etag 的返回头信息
        response.setDateHeader("If-Modified", lastModified);
        response.setHeader("Etag", this.eTag);

        // 获取页面提交过来的If-Modified与Etag值(上次请求时返回给客户端的)
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        String ifNoneMatch = request.getHeader("If-None-Match");

        // If-Modified 信息满足使用缓存的条件时
        if (lastModified >= 0 && ifModifiedSince < lastModified) {
            return isBlank(eTag) || eq(eTag, ifNoneMatch);
        }
        // If-Modified不满足使用缓存条件，判断Etag
        return !isBlank(eTag) && eq(eTag, ifNoneMatch);
    }
}
