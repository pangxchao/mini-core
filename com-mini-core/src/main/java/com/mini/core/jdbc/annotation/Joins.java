package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Joins {
	Join[] value() default {};
}
