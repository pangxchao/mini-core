package com.mini.web.test.context;

import com.mini.jdbc.model.Paging;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;

import static com.mini.util.TypeUtil.castToIntVal;

public class PagingArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@Nonnull MethodParameter parameter) {
        return Paging.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer, //
            @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String limit = webRequest.getParameter("limit"), page = webRequest.getParameter("page");
        return new Paging(castToIntVal(page), castToIntVal(limit));
    }
}
