package com.mini.core.inject.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySources {
	PropertySource[] value() default {};
}
