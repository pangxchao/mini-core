package com.mini.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.interceptor.ActionInvocation;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;

@Named
@Singleton
public final class ArgumentResolverServletContext implements ArgumentResolver {
    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return ServletContext.class == parameter.getType();
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        return invocation.getServletContext();
    }
}
