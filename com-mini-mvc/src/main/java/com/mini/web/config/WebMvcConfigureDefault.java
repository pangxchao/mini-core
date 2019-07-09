package com.mini.web.config;

import com.mini.util.ArraysUtil;
import com.mini.util.ClassUtil;
import com.mini.util.MappingUri;
import com.mini.util.StringUtil;
import com.mini.util.reflect.MiniParameter;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Before;
import com.mini.web.annotation.Controller;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.*;
import com.mini.web.model.factory.ModelFactoryJsonList;
import com.mini.web.model.factory.ModelFactoryJsonMap;
import com.mini.web.model.factory.ModelFactoryPage;
import com.mini.web.model.factory.ModelFactoryStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.util.StringUtil.def;
import static com.mini.util.StringUtil.join;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/**
 * Web 配置信息读取
 * @author xchao
 */
@Named
@Singleton
@ComponentScan("com.mini.web")
@PropertySource("classpath:mini-application.properties")
public final class WebMvcConfigureDefault implements Serializable {
    private static final long serialVersionUID = 6552131965083314522L;
    private ActionInvocationProxyConfigure actionInvocationProxyConfigure;
    private ArgumentResolverConfigure argumentResolverConfigure;
    private ModelFactoryConfigure modelFactoryConfigure;
    private HttpServletConfigure httpServletConfigure;
    private InterceptorConfigure interceptorConfigure;
    private ListenerConfigure listenerConfigure;
    private FilterConfigure filterConfigure;
    private ViewConfigure viewConfigure;

    private List<WebMvcConfigure> configures;
    // 依赖注入窗口上下文信息
    private ApplicationContext context;

    // 编码
    @Value("${mini.http.encoding.charset}")
    private String encodingCharset;

    // 访问路径
    @Value("${mini.http.servlet.urls}")
    private String[] urlPatterns;

    // 异步支持
    @Value("${mini.http.async.supported}")
    private boolean asyncSupported;

    // 开启文件上传
    @Value("${mini.http.multipart.enabled}")
    private boolean multipartEnabled;

    // 上传文件缓冲区大小
    @Value("${mini.http.multipart.file-size-threshold}")
    private int fileSizeThreshold;

    // 上传文件总大小限制
    @Value("${mini.http.multipart.max-request-size}")
    private long maxRequestSize;

    // 上传文件单个文件大小限制
    @Value("${mini.http.multipart.max-file-size}")
    private long maxFileSize;

    // 上传文件临时路径
    @Value("${mini.http.multipart.location}")
    private String location;

    // 默认日期时间格式
    @Value("${mini.http.datetime-format}")
    private String dateTimeFormat;

    // 默认日期格式
    @Value("${mini.http.date-format}")
    private String dateFormat;

    // 默认时间格式
    @Value("${mini.http.time-format}")
    private String timeFormat;

    // 默认视图前缀
    @Value("${mini.mvc.view.prefix}")
    private String ViewPrefix;

    // 默认视图后缀
    @Value("${mini.mvc.view.suffix}")
    private String viewSuffix;

    /**
     * 自动注入 ActionInvocationProxyConfigure 配置信息
     * @param actionInvocationProxyConfigure 配置信息
     */
    @Inject
    public void setActionInvocationProxyConfigure(ActionInvocationProxyConfigure actionInvocationProxyConfigure) {
        this.actionInvocationProxyConfigure = actionInvocationProxyConfigure;
    }

    /**
     * 自动注入 ArgumentResolverConfigure 配置信息
     * @param argumentResolverConfigure 配置信息
     */
    @Inject
    public void setArgumentResolverConfigure(ArgumentResolverConfigure argumentResolverConfigure) {
        this.argumentResolverConfigure = argumentResolverConfigure;
    }

    /**
     * 自动注入 ModelFactoryConfigure 配置信息
     * @param modelFactoryConfigure 配置信息
     */
    @Inject
    public void setModelFactoryConfigure(ModelFactoryConfigure modelFactoryConfigure) {
        this.modelFactoryConfigure = modelFactoryConfigure;
    }

    /**
     * 自动注入 HttpServletConfigure 配置信息
     * @param httpServletConfigure 配置信息
     */
    @Inject
    public void setHttpServletConfigure(HttpServletConfigure httpServletConfigure) {
        this.httpServletConfigure = httpServletConfigure;
    }

    /**
     * 自动注入 InterceptorConfigure 配置信息
     * @param interceptorConfigure 配置信息
     */
    @Inject
    public void setInterceptorConfigure(InterceptorConfigure interceptorConfigure) {
        this.interceptorConfigure = interceptorConfigure;
    }

    /**
     * 自动注入 ListenerConfigure 配置信息
     * @param listenerConfigure 配置信息
     */
    @Inject
    public void setListenerConfigure(ListenerConfigure listenerConfigure) {
        this.listenerConfigure = listenerConfigure;
    }

