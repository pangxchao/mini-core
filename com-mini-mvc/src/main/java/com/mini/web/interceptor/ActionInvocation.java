package com.mini.web.interceptor;

import com.mini.web.model.IModel;
import com.mini.web.util.RequestParameter;
import com.mini.web.util.WebUtil;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    RequestParameter[] getParameters();

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
     * 获取 HttpServlet  参数
     * @param name 参数名称
     * @return 获取结果
     */
    default String getParameter(String name) throws Error {
        return WebUtil.getParameter(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 long 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default long getParameterToLongVal(String name) throws Error {
        return WebUtil.getParameterToLongVal(getRequest(), name);

    }

    /**
     * 获取 HttpServletRequest 参数转换成 Long 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default Long getParameterToLong(String name) throws Error {
        return WebUtil.getParameterToLong(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 int 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default int getParameterToIntVal(String name) throws Error {
        return WebUtil.getParameterToIntVal(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Integer 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default Integer getParameterToInt(String name) throws Error {
        return WebUtil.getParameterToInt(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 short 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default short getParameterToShortVal(String name) throws Error {
        return WebUtil.getParameterToShortVal(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Short 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default Short getParameterToShort(String name) throws Error {
        return WebUtil.getParameterToShort(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 byte 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default byte getParameterToByteVal(String name) throws Error {
        return WebUtil.getParameterToByteVal(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Byte 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default Byte getParameterToByte(String name) throws Error {
        return WebUtil.getParameterToByte(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 double 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default double getParameterToDoubleVal(String name) throws Error {
        return WebUtil.getParameterToDoubleVal(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Double 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default Double getParameterToDouble(String name) throws Error {
        return WebUtil.getParameterToDouble(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 float 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default float getParameterToFloatVal(String name) throws Error {
        return WebUtil.getParameterToFloatVal(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Float 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default Float getParameterToFloat(String name) throws Error {
        return WebUtil.getParameterToFloat(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 boolean 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default boolean getParameterToBoolVal(String name) throws Error {
        return WebUtil.getParameterToBoolVal(getRequest(), name);
    }

    /**
     * 获取 HttpServletRequest 参数转换成 Boolean 类型
     * @param name 参数名称
     * @return 获取结果
     */
    default Boolean getParameterToBool(String name) throws Error {
        return WebUtil.getParameterToBool(getRequest(), name);
    }

    /**
     * 获取sessionId
     * @return session ID
     */
    default String getSessionId() throws Error {
        return WebUtil.getSessionId(getSession());
    }

    /**
     * 获取 HttpSession 的属性
     * @param name  属性名称
     * @param clazz 返回类型
     * @return 返回对象
     */
    default <T> T getSessionAttr(String name, Class<T> clazz) throws Error {
        return WebUtil.getAttribute(getSession(), name, clazz);
    }

    /**
     * 设置 HttpSession 的属性
     * @param name  属性名称
     * @param value 属性值
     */
    default void setSessionAttr(String name, Object value) {
        WebUtil.setAttribute(getSession(), name, value);
    }

    /**
     * 获取 HttpServletRequest 的属性
     * @param name  属性名称
     * @param clazz 返回类型
     * @return 返回对象
     */
    default <T> T getRequestAttr(String name, Class<T> clazz) throws Error {
        return WebUtil.getAttribute(getRequest(), name, clazz);
    }

    /**
     * 设置 HttpServletRequest 的属性
     * @param name  属性名称
     * @param value 属性值
     */
    default void setRequestAttr(String name, Object value) {
        WebUtil.setAttribute(getRequest(), name, value);
    }
}
