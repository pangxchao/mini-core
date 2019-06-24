package com.mini.dao.annotation;

import javax.inject.Scope;
import java.lang.annotation.*;

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Transaction {}
