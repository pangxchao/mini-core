
package com.mini.web.servlet;

import com.mini.logger.Logger;
import com.mini.util.ArraysUtil;
import com.mini.util.Function;
import com.mini.util.StringUtil;
import com.mini.util.reflect.MiniParameter;
import com.mini.validate.ValidateException;
import com.mini.web.annotation.Action;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.config.WebMvcConfigureDefault;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.IModel;
import com.mini.web.model.factory.ModelFactory;
import com.mini.web.view.IView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nonnull;
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

import static com.mini.logger.LoggerFactory.getLogger;
import static com.mini.util.StringUtil.isEmpty;
import static com.mini.web.model.IModel.MODEL_KEY;
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

    @Autowired
    private WebMvcConfigureDefault configurer;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private IView view;

    /**
     * Gets the value of configurer.
     * @return The value of configurer
     */
    public final WebMvcConfigureDefault getConfigurer() {
        return configurer;
    }

    /**
     * Gets the value of context.
     * @return The value of context
     */
    public final ApplicationContext getContext() {
        return context;
    }

    /**
     * Gets the value of view.
     * @return The value of view
     */
    public final IView getView() {
        return view;
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
        // 验证注入的 WebMvcConfigurer 对象是否为空，如果为空，返回 500 错误
        if (require(configurer, response, ERROR, "WebMvcConfigurer can be not null.", Objects::nonNull)) {
            return;
        }

        // 验证注入的 ApplicationContext 对象是否为空，如果为空，返回 500 错误
        if (require(context, response, ERROR, "ApplicationContext can be not null.", Objects::nonNull)) {
            return;
        }

        // 验证 RequestURI 是否为空，如果为空，返回 500 错误
        if (require(uri, response, ERROR, "Uri can be not null.", v -> !isEmpty(v))) {
            return;
        }

        // 获取并验证 Action 调用对象，如果该对象为空时，返回 404 错误
        final ActionInvocationProxy proxy = getInvocationProxy(uri, request);
        if (require(proxy, response, NOT_FOUND, "ActionInvocationProxy can be not null.", Objects::nonNull)) {
            return;
        }

        // 获取并验证请求方法是否支持，如果不支持，返回 500 错误
        final Action.Method[] methods = proxy.getSupportMethod();
        if (require(methods, response, ERROR, "No support method: " + method, v -> {
            return ArraysUtil.any(v, m -> m == method); //
        })) return;

        // 获取页面数据模型实现类并验证，如果该实现类为空，返回 500 错误
        final Class<? extends IModel<?>> modelClass = proxy.getModelClass();
        if (require(modelClass, response, ERROR, "Model Class can be not null.", Objects::nonNull)) {
            return;
        }

        // 获取数据模型工厂实现类并验证，如果该工厂为空，返回 500 错误
        final Class<? extends ModelFactory<?>> modelFactoryClass = configurer.getModelFactory(modelClass);
        if (require(modelFactoryClass, response, ERROR, "Model Factory Class can be not null.", Objects::nonNull)) {
            return;
        }

        // 获取数据模型工厂并验证，如果该工厂为空，返回 500 错误
        final ModelFactory<?> modelFactory = context.getBean(modelFactoryClass);
        if (require(modelFactory, response, ERROR, "Model Factory can be not null.", Objects::nonNull)) {
            return;
        }

        // 获取数据模型实例并验证，如果该实例为空，返回 500 错误
        final IModel<?> model = modelFactory.getModel(view, proxy.getViewPath());
        if (require(model, response, ERROR, "IModel can be not null.", v -> {
            request.setAttribute(MODEL_KEY, model);
            return model != null;
        })) return;

        // 获取参数解析器并验证，如果析器为空，返回 500 错误
        final Map<Class<?>, Class<? extends ArgumentResolver>> resolvers = configurer.getArgumentResolvers();
        if (require(resolvers, response, ERROR, "Argument resolver can be not null.", Objects::nonNull)) {
            return;
        }

        // 创建 ActionInvocation 对象
        ActionInvocation action = new ActionInvocation() {
            private List<ActionInterceptor> interceptors;
            private Iterator<ActionInterceptor> iterator;
            private Object instance;
            private IView view;

            @Override
            public Method getMethod() {
                return proxy.getMethod();
            }

            @Override
            public Class<?> getClazz() {
                return proxy.getClazz();
            }

            @Override
            public Object getInstance() {
                return Optional.ofNullable(instance).orElseGet(() -> //
                        instance = context.getBean(getClazz()));
            }

            @Override
            public Class<? extends IModel<?>> getModelClass() {
                return modelClass;
            }

            @Override
            public Action.Method[] getSupportMethod() {
                return methods;
            }

            @Nonnull
            @Override
            public List<ActionInterceptor> getInterceptors() {
                return Optional.ofNullable(interceptors).orElseGet(() -> {
                    List<ActionInterceptor> inters = new ArrayList<>();
                    proxy.getInterceptors().forEach(clazz -> { //
                        inters.add(context.getBean(clazz));
                    });
                    return interceptors = inters;
                });
            }

            @Nonnull
            private Iterator<ActionInterceptor> getIterator() {
                return iterator = Optional.ofNullable(iterator).orElseGet(() -> {
                    return getInterceptors().iterator(); //
                });
            }

            @Override
            public String getUrl() {
                return uri;
            }

            @Override
            public String getViewPath() {
                return proxy.getViewPath();
            }

            @Override
            public IModel<?> getModel() {
                return model;
            }

            @Override
            public IView getView() {
                return view = Optional.ofNullable(view).orElseGet(() -> {
                    return context.getBean(configurer.getViewClass()); //
                });
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
                    //Optional.ofNullable(resolvers.get(parameter.getType())).map( //
                    //        clazz -> injector.getInstance(clazz)).ifPresent(r -> {
                    //    try {
                    //        parameter.setValue(r.value(  //
                    //                parameter.getName(), //
                    //                parameter.getType(), //
                    //                request, response)); //
                    //    } catch (Exception | Error e) {
                    //        LOGGER.error("Argument Error!", e);
                    //    }
                    //});
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