    /**
     * 自动注入 FilterConfigure 配置信息
     * @param filterConfigure 配置信息
     */
    @Inject
    public void setFilterConfigure(FilterConfigure filterConfigure) {
        this.filterConfigure = filterConfigure;
    }

    /**
     * 自动注入 ViewConfigure 配置信息
     * @param viewConfigure 配置信息
     */
    @Inject
    public void setViewConfigure(ViewConfigure viewConfigure) {
        this.viewConfigure = viewConfigure;
    }

    /**
     * 自动注入依赖注入上下文环境
     * @param context 上下文环境
     */
    @Inject
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 自动注入所有自定义配置信息
     * @param configures 配置信息
     */
    @Inject
    public void setConfigures(List<WebMvcConfigure> configures) {
        this.configures = configures;
    }

    /**
     * 获取配置文件编码
     * @return 配置文件编码
     */
    public String getEncodingCharset() {
        return encodingCharset;
    }

    /**
     * 获取默认 Servlet 访问路径
     * @return Servlet 访问路径
     */
    public String[] getUrlPatterns() {
        return urlPatterns;
    }

    /**
     * 获取异步支持
     * @return true-支持异步
     */
    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    /**
     * 获取是否开启上传文件功能
     * @return true-开启
     */
    public boolean isMultipartEnabled() {
        return multipartEnabled;
    }

    /**
     * 获取文件上传缓冲区大小
     * @return 文件上传缓冲区大小
     */
    public int getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    /**
     * 同时上传文件的总大小
     * @return 同时上传文件的总大小
     */
    public long getMaxRequestSize() {
        return maxRequestSize;
    }

    /**
     * 单个文件大小限制
     * @return 单个文件大小限制
     */
    public long getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * 上传文件临时目录
     * @return 上传文件临时目录
     */
    public String getLocation() {
        return location;
    }

    /**
     * 默认日期时间格式
     * @return 默认日期时间格式
     */
    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * 默认日期格式
     * @return 默认日期格式
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * 默认时间格式
     * @return 默认时间格式
     */
    public String getTimeFormat() {
        return timeFormat;
    }

    /**
     * 视图路径前缀
     * @return 视图路径前缀
     */
    public String getViewPrefix() {
        return ViewPrefix;
    }

    /**
     * 视图路径后缀
     * @return 视图路径后缀
     */
    public String getViewSuffix() {
        return viewSuffix;
    }


    /**
     * 获取所有自定义配置信息
     * @return 配置信息
     */
    @Nonnull
    private List<WebMvcConfigure> getConfigures() {
        if (configures != null) return configures;
        return new ArrayList<>();
    }

    /**
     * 初始化所有配置信息
     * @param servletContext ServletContext 对象
     */
    public final void onStartup(ServletContext servletContext) {
        // 调用自定义启动项目
        for (WebMvcConfigure config : this.getConfigures()) {
            config.onStartup(servletContext);
        }

        // 绑定并初始化 actionInvocationProxyConfigure
        requireNonNull(actionInvocationProxyConfigure);
        actionInvocationProxyConfigure(actionInvocationProxyConfigure);

        // 绑定并初始化 argumentResolverConfigure
        requireNonNull(argumentResolverConfigure);
        argumentResolverConfigure(argumentResolverConfigure);

        // 绑定并初妈化 modelFactoryConfigure
        requireNonNull(modelFactoryConfigure);
        modelFactoryConfigure(modelFactoryConfigure);

        // 绑定并初始化 httpServletConfigure
        requireNonNull(httpServletConfigure);
        httpServletConfigure(httpServletConfigure);

        // 绑定并初始化 interceptorConfigure
        requireNonNull(interceptorConfigure);
        interceptorConfigure(interceptorConfigure);


        // 绑定并初始化 listenerConfigure
        requireNonNull(listenerConfigure);
        listenerConfigure(listenerConfigure);

        // 绑定并初始化 filterConfigure
        requireNonNull(filterConfigure);
        filterConfigure(filterConfigure);

        // 绑定并初始化 viewConfigure
        requireNonNull(viewConfigure);
        viewConfigure(viewConfigure);
    }

