/**
 * Created the com.cfinal.web.annotaion.CFAction.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package sn.mini.web.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sn.mini.web.http.rander.IRender;
import sn.mini.web.http.rander.Page;

/**
 * @author XChao
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	/**
	 * 指定视类型
	 * @return
	 */
	public Class<? extends IRender> value() default Page.class;

	/**
	 * 指定请求的访问权限码，该码可以根据系统逻辑自定义
	 * @return SessionWay对象
	 */
	public boolean login() default false;

	/**
	 * 该视访问地址
	 * @return 返回action的尾部地址
	 */
	public String url() default "";

	/**
	 * action后缀，默认取系统全局配置后缀
	 * @return
	 */
	public String suffix() default "";

	/**
	 * 扩展字段，允许用户自定义其含义
	 * @return
	 */
	public String extend() default "";

}
