package com.mini.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.interceptor.ActionInvocation;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@Named
@Singleton
public final class ArgumentResolverHttpRequest implements ArgumentResolver {
    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return HttpServletRequest.class == parameter.getType() || //
                ServletRequest.class == parameter.getType();
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        return invocation.getRequest();
    }
}