    /**
     * ActionInvocationProxy 配置
     * @param configure 配置信息
     */
    private void actionInvocationProxyConfigure(ActionInvocationProxyConfigure configure) {
        requireNonNull(context, "ApplicationContext can not be null");
        context.getBeansWithAnnotation(Controller.class).forEach((key, object) -> {
            Object control = requireNonNull(object, "Controller Object can not be null");
            Controller controller = control.getClass().getAnnotation(Controller.class);
            requireNonNull(controller, "@Controller can not be null");

            Before cBefore = control.getClass().getAnnotation(Before.class);
            ArraysUtil.forEach(control.getClass().getMethods(), method -> {
                Action action = method.getAnnotation(Action.class);
                if (action == null) return;

                // 创建 Action 代理对象
                Before before = defIfNull(method.getAnnotation(Before.class), cBefore);
                ActionInvocationProxy proxy = new ActionInvocationProxy() {
                    private List<ActionInterceptor> interceptors;
                    private String path;

                    @Nonnull
                    @Override
                    public Method getMethod() {
                        return method;
                    }

                    @Nonnull
                    @Override
                    public Class<?> getClazz() {
                        return control.getClass();
                    }

                    @Nonnull
                    @Override
                    public Object getInstance() {
                        return control;
                    }

                    @Nonnull
                    @Override
                    public Class<? extends IModel<?>> getModelClass() {
                        return action.value();
                    }

                    @Nonnull
                    @Override
                    public Action.Method[] getSupportMethod() {
                        return action.method();
                    }

                    @Nonnull
                    @Override
                    public List<ActionInterceptor> getInterceptors() {
                        return Optional.ofNullable(interceptors).orElseGet(() -> {
                            interceptors = Optional.ofNullable(before).map(b -> asList(b.value())).stream().flatMap(Collection::stream).map(clazz -> {
                                return context.getBean(clazz, ActionInterceptor.class);  //
                            }).collect(Collectors.toList());
                            return interceptors;
                        });
                    }

                    @Nonnull
                    @Override
                    public MiniParameter[] getParameters() {
                        return ClassUtil.getParameter(method);
                    }

                    @Override
                    public String getViewPath() {
                        return Optional.ofNullable(path).orElseGet(() -> {
                            String cPath = def(controller.path(), "");
                            String mPath = def(action.path(), method.getName());
                            path = join("/", cPath, mPath).replaceAll("(/)+", "/");
                            return path = (path.startsWith("/") ? path.substring(1) : path);
                        });
                    }
                };

                // 获取方法上的 urls 列表
                Arrays.stream(action.url().length > 0 ? action.url() : new String[]{method.getName()}).map(url -> {
                    return StringUtil.join("/", "", controller.url(), def(url, method.getName())); //
                }).map(url -> MappingUri.slashRemove(url.replaceAll("(/)+", "/"))).forEach(url -> {
                    configure.addInvocationProxy(url, proxy); //
                });
            });
        });

        // 调用自定义配置信息
        for (WebMvcConfigure config : this.getConfigures()) {
            config.actionInvocationProxyConfigure(configure);
        }
    }

    /**
     * 参数解析器配置
     * @param configure 配置信息
     */
    private void argumentResolverConfigure(ArgumentResolverConfigure configure) {
        // 调用自定义配置信息
        for (WebMvcConfigure config : this.getConfigures()) {
            config.argumentResolverConfigure(configure);
        }
    }

    /**
     * 渲染器配置
     * @param configure 配置信息
     */
    private void modelFactoryConfigure(ModelFactoryConfigure configure) {
        configure.addModelFactory(ModelJsonList.class, ModelFactoryJsonList.class);
        configure.addModelFactory(ModelJsonMap.class, ModelFactoryJsonMap.class);
        configure.addModelFactory(ModelStream.class, ModelFactoryStream.class);
        configure.addModelFactory(ModelPage.class, ModelFactoryPage.class);

        // 调用自定义配置信息
        for (WebMvcConfigure config : this.getConfigures()) {
            config.modelFactoryConfigure(configure);
        }
    }

    /**
     * HttpServlet 配置
     * @param configure 配置信息
     */
    private void httpServletConfigure(HttpServletConfigure configure) {
        configure.getDefaultElement().setFileUploadSupported(isMultipartEnabled())
                .setFileSizeThreshold(getFileSizeThreshold())
                .setMaxRequestSize(getMaxRequestSize())
                .setAsyncSupported(isAsyncSupported())
                .setMaxFileSize(getMaxFileSize())
                .addUrlPatterns(getUrlPatterns())
                .setLocation(getLocation());

        // 调用自定义配置信息
        for (WebMvcConfigure config : this.getConfigures()) {
            config.httpServletConfigure(configure);
        }
    }

    /**
     * 拦截器配置
     * @param configure 配置信息
     */
    private void interceptorConfigure(InterceptorConfigure configure) {
        // 调用自定义配置信息
        for (WebMvcConfigure config : this.getConfigures()) {
            config.interceptorConfigure(configure);
        }
    }

    /**
     * 监听器配置
     * @param configure 配置信息
     */
    private void listenerConfigure(ListenerConfigure configure) {
        // 调用自定义配置信息
        for (WebMvcConfigure config : this.getConfigures()) {
            config.listenerConfigure(configure);
        }
    }

    /**
     * 过虑器配置
     * @param configure 配置信息
     */
    private void filterConfigure(FilterConfigure configure) {
        // 调用自定义配置信息
        for (WebMvcConfigure config : this.getConfigures()) {
            config.filterConfigure(configure);
        }
    }

    /**
     * 视图配置
     * @param configure 配置信息
     */
    private void viewConfigure(ViewConfigure configure) {
        // 调用自定义配置信息
        Optional.ofNullable(configures).stream().flatMap(Collection::stream).forEach(config -> //
                config.viewConfigure(configure));
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
