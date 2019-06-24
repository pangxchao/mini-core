package com.mini.web.config;

import javax.inject.Singleton;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Singleton
public final class ServletHandlerConfigure implements Serializable {
    private static final long serialVersionUID = 729101566450254650L;
    private final Set<String> urlPatterns = new HashSet<>();
    private boolean fileUploadSupported = true;
    private boolean asyncSupported = true;
    private String servletName;

    /**
     * Servlet 名称
     * @return The value of servletName
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * Servlet 名称
     * @param servletName The value of servletName
     * @return {@Code #this}
     */
    public ServletHandlerConfigure setServletName(String servletName) {
        this.servletName = servletName;
        return this;
    }

    /**
     * 是否支持异步处理
     * @return true-是
     */
    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    /**
     * 是否支持异步处理
     * @param asyncSupported true-是
     * @return {@Code #this}
     */
    public ServletHandlerConfigure setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
        return this;
    }

    /**
     * 是否支持文件上传
     * @return true-是
     */
    public boolean isFileUploadSupported() {
        return fileUploadSupported;
    }

    /**
     * 是否支持文件上传
     * @param fileUploadSupported true-是
     * @return {@Code #this}
     */
    public ServletHandlerConfigure setFileUploadSupported(boolean fileUploadSupported) {
        this.fileUploadSupported = fileUploadSupported;
        return this;
    }

    /**
     * 访问路径配置
     * @return The value of urlPatterns
     */
    public String[] getUrlPatterns() {
        return urlPatterns.toArray(new String[0]);
    }

    /**
     * 添加访问路径
     * @param urlPatterns 访问路径
     * @return {@Code #this}
     */
    public ServletHandlerConfigure addUrlPatterns(String... urlPatterns) {
        Collections.addAll(this.urlPatterns, urlPatterns);
        return this;
    }
}
