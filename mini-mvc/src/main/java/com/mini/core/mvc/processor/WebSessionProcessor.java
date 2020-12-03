package com.mini.core.mvc.processor;

import com.mini.core.mvc.util.WebSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.mini.core.mvc.util.WebSession.SESSION_KEY;
import static org.springframework.web.util.WebUtils.getSessionAttribute;

public class WebSessionProcessor implements HandlerMethodArgumentResolver {
    @Override
    public final boolean supportsParameter(@NotNull MethodParameter parameter) {
        return WebSession.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return getSessionAttribute(Objects.requireNonNull(request), SESSION_KEY);
    }
}
