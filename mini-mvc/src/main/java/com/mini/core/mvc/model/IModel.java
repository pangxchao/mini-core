package com.mini.core.mvc.model;

import com.mini.core.mvc.support.config.Configures;
import com.mini.core.mvc.util.ResponseCode;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * 数据模型渲染器模板
 *
 * @author xchao
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class IModel<T extends IModel<T>> implements Serializable, ResponseCode {
    private static final Logger log = getLogger(IModel.class);
    private String contentType, message;
    private final Configures configures;
    private long lastModified = -1;
    private int status = 0;
    private String eTag;


    protected IModel(Configures configures, String contentType) {
        this.configures = configures;
        setContentType(contentType);
    }

    /**
     * 获取当前对象
     *
     * @return @this
     */
    protected abstract T model();

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public final int getStatus() {
        if (this.status == 0) {
            return OK;
        }
        return this.status;
    }

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    @Nonnull
    public final String getMessage() {
        return this.message;
    }

    /**
     * 获取内容类型
     *
     * @return 内容类型
     */
    public final String getContentType() {
        return contentType;
    }

    /**
     * 设置错误码
     *
     * @param status 错误码
     * @return @this
     */
    public final T setStatus(int status) {
        this.status = status;
        return model();
    }

    /**
     * 全局配置信息
     *
     * @return 全局配置信息
     */
    public final Configures getConfigures() {
        return configures;
    }

    /**
     * 设置错误消息
     *
     * @param message 错误消息
     * @return @this
     */
    public final T setMessage(String message) {
        this.message = message;
        return model();
    }

    /**
     * 设置Response头部ETag信息
     *
     * @param eTag ETag信息
     * @return @this
     */
    public final T setETag(@Nonnull String eTag) {
        this.eTag = eTag;
        return model();
    }

    /**
     * 设置资源最后修改时间
     *
     * @param lastModified 修改时间
     * @return @this
     */
    public final T setLastModified(long lastModified) {
        this.lastModified = lastModified;
        return model();
    }

    /**
     * 设置页面返回内容的类型
     *
     * @param contentType 内容类型
     * @return @this
     */
    public final T setContentType(@Nonnull String contentType) {
        this.contentType = contentType;
        return model();
    }

    /**
     * 设置资源最后修改时间
     *
     * @param lastModified 修改时间
     * @return @this
     */
    public final T setLastModified(@Nonnull Date lastModified) {
        this.lastModified = lastModified.getTime();
        return model();
    }

    /**
     * 提交渲染页面
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public final void onSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        try {
            // 设置返回数据类型和格式
            response.setContentType(contentType);
            // 验证返回码是否错误，并发送错误信息
            if (getStatus() < 200 || getStatus() >= 300) {
                this.onError(request, response);
                return;
            }
            // 处理缓存情况
            if (this.useModifiedOrNoneMatch(request, response)) {
                response.sendError(NOT_MODIFIED);
                return;
            }
            // 处理具体数据
            this.onSubmit(request, response);
        } catch (IOException | Error e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 出错时的处理方式
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    protected abstract void onError(HttpServletRequest request, HttpServletResponse response) throws Exception, Error;

    /**
     * 提交处理
     *
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     * @throws Exception 错误信息
     * @throws Error     错误信息
     */
    protected abstract void doSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception;

    // 判断该请求资源是否没有修改过(直接使用缓存返回页面)
    private boolean useModifiedOrNoneMatch(HttpServletRequest request, HttpServletResponse response) {
        // 不使用缓存
        if (IModel.this.lastModified == -1) {
            return false;
        }
        // 获取页面提交过来的If-Modified与Etag值(上次请求时返回给客户端的)
        if (request.getDateHeader("If-Modified-Since") < IModel.this.lastModified) {
            if (lastModified >= 0 && !response.containsHeader("Last-Modified")) {
                response.setDateHeader("Last-Modified", lastModified);
            }
            return false;
        }
        // 设置ETag标识
        final String ifNoneMatch = request.getHeader("If-None-Match");
        if (StringUtils.hasText(eTag) && !eTag.equals(ifNoneMatch)) {
            response.setHeader("ETag", IModel.this.eTag);
        }
        return true;
    }
}
