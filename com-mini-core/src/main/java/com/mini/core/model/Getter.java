package com.mini.core.model;

import lombok.AllArgsConstructor;

import java.lang.annotation.*;

import static com.mini.core.model.AccessLevel.PUBLIC;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Getter {
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
