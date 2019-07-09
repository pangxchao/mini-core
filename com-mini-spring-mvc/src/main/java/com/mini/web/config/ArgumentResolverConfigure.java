package com.mini.web.config;

import com.mini.jdbc.util.Paging;
import com.mini.web.argument.*;
import com.mini.web.model.*;
import org.springframework.stereotype.Component;

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

@Component
public final class ArgumentResolverConfigure implements Serializable {
    private static final long serialVersionUID = 1348664728363424911L;
    private final Map<Class<?>, Class<? extends ArgumentResolver>> argumentResolvers = new ConcurrentHashMap<>();


    public ArgumentResolverConfigure() {
        // 基础类型数据
        addResolver(String.class, ArgumentResolverString.class);
        addResolver(Long.class, ArgumentResolverLong.class);
        addResolver(long.class, ArgumentResolverLongVal.class);
        addResolver(Integer.class, ArgumentResolverInt.class);
        addResolver(int.class, ArgumentResolverIntVal.class);
        addResolver(Short.class, ArgumentResolverShort.class);
        addResolver(short.class, ArgumentResolverShortVal.class);
        addResolver(Byte.class, ArgumentResolverByte.class);
        addResolver(byte.class, ArgumentResolverByteVal.class);
        addResolver(Double.class, ArgumentResolverDouble.class);
        addResolver(double.class, ArgumentResolverDoubleVal.class);
        addResolver(Float.class, ArgumentResolverFloat.class);
        addResolver(float.class, ArgumentResolverFloatVal.class);
        addResolver(Boolean.class, ArgumentResolverBoolean.class);
        addResolver(boolean.class, ArgumentResolverBooleanVal.class);
        addResolver(Character.class, ArgumentResolverChar.class);
        addResolver(char.class, ArgumentResolverCharVal.class);

        // 基础类型数组
        addResolver(String[].class, ArgumentResolverArrayString.class);
        addResolver(Long[].class, ArgumentResolverArrayLong.class);
        addResolver(long[].class, ArgumentResolverArrayLongVal.class);
        addResolver(Integer[].class, ArgumentResolverArrayInt.class);
        addResolver(int[].class, ArgumentResolverArrayIntVal.class);
        addResolver(Short[].class, ArgumentResolverArrayShort.class);
        addResolver(short[].class, ArgumentResolverArrayShortVal.class);
        addResolver(Byte[].class, ArgumentResolverArrayByte.class);
        addResolver(byte[].class, ArgumentResolverArrayByteVal.class);
        addResolver(Double[].class, ArgumentResolverArrayDouble.class);
        addResolver(double[].class, ArgumentResolverArrayDoubleVal.class);
        addResolver(Float[].class, ArgumentResolverArrayFloat.class);
        addResolver(float[].class, ArgumentResolverArrayFloatVal.class);
        addResolver(Boolean[].class, ArgumentResolverArrayBoolean.class);
        addResolver(boolean[].class, ArgumentResolverArrayBooleanVal.class);
        addResolver(Character[].class, ArgumentResolverArrayChar.class);
        addResolver(char[].class, ArgumentResolverArrayCharVal.class);

        // 文件类型
        addResolver(Part.class, ArgumentResolverPart.class);
        addResolver(Part[].class, ArgumentResolverPartArray.class);

        // 日期时间类型
        addResolver(Date.class, ArgumentResolverDateTime.class);
        addResolver(java.sql.Date.class, ArgumentResolverDate.class);
        addResolver(java.sql.Time.class, ArgumentResolverTime.class);
        addResolver(java.sql.Timestamp.class, ArgumentResolverDateTime.class);

        // Web上下文相关类型
        addResolver(ServletContext.class, ArgumentResolverServletContext.class);
        addResolver(HttpServletResponse.class, ArgumentResolverResponse.class);
        addResolver(HttpServletRequest.class, ArgumentResolverRequest.class);
        addResolver(ServletResponse.class, ArgumentResolverResponse.class);
        addResolver(ServletRequest.class, ArgumentResolverRequest.class);
        addResolver(HttpSession.class, ArgumentResolverSession.class);

        // Model 类型
        addResolver(IModel.class, ArgumentResolverModel.class);
        addResolver(ModelPage.class, ArgumentResolverModel.class);
        addResolver(ModelJsonMap.class, ArgumentResolverModel.class);
        addResolver(ModelJsonList.class, ArgumentResolverModel.class);
        addResolver(ModelStream.class, ArgumentResolverModel.class);

        // 其它类型
        addResolver(Paging.class, ArgumentResolverPaging.class);
        addResolver(StringBuilder.class, ArgumentResolverBody.class);
    }

    /**
     * 添加一个参数解析器
     * @param resolver 参数解析器
     * @return {@Code this}
     */
    public  ArgumentResolverConfigure addResolver(Class<?> clazz, Class<? extends ArgumentResolver> resolver) {
        argumentResolvers.putIfAbsent(clazz, resolver);
        return this;
    }

    /**
     * Gets the value of argumentResolvers.
     * @return The value of argumentResolvers
     */
    public Map<Class<?>, Class<? extends ArgumentResolver>> getArgumentResolvers() {
        return argumentResolvers;
    }

}
