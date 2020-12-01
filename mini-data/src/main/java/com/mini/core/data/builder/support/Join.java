package com.mini.core.data.builder.support;

import com.mini.core.data.builder.AbstractSql;
import com.mini.core.data.builder.statement.JoinStatement.*;

import java.lang.annotation.*;
import java.util.Arrays;

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
            public final void exe(AbstractSql<?> sql, String table, On[] ons) {
                new JoinStatementImpl(sql).join(table, it -> { //
                    Arrays.stream(ons).forEach(on -> { //
                        on.join().exe(it, on);
                    });
                });
            }
        },
        INNER {
            @Override
            public final void exe(AbstractSql<?> sql, String table, On[] ons) {
                new InnerJoinStatementImpl(sql).join(table, it -> { //
                    Arrays.stream(ons).forEach(on -> { //
                        on.join().exe(it, on);
                    });
                });
            }
        },
        LEFT {
            @Override
            public final void exe(AbstractSql<?> sql, String table, On[] ons) {
                new LeftJoinStatementImpl(sql).join(table, it -> { //
                    Arrays.stream(ons).forEach(on -> { //
                        on.join().exe(it, on);
                    });
                });
            }
        },
        RIGHT {
            @Override
            public final void exe(AbstractSql<?> sql, String table, On[] ons) {
                new RightJoinStatementImpl(sql).join(table, it -> { //
                    Arrays.stream(ons).forEach(on -> { //
                        on.join().exe(it, on);
                    });
                });
            }
        },
        LEFT_OUTER {
            @Override
            public final void exe(AbstractSql<?> sql, String table, On[] ons) {
                new LeftOuterJoinStatementImpl(sql).join(table, it -> { //
                    Arrays.stream(ons).forEach(on -> { //
                        on.join().exe(it, on);
                    });
                });
            }
        },
        RIGHT_OUTER {
            @Override
            public final void exe(AbstractSql<?> sql, String table, On[] ons) {
                new RightOuterJoinStatementImpl(sql).join(table, it -> { //
                    Arrays.stream(ons).forEach(on -> { //
                        on.join().exe(it, on);
                    });
                });
            }
        },
        CROSS {
            @Override
            public final void exe(AbstractSql<?> sql, String table, On[] ons) {
                new CrossJoinStatementImpl(sql).join(table, it -> { //
                    Arrays.stream(ons).forEach(on -> { //
                        on.join().exe(it, on);
                    });
                });
            }
        };

        public abstract void exe(AbstractSql<?> sql, String table, On[] ons);

    }

    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Join[] value() default {};
    }
}
