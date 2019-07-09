package com.mini.web.config;

import com.mini.web.interceptor.ActionInterceptor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
public final class InterceptorConfigure implements Serializable {
    private static final long serialVersionUID = 299531539164896197L;
    private final Set<Class<? extends ActionInterceptor>> interceptors = new HashSet<>();

    /**
     * 添加一个拦截器
     * @param interceptor 拦截器
     * @return {@Code this}
     */
    public InterceptorConfigure addListener(Class<? extends ActionInterceptor> interceptor) {
        interceptors.add(interceptor);
        return this;
    }

    /**
     * Gets the value of interceptorList.
     * @return The value of interceptorList
     */
    public Set<Class<? extends ActionInterceptor>> getInterceptors() {
        return interceptors;
    }
}
