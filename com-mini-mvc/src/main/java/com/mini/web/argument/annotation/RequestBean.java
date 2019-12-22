package com.mini.web.argument.annotation;

import com.mini.core.inject.annotation.Associated;

import java.lang.annotation.*;

@Documented
@Associated
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBean {
}
