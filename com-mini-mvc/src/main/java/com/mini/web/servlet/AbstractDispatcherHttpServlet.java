
package com.mini.web.servlet;

import com.google.inject.Injector;
import com.mini.logger.Logger;
import com.mini.validate.ValidateException;
import com.mini.web.annotation.Action;
import com.mini.web.config.Configure;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.IModel;
import com.mini.web.util.RequestParameter;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mini.logger.LoggerFactory.getLogger;
import static com.mini.util.StringUtil.isEmpty;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;


/**
 * 默认的Servlet
 * @author xchao
 */
public abstract class AbstractDispatcherHttpServlet extends HttpServlet implements Serializable {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);
    private static final long serialVersionUID = -4503404425770992595L;
    private static final int ERROR = SC_INTERNAL_SERVER_ERROR;
    private static final int NOT_FOUND = SC_NOT_FOUND;

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
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doService(Action.Method.GET, request.getServletPath(), request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doService(Action.Method.POST, request.getServletPath(), request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doService(Action.Method.POST, request.getServletPath(), request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doService(Action.Method.POST, request.getServletPath(), request, response);
    }

    /**
     * ActionInvocation 核心处理方法
     * @param method   提交数据是以哪种方法提交的
     * @param uri      方法连接相对当前项目的 URI 路径
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     */
    protected final void doService(Action.Method method, String uri, HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // 验证 RequestURI 是否为空，如果为空，返回 500 错误
        if (require(uri, response, ERROR, "Server Error! " + uri, v -> !isEmpty(v))) {
            return;
        }

        // 验证注入的 Injector 对象是否为空，如果为空，返回 500 错误
        if (require(injector, response, ERROR, "Server Error! " + uri, Objects::nonNull)) {
            return;
        }

        // 验证注入的 Configure 对象是否为空，如果为空，返回 500 错误
        if (require(configure, response, ERROR, "Server Error! " + uri, Objects::nonNull)) {
            return;
        }

        // 获取并验证 Action 调用对象，如果该对象为空时，返回 404 错误
        final ActionInvocationProxy proxy = getInvocationProxy(uri, method, request);
        if (require(proxy, response, NOT_FOUND, "Not Found Page:" + uri, Objects::nonNull)) {
            return;
        }

        // 获取数据模型实例并验证，如果该实例为空，返回 500 错误
        final IModel<?> model = proxy.getModel(configure.getView(), proxy.getViewPath(), request);
        if (require(model, response, ERROR, "Server Error:" + uri, Objects::nonNull)) {
            return;
        }

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
                return stream(getParameters()).map(p -> p.getValue( //
                        configure, request, response)).toArray();
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

        try { // 调用目标方法
            action.invoke();
        } catch (ValidateException exception) {
            String msg = exception.getMessage();
            int error = exception.getStatus();
            model.sendError(error, msg);
        } catch (Throwable exception) {
            LOGGER.error(exception);
            model.sendError(ERROR);
        }
        // 这里加入错误码处理
        try { // 返回数据
            model.submit(request, response);
        } catch (Exception | Error exception) {
            LOGGER.error(exception);
        }
    }

    /**
     * 获取 ActionInvocationProxy 对象
     * @param uri     RequestURI
     * @param method  Action.Method
     * @param request HttpServletRequest
     * @return ActionInvocationProxy 对象
     */
    protected abstract ActionInvocationProxy getInvocationProxy(String uri, Action.Method method, HttpServletRequest request) throws ServletException,
            IOException;

    /**
     * 验证表达式，并返回错误信息
     * @param instance 验证对象
     * @param response HttpServletResponse
     * @param code     错误码
     * @param message  错误消息
     * @param function 验证回调
     * @return true-验证未通过
     */
    private <T> boolean require(T instance, HttpServletResponse response, int code, String message, Predicate<T> function) throws IOException {
        if (function.test(instance)) return false;
        response.sendError(code, message);
        LOGGER.error(message);
        return true;
    }
}
