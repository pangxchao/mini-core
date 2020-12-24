package com.mini.core.data.builder.support;

import com.mini.core.data.builder.fragment.SelectFragment;

import java.lang.annotation.*;

import static java.util.Arrays.stream;

@Documented
@Target({ElementType.TYPE})
@Repeatable(Join.List.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {

    String table();

    On[] on();

    JoinType type() default JoinType.LEFT_OUTER;

    enum JoinType {
        DEF {
            @Override
            public final void exe(SelectFragment<?> sql, String table, On[] ons) {
                sql.join(table, it -> stream(ons).forEach(on -> on.join().exe(it, on)));
            }
        },
        INNER {
            @Override
            public final void exe(SelectFragment<?> sql, String table, On[] ons) {
                sql.join(table, it -> stream(ons).forEach(on -> on.join().exe(it, on)));
            }
        },
        LEFT {
            @Override
            public final void exe(SelectFragment<?> sql, String table, On[] ons) {
                sql.leftJoin(table, it -> stream(ons).forEach(on -> on.join().exe(it, on)));
            }
        },
        RIGHT {
            @Override
            public final void exe(SelectFragment<?> sql, String table, On[] ons) {
                sql.rightJoin(table, it -> stream(ons).forEach(on -> on.join().exe(it, on)));
            }
        },
        LEFT_OUTER {
            @Override
            public final void exe(SelectFragment<?> sql, String table, On[] ons) {
                sql.leftOuterJoin(table, it -> stream(ons).forEach(on -> on.join().exe(it, on)));
            }
        },
        RIGHT_OUTER {
            @Override
            public final void exe(SelectFragment<?> sql, String table, On[] ons) {
                sql.rightOuterJoin(table, it -> stream(ons).forEach(on -> on.join().exe(it, on)));
            }
        },
        CROSS {
            @Override
            public final void exe(SelectFragment<?> sql, String table, On[] ons) {
                sql.crossJoin(table, it -> stream(ons).forEach(on -> on.join().exe(it, on)));
            }
        };

        public abstract void exe(SelectFragment<?> sql, String table, On[] ons);

    }

    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Join[] value() default {};
    }
}
