package com.mini.web.config;

import com.mini.core.logger.Logger;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import static com.mini.core.logger.LoggerFactory.getLogger;
import static java.lang.System.getProperty;

public final class ServletElement implements EventListener, Serializable {
    private static final Logger logger = getLogger(ServletElement.class);
    private final Set<String> urlPatterns = new HashSet<>();
    private static final String TEMP_KEY = "java.io.tmpdir";
    private static final long serialVersionUID = 1L;
    private boolean multipartEnabled = false;
    private boolean asyncSupported = true;
    private int fileSizeThreshold = 4096;
    private long maxRequestSize = -1L;
    private String location = "temp";
    private long maxFileSize = -1L;
    private HttpServlet servlet;

    public ServletElement addUrlPatterns(String... urlPatterns) {
        Collections.addAll(this.urlPatterns, urlPatterns);
        return this;
    }

    public ServletElement setServlet(HttpServlet servlet) {
        this.servlet = servlet;
        return this;
    }

    public ServletElement setMultipartEnabled(boolean multipartEnabled) {
        this.multipartEnabled = multipartEnabled;
        return this;
    }


    public ServletElement setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
        return this;
    }

    public ServletElement setFileSizeThreshold(int fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
        return this;
    }

    public ServletElement setMaxRequestSize(long maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
        return this;
    }

    public ServletElement setLocation(String location) {
        this.location = location;
        return this;
    }

    public ServletElement setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }

    public ServletElement clearUrlPatterns() {
        urlPatterns.clear();
        return this;
    }

    public String[] getUrlPatterns() {
        return urlPatterns.toArray(new String[0]);
    }

    public HttpServlet getServlet() {
        return servlet;
    }

    public boolean isMultipartEnabled() {
        return multipartEnabled;
    }

    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    public int getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    public long getMaxRequestSize() {
        return maxRequestSize;
    }

    public String getLocation() {
        return location;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public MultipartConfigElement getMultipartConfigElement() {
        if (!this.isMultipartEnabled()) return null;
        // 文件上传的临时目录
        File localFile = new File(this.getLocation());
        if (!localFile.exists() && !localFile.mkdirs()) {
            // 获取系统临时目录
            localFile = new File(getProperty(TEMP_KEY));
        }
        // 重新创建文件上传的临时目录
        if (!localFile.exists() && !localFile.mkdirs()) {
            throw new RuntimeException("Can not create folder:" //
                    + localFile.getAbsolutePath());
        }
        // 输出临时目录位置，并返回文件上传设置
        logger.debug("文件上传的临时目录：" + localFile.getAbsolutePath());
        return new MultipartConfigElement(localFile.getAbsolutePath(), //
                getMaxFileSize(), getMaxRequestSize(), //
                getFileSizeThreshold());
    }
}
