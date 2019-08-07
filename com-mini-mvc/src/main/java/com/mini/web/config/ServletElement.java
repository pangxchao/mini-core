package com.mini.web.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ServletElement {
    private final Set<String> urlPatterns = new HashSet<>();
    private Class<? extends HttpServlet> servlet;
    private boolean multipartEnabled = false;
    private boolean asyncSupported = true;
    private int fileSizeThreshold = 4096;
    private long maxRequestSize = -1L;
    private String location = "temp";
    private long maxFileSize = -1L;

    public ServletElement addUrlPatterns(String... urlPatterns) {
        Collections.addAll(this.urlPatterns, urlPatterns);
        return this;
    }

    public ServletElement setServlet(Class<? extends HttpServlet> servlet) {
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

    public Class<? extends HttpServlet> getServlet() {
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
        if (!isMultipartEnabled()) return null;
        File file = new File(getLocation());
        if (file.exists() || file.mkdirs()) {
            return new MultipartConfigElement(//
                    file.getAbsolutePath(), //
                    getMaxFileSize(),       //
                    getMaxRequestSize(),    //
                    getFileSizeThreshold());
        }
        return null;
    }
}
