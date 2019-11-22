package com.mini.web.servlet;

import com.google.inject.Injector;
import com.mini.core.logger.Logger;
import com.mini.core.util.StringUtil;
import com.mini.core.util.matcher.PathMatcher;
import com.mini.core.util.matcher.PathMatcherAnt;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.annotation.Action;
import com.mini.web.config.Configure;
import com.mini.web.handler.ExceptionHandler;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.interceptor.ActionProxy;
import com.mini.web.model.IModel;
import com.mini.web.util.ResponseCode;

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
import java.util.*;
import java.util.stream.Stream;

import static com.mini.core.logger.LoggerFactory.getLogger;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * 默认的Servlet
 * @author xchao
 */
public abstract class AbstractDispatcherHttpServlet extends HttpServlet implements ResponseCode {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);
    private static final PathMatcher matcher = new PathMatcherAnt();
    private Configure configure;
    private Injector injector;

    public final Configure getConfigure() {
        return configure;
    }

    public final Injector getInjector() {
        return injector;
    }

    @Inject
    public final void setConfigure(Configure configure) {
        this.configure = configure;
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
        // 获取请求路径-URI
        String uri = StringUtil.strip(getActionProxyUri(request).strip(), "/"), path;
        // 找出当前 URI 对应的 ActionProxy 对象
        Map<Action.Method, ActionProxy> proxyMap = configure.getActionProxy(uri);
        if (java.util.Objects.isNull(proxyMap)) {
            LOGGER.info("Not Found %s" + uri);
            response.sendError(SC_NOT_FOUND);
            return;
        }
        // 根据请求方法获取具体映射,不支持时返回405错误
        final ActionProxy proxy = proxyMap.get(method);
        if (java.util.Objects.isNull(proxy)) {
            response.sendError(SC_METHOD_NOT_ALLOWED);
            return;
        }
        // 根据请求方法获取具体映射,不支持时返回405错误
        if (of(proxy.getSupportMethod()).noneMatch(m -> m == method)) {
            response.sendError(SC_METHOD_NOT_ALLOWED);
            return;
        }
        // 获取 Ant 类型的URL中的参数信息
        Map<String, String> uriParam = new HashMap<>();
        if (matcher.isPattern((path = proxy.getViewPath()))) {
            uriParam.putAll(matcher.extractVariables(path, uri));
        }
        // 获取数据模型实例并验证是否为空
        final IModel<?> model = proxy.getModel(configure.getView());
        try {
            // 获取拦截器列表的迭代器对象
            Iterator<ActionInterceptor> iterator = proxy.getInterceptors().iterator();
            // 获取控制类的实例对象
            Object instance = injector.getInstance(proxy.getClazz());
            // 创建 ActionInvocation 对象
            final ActionInvocation action = new ActionInvocation() {

                @Nonnull
                @Override
                public final Method getMethod() {
                    return proxy.getMethod();
                }

                @Nonnull
                @Override
                public final Class<?> getClazz() {
                    return proxy.getClazz();
                }

                @Nonnull
                @Override
                public final Object getInstance() {
                    return instance;
                }

                @Nonnull
                @Override
                public final List<ActionInterceptor> getInterceptors() {
                    return proxy.getInterceptors();
                }

                @Override
                public final String getViewPath() {
                    return proxy.getViewPath();
                }

                @Nonnull
                @Override
                public final IModel<?> getModel() {
                    return model;
                }

                @Nonnull
                @Override
                public final HttpServletRequest getRequest() {
                    return request;
                }

                @Nonnull
                @Override
                public final HttpServletResponse getResponse() {
                    return response;
                }

                @Override
                public final HttpSession getSession() {
                    return request.getSession();
                }

                @Override
                public final ServletContext getServletContext() {
                    return request.getServletContext();
                }

                @Nonnull
                @Override
                public final Map<String, String> getUriParameters() {
                    return uriParam;
                }

                @Nonnull
                @Override
                public MiniParameter[] getParameters() {
                    return proxy.getParameters();
                }

                @Nonnull
                @Override
                public synchronized final Object[] getParameterValues() {
                    return Stream.of(proxy.getParameterHandlers()).map(param -> {
                        return param.getValue(this); //
                    }).toArray(Object[]::new);
                }

                @Override
                public synchronized final Object invoke() throws Throwable {
                    try {
                        if (iterator.hasNext()) iterator.next().invoke(this);
                        return getMethod().invoke(instance, getParameterValues());
                    } catch (InvocationTargetException ex) {
                        throw ex.getTargetException();
                    }
                }
            };

            // 调用目标方法
            action.invoke();
        } catch (Throwable exception) {
            Throwable e = exception, el = null;
            for (ExceptionHandler<?> handler : configure.getExceptionHandlerList()) {
                e = getCauseByThrowable(e, handler.getExceptionClass());
                ofNullable(e).ifPresent(ex -> handler.handler( //
                        model, ex, request, response));
                el = exception;
            }
            // 没有找到指定的异常处理
            if (Objects.isNull(el)) {
                model.setStatus(INTERNAL_SERVER_ERROR);
                model.setMessage("Service Error!");
                LOGGER.error(exception);
            }
        }

        try { // 返回数据
            model.onSubmit(request, response);
        } catch (Exception | Error exception) {
            LOGGER.error(exception);
        }
    }

    private <T> T getCauseByThrowable(Throwable exception, Class<T> exType) {
        if (exception != null && exType.isAssignableFrom(exception.getClass())) {
            return getCauseByThrowable(exception.getCause(), exType);
        }
        return null;
    }

    /**
     * 获取实际的 ActionProxy 对象的 Uri
     * @param request HttpServletRequest 对象
     * @return ActionProxy Uri
     */
    protected abstract String getActionProxyUri(HttpServletRequest request);
}
