package com.mini.core.mvc.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
public @interface Param {
    Value value() default Value.PARAM;

    String name() default "";

    enum Value {
        HEADER, COOKIE, PARAM, URI,
    }
}
