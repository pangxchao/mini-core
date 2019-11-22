package com.mini.web.servlet;

import com.google.inject.Injector;
import com.mini.logger.Logger;
import com.mini.web.annotation.Action;
import com.mini.web.config.Configure;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.IModel;
import com.mini.web.util.RequestParameter;
import com.mini.web.util.ResponseCode;
import com.mini.web.util.WebUtil;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.mini.logger.LoggerFactory.getLogger;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

/**
 * 默认的Servlet
 * @author xchao
 */
public abstract class AbstractDispatcherHttpServlet extends HttpServlet implements ResponseCode {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);

    @Inject
    private Configure configure;

    // @Inject
    private Injector injector;

    public final Configure getConfigure() {
        return configure;
    }

    public final Injector getInjector() {
        return injector;
    }

    @Inject
    public final void setInjector(Injector injector) {
        this.injector = injector;
    }

    @Override
    public final void init(ServletConfig config) throws ServletException {
        Objects.requireNonNull(configure);
        Objects.requireNonNull(injector);
        super.init(config);
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doService(Action.Method.HEAD, request, response);
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doService(Action.Method.GET, request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doService(Action.Method.POST, request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doService(Action.Method.PUT, request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doService(Action.Method.DELETE, request, response);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doService(Action.Method.OPTIONS, request, response);
    }

    /**
     * ActionInvocation 核心处理方法
     * @param method   提交数据是以哪种方法提交的
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     */
    private void doService(Action.Method method, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取计验证请求地址
        final String uri = WebUtil.getRequestPath(request);
        // 获取并验证 Action 调用对象，如果该对象为空时，返回 404 错误
        final ActionInvocationProxy proxy = this.getInvocationProxy(uri, method, request);
        if (proxy == null) {
            response.sendError(500);
            return;
        }
        //if (require(proxy, response, NOT_FOUND, "Not Found Page:" + uri, Objects::nonNull)) {
        //    return;
        //}

        // 获取数据模型实例并验证，如果该实例为空，返回 500 错误
        final IModel<?> model = proxy.getModel(configure.getView());
        try {


            // 创建 ActionInvocation 对象
            final ActionInvocation action = new ActionInvocation() {
                private List<ActionInterceptor> interceptors;
                private Iterator<ActionInterceptor> iterator;
                private Object instance;

                @Nonnull
                @Override
                public synchronized final Method getMethod() {
                    return proxy.getMethod();
                }

                @Nonnull
                @Override
                public synchronized final Class<?> getClazz() {
                    return proxy.getClazz();
                }

                @Nonnull
                @Override
                public synchronized final Object getInstance() {
                    return Optional.ofNullable(instance).orElseGet(() -> {
                        instance = injector.getInstance(getClazz());
                        return instance;
                    });
                }

                @Nonnull
                @Override
                public synchronized final List<ActionInterceptor> getInterceptors() {
                    return Optional.ofNullable(interceptors).orElseGet(() -> {
                        interceptors = proxy.getInterceptors().stream().map(c -> {
                            return injector.getInstance(c); //
                        }).collect(Collectors.toList());
                        return interceptors;
                    });
                }

                @Nonnull
                private synchronized Iterator<ActionInterceptor> getIterator() {
                    return ofNullable(iterator).orElseGet(() -> {
                        iterator = getInterceptors().iterator();
                        return iterator;
                    });
                }

                @Nonnull
                @Override
                public synchronized final String getUrl() {
                    return uri;
                }

                @Override
                public synchronized final String getViewPath() {
                    return proxy.getViewPath();
                }

                @Nonnull
                @Override
                public synchronized final IModel<?> getModel() {
                    return model;
                }

                @Nonnull
                @Override
                public synchronized final HttpServletRequest getRequest() {
                    return request;
                }

                @Nonnull
                @Override
                public synchronized final HttpServletResponse getResponse() {
                    return response;
                }

                @Override
                public synchronized final HttpSession getSession() {
                    return request.getSession();
                }

                @Override
                public synchronized final ServletContext getServletContext() {
                    return request.getServletContext();
                }

                @Nonnull
                @Override
                public synchronized final RequestParameter[] getParameters() {
                    return proxy.getParameters();
                }

                @Nonnull
                @Override
                public synchronized final Object[] getParameterValues() {
                    return stream(getParameters()).map(p -> p.getValue(//
                            configure, this)).toArray();
                }

                @Override
                public synchronized final Object invoke() throws Throwable {
                    try {
                        Iterator<ActionInterceptor> iterator;
                        if ((iterator = getIterator()).hasNext()) {
                            return iterator.next().invoke(this);
                        }
                        Object object = getInstance();
                        Object[] values = getParameterValues();
                        return getMethod().invoke(object, values);
                    } catch (InvocationTargetException ex) {
                        throw ex.getTargetException();
                    }
                }
            };

            // 调用目标方法
            action.invoke();
        } catch (Throwable exception) {

        }

        try { // 返回数据
            model.onSubmit(request, response);
        } catch (Exception | Error exception) {
            LOGGER.error(exception);
        }
    }

    /**
     * 获取实际的 InvocationProxy 对象的Uri
     * @param requestPath 请求路径
     * @param request     HttpServletRequest 对象
     * @return InvocationProxy Uri
     */
    protected abstract String getInvocationProxyUri(@Nonnull String requestPath, HttpServletRequest request);

    /**
     * 是否开启后缀匹配模式
     * @param suffixPattern 配置文件中的该值
     * @return true-是
     */
    protected boolean useSuffixPatternMatch(boolean suffixPattern) {
        return suffixPattern;
    }

    /**
     * 是否自动后缀路径模式匹配
     * @param trailingSlash 配置文件中的该值
     * @return true-是
     */
    protected boolean useTrailingSlashMatch(boolean trailingSlash) {
        return trailingSlash;
    }

    /**
     * 获取路径在匹配过程的参数回调
     * @param request 配置文件中的该值
     * @return BiConsumer 对象
     */
    protected BiConsumer<String, String> getBiConsumer(HttpServletRequest request) {
        return request::setAttribute;
    }

    /**
     * 获取 ActionInvocationProxy 对象
     * @param requestPath 请求路径
     * @param method      Action.Method
     * @param request     HttpServletRequest
     * @return ActionInvocationProxy 对象
     */
    private ActionInvocationProxy getInvocationProxy(String requestPath, Action.Method method, HttpServletRequest request) {
        return getConfigure().getInvocationProxy(getInvocationProxyUri(requestPath, request), method,
                useSuffixPatternMatch(configure.isSuffixPattern()), useTrailingSlashMatch(configure.isTrailingSlash()),
                this.getBiConsumer(request));
    }

    ///**
    // * 验证表达式，并返回错误信息
    // * @param instance 验证对象
    // * @param response HttpServletResponse
    // * @param code     错误码
    // * @param message  错误消息
    // * @param function 验证回调
    // * @return true-验证未通过
    // */
    //private <T> boolean require(T instance, HttpServletResponse response, int code, String message, Predicate<T> function)
    //        throws IOException {

    //}
}
