package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	String value();
}
