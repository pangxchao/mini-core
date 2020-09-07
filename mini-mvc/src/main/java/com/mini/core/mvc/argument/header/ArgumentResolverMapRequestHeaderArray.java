package com.mini.core.mvc.argument.header;

import com.mini.core.mvc.annotation.Param;
import com.mini.core.mvc.argument.ArgumentResolverAbstract;
import com.mini.core.mvc.interceptor.ActionInvocation;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
public final class ArgumentResolverMapRequestHeaderArray extends ArgumentResolverAbstract {

    @Override
    @SuppressWarnings({"ConstantConditions", "DuplicatedCode"})
    public boolean supportParameter(MethodParameter parameter) {
        Param param = parameter.getParameterAnnotation(Param.class);
        if (param == null || param.value() != Param.Value.HEADER) {
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
        invocation.getRequest().getHeaderNames().asIterator().forEachRemaining(name -> {
            result.put(name, Stream.of(invocation.getRequest().getHeaders(name)).flatMap(v -> {
                Stream.Builder<String> builder = Stream.builder();
                v.asIterator().forEachRemaining(builder::add);
                return builder.build();
            }).toArray(String[]::new)); //
        });
        return result;
    }
}
