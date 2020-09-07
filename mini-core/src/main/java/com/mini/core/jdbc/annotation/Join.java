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
            public void execute(SQLBuilder builder, String join) {
                builder.JOIN(join);
            }
        },
        LEFT {
            public void execute(SQLBuilder builder, String join) {
                builder.LEFT_JOIN(join);
            }
        },
        RIGHT {
            public void execute(SQLBuilder builder, String join) {
                builder.RIGHT_JOIN(join);
            }
        },
        OUTER {
            public void execute(SQLBuilder builder, String join) {
                builder.OUTER_JOIN(join);
            }
        };

        public abstract void execute(SQLBuilder builder, String join);
    }

    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Join[] value() default {};
    }

}
