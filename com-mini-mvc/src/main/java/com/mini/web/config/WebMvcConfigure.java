package com.mini.web.config;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.mini.inject.annotation.ComponentScan;
import com.mini.inject.annotation.EnableTransaction;
import com.mini.jdbc.transaction.Transactional;
import com.mini.jdbc.transaction.TransactionalInterceptor;
import com.mini.jdbc.util.Paging;
import com.mini.util.ClassUtil;
import com.mini.util.StringUtil;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Before;
import com.mini.web.annotation.Controller;
import com.mini.web.argument.*;
import com.mini.web.filter.AccessControlAllowOriginFilter;
import com.mini.web.filter.CacheControlFilter;
import com.mini.web.filter.CharacterEncodingFilter;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.*;
import com.mini.web.servlet.DispatcherHttpServlet;
import com.mini.web.util.RequestParameter;
import com.mini.web.view.FreemarkerView;
import com.mini.web.view.IView;
import org.aopalliance.intercept.MethodInterceptor;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.mini.util.FileUtil.getFileExt;
import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.util.StringUtil.def;
import static com.mini.util.StringUtil.join;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static javax.servlet.DispatcherType.*;

/**
 * Web 配置信息读取
 * @author xchao
 */
public abstract class WebMvcConfigure implements Module {
    // 定义URL路径与文件路径的分隔符
    private static final String SEP = "/";
    @Inject
    private ServletContext servletContext;
    @Inject
    private Configure configure;

    @Override
    public synchronized final void configure(Binder binder) {
        WebMvcConfigure.this.onStartupBinding(binder);
        // 数据库事务处理
        if (getAnnotation(EnableTransaction.class) != null) return;
        MethodInterceptor interceptor = new TransactionalInterceptor();
        binder.bindInterceptor(any(), annotatedWith(Transactional.class), interceptor);
    }

    /**
     * 该方法只难做依赖绑定相关操作
     * @param binder 绑定器
     */
    protected synchronized void onStartupBinding(Binder binder) {
    }

    public synchronized final void onStartupRegister() {
        Objects.requireNonNull(servletContext);
        Objects.requireNonNull(configure);
        // 注册默认的 ActionInvocationProxy
        registerActionInvocationProxy(configure);
        // 注册默认Servlet
        registerServlet(configure);
        // 注册默认过虑器
        registerFilter(configure);
        // 注册默认参数解析器
        registerArgumentResolver(configure);
        //  注册默认视图实现类
        registerView(configure);
        // 调用自定义初始化方法
        onStartupRegister(servletContext, configure);
    }

    /**
     * 该方法在自动注入之后调用，使用时需要注意顺序
     * @param servletContext ServletContext 对象
     * @param configure      配置信息
     */
    protected void onStartupRegister(ServletContext servletContext, Configure configure) {
    }

    // 获取当前类指定注解信息
    public final <T extends Annotation> T getAnnotation(Class<T> clazz) {
        return this.getClass().getAnnotation(clazz);
    }

