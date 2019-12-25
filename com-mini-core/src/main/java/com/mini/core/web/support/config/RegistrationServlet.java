package com.mini.core.web.support.config;

import javax.inject.Singleton;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.http.HttpServlet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.of;

@Singleton
public final class RegistrationServlet implements Registration {
    private static final String[] EMPTY = new String[0];
    private Set<String> urlPatterns = new HashSet<>();
    private final HttpServlet servlet;
    private boolean multipartEnabled;
    private boolean asyncSupported;
    private int fileSizeThreshold;
    private long maxRequestSize;
    private String locationPath;
    private int loadOnStartup;
    private long maxFileSize;
    private String name;

    public RegistrationServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }

    public void addUrlPatterns(String... patterns) {
        Collections.addAll(urlPatterns, patterns);
    }

    public void setMultipartEnabled(boolean multipartEnabled) {
        this.multipartEnabled = multipartEnabled;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    public void setFileSizeThreshold(int fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

    public void setMaxRequestSize(long maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    public void setLocationPath(String locationPath) {
        this.locationPath = locationPath;
    }

    public void setLoadOnStartup(int loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized void register(ServletContext context) {
        Dynamic register = context.addServlet(name, servlet);
        // 添加请求映射
        register.addMapping(urlPatterns.toArray(EMPTY));
        // 异步支持
        register.setAsyncSupported(asyncSupported);
        // 启动顺序
        register.setLoadOnStartup(loadOnStartup);
        // 文件上传配置
        of(multipartEnabled).filter(v -> v).map(v -> {
            return new MultipartConfigElement(
                    locationPath,
                    maxFileSize,
                    maxRequestSize,
                    fileSizeThreshold); //
        }).ifPresent(register::setMultipartConfig);
    }
}
