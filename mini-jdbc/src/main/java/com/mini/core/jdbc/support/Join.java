package com.mini.core.jdbc.support;

import com.mini.core.jdbc.builder.SQLBuilder;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Repeatable(Join.List.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
    JoinType type() default JoinType.INNER;

    String value();

    enum JoinType {
        INNER {
            public void execute(SQLBuilder builder, String value) {
                builder.JOIN(value);
            }
        },
        LEFT {
            public void execute(SQLBuilder builder, String value) {
                builder.LEFT_JOIN(value);
            }
        },
        RIGHT {
            public void execute(SQLBuilder builder, String value) {
                builder.RIGHT_JOIN(value);
            }
        },
        OUTER {
            public void execute(SQLBuilder builder, String value) {
                builder.OUTER_JOIN(value);
            }
        };

        public abstract void execute(SQLBuilder builder, String value);
    }

    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Join[] value() default {};
    }

}
