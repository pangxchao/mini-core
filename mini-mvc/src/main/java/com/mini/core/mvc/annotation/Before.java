package com.mini.core.mvc.annotation;

import com.mini.core.mvc.interceptor.ActionInterceptor;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * Web 拦截器
 *
 * @author xchao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Before {
    @Nonnull
    Class<? extends ActionInterceptor>[] value() default {};
}
