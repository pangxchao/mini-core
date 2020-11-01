package com.mini.core.mvc.processor;

import com.mini.core.mvc.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;


public abstract class IModelProcessor implements HandlerMethodArgumentResolver {

    @NotNull
    protected final <T extends IModel<?, T>> T setModelToRequest(NativeWebRequest webRequest, @NotNull T value) {
        webRequest.setAttribute(IModel.MODEL_KEY, value, SCOPE_REQUEST);
        return value;
    }

    @Nullable
    protected final <T extends IModel<?, T>> T getModelFromRequest(@NotNull NativeWebRequest webRequest, @NotNull Class<T> modelType) {
        return modelType.cast(webRequest.getAttribute(IModel.MODEL_KEY, SCOPE_REQUEST));
    }

}
