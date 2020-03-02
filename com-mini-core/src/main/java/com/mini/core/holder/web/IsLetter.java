package com.mini.core.holder.web;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface IsLetter {
	int error() default 0;

	@Nonnull
	String message() default "";

	boolean require() default true;
}
