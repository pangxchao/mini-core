package com.mini.core.holder.model;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface HiddenThrows {
	
	/**
	 * 要隐藏的类型数组
	 * @return 类型数组
	 */
	Class<? extends Throwable>[] value() default {
		Throwable.class
	};
}
