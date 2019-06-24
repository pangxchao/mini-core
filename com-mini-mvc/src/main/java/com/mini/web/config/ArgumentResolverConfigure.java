package com.mini.web.config;

import com.mini.dao.Paging;
import com.mini.web.argument.*;
import com.mini.web.model.*;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class ArgumentResolverConfigure implements Serializable {
    private static final long serialVersionUID = 1348664728363424911L;
    private final Map<Class<?>, ArgumentResolver> argumentResolvers = new ConcurrentHashMap<>();


    public ArgumentResolverConfigure() {
        // 基础类型数据
        addResolver(String.class, new ArgumentResolverString());
        addResolver(Long.class, new ArgumentResolverLong());
        addResolver(long.class, new ArgumentResolverLongVal());
        addResolver(Integer.class, new ArgumentResolverInt());
        addResolver(int.class, new ArgumentResolverIntVal());
        addResolver(Short.class, new ArgumentResolverShort());
        addResolver(short.class, new ArgumentResolverShortVal());
        addResolver(Byte.class, new ArgumentResolverByte());
        addResolver(byte.class, new ArgumentResolverByteVal());
        addResolver(Double.class, new ArgumentResolverDouble());
        addResolver(double.class, new ArgumentResolverDoubleVal());
        addResolver(Float.class, new ArgumentResolverFloat());
        addResolver(float.class, new ArgumentResolverFloatVal());
        addResolver(Boolean.class, new ArgumentResolverBoolean());
        addResolver(boolean.class, new ArgumentResolverBooleanVal());
        addResolver(Character.class, new ArgumentResolverChar());
        addResolver(char.class, new ArgumentResolverCharVal());

        // 基础类型数组
        addResolver(String[].class, new ArgumentResolverArrayString());
        addResolver(Long[].class, new ArgumentResolverArrayLong());
        addResolver(long[].class, new ArgumentResolverArrayLongVal());
        addResolver(Integer[].class, new ArgumentResolverArrayInt());
        addResolver(int[].class, new ArgumentResolverArrayIntVal());
        addResolver(Short[].class, new ArgumentResolverArrayShort());
        addResolver(short[].class, new ArgumentResolverArrayShortVal());
        addResolver(Byte[].class, new ArgumentResolverArrayByte());
        addResolver(byte[].class, new ArgumentResolverArrayByteVal());
        addResolver(Double[].class, new ArgumentResolverArrayDouble());
        addResolver(double[].class, new ArgumentResolverArrayDoubleVal());
        addResolver(Float[].class, new ArgumentResolverArrayFloat());
        addResolver(float[].class, new ArgumentResolverArrayFloatVal());
        addResolver(Boolean[].class, new ArgumentResolverArrayBoolean());
        addResolver(boolean[].class, new ArgumentResolverArrayBooleanVal());
        addResolver(Character[].class, new ArgumentResolverArrayChar());
        addResolver(char[].class, new ArgumentResolverArrayCharVal());

        // 文件类型
        addResolver(Part.class, new ArgumentResolverPart());
        addResolver(Part[].class, new ArgumentResolverPartArray());

        // 日期时间类型
        addResolver(Date.class, new ArgumentResolverDateTime());
        addResolver(java.sql.Date.class, new ArgumentResolverDate());
        addResolver(java.sql.Time.class, new ArgumentResolverTime());
        addResolver(java.sql.Timestamp.class, new ArgumentResolverDateTime());

        // Web上下文相关类型
        addResolver(ServletContext.class, new ArgumentResolverServletContext());
        addResolver(HttpServletResponse.class, new ArgumentResolverResponse());
        addResolver(HttpServletRequest.class, new ArgumentResolverRequest());
        addResolver(ServletResponse.class, new ArgumentResolverResponse());
        addResolver(ServletRequest.class, new ArgumentResolverRequest());
        addResolver(HttpSession.class, new ArgumentResolverSession());

        // Model 类型
        addResolver(IModel.class, new ArgumentResolverModel());
        addResolver(ModelPage.class, new ArgumentResolverModel());
        addResolver(ModelJsonMap.class, new ArgumentResolverModel());
        addResolver(ModelJsonList.class, new ArgumentResolverModel());
        addResolver(ModelStream.class, new ArgumentResolverModel());

        // 其它类型
        addResolver(Paging.class, new ArgumentResolverPaging());
        addResolver(StringBuilder.class, new ArgumentResolverBody());
    }

    /**
     * 添加一个监听器
     * @param resolver 参数解析器
     * @return {@Code this}
     */
    public synchronized ArgumentResolverConfigure addResolver(Class<?> clazz, ArgumentResolver resolver) {
        argumentResolvers.putIfAbsent(clazz, resolver);
        return this;
    }

    /**
     * Gets the value of resolveMap.
     * @return The value of resolveMap
     */
    public Map<Class<?>, ArgumentResolver> getArgumentResolvers() {
        return argumentResolvers;
    }

}
