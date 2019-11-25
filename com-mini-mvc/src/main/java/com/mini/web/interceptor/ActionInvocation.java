package com.mini.web.interceptor;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.model.IModel;
import com.mini.web.util.WebSession;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.mini.web.util.WebSession.SESSION_KEY;

public interface ActionInvocation {

    /**
     * 获取目标方法对象
     * @return 目标方法对象
     */
    @Nonnull
    Method getMethod();

    /**
     * 获取Controller类Class对象
     * @return Class对象
     */
    @Nonnull
    Class<?> getClazz();

    /**
     * 获取Controller类实例
     * @return Controller类实例
     */
    @Nonnull
    Object getInstance();

    /**
     * 获取所有拦截器对象
     * @return 拦截器对象
     */
    @Nonnull
    List<ActionInterceptor> getInterceptors();

    /**
     * 获取 Action 视图路径
     * @return Action 视图路径
     */
    String getViewPath();


    /**
     * 获取数据模型
     * @return 数据模型
     */
    @Nonnull
    IModel<?> getModel();

    /**
     * 获取 HttpServletRequest
     * @return HttpServletRequest
     */
    @Nonnull
    HttpServletRequest getRequest();


    /**
     * 获取 HttpServletResponse
     * @return HttpServletResponse
     */
    @Nonnull
    HttpServletResponse getResponse();


    /**
     * 获取 HttpSession
     * @return HttpSession
     */
    HttpSession getSession();

    /**
     * 获取 ServletContext
     * @return ServletContext
     */

    ServletContext getServletContext();

    /**
     * 获取目标方法的所有参数信息
     * @return 所有参数信息
     */
    @Nonnull
    Map<String, String> getUriParameters();

    /**
     * 获取方法参数信息
     * @return 方法参数信息
     */
    @Nonnull
    MiniParameter[] getParameters();

    /**
     * 获取所有参数值
     * @return 所有参数值
     */
    @Nonnull
    Object[] getParameterValues();

    /**
     * 调用目标方法或者下一个拦截器
     * @return 目标方法返回值
     */
    Object invoke() throws Throwable;

    /**
     * 获取登录 Session
     * @param clazz 实现类型
     * @return 登录 Session
     */
    default <T extends WebSession> T getLoginSession(Class<T> clazz) {
        return clazz.cast(getSession().getAttribute(SESSION_KEY));
    }

    /**
     * 设置登录Session
     * @param session 登录Session
     */
    default <T extends WebSession> void setLoginSession(T session) {
        getSession().setAttribute(SESSION_KEY, session);
    }
}
