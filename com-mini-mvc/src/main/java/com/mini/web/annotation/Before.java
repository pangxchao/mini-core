package com.mini.web.annotation;

import java.lang.annotation.*;

import javax.annotation.Nonnull;

import com.mini.web.interceptor.ActionInterceptor;

/**
 * Web 拦截器
 * @author xchao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Before {
    @Nonnull
    Class<? extends ActionInterceptor>[] value() default {};
}
