package com.mini.core.processor;

import java.lang.annotation.*;

import static com.mini.core.processor.AccessLevel.PUBLIC;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Setter {
	/**
	 * 是否包含父类的字段
	 * @return true-j是(默认-true)
	 */
	boolean includeSuper() default true;

	/**
	 * 方法修饰符
	 * @return 默认-PUBLIC
	 * @see AccessLevel
	 */
	AccessLevel access() default PUBLIC;
}
