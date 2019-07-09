package com.mini.web.config;

import com.mini.util.*;
import com.mini.util.reflect.MiniParameter;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Before;
import com.mini.web.annotation.Controller;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.config.FilterConfigure.FilterElement;
import com.mini.web.config.HttpServletConfigure.HttpServletElement;
import com.mini.web.filter.CharacterEncodingFilter;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.*;
import com.mini.web.model.factory.*;
import com.mini.web.servlet.DispatcherHttpServlet;
import com.mini.web.view.IView;
import com.mini.web.view.ViewFreemarker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.*;

import static com.mini.util.ClassUtil.scanner;
import static com.mini.util.StringUtil.*;
import static com.mini.util.TypeUtil.*;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static javax.servlet.DispatcherType.*;

/**
 * Web 配置信息读取
 * @author xchao
 */
@Transactional
@PropertySource("classpath:mini-application.properties")
public abstract class WebMvcConfigureDefault implements WebMvcConfigure {
    private final ActionInvocationProxyConfigure actionInvocationProxyConfigure = new ActionInvocationProxyConfigure();
    private final ArgumentResolverConfigure argumentResolverConfigure = new ArgumentResolverConfigure();
    private final ModelFactoryConfigure modelFactoryConfigure = new ModelFactoryConfigure();
    private final HttpServletConfigure httpServletConfigure = new HttpServletConfigure();
    private final InterceptorConfigure interceptorConfigure = new InterceptorConfigure();
    private final ListenerConfigure listenerConfigure = new ListenerConfigure();
    private final FilterConfigure filterConfigure = new FilterConfigure();
    private final ViewConfigure viewConfigure = new ViewConfigure();

    //@javax.inject.Inject
    //@Named("mini.http.multipart.file-size-threshold")
    private String fileSizeThreshold;
    //
    //@javax.inject.Inject
    //@Named("mini.http.multipart.enabled")
    private String multipartEnabled;
    //
    //@javax.inject.Inject
    //@Named("mini.http.multipart.max-request-size")
    private String maxRequestSize;
    //
    //@javax.inject.Inject
    //@Named("mini.http.async.enabled")
    private String asyncEnabled;
    //
    //@javax.inject.Inject
    //@Named("mini.http.multipart.max-file-size")
    private String maxFileSize;
    //
    //@javax.inject.Inject
    //@Named("mini.http.servlet.urls")
    private String urlPatterns;
    //
    //@javax.inject.Inject
    //@Named("mini.http.multipart.location")
    private String location;

    /**
     * Gets the value of fileSizeThreshold.
     * @return The value of fileSizeThreshold
     */
    public final int getFileSizeThreshold() {
        return castToIntVal(fileSizeThreshold);
    }

    /**
     * Gets the value of maxRequestSize.
     * @return The value of maxRequestSize
     */
    public final long getMaxRequestSize() {
        if (StringUtil.isBlank(maxRequestSize)) {
            return -1L;
        }
        return castToLongVal(maxRequestSize);
    }

    /**
     * Gets the value of maxFileSize.
     * @return The value of maxFileSize
     */
    public final long getMaxFileSize() {
        if (StringUtil.isBlank(maxFileSize)) {
            return -1L;
        }
        return castToLongVal(maxFileSize);
    }

    /**
     * Gets the value of location.
     * @return The value of location
     */
    public final String getLocation() {
        return location;
    }

    /**
     * Gets the value of multipartEnabled.
     * @return The value of multipartEnabled
     */
    public final boolean isMultipartEnabled() {
        if (StringUtil.isBlank(multipartEnabled)) {
            return true;
        }
        return castToBoolVal(multipartEnabled);
    }

    /**
     * Gets the value of asyncEnabled.
     * @return The value of asyncEnabled
     */
    public final boolean isAsyncEnabled() {
        if (StringUtil.isBlank(asyncEnabled)) {
            return true;
        }
        return castToBoolVal(asyncEnabled);
    }

    /**
     * Gets the value of UrlPatterns.
     * @return The value of UrlPatterns
     */
    public final String[] getUrlPatterns() {
        return split(urlPatterns, "[,]");
    }

    /**
     * 初始化配置信息
     * @param servletContext     ServletContext
     * @param applicationContext ApplicationContext
     */
    public final void configure(ServletContext servletContext, ApplicationContext applicationContext) {
        WebMvcConfigureDefault.this.onStartup(servletContext, applicationContext);
        actionInvocationProxyConfigure(actionInvocationProxyConfigure);
        argumentResolverConfigure(argumentResolverConfigure);
        modelFactoryConfigure(modelFactoryConfigure);
        httpServletConfigure(httpServletConfigure);
        interceptorConfigure(interceptorConfigure);
        listenerConfigure(listenerConfigure);
        filterConfigure(filterConfigure);
        viewConfigure(viewConfigure);
    }

    /** 初始化所有配置信息 */
    public void onStartup(ServletContext servletContext, ApplicationContext applicationContext) {

    }

