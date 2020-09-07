package com.mini.core.mvc.argument;

import com.mini.core.mvc.interceptor.ActionInvocation;
import com.mini.core.mvc.model.IModel;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public final class ArgumentResolverModel implements ArgumentResolver {
    @Override
    public boolean supportParameter(MethodParameter parameter) {
        return IModel.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object getValue(MethodParameter parameter, ActionInvocation invocation) {
        return invocation.getModel();
    }

    @Override
    public final int compareTo(@Nonnull ArgumentResolver o) {
        return this.hashCode() - o.hashCode();
    }
}
