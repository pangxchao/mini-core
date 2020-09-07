package com.mini.core.mvc.argument.param;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.argument.ArgumentResolverAbstract;
import com.mini.core.mvc.interceptor.ActionInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static java.util.Optional.ofNullable;

@Component
public final class ArgumentResolverPartArrayRequestParam extends ArgumentResolverAbstract {
    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean supportParameter(MethodParameter parameter) {
        Param param = parameter.getParameterAnnotation(Param.class);
        if (param == null || param.value() != Param.Value.PARAM) {
            return false;
        }
        return Part[].class == parameter.getParameterType();
    }

    @Override
    public Object getValue(MethodParameter parameter, ActionInvocation invocation) {
        try {
            String name = this.getParameterName(parameter);
            return ofNullable(invocation.getRequest().getParts())
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(Objects::nonNull)
                    .filter(p -> name.equals(p.getName()))
                    .filter(p -> p.getSize() > 0)
                    .toArray(Part[]::new);
        } catch (IOException | ServletException ignored) {
        }
        return null;
    }

}
