package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Ref {
	/**
	 * 指向的表
	 * @return 表名称
	 */
	String table();

	/**
	 * 指向的字段
	 * @return 字段名称
	 */
	String column();
}
