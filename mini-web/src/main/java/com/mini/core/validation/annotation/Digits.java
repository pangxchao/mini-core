package com.mini.core.validation.annotation;

import com.mini.core.validation.Constraint;
import com.mini.core.validation.constraint.DigitsConstraintValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mini.core.web.util.ResponseCode.VERIFY;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 带注解的元素必须是可接受范围内的数字
 * <br />
 * 支持的类型
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code String}</li>
 *     <li>{@code Byte}</li>
 *     <li>{@code Short}</li>
 *     <li>{@code Integer}</li>
 *     <li>{@code Long}</li>
 *     <li>{@code byte}</li>
 *     <li>{@code short}</li>
 *     <li>{@code int}</li>
 *     <li>{@code long}</li>
 * </ul>
 * @author xchao
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD})
@Constraint(DigitsConstraintValidation.class)
public @interface Digits {
	int error() default VERIFY;
	
	String message() default "";
	
	boolean require() default true;
	
	/**
	 * 整数位数
	 * @return 整数位数
	 */
	int integer() default 17;
	
	/**
	 * 小数位数
	 * @return 小数位数
	 */
	int fraction() default 2;
}
