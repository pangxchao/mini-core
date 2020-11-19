package com.mini.core.validation.annotation;

import com.mini.core.validation.Constraint;
import com.mini.core.validation.constraint.BlankConstraintValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mini.core.web.util.ResponseCode.VERIFY;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 带注解的元素为空或者只包含空白字符
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code CharSequence}</li>
 * </ul>
 * @author xchao
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD})
@Constraint(BlankConstraintValidation.class)
public @interface Blank {
	int error() default VERIFY;
	
	String message() default "";
}
