package com.mini.core.mvc.argument.defaults;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.argument.ArgumentResolverAbstract;
import com.mini.core.mvc.interceptor.ActionInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;

import static java.util.Optional.ofNullable;

@Component
public final class ArgumentResolverPartDefault extends ArgumentResolverAbstract {

    @Override
    public boolean supportParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Param.class)) {
            return false;
        }
        return Part.class == parameter.getParameterType();
    }

    @Override
    public Object getValue(MethodParameter parameter, ActionInvocation invocation) {
        try {
            String name = this.getParameterName(parameter);
            return ofNullable(invocation.getRequest().getPart(name))
                    .filter(part -> part.getSize() > 0)
                    .orElse(null);
        } catch (IOException | ServletException ignored) {
        }
        return null;
    }
}
