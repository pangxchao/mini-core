package com.mini.core.web.model;

import com.mini.core.util.StringUtil;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.util.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * 数据模型渲染器模板
 * @author xchao
 */
public abstract class IModel<T extends IModel<T>> implements ResponseCode, Serializable {
	
	private static final long serialVersionUID = -8709093093109721059L;
	private static final String URL_REGEX = "http(s)?://([\\s\\S])+";
	private static final Logger log = getLogger(IModel.class);
	private String contentType, viewPath, message;
	private long lastModified = -1;
	@Inject
	private Configures configures;
	private int status = OK;
	private String eTag;
	
	
	public IModel() {
	}
	
	protected IModel(String contentType) {
		setContentType(contentType);
	}
	
	/**
	 * 获取当前对象
	 * @return @this
	 */
	protected abstract T model();
	
	/**
	 * 获取错误码
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
	 * @return 错误消息
	 */
	@Nonnull
	public final String getMessage() {
		return this.message;
	}
	
	/**
	 * 获取内容类型
	 * @return 内容类型
	 */
	public final String getContentType() {
		return contentType;
	}
	
	/**
	 * 设置错误码
	 * @param status 错误码
	 * @return @this
	 */
	public final T setStatus(int status) {
		this.status = status;
		return model();
	}
	
	/**
	 * 全局配置信息
	 * @return 全局配置信息
	 */
	public final Configures getConfigures() {
		return configures;
	}
	
	/**
	 * 设置错误消息
	 * @param message 错误消息
	 * @return @this
	 */
	public final T setMessage(String message) {
		this.message = message;
		return model();
	}
	
	/**
	 * 设置Response头部ETag信息
	 * @param eTag ETag信息
	 * @return @this
	 */
	public final T setETag(@Nonnull String eTag) {
		this.eTag = eTag;
		return model();
	}
	
	/**
	 * 设置返回视图路径/可以重定向和转发
	 * @param viewPath 返回视图
	 * @return @this
	 */
	public T setViewPath(@Nonnull String viewPath) {
		this.viewPath = viewPath;
		return model();
	}
	
	/**
	 * 设置资源最后修改时间
	 * @param lastModified 修改时间
	 * @return @this
	 */
	public final T setLastModified(long lastModified) {
		this.lastModified = lastModified;
		return model();
	}
	
	/**
	 * 设置页面返回内容的类型
	 * @param contentType 内容类型
	 * @return @this
	 */
	public final T setContentType(@Nonnull String contentType) {
		this.contentType = contentType;
		return model();
	}
	
	/**
	 * 设置资源最后修改时间
	 * @param lastModified 修改时间
	 * @return @this
	 */
	public final T setLastModified(@Nonnull Date lastModified) {
		this.lastModified = lastModified.getTime();
		return model();
	}
	
	/**
	 * 提交渲染页面
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
			// 请求转发处理
			if (viewPath != null && startsWithIgnoreCase(viewPath, "f:")) {
				viewPath = viewPath.substring(2);
				// 如果不是以“/”开头，则添加
				if (!viewPath.startsWith("/")) {
					viewPath = "/" + viewPath;
				}
				// 转发请求
				request.getRequestDispatcher(viewPath).forward(request, response);
				return;
			}
			// 重定向处理
			if (viewPath != null && startsWithIgnoreCase(viewPath, "r:")) {
				viewPath = viewPath.substring(2);
				// http:// 或者 https:// 开头的绝对地址
				if (viewPath.toLowerCase().matches(URL_REGEX)) {
					response.sendRedirect(viewPath);
					return;
				}
				
				// “/” 开头的绝对地址
				if (StringUtils.startsWith(viewPath, "/")) {
					response.sendRedirect(viewPath);
					return;
				}
				
				// 构建绝对地址
				String contextPath = request.getContextPath();
				viewPath = contextPath + "/" + viewPath;
				response.sendRedirect(viewPath);
				return;
			}
			// 处理缓存情况
			if (this.useModifiedOrNoneMatch(request, response)) {
				response.sendError(NOT_MODIFIED);
				return;
			}
			// 处理具体数据
			this.onSubmit(request, response, viewPath);
		} catch (IOException | Error e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 出错时的处理方式
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 */
	protected abstract void onError(HttpServletRequest request, HttpServletResponse response) throws Exception, Error;
	
	/**
	 * 提交处理
	 * @param request  HttpServletRequest 对象
	 * @param response HttpServletResponse 对象
	 * @param viewPath 页面路径/重定向路径/请求转发路径
	 * @throws Exception 错误信息
	 * @throws Error     错误信息
	 */
	protected abstract void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception;
	
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
		if (isNotBlank(eTag) && !StringUtil.equals(eTag, ifNoneMatch)) {
			response.setHeader("ETag", IModel.this.eTag);
		}
		return true;
	}
}
