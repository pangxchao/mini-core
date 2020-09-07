package com.mini.core.mvc.argument.uri;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.argument.ArgumentResolver;
import com.mini.core.mvc.interceptor.ActionInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("DuplicatedCode")
public final class ArgumentResolverMapRequestUriArray implements ArgumentResolver {

    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean supportParameter(MethodParameter parameter) {
        Param param = parameter.getParameterAnnotation(Param.class);
        if (param == null || param.value() != Param.Value.URI) {
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
            return arr[1].getTypeName().equals(String[].class.getName());
        }
        return false;
    }

    @Override
    public Object getValue(MethodParameter parameter, ActionInvocation invocation) {
        HashMap<String, String[]> result = new HashMap<>();
        invocation.getUriParameters().forEach((k, v) -> {
            result.put(k, new String[]{v}); //
        });
        return result;
    }
}
