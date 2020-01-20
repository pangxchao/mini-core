package com.mini.core.holder.model;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Formatted {
	
	/**
	 * 为空时，默认为系统格式化字符串
	 * @return 格式化字符串
	 */
	String value() default "";
	
	/**
	 * 默认格式化类型
	 * @return 格式化类型
	 */
	FormattedType[] type() default {
		FormattedType.DATETIME
	};
	
	enum FormattedType {
		TIMESTAMP,
		DATETIME,
		DATE,
		TIME
	}
}