    // 注册默认的 ActionInvocationProxy
    private void registerActionInvocationProxy(Configure configure) {
        ComponentScan scan = getClass().getAnnotation(ComponentScan.class);
        String[] componentValue = new String[]{getClass().getPackage().getName()};
        if (scan != null && scan.value().length > 0) componentValue = scan.value();
        ClassUtil.scanner(componentValue, Controller.class).forEach(clazz -> {
            Controller controller = clazz.getAnnotation(Controller.class);
            requireNonNull(controller, "@Controller can not be null");

            Before cBefore = clazz.getAnnotation(Before.class);
            Arrays.stream(clazz.getMethods()).forEach(method -> {
                Action action = method.getAnnotation(Action.class);
                if (action == null) return;

                // 创建 Action 代理对象
                Before before = defIfNull(method.getAnnotation(Before.class), cBefore);
                ActionInvocationProxy proxy = new ActionInvocationProxy() {
                    private List<Class<? extends ActionInterceptor>> interceptors;
                    private RequestParameter[] parameters;
                    private String path;

                    @Nonnull
                    @Override
                    public Method getMethod() {
                        return method;
                    }

                    @Nonnull
                    @Override
                    public Class<?> getClazz() {
                        return clazz;
                    }

                    @Nonnull
                    @Override
                    public IModel<?> getModel(IView view, String viewPath) {
                        return action.value().getModel(view, viewPath);
                    }

                    @Nonnull
                    @Override
                    public Action.Method[] getSupportMethod() {
                        return action.method();
                    }

                    @Nonnull
                    @Override
                    public List<Class<? extends ActionInterceptor>> getInterceptors() {
                        return Optional.ofNullable(interceptors).orElseGet(() -> {
                            if (before == null) interceptors = new ArrayList<>();
                            else interceptors = asList(before.value());
                            return interceptors;
                        });
                    }

                    @Nonnull
                    @Override
                    public RequestParameter[] getParameters() {
                        return Optional.ofNullable(parameters).orElseGet(() -> {
                            parameters = Arrays.stream(ClassUtil.getParameter(method)) //
                                    .map(RequestParameter::new)         //
                                    .toArray(RequestParameter[]::new); //
                            return parameters;
                        });
                    }

                    @Override
                    public String getViewPath() {
                        if (!StringUtil.isBlank(path)) {
                            return path;
                        }
                        String cPath = def(controller.path(), "");
                        String mPath = def(action.path(), method.getName());
                        path = join("/", cPath, mPath).replaceAll("(/)+", "/");
                        return path = (path.startsWith("/") ? path.substring(1) : path);
                    }
                };

                // 处理 URL 的连接
                Stream.concat(Stream.of(action.url()), Stream.of(method.getName())).map(url -> {
                    url = Stream.of(controller.url(), url).map(v -> v.split(SEP)) //
                            .flatMap(Stream::of).filter(v -> !v.isBlank())  //
                            .collect(Collectors.joining(SEP));
                    // 如果路径上没有配置后缀名，使用默认后缀
                    if (StringUtil.isBlank(getFileExt(url))) {
                        url = url + action.suffix();
                    }
                    return url;
                }).distinct().forEach(url -> configure.addInvocationProxy(url, proxy));
            });
        });
    }

    // 注册默认Servlet
    private void registerServlet(Configure configure) {
        // 默认Action处理Servlet
        ServletElement element = configure.addServlet(DispatcherHttpServlet.class);
        element.setFileSizeThreshold(configure.getFileSizeThreshold());
        element.setMultipartEnabled(configure.isMultipartEnabled());
        element.setAsyncSupported(configure.isAsyncSupported());
        element.setMaxRequestSize(configure.getMaxRequestSize());
        element.addUrlPatterns(configure.getUrlPatterns());
        element.setMaxFileSize(configure.getMaxFileSize());
        element.setLocation(configure.getLocation());
    }

    // 配置默认过虑器
    private void registerFilter(Configure configure) {
        // 编码统一管理过虑器
        FilterElement element = configure.addFilter(CharacterEncodingFilter.class);
        element.addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ASYNC, ERROR);
        element.addUrlPatterns("/*").setMatchAfter(true);

