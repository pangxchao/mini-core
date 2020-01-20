package com.mini.core.holder.model;

import javax.lang.model.element.Modifier;
import java.lang.annotation.*;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * 生成 log 静态属性
 * <p>该日志使用的是“SLF4J”日志框架</p>
 * @author xchao
 * @see org.slf4j.LoggerFactory
 * @see org.slf4j.Logger
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Log {
	/**
	 * 属性修饰符
	 * @return 修饰符
	 */
	Modifier[] modifiers() default {
		PRIVATE, STATIC
	};
}
