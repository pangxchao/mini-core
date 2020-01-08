package com.mini.core.holder.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Repeatable(Join.Joins.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
	String column();
	
	JoinType type() default JoinType.INNER;
	
	enum JoinType {
		INNER {
			public void execute(SQLBuilder builder, String format, Object... args) {
				builder.join(format, args);
			}
		},
		LEFT {
			public void execute(SQLBuilder builder, String format, Object... args) {
				builder.leftJoin(format, args);
			}
		},
		RIGHT {
			public void execute(SQLBuilder builder, String format, Object... args) {
				builder.rightJoin(format, args);
			}
		},
		OUTER {
			public void execute(SQLBuilder builder, String format, Object... args) {
				builder.outerJoin(format, args);
			}
		};
		
		public abstract void execute(SQLBuilder builder, String format, Object... args);
	}
	
	@Documented
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface Joins {
		Join[] value() default {};
	}
	
}
