/**
 * Created the sn.mini.web.annotaion.Before.java
 * @created 2017年10月9日 下午5:51:21
 * @version 1.0.0
 */
package sn.mini.web.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sn.mini.web.http.interceptor.Interceptor;

/**
 * sn.mini.web.annotaion.Before.java
 * @author XChao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Before {
	public Class<? extends Interceptor>[] value() default {};
}
