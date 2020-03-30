package com.mini.core.web.annotation;

import java.lang.annotation.*;

import static com.mini.core.web.annotation.Param.Value.PARAM;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
public @interface Param {
	Value value() default PARAM;
	
	String name() default "";
	
	enum Value {
		HEADER, COOKIE, PARAM, URI,
	}
}
