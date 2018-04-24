/**
 * Created the sn.mini.java.web.annotaion.Menu.java
 * @created 2017年10月9日 下午5:55:07
 * @version 1.0.0
 */
package sn.mini.java.web.annotaion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sn.mini.java.web.annotaion.Menu.java
 * @author XChao
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Menu {
	/**
	 * 该控制器是否为一个菜单
	 * @return
	 */
	public boolean is() default false;

	/**
	 * 权限设计中的菜单ID
	 * @return
	 */
	public int id() default 0;

	/**
	 * 权限设计中的菜单名称
	 * @return
	 */
	public String name() default "";

	/**
	 * 菜单权限
	 * @return
	 */
	public int value() default 0;

	/**
	 * 指定菜单类型, 如果type相同,菜表示为同一类别菜单, 比如: 运营系统的所有菜单type值都应该一样
	 * @return
	 */
	public int type() default 0;

	/**
	 * 返回该菜单的上级菜单ID
	 * @return
	 */
	public int parentId() default 0;

	/**
	 * 只有当该级菜单的上级菜单ID指向了实际菜单ID时,该名称才生效,否则,该级菜单读取的应该是该上级菜单ID指向的实际菜单名称
	 * @return 返回该菜单的上级菜单名称
	 */
	public String parentName() default "";
}
