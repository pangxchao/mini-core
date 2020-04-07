package com.mini.core.inject.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Repeatable(PropertySource.List.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySource {
	String value();
	
	@Documented
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface List {
		PropertySource[] value() default {};
	}
}
