package com.mini.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.interceptor.ActionInvocation;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Named
@Singleton
public final class ArgumentResolverHttpResponse implements ArgumentResolver {
    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return HttpServletResponse.class == parameter.getType() || //
                ServletResponse.class == parameter.getType();
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        return invocation.getResponse();
    }
}
