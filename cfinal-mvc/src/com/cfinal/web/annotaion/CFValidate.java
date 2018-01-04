/**
 * Created the com.cfinal.web.annotaion.CFValidate.java
 * @created 2017年8月28日 上午10:23:30
 * @version 1.0.0
 */
package com.cfinal.web.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.cfinal.web.annotaion.CFValidate.java
 * @author XChao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.PARAMETER })
public @interface CFValidate {
	public CFValiType value() default CFValiType.NONE;

	public int error() default 0;

	public String message() default "";

}
