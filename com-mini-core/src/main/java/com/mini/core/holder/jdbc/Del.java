package com.mini.core.holder.jdbc;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Del {
	// 标识删除的字段的值
	int value() default 1;
}
