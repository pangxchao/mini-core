package com.mini.core.validation.annotation;

import com.mini.core.validation.Constraint;
import com.mini.core.validation.constraint.SizeConstraintValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mini.core.web.util.ResponseCode.VERIFY;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 带注解的元素必须在大于或者等于指定最后值并且小于指定最大值的元素个数
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code List}</li>
 *     <li>{@code Map}</li>
 * </ul>
 * @author xchao
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD})
@Constraint(SizeConstraintValidation.class)
public @interface Size {
	int error() default VERIFY;
	
	String message() default "";
	
	boolean require() default true;
	
	/**
	 * 最小大小
	 * @return 最小大小
	 */
	int min() default 0;
	
	/**
	 * 最大大小
	 * @return 最大大小
	 */
	int max() default Integer.MAX_VALUE;
}
