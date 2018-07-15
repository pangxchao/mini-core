/**
 * Created the com.cfinal.web.annotaion.CFAction.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package sn.mini.java.web.annotaion;

import sn.mini.java.web.http.rander.IRender;
import sn.mini.java.web.http.rander.Page;

import java.lang.annotation.*;

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
	Class<? extends IRender> value() default Page.class;

	/**
	 * 指定请求的访问权限码，该码可以根据系统逻辑自定义
	 * @return SessionWay对象
	 */
	boolean login() default false;

	/**
	 * 该视访问地址
	 * @return 返回action的尾部地址
	 */
	String url() default "";

	/**
	 * action后缀，默认取系统全局配置后缀
	 * @return
	 */
	String suffix() default "";

	/**
	 * 扩展字段，允许用户自定义其含义
	 * @return
	 */
	String extend() default "";

}
