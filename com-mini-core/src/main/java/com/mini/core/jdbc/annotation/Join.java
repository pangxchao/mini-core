package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Documented
@Repeatable(Joins.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
	String column();

	JoinType type() default JoinType.NONE;

	enum JoinType {
		NONE,
		LEFT,
		RIGHT,
		INNER,
		OUTER
	}
}
