package com.mini.core.holder.web;

import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import java.lang.annotation.*;
import java.util.function.BiPredicate;

import static com.mini.core.web.util.ResponseCode.VERIFY;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Is {
	int error() default VERIFY;

	@Nonnull
	String message() default "";

	boolean require() default true;

	Class<? extends BiPredicate<Object, ActionInvocation>> validator();
}
