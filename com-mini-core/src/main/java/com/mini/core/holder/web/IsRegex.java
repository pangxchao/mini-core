package com.mini.core.holder.web;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

import static com.mini.core.web.util.ResponseCode.VERIFY;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface IsRegex {
	@Nonnull
	String regex();

	int error() default VERIFY;

	@Nonnull
	String message() default "";

	boolean require() default true;
}
