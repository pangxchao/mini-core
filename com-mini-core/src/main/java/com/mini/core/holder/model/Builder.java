package com.mini.core.holder.model;

import javax.lang.model.element.Modifier;
import java.lang.annotation.*;

/**
 * 生成 Builder 模式
 * @author xchao
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Builder {
	/**
	 * 静态Builder方法名称
	 * @return 方法名称
	 */
	String method() default "builder";
	
	/**
	 * 是否生成 copy 方法
	 * @return true-是(默认)
	 */
	boolean copy() default true;
	
	/**
	 * 静态 Builder 类名称
	 * @return 类名称
	 */
	String clazz() default "Builder";
	
	/**
	 * Builder 类修饰符
	 * @return 修饰符
	 */
	Modifier[] modifiers() default {
		Modifier.PUBLIC
	};
	
	/**
	 * 生成 withXXX 方法
	 * @return false-否(默认)
	 */
	boolean with() default false;
	
	/**
	 * 生成 setXXX 方法
	 * @return false-否(默认)
	 */
	boolean setter() default false;
	
	/**
	 * 是否调用父类方法
	 * @return true-是(默认)
	 */
	boolean supers() default true;
}
