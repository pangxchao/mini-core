package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.statement.JoinStatement;
import com.mini.core.jdbc.builder.statement.WhereStatement;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class NamedDeleteSql extends NamedSql<NamedDeleteSql> implements BaseDeleteSql<NamedDeleteSql> {
    private final DeleteSqlImpl<NamedDeleteSql> impl = new DeleteSqlImpl<>() {
        protected NamedDeleteSql getDeleteSql() {
            return NamedDeleteSql.this;
        }
    };

    public NamedDeleteSql() {
    }

    public final NamedDeleteSql DELETE(String... tables) {
        return impl.DELETE(tables);
    }

    public NamedDeleteSql FROM(String... tables) {
        return impl.FROM(tables);
    }

    public NamedDeleteSql JOIN(String join) {
        return impl.JOIN(join);
    }

    public NamedDeleteSql INNER_JOIN(String join) {
        return impl.INNER_JOIN(join);
    }

    public NamedDeleteSql LEFT_JOIN(String join) {
        return impl.LEFT_JOIN(join);
    }

    public NamedDeleteSql RIGHT_JOIN(String join) {
        return impl.RIGHT_JOIN(join);
    }

    public NamedDeleteSql LEFT_OUTER_JOIN(String join) {
        return impl.LEFT_OUTER_JOIN(join);
    }

    public NamedDeleteSql RIGHT_OUTER_JOIN(String join) {
        return impl.RIGHT_OUTER_JOIN(join);
    }

    public NamedDeleteSql CROSS_JOIN(String join) {
        return impl.CROSS_JOIN(join);
    }

    public NamedDeleteSql JOIN(Consumer<JoinStatement> consumer) {
        return impl.JOIN(consumer);
    }

    public NamedDeleteSql WHERE(String where) {
        return impl.WHERE(where);
    }

    public NamedDeleteSql WHERE(Consumer<WhereStatement> consumer) {
        return impl.WHERE(consumer);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static NamedDeleteSql of(Consumer<NamedDeleteSql> consumer) {
        NamedDeleteSql builder = new NamedDeleteSql();
        consumer.accept(builder);
        return builder;
    }

    public static NamedDeleteSql of() {
        return new NamedDeleteSql();
    }
}
