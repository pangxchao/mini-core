package com.mini.web.interceptor;

import com.mini.util.reflect.MiniParameter;
import com.mini.web.annotation.Action;
import com.mini.web.model.IModel;
import com.mini.web.util.WebUtil;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

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
     * 获取页面数据模型实现类
     * @return 页面数据模型实现类
     */
    @Nonnull
    Class<? extends IModel<?>> getModelClass();

    /**
     * 获取控制器支持的方法
     * @return 控制器支持的方法
     */
    @Nonnull
    Action.Method[] getSupportMethod();

    /**
     * 获取所有拦截器对象
     * @return 拦截器对象
     */
    @Nonnull
    List<ActionInterceptor> getInterceptors();


    /**
     * 获取 Action Url 访问路径
     * @return Action Url 访问路径
     */
    @Nonnull
    String getUrl();


    /**
     * 获取 Action 视图路径
     * @return Action 视图路径
     */
    String getViewPath();


    /**
     * 获取数据模型
     * @return 数据模型
     */
    IModel<?> getModel();

    /**
     * 获取 HttpServletRequest
     * @return HttpServletRequest
     */
    HttpServletRequest getRequest();


    /**
     * 获取 HttpServletResponse
     * @return HttpServletResponse
     */
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
    MiniParameter[] getParameters();

    /**
     * 获取所有参数值
     * @return 所有参数值
     */
    @Nonnull
    Object[] getParameterValues() throws Throwable;

    /**
     * 请求转发
     * @param viewPath 转发路径
     */
    default void forward(String viewPath) throws ServletException, IOException {
        WebUtil.forward(viewPath, getRequest(), getResponse());
    }

    /**
     * 重定向处理
     * @param viewPath 重定向路径
     */
    default void sendRedirect(String viewPath) throws IOException {
        WebUtil.sendRedirect(viewPath, getRequest(), getResponse());
    }

    /**
     * 调用目标方法或者下一个拦截器
     * @return 目标方法返回值
     */
    Object invoke() throws Throwable;


}
