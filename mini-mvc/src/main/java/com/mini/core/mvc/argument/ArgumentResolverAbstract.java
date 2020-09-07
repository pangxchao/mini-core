package com.mini.core.mvc.argument;

import com.mini.core.mvc.annotation.Param;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;

public abstract class ArgumentResolverAbstract implements ArgumentResolver {

    /**
     * 获取参数名称
     *
     * @param parameter 参数对象
     * @return 参数名称
     */
    @NotNull
    @SuppressWarnings("ConstantConditions")
    protected final String getParameterName(MethodParameter parameter) {
        Param param = parameter.getParameterAnnotation(Param.class);
        if (param != null && !param.name().isBlank()) {
            return param.name();
        }
        return parameter.getParameterName();
    }
}
