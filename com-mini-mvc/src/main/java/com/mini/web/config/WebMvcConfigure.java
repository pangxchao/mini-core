package com.mini.web.config;

import com.google.inject.Binder;
import com.mini.inject.MiniModule;
import com.mini.inject.annotation.Scanning;
import com.mini.util.*;
import com.mini.util.reflect.MiniParameter;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Before;
import com.mini.web.annotation.Controller;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.filter.CharacterEncodingFilter;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.model.*;
import com.mini.web.model.factory.*;
import com.mini.web.servlet.ActionInvocationProxy;
import com.mini.web.view.IView;
import com.mini.web.view.ViewFreemarker;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.inject.Named;
import javax.servlet.MultipartConfigElement;
import java.lang.reflect.Method;
import java.util.*;

import static com.mini.util.ClassUtil.scanner;
import static com.mini.util.StringUtil.def;
import static com.mini.util.StringUtil.join;
import static com.mini.util.TypeUtil.*;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static javax.servlet.DispatcherType.*;

/**
 * Web 配置信息读取
 * @author xchao
 */
public abstract class WebMvcConfigure extends MiniModule {
    private final ActionInvocationProxyConfigure actionInvocationProxyConfigure = new ActionInvocationProxyConfigure();
    private final ArgumentResolverConfigure argumentResolverConfigure = new ArgumentResolverConfigure();
    private final ServletHandlerConfigure servletHandlerConfigure = new ServletHandlerConfigure();
    private final ModelFactoryConfigure modelFactoryConfigure = new ModelFactoryConfigure();
    private final InterceptorConfigure interceptorConfigure = new InterceptorConfigure();
    private final MultipartConfigure multipartConfigure = new MultipartConfigure();
    private final ListenerConfigure listenerConfigure = new ListenerConfigure();
    private final FilterConfigure filterConfigure = new FilterConfigure();
    private final ViewConfigure viewConfigure = new ViewConfigure();

    @javax.inject.Inject
    @Named("mini.http.async.enabled")
    private String asyncEnabled;

    @javax.inject.Inject
    @Named("mini.http.multipart.enabled")
    private String multipartEnabled;

    @javax.inject.Inject
    @Named("mini.http.multipart.file-size-threshold")
    private String fileSizeThreshold;

    @javax.inject.Inject
    @Named("mini.http.multipart.max-request-size")
    private String maxRequestSize;

    @javax.inject.Inject
    @Named("mini.http.multipart.max-file-size")
    private String maxFileSize;

    @javax.inject.Inject
    @Named("mini.http.multipart.location")
    private String location;


    @Override
    protected final void configures(Binder binder) throws Error {
        WebMvcConfigure.this.configureInitializer(binder);
        actionInvocationProxyConfigure(actionInvocationProxyConfigure);
        argumentResolverConfigure(argumentResolverConfigure);
        servletHandlerConfigure(servletHandlerConfigure);
        modelFactoryConfigure(modelFactoryConfigure);
        interceptorConfigure(interceptorConfigure);
        multipartConfigure(multipartConfigure);
        listenerConfigure(listenerConfigure);
        filterConfigure(filterConfigure);
        viewConfigure(viewConfigure);
    }

    /** 初始化所有配置信息 */
    protected void configureInitializer(Binder binder) throws Error {}

