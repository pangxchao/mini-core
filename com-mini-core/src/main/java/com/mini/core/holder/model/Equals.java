package com.mini.core.holder.model;

import javax.lang.model.element.Modifier;
import java.lang.annotation.*;

/**
 * 生成 Equals 方法
 * @author xchao
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Equals {
	/**
	 * 方法修饰符
	 * @return 修饰符
	 */
	Modifier[] modifiers() default {
		Modifier.PUBLIC
	};
	
	/**
	 * 是否调用父类的属性
	 * @return true-是(默认)
	 */
	boolean supers() default true;
}
