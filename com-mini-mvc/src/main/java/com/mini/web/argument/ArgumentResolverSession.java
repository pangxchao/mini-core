package com.mini.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.util.LoginSession;

import javax.inject.Named;
import javax.inject.Singleton;

import static com.mini.web.util.LoginSession.SESSION_KEY;

@Named
@Singleton
public final class ArgumentResolverSession implements ArgumentResolver {

    @Override
    public boolean supportParameter(MiniParameter parameter) {
        return LoginSession.class.isAssignableFrom(parameter.getType());
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        return invocation.getSession().getAttribute(SESSION_KEY);
    }
}
