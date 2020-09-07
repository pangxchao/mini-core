package com.mini.core.mvc.argument.defaults;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.argument.ArgumentResolverAbstract;
import com.mini.core.mvc.interceptor.ActionInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public final class ArgumentResolverMapDefault extends ArgumentResolverAbstract {

    @Override
    @SuppressWarnings("DuplicatedCode")
    public boolean supportParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Param.class)) {
            return false;
        }
        if (Map.class != parameter.getParameterType()) {
            return false;
        }
        Type type = parameter.getGenericParameterType();
        if (type instanceof ParameterizedType) {
            var t = (ParameterizedType) type;
            var arr = t.getActualTypeArguments();
            if (arr == null || arr.length != 2) {
                return false;
            }
            if (!arr[0].getTypeName().equals(String.class.getName())) {
                return false;
            }
            return arr[1].getTypeName().equals(String.class.getName());
        }
        return false;
    }

    @Override
    public Object getValue(MethodParameter parameter, ActionInvocation invocation) {
        HashMap<String, String> result = new HashMap<>();
        invocation.getRequest().getParameterNames().asIterator().forEachRemaining(name -> {
            result.put(name, invocation.getRequest().getParameter(name)); //
        });
        for (Map.Entry<String, String> entry : invocation.getUriParameters().entrySet()) {
            result.putIfAbsent(entry.getKey(), entry.getValue());
        }


        return result;
    }
}
