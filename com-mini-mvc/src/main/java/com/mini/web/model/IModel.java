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
 * 数据模型渲染器模板
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
