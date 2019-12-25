package com.mini.core.web.argument;

import com.mini.core.util.Assert;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Singleton
public class ArgumentResolverContext implements ArgumentResolver {
    private final Map<Class<?>, Function<ActionInvocation, Object>> map = new HashMap<>() {{
        // ServletContext.class 处理
        put(ServletContext.class, ActionInvocation::getServletContext);
        // HttpServletResponse.class 处理
        put(HttpServletResponse.class, ActionInvocation::getResponse);
        // ServletResponse.class 处理
        put(ServletResponse.class, ActionInvocation::getResponse);
        // HttpServletRequest.class 处理
        put(HttpServletRequest.class, ActionInvocation::getRequest);
        // ServletRequest.class 处理
        put(ServletRequest.class, ActionInvocation::getRequest);
        // HttpSession.class 处理
        put(HttpSession.class, ActionInvocation::getSession);
    }};

    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return map.get(parameter.getType()) != null;
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        Function<ActionInvocation, ?> function = map.get(parameter.getType());
        Assert.notNull(function, "Unsupported parameter type.");
        // 获取参数名称和参值，并处理
        return function.apply(invocation);
    }
}
