package com.mini.core.holder.web;

import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import java.lang.annotation.*;
import java.util.function.BiPredicate;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Is {

	int error() default 0;

	@Nonnull
	String message() default "";

	boolean require() default true;

	Class<? extends BiPredicate<Object, ActionInvocation>> validator();
}
