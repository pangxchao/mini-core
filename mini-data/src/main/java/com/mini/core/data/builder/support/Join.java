package com.mini.core.data.builder.support;

import com.mini.core.data.builder.BaseSelectSql;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Repeatable(Join.JoinList.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {

    /**
     * 表名称
     *
     * @return 表名称
     */
    String table();

    On[] on() default {};

    JoinType type() default JoinType.INNER;

    enum JoinType {
        DEF,
        INNER,
        LEFT,
        RIGHT,
        LEFT_OUTER,
        RIGHT_OUTER,
        CROSS;

        public void execute(BaseSelectSql<?> selectSql, Join join) {
            selectSql.JOIN(join.table(), jos -> {
                for (final On on : join.on()) {
                    on.join().execute(jos, on);
                }
            });
        }
    }

    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface JoinList {
        Join[] value() default {};
    }
}
