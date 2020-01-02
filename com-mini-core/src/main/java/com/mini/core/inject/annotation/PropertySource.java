package com.mini.core.inject.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Repeatable(PropertySources.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySource {
	String value();
}
