package com.mini.core.validation.annotation;

import com.mini.core.validation.Constraint;
import com.mini.core.validation.constraint.AssertTrueConstraintValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mini.core.web.util.ResponseCode.VERIFY;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 带注解的元素必须为“True”
 * <br />
 * 支持的数据类型：
 * <ul>
 *     <li>{@code Boolean}</li>
 *     <li>{@code boolean}</li>
 * </ul>
 * @author xchao
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD})
@Constraint(AssertTrueConstraintValidation.class)
public @interface AssertTrue {
	int error() default VERIFY;
	
	String message() default "";
	
	boolean require() default true;
}
