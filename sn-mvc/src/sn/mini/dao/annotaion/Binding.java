/**
 * Created the sn.mini.dao.annotaion.Binding.java
 * @created 2017年11月2日 上午11:58:58
 * @version 1.0.0
 */
package sn.mini.dao.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sn.mini.dao.annotaion.Binding.java
 * @author XChao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Binding {
	public String value() default "";

	public int des() default 0;
}
