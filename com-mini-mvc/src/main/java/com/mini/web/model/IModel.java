package com.mini.web.model;

import com.mini.util.StringUtil;
import com.mini.web.util.WebUtil;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;

import static com.mini.util.StringUtil.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED;

public abstract class IModel<T extends IModel> implements Serializable {
    private static final long serialVersionUID = -8709093093109721059L;
    public static final String MODEL_KEY = "MINI_REQUEST_MODEL_KEY";
    public static final String URL_REGEX = "http(s)?://([\\s\\S])+";
    private int status = HttpServletResponse.SC_OK;
    private String contentType, viewPath, message;
    private long lastModified = -1;
    private String eTag;

    public IModel() {}

    public IModel(String contentType) {
        setContentType(contentType);
    }

    protected abstract T model();

    // 获取错误码
    protected final int getStatus() {
        return status;
    }

    // 获取错误消息
    protected final String getMessage() {
        return message;
    }

    // 获取内容类型
    protected final String getContentType() {
        return contentType;
    }

    /**
     * 发送一个错误码
     * @param status 错误码
     * @return {@Code this}
     * @see HttpServletResponse#sendError(int)
     */
    public final T sendError(int status) {
        this.status = status;
        return model();
    }

    /**
     * 发送一个错误码
     * @param status  错误码
     * @param message 错误消息
     * @return {@Code this}
     * @see HttpServletResponse#sendError(int, String)
     */
    public final T sendError(int status, String message) {
        this.message = message;
        this.status  = status;
        return model();
    }

    /**
     * 设置返回视图路径，可以重定向和转发
     * @param viewPath 视图路径
     * @return {@Code #this}
     */
    public T setViewPath(@Nonnull String viewPath) {
        this.viewPath = viewPath;
        return model();
    }

    /**
     * 设置页面返回内容的类型
     * @param contentType 返回内容的类型
     * @return {@Code this}
     */
    public final T setContentType(@Nonnull String contentType) {
        this.contentType = contentType;
        return model();
    }

    /**
     * 设置资源最后修改时间
     * @param lastModified 最后修改时间
     * @return {@Code this}
     */
    public final T setLastModified(long lastModified) {
        this.lastModified = lastModified;
        return model();
    }

    /**
     * 设置资源最后修改时间
     * @param lastModified 最后修改时间
     * @return {@Code this}
     */
    public final T setLastModified(@Nonnull Date lastModified) {
        this.lastModified = lastModified.getTime();
        return model();
    }

    /**
     * 设置Response头部ETag信息
     * @param eTag ETag 信息
     * @return {@Code this}
     */
    public final T setETag(@Nonnull String eTag) {
        this.eTag = eTag;
        return model();
    }


    /**
     * 提交渲染页面
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public final void submit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        // 错误码处理和返回数据格式处理
        response.setContentType(contentType);

        //  发送错误信息
        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status, message);
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
        IModel.this.submit(request, response, viewPath);
    }

    protected abstract void submit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error;

    // 判断该请求资源是否没有修改过(直接使用缓存返回页面)
    protected final boolean useModifiedOrNoneMatch(HttpServletRequest request, HttpServletResponse response) {
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
