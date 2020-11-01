package com.mini.core.mvc.processor;

import com.mini.core.mvc.model.JsonModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class JsonModelProcessor extends IModelProcessor implements HandlerMethodArgumentResolver {

    @Override
    public final boolean supportsParameter(@NotNull MethodParameter parameter) {
        return JsonModel.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public final Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return setModelToRequest(webRequest, ofNullable(getModelFromRequest(webRequest, JsonModel.class)).orElseGet(() -> {
            HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            return createModel(requireNonNull(request), requireNonNull(response));
        }));
    }

    protected JsonModel createModel(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        return new JsonModel(request, response);
    }
}
