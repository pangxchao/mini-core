package com.mini.spring.argument;

import com.mini.spring.util.WebUtil;
import com.mini.util.dao.Paging;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

@Component
public class PagingMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Paging.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        int page = 1, rows = 10;
        if (webRequest.getNativeRequest() instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            page = WebUtil.getParameterToInt(request, "page");
            rows = WebUtil.getParameterToInt(request, "rows");
        }
        return new Paging(page, rows);
    }
}
