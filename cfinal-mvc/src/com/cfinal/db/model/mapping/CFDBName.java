/**
 * Created the com.cfinal.db.model.mapping.CFDBName.java
 * @created 2017年4月26日 下午4:32:43
 * @version 1.0.0
 */
package com.cfinal.db.model.mapping;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.cfinal.db.model.mapping.CFDBName.java
 * @author XChao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface CFDBName {
	/**
	 * 对应数据库名称
	 */
	public String value() default "";
}
