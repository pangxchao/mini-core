package com.mini.core.validation.annotation;

import com.mini.core.validation.Constraint;
import com.mini.core.validation.constraint.LengthConstraintValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mini.core.web.util.ResponseCode.VERIFY;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 带注解的元素必须在指定的长度之间
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code Array}</li>
 * </ul>
 * @author xchao
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD})
@Constraint(LengthConstraintValidation.class)
public @interface Length {
	int error() default VERIFY;
	
	String message() default "";
	
	boolean require() default true;
	
	/**
	 * 最小长度
	 * @return 最小长度
	 */
	int min() default 0;
	
	/**
	 * 最大长度
	 * @return 最大长度
	 */
	int max() default Integer.MAX_VALUE;
}
