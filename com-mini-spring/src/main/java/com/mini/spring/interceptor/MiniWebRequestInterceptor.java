package com.mini.spring.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import javax.annotation.Nonnull;

public class MiniWebRequestInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(@Nonnull WebRequest request) throws Exception {

    }

    @Override
    public void postHandle(@Nonnull WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(@Nonnull WebRequest request, Exception ex) throws Exception {

    }
}
