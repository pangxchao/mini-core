package com.mini.web.config;

import com.mini.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.Serializable;
import java.util.*;

@Component
public final class HttpServletConfigure implements Serializable {
    private static final long serialVersionUID = 4064096242570613253L;
    private final List<HttpServletElement> elements = new ArrayList<>();

    public HttpServletElement addServlet(Class<? extends HttpServlet> servletClass) {
        HttpServletElement element = new HttpServletElement();
        element.setServletName(servletClass.getName());
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

        public HttpServletElement setServletClass(Class<? extends HttpServlet> servletClass) {
            this.servletClass = servletClass;
            return this;
        }

        public HttpServletElement setFileUploadSupported(boolean fileUploadSupported) {
            this.fileUploadSupported = fileUploadSupported;
            return this;
        }

        public HttpServletElement setAsyncSupported(boolean asyncSupported) {
            this.asyncSupported = asyncSupported;
            return this;
        }

        public HttpServletElement setFileSizeThreshold(int fileSizeThreshold) {
            this.fileSizeThreshold = fileSizeThreshold;
            return this;
        }

        public HttpServletElement setMaxRequestSize(long maxRequestSize) {
            this.maxRequestSize = maxRequestSize;
            return this;
        }

        public HttpServletElement setLocation(String location) {
            this.location = location;
            return this;
        }

        public HttpServletElement setMaxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        public HttpServletElement setServletName(String servletName) {
            this.servletName = servletName;
            return this;
        }


        /**
         * 注册 Servlet
         * @param context            ServletContext
         * @param applicationContext ApplicationContext
         */
        public final void register(ServletContext context, ApplicationContext applicationContext) {
            String name = StringUtil.def(servletName, servletClass.getName());
            HttpServlet servlet = applicationContext.getBean(name, HttpServlet.class);
            ServletRegistration.Dynamic register = context.addServlet(name, servlet);
            register.addMapping(urlPatterns.toArray(new String[0]));
            register.setAsyncSupported(asyncSupported);

            // 文件上传配置
            Optional.of(new File(this.location)).filter(file -> {
                if (!this.fileUploadSupported) return false;
                return file.exists() || file.mkdirs();
            }).ifPresent(file -> {
                MultipartConfigElement e = new MultipartConfigElement(
                        file.getAbsolutePath(),     //
                        maxFileSize,                //
                        maxRequestSize,             //
                        fileSizeThreshold);         //
                register.setMultipartConfig(e);
            });
        }
    }
}
