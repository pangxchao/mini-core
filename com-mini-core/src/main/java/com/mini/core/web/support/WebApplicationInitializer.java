package com.mini.core.web.support;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.mini.core.inject.MiniModule;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.view.PageViewResolver;
import com.mini.core.web.view.PageViewResolverFreemarker;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.view.PageViewResolver;
import com.mini.core.web.view.PageViewResolverFreemarker;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Singleton
@ComponentScan
public abstract class WebApplicationInitializer extends MiniModule implements Module {
    private static final String TEMP_KEY = "java.io.tmpdir";

    @Override
    protected final void onStartup(Binder binder) {
        onStartupBinding(binder);
    }

    /**
     * 该方法只难做依赖绑定相关操作
     * @param binder 绑定器
     */
    protected void onStartupBinding(Binder binder) {
    }

    /**
     * 该方法在自动注入之后调用，使用时需要注意顺序
     * @param context   ServletContext 对象
     * @param configure 配置信息
     */
    public void onStartupRegister(ServletContext context, Configures configure) {
    }

    /**
     * 获取页面类型视图解析器
     * @return 默认为“PageViewResolverFreemarker”
     * @see PageViewResolverFreemarker
     */
    @Provides
    @Singleton
    @Named("DefaultPageViewResolver")
    public final PageViewResolver getDefaultPageViewResolver() {
        return new PageViewResolverFreemarker();
    }

    /**
     * 获取字符集编码
     * @return 默认为“UTF-8”
     */
    @Provides
    @Singleton
    @Named("CharsetEncoding")
    public final String getCharsetEncoding() {
        return UTF_8.name();
    }

    /**
     * 是否支持异步处理
     * @return 默认为“true”
     */
    @Provides
    @Singleton
    @Named("AsyncSupported")
    public final boolean isAsyncSupported() {
        return true;
    }

    /**
     * 获取默认请求拦截
     * @return 默认为“*.htm”
     */
    @Provides
    @Singleton
    @Named("DefaultMapping")
    public final String getDefaultMapping() {
        return "*.htm";
    }

    /**
     * 是否支持文件上传
     * @return 默认为“true”
     */
    @Provides
    @Singleton
    @Named("MultipartEnabled")
    public final boolean isMultipartEnabled() {
        return true;
    }

    /**
     * 获取上传文件缓冲区大小
     * @return 默认为“0”
     */
    @Provides
    @Singleton
    @Named("FileSizeThreshold")
    public final int getFileSizeThreshold() {
        return 0;
    }

    /**
     * 获取上传文件总大小限制
     * @return 默认为“-1”表示不限制
     */
    @Provides
    @Singleton
    @Named("MaxRequestSize")
    public final long getMaxRequestSize() {
        return -1;
    }

    /**
     * 获取上传文件单个文件大小限制
     * @return 默认为“-1”表示不限制
     */
    @Provides
    @Singleton
    @Named("MaxFileSize")
    public final long getMaxFileSize() {
        return -1;
    }

    /**
     * 获取上传文件临时路径
     * @return 默认为系统临时目录
     */
    @Provides
    @Singleton
    @Named("LocationPath")
    public final synchronized String getLocationPath() {
        return Optional.of(TEMP_KEY)
                .map(System::getProperty)
                .map(File::new)
                .filter(f -> f.exists() || f.mkdirs())
                .map(File::getAbsolutePath)
                .orElseThrow();
    }

    /**
     * 获取日期时间格式
     * @return 默认为“yyyy-MM-dd HH[:mm[:ss]]”
     */
    @Provides
    @Singleton
    @Named("DateTimeFormat")
    public final String getDateTimeFormat() {
        return "yyyy-MM-dd HH[:mm[:ss]]";
    }

    /**
     * 获取日期格式
     * @return 默认为“yyyy[-MM[-dd]]”
     */
    @Provides
    @Singleton
    @Named("DateFormat")
    public final String getDateFormat() {
        return "yyyy[-MM[-dd]]";
    }

    /**
     * 获取时间格式
     * @return 默认为“HH[:mm[:ss]]”
     */
    @Provides
    @Singleton
    @Named("TimeFormat")
    public final String getTimeFormat() {
        return "HH[:mm[:ss]]";
    }

    /**
     * 获取视图前缀
     * @return 默认为“/WEB-INF/”
     */
    @Provides
    @Singleton
    @Named("ViewPrefix")
    public final String getViewPrefix() {
        return "/WEB-INF/";
    }

    /**
     * 获取视图后缀
     * @return 默认为“.ftl”
     */
    @Provides
    @Singleton
    @Named("ViewSuffix")
    public final String getViewSuffix() {
        return ".ftl";
    }

    /**
     * 获取跨域请求方法配置
     * @return 默认为“POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD”
     */
    @Provides
    @Singleton
    @Named("AccessControlAllowMethods")
    public final String getAccessControlAllowMethods() {
        return "POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD";
    }

    /**
     * 获取跨域请求头设置
     * <p> x-requested-with表示AJAX请求</p>
     * @return 默认为“x-requested-with, Content-Type”
     */
    @Provides
    @Singleton
    @Named("AccessControlAllowHeaders")
    public final String getAccessControlAllowHeaders() {
        return "x-requested-with, Content-Type";
    }

    /**
     * 获取跨域请求域名设置
     * @return 默认为“*”
     */
    @Provides
    @Singleton
    @Named("AccessControlAllowOrigin")
    public final String getAccessControlAllowOrigin() {
        return "*";
    }

    /**
     * 获取跨域超时时间设置
     * @return 默认为“3600”
     */
    @Provides
    @Singleton
    @Named("AccessControlAllowOrigin")
    public final int getAccessControlMaxAge() {
        return 3600;
    }

    /**
     * 获取缓存控制器
     * @return 默认为“No-Cache”
     */
    @Provides
    @Singleton
    @Named("CacheControl")
    public final String getCacheControl() {
        return "No-Cache";
    }

    /**
     * 获取缓存标注
     * @return 默认为“No-Cache”
     */
    @Provides
    @Singleton
    @Named("CachePragma")
    public final String getCachePragma() {
        return "No-Cache";
    }

    /**
     * 获取缓存过期时间
     * @return 默认为“0”
     */
    @Provides
    @Singleton
    @Named("CacheExpires")
    public final int getCacheExpires() {
        return 0;
    }
}
