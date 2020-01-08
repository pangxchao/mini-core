package com.mini.core.holder.jdbc;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Comment {
	/**
	 * 字段说明内容
	 * @return 说明内容
	 */
	String value();
}
