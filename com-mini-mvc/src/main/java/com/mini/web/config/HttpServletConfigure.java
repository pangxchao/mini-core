package com.mini.web.config;

import com.mini.util.StringUtil;
import com.mini.web.servlet.DispatcherHttpServlet;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.Serializable;
import java.util.*;

@Singleton
@Named("httpServletConfigure")
public  class HttpServletConfigure implements Serializable {
    private static final long serialVersionUID = 4064096242570613253L;
    private final List<HttpServletElement> elements = new ArrayList<>();
    private HttpServletElement defaultElement;

    public HttpServletElement addServlet(Class<? extends HttpServlet> servletClass) {
        HttpServletElement element = new HttpServletElement();
        element.setServletName(servletClass.getName());
        element.setServletClass(servletClass);
        elements.add(element);
        return element;
    }

    /**
     * Gets the value of servlets.
     * @return The value of servlets
     */
    public List<HttpServletElement> getElements() {
        return elements;
    }

    /**
     * 获取默认的 DispatcherHttpServlet
     * @return 默认的 DispatcherHttpServlet
     */
    public HttpServletElement getDefaultElement() {
        return Optional.ofNullable(defaultElement).orElseGet(() -> {
            HttpServletElement element = new HttpServletElement();
            element.setServletClass(DispatcherHttpServlet.class);
            element.setServletName("DispatcherHttpServlet");
            return defaultElement = element;
        });
    }

    public final class HttpServletElement implements Serializable {
        private static final long serialVersionUID = 5240191497969156313L;
        private final Set<String> urlPatterns = new HashSet<>();
        private Class<? extends HttpServlet> servletClass;
        private boolean fileUploadSupported = true;
        private boolean asyncSupported = true;
        private int fileSizeThreshold = 4096;
        private long maxRequestSize = -1L;
        private String location = "temp";
        private long maxFileSize = -1L;
        private String servletName;

        /**
         * 添加访问路径
         * @param urlPatterns 访问路径
         * @return {@Code #this}
         */
        public HttpServletElement addUrlPatterns(String... urlPatterns) {
            Collections.addAll(this.urlPatterns, urlPatterns);
            return this;
        }

        /**
         * Gets the value of urlPatterns.
         * @return The value of urlPatterns
         */
        public String[] getUrlPatterns() {
            return urlPatterns.toArray(new String[0]);
        }

        /**
         * Gets the value of servletClass.
         * @return The value of servletClass
         */
        public Class<? extends HttpServlet> getServletClass() {
            return servletClass;
        }

        /**
         * The value of servletClass
         * @param servletClass The value of servletClass
         * @return {@Code this}
         */
        public HttpServletElement setServletClass(Class<? extends HttpServlet> servletClass) {
            this.servletClass = servletClass;
            return this;
        }

        /**
         * Gets the value of fileUploadSupported.
         * @return The value of fileUploadSupported
         */
        public boolean isFileUploadSupported() {
            return fileUploadSupported;
        }

        /**
         * The value of fileUploadSupported
         * @param fileUploadSupported The value of fileUploadSupported
         * @return {@Code this}
         */
        public HttpServletElement setFileUploadSupported(boolean fileUploadSupported) {
            this.fileUploadSupported = fileUploadSupported;
            return this;
        }

        /**
         * Gets the value of asyncSupported.
         * @return The value of asyncSupported
         */
        public boolean isAsyncSupported() {
            return asyncSupported;
        }

        /**
         * The value of asyncSupported
         * @param asyncSupported The value of asyncSupported
         * @return {@Code this}
         */
        public HttpServletElement setAsyncSupported(boolean asyncSupported) {
            this.asyncSupported = asyncSupported;
            return this;
        }

        /**
         * Gets the value of fileSizeThreshold.
         * @return The value of fileSizeThreshold
         */
        public int getFileSizeThreshold() {
            return fileSizeThreshold;
        }

        /**
         * The value of fileSizeThreshold
         * @param fileSizeThreshold The value of fileSizeThreshold
         * @return {@Code this}
         */
        public HttpServletElement setFileSizeThreshold(int fileSizeThreshold) {
            this.fileSizeThreshold = fileSizeThreshold;
            return this;
        }

        /**
         * Gets the value of maxRequestSize.
         * @return The value of maxRequestSize
         */
        public long getMaxRequestSize() {
            return maxRequestSize;
        }

        /**
         * The value of maxRequestSize
         * @param maxRequestSize The value of maxRequestSize
         * @return {@Code this}
         */
        public HttpServletElement setMaxRequestSize(long maxRequestSize) {
            this.maxRequestSize = maxRequestSize;
            return this;
        }

        /**
         * Gets the value of location.
         * @return The value of location
         */
        public String getLocation() {
            return location;
        }

        /**
         * The value of location
         * @param location The value of location
         * @return {@Code this}
         */
        public HttpServletElement setLocation(String location) {
            this.location = location;
            return this;
        }

        /**
         * Gets the value of maxFileSize.
         * @return The value of maxFileSize
         */
        public long getMaxFileSize() {
            return maxFileSize;
        }

        /**
         * The value of maxFileSize
         * @param maxFileSize The value of maxFileSize
         * @return {@Code this}
         */
        public HttpServletElement setMaxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        /**
         * Gets the value of servletName.
         * @return The value of servletName
         */
        @Nonnull
        public String getServletName() {
            Objects.requireNonNull(servletClass, "Servlet Class can not be null");
            return StringUtil.def(servletName, servletClass.getName());
        }

        /**
         * The value of servletName
         * @param servletName The value of servletName
         * @return {@Code this}
         */
        public HttpServletElement setServletName(String servletName) {
            this.servletName = servletName;
            return this;
        }

        /**
         * 获取文件上传配置
         * @return 文件上传配置
         */
        public MultipartConfigElement getMultipartConfigElement() {
            File file = new File(this.location);
            if (!file.exists() || file.mkdirs()) {
                return null;
            }
            return new MultipartConfigElement(file.getAbsolutePath(),
                    maxFileSize, maxRequestSize, fileSizeThreshold);
        }
    }
}
