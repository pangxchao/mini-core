package com.mini.core.mvc.argument.defaults;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.argument.ArgumentResolverBasic;
import com.mini.core.mvc.interceptor.ActionInvocation;
import com.mini.core.mvc.support.config.Configures;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;


@Component
public final class ArgumentResolverBasicDefault extends ArgumentResolverBasic {

    @Autowired
    public ArgumentResolverBasicDefault(@NotNull Configures configures) {
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
    protected String getValue(String name, ActionInvocation invocation) {
        String value = invocation.getRequest().getParameter(name);
        if (value != null && !value.isBlank()) return value;
        return invocation.getUriParameters().get(name);
    }
}