        // 跨域请求过虑器
        element = configure.addFilter(AccessControlAllowOriginFilter.class);
        element.addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ASYNC, ERROR);
        element.addUrlPatterns("/*").setMatchAfter(true);

        // 静态资源缓存过虑器
        element = configure.addFilter(CacheControlFilter.class);
        element.addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ASYNC);
        element.addUrlPatterns("/*").setMatchAfter(true);
    }

    // 配置默认参数解析器
    private void registerArgumentResolver(Configure configure) {
        // 基础类型数据
        configure.addResolver(String.class, ArgumentResolverString.class);
        configure.addResolver(Long.class, ArgumentResolverLong.class);
        configure.addResolver(long.class, ArgumentResolverLongVal.class);
        configure.addResolver(Integer.class, ArgumentResolverInt.class);
        configure.addResolver(int.class, ArgumentResolverIntVal.class);
        configure.addResolver(Short.class, ArgumentResolverShort.class);
        configure.addResolver(short.class, ArgumentResolverShortVal.class);
        configure.addResolver(Byte.class, ArgumentResolverByte.class);
        configure.addResolver(byte.class, ArgumentResolverByteVal.class);
        configure.addResolver(Double.class, ArgumentResolverDouble.class);
        configure.addResolver(double.class, ArgumentResolverDoubleVal.class);
        configure.addResolver(Float.class, ArgumentResolverFloat.class);
        configure.addResolver(float.class, ArgumentResolverFloatVal.class);
        configure.addResolver(Boolean.class, ArgumentResolverBoolean.class);
        configure.addResolver(boolean.class, ArgumentResolverBooleanVal.class);
        configure.addResolver(Character.class, ArgumentResolverChar.class);
        configure.addResolver(char.class, ArgumentResolverCharVal.class);

        // 基础类型数组
        configure.addResolver(String[].class, ArgumentResolverArrayString.class);
        configure.addResolver(Long[].class, ArgumentResolverArrayLong.class);
        configure.addResolver(long[].class, ArgumentResolverArrayLongVal.class);
        configure.addResolver(Integer[].class, ArgumentResolverArrayInt.class);
        configure.addResolver(int[].class, ArgumentResolverArrayIntVal.class);
        configure.addResolver(Short[].class, ArgumentResolverArrayShort.class);
        configure.addResolver(short[].class, ArgumentResolverArrayShortVal.class);
        configure.addResolver(Byte[].class, ArgumentResolverArrayByte.class);
        configure.addResolver(byte[].class, ArgumentResolverArrayByteVal.class);
        configure.addResolver(Double[].class, ArgumentResolverArrayDouble.class);
        configure.addResolver(double[].class, ArgumentResolverArrayDoubleVal.class);
        configure.addResolver(Float[].class, ArgumentResolverArrayFloat.class);
        configure.addResolver(float[].class, ArgumentResolverArrayFloatVal.class);
        configure.addResolver(Boolean[].class, ArgumentResolverArrayBoolean.class);
        configure.addResolver(boolean[].class, ArgumentResolverArrayBooleanVal.class);
        configure.addResolver(Character[].class, ArgumentResolverArrayChar.class);
        configure.addResolver(char[].class, ArgumentResolverArrayCharVal.class);

        // 文件类型
        configure.addResolver(Part.class, ArgumentResolverPart.class);
        configure.addResolver(Part[].class, ArgumentResolverPartArray.class);

        // 日期时间类型
        configure.addResolver(java.util.Date.class, ArgumentResolverDateTime.class);
        configure.addResolver(LocalDateTime.class, ArgumentResolverDateTime.class);
        configure.addResolver(Timestamp.class, ArgumentResolverDateTime.class);
        configure.addResolver(LocalDate.class, ArgumentResolverDate.class);
        configure.addResolver(LocalTime.class, ArgumentResolverTime.class);
        configure.addResolver(Date.class, ArgumentResolverDate.class);
        configure.addResolver(Time.class, ArgumentResolverTime.class);

        // Web上下文相关类型
        configure.addResolver(ServletContext.class, ArgumentResolverServletContext.class);
        configure.addResolver(HttpServletResponse.class, ArgumentResolverResponse.class);
        configure.addResolver(HttpServletRequest.class, ArgumentResolverRequest.class);
        configure.addResolver(ServletResponse.class, ArgumentResolverResponse.class);
        configure.addResolver(ServletRequest.class, ArgumentResolverRequest.class);
        configure.addResolver(HttpSession.class, ArgumentResolverHttpSession.class);

        // Model 类型
        configure.addResolver(StringModel.class, ArgumentResolverModel.class);
        configure.addResolver(StreamModel.class, ArgumentResolverModel.class);
        configure.addResolver(PageModel.class, ArgumentResolverModel.class);
        configure.addResolver(ListModel.class, ArgumentResolverModel.class);
        configure.addResolver(MapModel.class, ArgumentResolverModel.class);
        configure.addResolver(IModel.class, ArgumentResolverModel.class);

        // 其它类型
        configure.addResolver(Paging.class, ArgumentResolverPaging.class);
        configure.addResolver(StringBuilder.class, ArgumentResolverBody.class);
    }

    // 配置默认视图实现类
    private void registerView(Configure configure) {
        configure.setViewClass(FreemarkerView.class);
    }
}
