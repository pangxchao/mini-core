/**
 * Created the com.cfinal.web.annotaion.CFAction.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package com.cfinal.web.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cfinal.web.render.CFRender;
import com.cfinal.web.render.CFPageRender;

/**
 * @author XChao
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CFAction {
	/**
	 * 指定视类型
	 * @return
	 */
	public CFType value() default CFType.page;

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
	 * 该Action重新指定拦截器
	 * @return
	 */
	public String use() default "";

	/**
	 * 使用视渲染器的名称
	 * @return
	 */
	public String view() default "";

	/**
	 * 用户自定义返回类型,必须和XView.defined 联合使用
	 * @return
	 */
	public Class<? extends CFRender> defined() default CFPageRender.class;

	/**
	 * 设置是否可以异步返回, 该功能暂时未实现
	 * @return
	 */
	public boolean async() default false;

	/**
	 * 指定请求的访问权限码，该码可以根据系统逻辑自定义
	 * @return SessionWay对象
	 */
	public int permiss() default 0;

	/**
	 * 该资源权限是否为需要显示的菜单, 如果不需要显示, 则表示为普通资源
	 * @return
	 */
	public boolean isMenu() default false;

	/**
	 * 权限设计中的菜单ID
	 * @return
	 */
	public int menuId() default 0;

	/**
	 * 权限设计中的菜单名称<br/>
	 * 只有isMenu=true的菜单上有效
	 * @return
	 */
	public String menuName() default "";

	/**
	 * 指定菜单类型, 如果type相同,菜表示为同一类别菜单, 比如: 运营系统的所有菜单type值都应该一样
	 * @return
	 */
	public int menuType() default 0;

	/**
	 * 返回该菜单的上级菜单ID
	 * @return
	 */
	public int menuParentId() default 0;

	/**
	 * 只有当该级菜单的上级菜单ID指向了实际菜单ID时,该名称才生效,否则,该级菜单读取的应该是该上级菜单ID指向的实际菜单名称
	 * @return 返回该菜单的上级菜单名称
	 */
	public String menuParentName() default "";
}
