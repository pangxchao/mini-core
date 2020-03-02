package com.mini.core.holder.web;

import javax.annotation.Nonnull;
import java.lang.annotation.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Is {
	@Nonnull
	String expression();

	int error() default 0;

	@Nonnull
	String message() default "";

	boolean require() default true;
}
