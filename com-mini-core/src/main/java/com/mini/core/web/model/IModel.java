package com.mini.core.web.model;

import com.mini.core.web.util.ResponseCode;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

/**
 * 数据模型渲染器模板
 * @author xchao
 */
public abstract class IModel<T extends IModel<T>> implements Serializable, ResponseCode {
	private static final long serialVersionUID = -8709093093109721059L;
	private static final String URL_REGEX = "http(s)?://([\\s\\S])+";
	private String contentType, viewPath, message;
	private long lastModified = -1;
	private ResourceBundle bundle;
	private int status = OK;
	private String eTag;
	
	public IModel() {
	}
	
	public IModel(String contentType) {
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
		return Optional.of(status)
			.filter(v -> v >= 0)
			.orElse(200);
	}
	
	/**
	 * 获取错误消息
	 * @return 错误消息
	 */
	@Nonnull
	public final String getMessage() {
		return ofNullable(bundle).map(b -> {
			String code = valueOf(status);
			return b.getString(code);
		}).orElse(message == null //
			? "" : message);
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
	 * 设置错误消息
	 * @param message 错误消息
	 * @return @this
	 */
	public final T setMessage(String message) {
		this.message = message;
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
	 * 设置资源信息包
	 * @param bundle 资源信息包
	 * @return @this
	 */
	public final T setResourceBundle(ResourceBundle bundle) {
		this.bundle = bundle;
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
	public final T setLastModified(long lastModified) {
		this.lastModified = lastModified;
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
	 * 设置Response头部ETag信息
	 * @param eTag ETag信息
	 * @return @this
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
		// If-Modified与Etag都未设置，表示该请求不支持缓存
		if (lastModified < 0 && isBlank(eTag)) {
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
			return isBlank(eTag) || eTag.equals(ifNoneMatch);
		}
		// If-Modified不满足使用缓存条件，判断Etag
		return !isBlank(eTag) && eTag.equals(ifNoneMatch);
	}
}
