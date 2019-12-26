package com.mini.core.model;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Equals {
	/**
	 * 是否包含父类的字段
	 * @return true-j是(默认-true)
	 */
	boolean includeSuper() default true;

}
