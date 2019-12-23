package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
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
