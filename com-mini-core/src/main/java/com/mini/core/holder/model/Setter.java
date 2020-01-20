package com.mini.core.holder.model;

import javax.lang.model.element.Modifier;
import java.lang.annotation.*;

/**
 * 生成 Getter 方法
 * @author xchao
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Setter {
	/**
	 * 方法修饰符
	 * @return 修饰符
	 */
	Modifier[] modifiers() default {
		Modifier.PUBLIC
	};
}
