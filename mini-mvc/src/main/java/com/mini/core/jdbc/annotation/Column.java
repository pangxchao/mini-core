package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	
	/**
	 * 数据库字段别名
	 * @return 字段别名
	 */
	String alias() default "";
	
	/**
	 * 数据库字段名称
	 * @return 字段名称
	 */
	String value();
}
