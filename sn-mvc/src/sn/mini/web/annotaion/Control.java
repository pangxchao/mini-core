/**
 * Created the com.cfinal.web.annotaion.CFControl.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package sn.mini.web.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Control {
	/**
	 * 控制器的名字属性
	 * @return 控制器名称，默认去控制器的简单类名称，
	 */
	public String name() default "";

	/**
	 * 配置控制器的url地址
	 * @return url相对地址字串
	 */
	public String url() default "";

	/**
	 * 控制器下的所有action 后缀
	 * @return 返回后缀字串
	 */
	public String suffix() default ".htm";
}
