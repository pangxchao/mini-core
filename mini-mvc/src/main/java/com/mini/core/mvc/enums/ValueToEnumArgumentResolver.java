package com.mini.core.mvc.enums;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ValueToEnumArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(@NotNull MethodParameter parameter) {
        return IEnum.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final Class<?> classes = parameter.getParameterType();
        if (!IEnum.class.isAssignableFrom(parameter.getParameterType())) {
            return null;
        }
        // 获取参数名称
        String parameterName = parameter.getParameterName();
        if (StringUtils.isBlank(parameterName)) {
            return null;
        }

        // 获取参数内容
        String source = webRequest.getParameter(parameterName);
        if (StringUtils.isBlank(source)) {
            return null;
        }

        // 读取枚举值生成枚举对象
        final int value = Integer.parseInt(source);
        final var enumClass = classes.asSubclass(IEnum.class);
        for (IEnum enumInstance : enumClass.getEnumConstants()) {
            if (value == enumInstance.getValue()) {
                return enumInstance;
            }
        }
        // 枚举值错误，无法创建枚举对象
        var message = "No enum value " + enumClass.getCanonicalName() + "." + value;
        throw new IllegalArgumentException(message);
    }
}