    /**
     * ActionInvocationProxy 配置
     * @param configure 配置信息
     */
    @OverridingMethodsMustInvokeSuper
    public void actionInvocationProxyConfigure(ActionInvocationProxyConfigure configure) {
        ComponentScan scanner = this.getClass().getAnnotation(ComponentScan.class);
        String[] packages = new String[]{this.getClass().getPackageName()};
        if (scanner != null && scanner.value().length > 0) {
            packages = scanner.value();
        }

        ArraysUtil.forEach(scanner(packages, Controller.class), clazz -> {
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller == null) return;

            Before cBefore = clazz.getAnnotation(Before.class);
            ArraysUtil.forEach(clazz.getMethods(), method -> {
                Action action = method.getAnnotation(Action.class);
                if (action == null) return;

                // 创建 Action 代理对象
                Before mBefore = method.getAnnotation(Before.class);
                ActionInvocationProxy proxy = new ActionInvocationProxy() {
                    private List<Class<? extends ActionInterceptor>> list;
                    private String path;

                    @Override
                    public Method getMethod() {
                        return method;
                    }

                    @Override
                    public Class<?> getClazz() {
                        return clazz;
                    }

                    @Override
                    public Class<? extends IModel<?>> getModelClass() {
                        return action.value();
                    }

                    @Override
                    public Action.Method[] getSupportMethod() {
                        return action.method();
                    }

                    @Nonnull
                    @Override
                    public List<Class<? extends ActionInterceptor>> getInterceptors() {
                        return Optional.ofNullable(list).orElseGet(() -> {
                            return list = ofNullable(mBefore).map(b -> {
                                return asList(b.value());       //
                            }).orElseGet(() -> ofNullable(cBefore).map(b -> {
                                return asList(b.value());   //
                            }).orElse(new ArrayList<>())); //
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
    }

    /**
     * 获取所有 ActionInvocationProxy
     * @return 所有 ActionInvocationProxy
     */
    public final MappingUri<ActionInvocationProxy> getInvocationProxy() {
        return actionInvocationProxyConfigure.getInvocationProxy();
    }

    /**
     * 获取 ActionInvocationProxy
     * @param url  路径
     * @param func 回调
     * @return 代理对象
     */
    public final ActionInvocationProxy getInvocationProxy(String url, Function.F2<String, String> func) {
        return actionInvocationProxyConfigure.getInvocationProxy().get(url, func);
    }

    /**
     * Action接收的参数配置
     * @param configure 配置信息
     */
    public void argumentResolverConfigure(ArgumentResolverConfigure configure) {
    }

    /**
     * 获取所有参数解析器的映射
     * @return 参数解析器的映射
     */
    public final Map<Class<?>, Class<? extends ArgumentResolver>> getArgumentResolvers() {
        return argumentResolverConfigure.getArgumentResolvers();
    }

    /**
     * 渲染器配置
     * @param configure 配置信息
     */
    @OverridingMethodsMustInvokeSuper
    public void modelFactoryConfigure(ModelFactoryConfigure configure) {
        configure.addModelFactory(ModelJsonList.class, ModelFactoryJsonList.class);
        configure.addModelFactory(ModelJsonMap.class, ModelFactoryJsonMap.class);
        configure.addModelFactory(ModelStream.class, ModelFactoryStream.class);
        configure.addModelFactory(ModelPage.class, ModelFactoryPage.class);
    }

    /**
     * 获取所有 Model 工厂映射
     * @return 所有 Model 工厂映射
     */
    public final Map<Class<? extends IModel<?>>, Class<? extends ModelFactory<?>>> getModelFactory() {
        return modelFactoryConfigure.getModelFactory();
    }

    /**
     * 根据 ModelClass 获取 Model 工厂
     * @param clazz ModelClass
     * @return Model工厂
     */
    public final Class<? extends ModelFactory<?>> getModelFactory(Class<? extends IModel<?>> clazz) {
        return modelFactoryConfigure.getModelFactory().get(clazz);
    }

    /**
     * HttpServlet 配置
     * @param configure 配置信息
     * @return 默认 HttpServlet
     */
    @OverridingMethodsMustInvokeSuper
    public void httpServletConfigure(HttpServletConfigure configure) {
        configure.addServlet(DispatcherHttpServlet.class)
                .setFileUploadSupported(isMultipartEnabled())
                .setFileSizeThreshold(getFileSizeThreshold())
                .setServletName("DispatcherHttpServlet")
                .setMaxRequestSize(getMaxRequestSize())
                .setAsyncSupported(isAsyncEnabled())
                .setMaxFileSize(getMaxFileSize())
                .addUrlPatterns(getUrlPatterns())
                .setLocation(getLocation());
    }

    /**
     * 获取所有的 HttpServletElement 对象
     * @return 所有的 HttpServletElement 对象
     */
    public final List<HttpServletElement> getHttpServletElements() {
        return httpServletConfigure.getElements();
    }

    /**
     * 拦截器配置
     * @param configure 配置信息
     */
    public void interceptorConfigure(InterceptorConfigure configure) {
    }

    /**
     * 获取所有拦截器实例
     * @return 所有拦截器实例
     */
    public final Set<Class<? extends ActionInterceptor>> getInterceptors() {
        return interceptorConfigure.getInterceptors();
    }

    /**
     * 监听器配置
     * @param configure 配置信息
     */
    public void listenerConfigure(ListenerConfigure configure) {
    }

    /**
     * 获取所有监听器实例
     * @return 所有监听器实例
     */
    public final Set<EventListener> getListeners() {
        return listenerConfigure.getListeners();
    }

    /**
     * 过虑器配置
     * @param configure 配置信息
     */
    public void filterConfigure(FilterConfigure configure) {
        configure.addFilter(CharacterEncodingFilter.class).setMatchAfter(true)
                .addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ERROR)
                .setFilterName("CharacterEncodingFilter")
                .addUrlPatterns("/*");
    }

    /**
     * 获取所有过虑器信息
     * @return 所有过虑器信息
     */
    public final List<FilterElement> getFilterElements() {
        return filterConfigure.getElements();
    }

    /**
     * 视图配置
     * @param configure 配置信息
     */
    public void viewConfigure(ViewConfigure configure) {
        configure.setViewClass(ViewFreemarker.class);
    }

    /**
     * 获取视图实现类实例
     * @return 视图实现类实例
     */
    public final Class<? extends IView> getViewClass() {
        return viewConfigure.getViewClass();
    }

    @Bean
    public final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
