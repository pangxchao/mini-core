package com.mini.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.interceptor.ActionInvocation;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpSession;

@Named
@Singleton
public final class ArgumentResolverHttpSession implements ArgumentResolver {

    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return parameter.getType() == HttpSession.class;
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        return invocation.getRequest().getSession();
    }
}
