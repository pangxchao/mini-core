package com.mini.core.mvc.argument.defaults;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.argument.ArgumentResolverArray;
import com.mini.core.mvc.interceptor.ActionInvocation;
import com.mini.core.mvc.support.config.Configures;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

@Component
public final class ArgumentResolverArrayDefault extends ArgumentResolverArray {

    @Autowired
    public ArgumentResolverArrayDefault(@NotNull Configures configures) {
        super(configures);
    }

    @Override
    public boolean supportParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Param.class)) {
            return false;
        }
        return super.supportParameter(parameter);
    }

    @Override
    protected String[] getValue(String name, ActionInvocation invocation) {
        return invocation.getRequest().getParameterValues(name);
    }
}
