package com.mini.core.mvc.argument;

import com.mini.core.mvc.interceptor.ActionInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public final class ArgumentResolverContext implements ArgumentResolver {
    private final Map<Class<?>, Function<ActionInvocation, Object>> map = new HashMap<>() {{
        put(ServletContext.class, ActionInvocation::getServletContext);
        put(HttpServletResponse.class, ActionInvocation::getResponse);
        put(ServletResponse.class, ActionInvocation::getResponse);
        put(HttpServletRequest.class, ActionInvocation::getRequest);
        put(ServletRequest.class, ActionInvocation::getRequest);
        put(HttpSession.class, ActionInvocation::getSession);
    }};

    @Override
    public boolean supportParameter(MethodParameter parameter) {
        return map.get(parameter.getParameterType()) != null;
    }

    @Override
    public Object getValue(MethodParameter parameter, ActionInvocation invocation) {
        Function<ActionInvocation, ?> function = map.get(parameter.getParameterType());
        Objects.requireNonNull(function, "Unsupported parameter type.");
        return function.apply(invocation);
    }

    @Override
    public final int compareTo(@Nonnull ArgumentResolver o) {
        return this.hashCode() - o.hashCode();
    }
}
