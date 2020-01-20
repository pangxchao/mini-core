package com.mini.core.holder.model;

import javax.lang.model.element.Modifier;
import java.lang.annotation.*;

/**
 * 生成无参数构造方法
 * @author xchao
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ConstructorArgsNo {
	/**
	 * 构造方法修饰符
	 * @return 修饰符
	 */
	Modifier[] modifiers() default {
		Modifier.PUBLIC
	};
}
