package com.mini.web.annotation;

import com.mini.web.interceptor.ActionInterceptor;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

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