    /**
     * ActionInvocationProxy 配置
     * @param configure 配置信息
     */
    @OverridingMethodsMustInvokeSuper
    protected void actionInvocationProxyConfigure(ActionInvocationProxyConfigure configure) {
        Scanning scanner = this.getClass().getAnnotation(Scanning.class);
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
    protected void argumentResolverConfigure(ArgumentResolverConfigure configure) {
    }

    /**
     * 获取所有参数解析器的映射
     * @return 参数解析器的映射
     */
    public final Map<Class<?>, ArgumentResolver> getArgumentResolvers() {
        return argumentResolverConfigure.getArgumentResolvers();
    }

    /**
     * 默认的 Servlet 配置处理
     * @param configurer 配置信息
     */
    protected void servletHandlerConfigure(ServletHandlerConfigure configurer) {
        if (!StringUtil.isBlank(multipartEnabled)) {
            boolean b = castToBoolVal(multipartEnabled);
            configurer.setFileUploadSupported(b);
        }
        if (!StringUtil.isBlank(asyncEnabled)) {
            boolean b = castToBoolVal(asyncEnabled);
            configurer.setAsyncSupported(b);
        }
    }

    /**
     * 获取访问路径配置
     * @return 访问路径配置
     */
    public final String[] getServletUrlPatterns() {
        return servletHandlerConfigure.getUrlPatterns();
    }

    /**
     * 是否支持文件上传功能
     * @return true-是
     */
    public final boolean isFileUploadSupported() {
        return servletHandlerConfigure.isFileUploadSupported();
    }

    /**
     * 是否支持异步处理功能
     * @return true-是
     */
    public final boolean isAsyncSupported() {
        return servletHandlerConfigure.isAsyncSupported();
    }

    /**
     * 获取默认 Servlet 名称
     * @return Servlet 名称
     */
    public final String getServletName() {
        return servletHandlerConfigure.getServletName();
    }

    /**
     * 渲染器配置
     * @param configure 配置信息
     */
    protected void modelFactoryConfigure(ModelFactoryConfigure configure) {
        configure.addRenderer(ModelPage.class, new ModelFactoryPage());
        configure.addRenderer(ModelJsonMap.class, new ModelFactoryJsonMap());
        configure.addRenderer(ModelJsonList.class, new ModelFactoryJsonList());
        configure.addRenderer(ModelStream.class, new ModelFactoryStream());
    }

    /**
     * 获取所有 Model 工厂映射
     * @return 所有 Model 工厂映射
     */
    public final Map<Class<? extends IModel<?>>, ModelFactory<?>> getModelFactory() {
        return modelFactoryConfigure.getModelFactory();
    }

    /**
     * 根据 ModelClass 获取 Model 工厂
     * @param clazz ModelClass
     * @return Model工厂
     */
    public final ModelFactory<?> getModelFactory(Class<? extends IModel<?>> clazz) {
        return modelFactoryConfigure.getModelFactory().get(clazz);
    }

    /**
     * 拦截器配置
     * @param configure 配置信息
     */
    protected void interceptorConfigure(InterceptorConfigure configure) {
    }

    /**
     * 获取所有拦截器实例
     * @return 所有拦截器实例
     */
    public final Set<ActionInterceptor> getInterceptors() {
        return interceptorConfigure.getInterceptors();
    }

    /**
     * 文件上传配置处理
     * @param configurer 配置信息
     */
    protected void multipartConfigure(MultipartConfigure configurer) {
        if (!StringUtil.isBlank(fileSizeThreshold)) {
            int size = castToIntVal(fileSizeThreshold);
            configurer.setFileSizeThreshold(size);
        }
        if (!StringUtil.isBlank(maxRequestSize)) {
            long size = castToLongVal(maxRequestSize);
            configurer.setMaxRequestSize(size);
        }
        if (!StringUtil.isBlank(maxFileSize)) {
            long size = castToLongVal(maxFileSize);
            configurer.setMaxFileSize(size);
        }
        if (!StringUtil.isBlank(location)) {
            configurer.setLocation(location);
        }
    }

    /**
     * 获取 MultipartConfigElement 对象
     * @return MultipartConfigElement 对象
     */
    public final MultipartConfigElement getMultipartConfigElement() {
        return multipartConfigure.getMultipartConfigElement();
    }

    /**
     * 监听器配置
     * @param configure 配置信息
     */
    protected void listenerConfigure(ListenerConfigure configure) {
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
    protected void filterConfigure(FilterConfigure configure) {
        configure.addFilter(new CharacterEncodingFilter()).setMatchAfter(true)
                .addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ERROR)
                .setFilterName("CharacterEncodingFilter")
                .addUrlPatterns("/*");
    }

    /**
     * 获取所有过虑器信息
     * @return 所有过虑器信息
     */
    public final List<FilterConfigure.FilterConfigureElement> getFilterConfigureElements() {
        return filterConfigure.getElements();
    }

    /**
     * 视图配置
     * @param configure 配置信息
     */
    public void viewConfigure(ViewConfigure configure) {
        configure.setView(new ViewFreemarker());
    }

    /**
     * 获取视图实现类实例
     * @return 视图实现类实例
     */
    public final IView getView() {
        return viewConfigure.getView();
    }


}
