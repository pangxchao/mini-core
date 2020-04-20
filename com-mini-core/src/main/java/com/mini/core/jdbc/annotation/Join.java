package com.mini.core.jdbc.annotation;

import com.mini.core.jdbc.builder.SQLBuilder;

import java.lang.annotation.*;

import static com.mini.core.jdbc.annotation.Join.JoinType.INNER;

@Documented
@Target({ElementType.TYPE})
@Repeatable(Join.List.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
	String value() default "%s ON %s = %s";
	
	String[] args() default {};
	
	JoinType type() default INNER;
	
	enum JoinType {
		INNER {
			public void execute(SQLBuilder builder, String format, Object[] args) {
				builder.join(format, args);
			}
		},
		LEFT {
			public void execute(SQLBuilder builder, String format, Object[] args) {
				builder.leftJoin(format, args);
			}
		},
		RIGHT {
			public void execute(SQLBuilder builder, String format, Object[] args) {
				builder.rightJoin(format, args);
			}
		},
		OUTER {
			public void execute(SQLBuilder builder, String format, Object[] args) {
				builder.outerJoin(format, args);
			}
		};
		
		public abstract void execute(SQLBuilder builder, String format, Object[] args);
	}
	
	@Documented
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		Join[] value() default {};
	}
	
}
