package com.mini.core.data.builder;

import com.mini.core.data.builder.statement.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class NamedSelectSql extends NamedSql<NamedSelectSql> implements BaseSelectSql<NamedSelectSql> {
    private final SelectSql<NamedSelectSql> impl = new SelectSql<>() {
        protected NamedSelectSql getSelectSql() {
            return NamedSelectSql.this;
        }
    };

    private NamedSelectSql() {
    }

    public NamedSelectSql SELECT(String... column) {
        return impl.SELECT(column);
    }

    public NamedSelectSql SELECT(Consumer<SelectStatement> consumer) {
        return impl.SELECT(consumer);
    }

    public NamedSelectSql FROM(String... tables) {
        return impl.FROM(tables);
    }

    public NamedSelectSql JOIN(String join) {
        return impl.JOIN(join);
    }

    public NamedSelectSql JOIN(String table, Consumer<JoinStatement.JoinOnStatement> consumer) {
        return impl.JOIN(table, consumer);
    }

    public NamedSelectSql INNER_JOIN(String join) {
        return impl.INNER_JOIN(join);
    }

    public NamedSelectSql INNER_JOIN(String table, Consumer<JoinStatement.JoinOnStatement> consumer) {
        return impl.INNER_JOIN(table, consumer);
    }

    public NamedSelectSql LEFT_JOIN(String join) {
        return impl.LEFT_JOIN(join);
    }

    public NamedSelectSql LEFT_JOIN(String table, Consumer<JoinStatement.JoinOnStatement> consumer) {
        return impl.LEFT_JOIN(table, consumer);
    }

    public NamedSelectSql RIGHT_JOIN(String join) {
        return impl.RIGHT_JOIN(join);
    }

    public NamedSelectSql RIGHT_JOIN(String table, Consumer<JoinStatement.JoinOnStatement> consumer) {
        return impl.RIGHT_JOIN(table, consumer);
    }

    public NamedSelectSql LEFT_OUTER_JOIN(String join) {
        return impl.LEFT_OUTER_JOIN(join);
    }

    public NamedSelectSql LEFT_OUTER_JOIN(String table, Consumer<JoinStatement.JoinOnStatement> consumer) {
        return impl.LEFT_OUTER_JOIN(table, consumer);
    }

    public NamedSelectSql RIGHT_OUTER_JOIN(String join) {
        return impl.RIGHT_OUTER_JOIN(join);
    }

    public NamedSelectSql RIGHT_OUTER_JOIN(String table, Consumer<JoinStatement.JoinOnStatement> consumer) {
        return impl.RIGHT_OUTER_JOIN(table, consumer);
    }

    public NamedSelectSql CROSS_JOIN(String join) {
        return impl.CROSS_JOIN(join);
    }

    public NamedSelectSql CROSS_JOIN(String table, Consumer<JoinStatement.JoinOnStatement> consumer) {
        return impl.CROSS_JOIN(table, consumer);
    }

    public NamedSelectSql JOIN(Consumer<JoinStatement> consumer) {
        return impl.JOIN(consumer);
    }

    public NamedSelectSql WHERE(String where) {
        return impl.WHERE(where);
    }

    public NamedSelectSql WHERE(Consumer<WhereStatement> consumer) {
        return impl.WHERE(consumer);
    }

    public NamedSelectSql GROUP_BY(String... columns) {
        return impl.GROUP_BY(columns);
    }

    public NamedSelectSql HAVING(Consumer<HavingStatement> consumer) {
        return impl.HAVING(consumer);
    }

    public NamedSelectSql ORDER_BY(String... columns) {
        return impl.ORDER_BY(columns);
    }

    public NamedSelectSql ORDER_BY_ASC(String... columns) {
        return impl.ORDER_BY_ASC(columns);
    }

    public NamedSelectSql ORDER_BY_DESC(String... columns) {
        return impl.ORDER_BY_DESC(columns);
    }

    public NamedSelectSql ORDER_BY(Consumer<OrderByStatement> consumer) {
        return impl.ORDER_BY(consumer);
    }

    public <E> NamedSelectSql SELECT_FROM_JOIN(@NotNull Class<E> type) {
        return impl.SELECT_FROM_JOIN(type);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static NamedSelectSql of(Consumer<NamedSelectSql> consumer) {
        NamedSelectSql builder = new NamedSelectSql();
        consumer.accept(builder);
        return builder;
    }

    public static NamedSelectSql of() {
        return new NamedSelectSql();
    }
}
