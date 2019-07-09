
package com.mini.web.servlet;

import com.mini.logger.Logger;
import com.mini.util.ArraysUtil;
import com.mini.util.Function;
import com.mini.util.StringUtil;
import com.mini.util.reflect.MiniParameter;
import com.mini.validate.ValidateException;
import com.mini.web.annotation.Action;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.config.ActionInvocationProxyConfigure;
import com.mini.web.config.ArgumentResolverConfigure;
import com.mini.web.config.ViewConfigure;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.IModel;
import com.mini.web.model.factory.ModelFactory;
import com.mini.web.view.IView;
import org.springframework.context.ApplicationContext;

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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.logger.LoggerFactory.getLogger;
import static com.mini.util.ArraysUtil.any;
import static com.mini.util.StringUtil.isEmpty;
import static com.mini.web.model.IModel.MODEL_KEY;
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

    private final Map<String, ModelFactory<?>> modelFactoryMap = new ConcurrentHashMap<>();
    private final Map<String, ArgumentResolver> resolverMap = new ConcurrentHashMap<>();
    private ActionInvocationProxyConfigure actionInvocationProxyConfigure;
    private ArgumentResolverConfigure argumentResolverConfigure;
    private ViewConfigure viewConfigure;
    private ApplicationContext context;
    private IView view;

    /**
     * 自动注入 ActionInvocationProxyConfigure 配置信息
     * @param actionInvocationProxyConfigure 配置信息
     */
    @Inject
    public final void setActionInvocationProxyConfigure(ActionInvocationProxyConfigure actionInvocationProxyConfigure) {
        this.actionInvocationProxyConfigure = actionInvocationProxyConfigure;
    }

    /**
     * 自动注入 ArgumentResolverConfigure 配置信息
     * @param argumentResolverConfigure 配置信息
     */
    @Inject
    public final void setArgumentResolverConfigure(ArgumentResolverConfigure argumentResolverConfigure) {
        this.argumentResolverConfigure = argumentResolverConfigure;
    }

    /**
     * 自动注入 ViewConfigure 配置信息
     * @param viewConfigure 配置信息
     */
    @Inject
    public final void setViewConfigure(ViewConfigure viewConfigure) {
        this.viewConfigure = viewConfigure;
    }

    /**
     * 自动注入 ApplicationContext 上下文环境
     * @param context 上下文环境
     */
    @Inject
    public final void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 根据URI获取 ActionInvocationProxy 对象
     * @param uri  URI
     * @param func 回调
     * @return ActionInvocationProxy 对象
     */
    protected final ActionInvocationProxy getActionInvocationProxy(String uri, Function.F2<String, String> func) {
        return actionInvocationProxyConfigure.getInvocationProxy(uri, func);
    }

    /**
     * 根据URI获取 ActionInvocationProxy 对象
     * @param uri URI
     * @return ActionInvocationProxy 对象
     */
    protected final ActionInvocationProxy getActionInvocationProxy(String uri) {
        return actionInvocationProxyConfigure.getInvocationProxy(uri);
    }

    /**
     * 根据参数类型，获取参数解析器
     * @param paramType 参数类型
     * @return 数解析器
     */
    protected final ArgumentResolver getArgumentResolver(Class<?> paramType) {
        Class<?> key = argumentResolverConfigure.getArgumentResolver(paramType);
        return key != null ? resolverMap.computeIfAbsent(key.getName(), name -> {
            return context.getBean(name, ArgumentResolver.class); //
        }) : null;
    }

    /**
     * 根据 Model Class 获取 ModelFactory 对象
     * @param modelClass Model Class
     * @return ModelFactory 对象
     */
    protected final ModelFactory<?> getModelFactory(Class<?> modelClass) {
        return modelFactoryMap.computeIfAbsent(modelClass.getName(), name -> {
            return context.getBean(name, ModelFactory.class); //
        });
    }

    /**
     * 获取依赖注入上下文环境
     * @return 上下文环境
     */
    protected final ApplicationContext getContext() {
        return context;
    }

    /**
     * 获取视图实现类
     * @return 视图实现类
     */
    protected final com.mini.web.view.IView getView() {
        return Optional.ofNullable(view).orElseGet(() -> {
            String name = viewConfigure.getClassName();
            view = context.getBean(name, IView.class);
            return view;
        });
    }

    /**
     * 获取 RequestURI
     * @param request HttpServletRequest
     * @return RequestURI
     */
    protected final String getRequestURIReplace(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String path = request.getContextPath();
        if (isEmpty(uri) || isEmpty(path)) return uri;
        return StringUtil.replaceFirst(uri, path, "");
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doService(Action.Method.GET, getRequestURIReplace(request), request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doService(Action.Method.POST, getRequestURIReplace(request), request, response);
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
        // 验证注入的 ActionInvocationProxyConfigure 对象是否为空，如果为空，返回 500 错误
        if (require(actionInvocationProxyConfigure, response, ERROR, "ActionInvocationProxyConfigure can not be null", Objects::isNull)) {
            return;
        }

        // 验证注入的 ArgumentResolverConfigure 对象是否为空，如果为空，返回 500 错误
        if (require(argumentResolverConfigure, response, ERROR, "ArgumentResolverConfigure can not be null", Objects::isNull)) {
            return;
        }

        // 验证注入的 ModelFactoryConfigure 对象是否为空，如果为空，返回 500 错误
        if (require(modelFactoryMap, response, ERROR, "ModelFactoryMap can not be null", Objects::isNull)) {
            return;
        }

        // 验证注入的 ViewConfigure 对象是否为空，如果为空，返回 500 错误
        if (require(viewConfigure, response, ERROR, "ViewConfigure can not be null", Objects::isNull)) {
            return;
        }

        // 验证注入的 ApplicationContext 对象是否为空，如果为空，返回 500 错误
        if (require(context, response, ERROR, "ApplicationContext can not be null.", Objects::nonNull)) {
            return;
        }

        // 验证 RequestURI 是否为空，如果为空，返回 500 错误
        if (require(uri, response, ERROR, "Uri can not be null.", v -> !isEmpty(v))) {
            return;
        }

        // 获取并验证 Action 调用对象，如果该对象为空时，返回 404 错误
        final ActionInvocationProxy proxy = getInvocationProxy(uri, request);
        if (require(proxy, response, NOT_FOUND, "ActionInvocationProxy can not be null.", Objects::nonNull)) {
            return;
        }

        // 获取并验证请求方法是否支持，如果不支持，返回 500 错误
        final Action.Method[] methods = proxy.getSupportMethod();
        if (require(methods, response, ERROR, "No support method: " + method, v -> any(v, m -> m == method))) {
            return;
        }

        // 获取数据模型工厂并验证，如果该工厂为空，返回 500 错误
        final ModelFactory<?> modelFactory = getModelFactory(proxy.getModelClass());
        if (require(modelFactory, response, ERROR, "Model Factory can not be null.", Objects::nonNull)) {
            return;
        }

        // 获取数据模型实例并验证，如果该实例为空，返回 500 错误
        final IModel<?> model = modelFactory.getModel(getView(), proxy.getViewPath());
        if (require(model, response, ERROR, "IModel can not be null.", v -> {
            request.setAttribute(MODEL_KEY, model);
            return model != null;
        })) return;

        // 创建 ActionInvocation 对象
        ActionInvocation action = new ActionInvocation() {
            private Iterator<ActionInterceptor> iterator;

            @Nonnull
            @Override
            public Method getMethod() {
                return proxy.getMethod();
            }

            @Nonnull
            @Override
            public Class<?> getClazz() {
                return proxy.getClazz();
            }

            @Nonnull
            @Override
            public Object getInstance() {
                return proxy.getInstance();
            }

            @Nonnull
            @Override
            public Class<? extends IModel<?>> getModelClass() {
                return proxy.getModelClass();
            }

            @Nonnull
            @Override
            public Action.Method[] getSupportMethod() {
                return methods;
            }

            @Nonnull
            @Override
            public List<ActionInterceptor> getInterceptors() {
                return proxy.getInterceptors();
            }

            @Nonnull
            private Iterator<ActionInterceptor> getIterator() {
                return ofNullable(iterator).orElseGet(() -> {
                    iterator = getInterceptors().iterator();
                    return iterator;
                });
            }

            @Nonnull
            @Override
            public String getUrl() {
                return uri;
            }

            @Override
            public String getViewPath() {
                return proxy.getViewPath();
            }

            @Nonnull
            @Override
            public IModel<?> getModel() {
                return model;
            }

            @Override
            public HttpServletRequest getRequest() {
                return request;
            }

            @Override
            public HttpServletResponse getResponse() {
                return response;
            }

            @Override
            public HttpSession getSession() {
                return request.getSession();
            }

            @Override
            public ServletContext getServletContext() {
                return request.getServletContext();
            }

            @Nonnull
            @Override
            public MiniParameter[] getParameters() {
                return proxy.getParameters();
            }

            @Nonnull
            @Override
            public Object[] getParameterValues() {
                return ArraysUtil.stream(getParameters()).map(parameter -> {
                    ofNullable(getArgumentResolver(parameter.getType())).ifPresent(r -> {
                        try {
                            parameter.setValue(r.value(parameter.getName(), //
                                    parameter.getType(), request, response)); //
                        } catch (Exception | Error e) {
                            LOGGER.error("Argument Error!", e);
                        }
                    });
                    return parameter.getValue();
                }).toArray();
            }

            @Override
            public final Object invoke() throws Throwable {
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

        try {
            // 调用目标方法
            action.invoke();
        } catch (Throwable e) {
            errorHandler(e, response);
            return;
        }
        // 这里加入错误码处理
        try {
            // 返回数据
            model.submit(request, response);
        } catch (Exception | Error e) {
            errorHandler(e, response);
        }
    }

    /**
     * 获取 ActionInvocationProxy 对象
     * @param uri     RequestURI
     * @param request HttpServletRequest
     * @return ActionInvocationProxy 对象
     */
    protected abstract ActionInvocationProxy getInvocationProxy(String uri, HttpServletRequest request) throws ServletException, IOException;

    /**
     * 验证表达式，并返回错误信息
     * @param instance 验证对象
     * @param response HttpServletResponse
     * @param code     错误码
     * @param message  错误消息
     * @param function 验证回调
     * @return true-验证未通过
     */
    private <T> boolean require(T instance, HttpServletResponse response, int code, String message, Function.FR1<Boolean, T> function) throws IOException {
        if (function.apply(instance)) return false;
        response.sendError(code, message);
        LOGGER.debug(message);
        return true;
    }

    /**
     * 异常处理方法
     * @param e 异常信息
     */
    private void errorHandler(Throwable e, HttpServletResponse response) throws IOException {
        LOGGER.error(e.getMessage(), e);
        if (response.isCommitted()) return;
        // 参数验证错误处理
        if (ValidateException.class.isAssignableFrom(e.getClass())) {
            ValidateException ex = (ValidateException) e;
            response.sendError(ex.getStatus(), ex.getMessage());
            return;
        }
        // 其它异常信息处理
        response.sendError(ERROR, e.getMessage());
    }

}
