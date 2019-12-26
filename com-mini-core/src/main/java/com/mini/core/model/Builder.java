package com.mini.core.model;

import java.lang.annotation.*;

import static com.mini.core.model.AccessLevel.PUBLIC;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Builder {

	/**
	 * 默认 Builder 类的类名
	 * <p>{className}可以读取当前类名</p>
	 * @return Builder 类名
	 */
	String builderClassName() default "Builder";

	/**
	 * 创建 Builder 对象的静态方法名
	 * @return Builder静态方法名
	 */
	String builderMethodName() default "builder";

	/**
	 * Builder 生成实体的“build”方法名称
	 * @return Builder静态方法名
	 */
	String buildMethodName() default "build";

	/**
	 * 是否生成静态的“builder()”方法
	 * @return true-是(默认-true)
	 */
	boolean hasBuilder() default true;

	/**
	 * 是否生静态的“builder copy”方法
	 * @return true-是(默认-true)
	 */
	boolean hasCopy() default true;

	/**
	 * Builder方法以使用“set”
	 * @return true-是(默认false)
	 */
	boolean useSet() default false;

	/**
	 * 是否使用“Nonnull”、“Nullable”注解
	 * @return true-是(默认-true)
	 * @see javax.annotation.Nullable
	 * @see javax.annotation.Nonnull
	 */
	boolean useFindbugs() default true;

	/**
	 * Builder类是否为“final”
	 * @return true-是(默认-true)
	 */
	boolean isFinal() default true;

	/**
	 * 是否包含父类的字段
	 * @return true-j是(默认-true)
	 */
	boolean includeSuper() default true;

	/**
	 * 方法修饰符
	 * @return 默认-PUBLIC
	 * @see AccessLevel
	 */
	AccessLevel access() default PUBLIC;
}
