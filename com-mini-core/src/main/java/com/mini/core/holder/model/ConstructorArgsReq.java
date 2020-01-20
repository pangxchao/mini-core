package com.mini.core.holder.model;

import javax.lang.model.element.Modifier;
import java.lang.annotation.*;

/**
 * 生成指定参数构造方法
 * <p>指定的参数列表由{@link ConstructorArgsReqField}确定</p>
 * @author xchao
 * @see ConstructorArgsReqField
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ConstructorArgsReq {
	/**
	 * 构造方法修饰符
	 * @return 修饰符
	 */
	Modifier[] modifiers() default {
		Modifier.PUBLIC
	};
}
