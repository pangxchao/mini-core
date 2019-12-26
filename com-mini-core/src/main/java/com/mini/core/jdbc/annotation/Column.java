package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 字段名称
	 * @return 字段名称
	 */
	String value();
}
