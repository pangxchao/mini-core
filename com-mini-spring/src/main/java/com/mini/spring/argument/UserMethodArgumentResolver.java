package com.mini.spring.argument;

import com.mini.spring.util.IUser;
import com.mini.spring.util.WebUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

@Component
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(IUser.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        if (webRequest.getNativeRequest() instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            return WebUtil.getAttribute(request.getSession(), IUser.USER_KEY, IUser.class);
        }
        return null;
    }
}
