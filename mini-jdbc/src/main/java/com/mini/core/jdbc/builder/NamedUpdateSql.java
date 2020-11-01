package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.statement.JoinStatement;
import com.mini.core.jdbc.builder.statement.SetStatement;
import com.mini.core.jdbc.builder.statement.WhereStatement;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class NamedUpdateSql extends NamedSql<NamedUpdateSql> implements BaseUpdateSql<NamedUpdateSql> {
    private final UpdateSql<NamedUpdateSql> impl = new UpdateSql<>() {
        protected NamedUpdateSql getUpdateSql() {
            return NamedUpdateSql.this;
        }
    };

    private NamedUpdateSql() {
    }

    public NamedUpdateSql UPDATE(String... tables) {
        return impl.UPDATE(tables);
    }

    public NamedUpdateSql JOIN(String join) {
        return impl.JOIN(join);
    }

    public NamedUpdateSql INNER_JOIN(String join) {
        return impl.INNER_JOIN(join);
    }

    public NamedUpdateSql LEFT_JOIN(String join) {
        return impl.LEFT_JOIN(join);
    }

    public NamedUpdateSql RIGHT_JOIN(String join) {
        return impl.RIGHT_JOIN(join);
    }

    public NamedUpdateSql LEFT_OUTER_JOIN(String join) {
        return impl.LEFT_OUTER_JOIN(join);
    }

    public NamedUpdateSql RIGHT_OUTER_JOIN(String join) {
        return impl.RIGHT_OUTER_JOIN(join);
    }

    public NamedUpdateSql CROSS_JOIN(String join) {
        return impl.CROSS_JOIN(join);
    }

    public NamedUpdateSql JOIN(Consumer<JoinStatement> consumer) {
        return impl.JOIN(consumer);
    }

    public NamedUpdateSql SET(String set) {
        return impl.SET(set);
    }

    public NamedUpdateSql SET(Consumer<SetStatement> consumer) {
        return impl.SET(consumer);
    }

    public NamedUpdateSql WHERE(String where) {
        return impl.WHERE(where);
    }

    public NamedUpdateSql WHERE(Consumer<WhereStatement> consumer) {
        return impl.WHERE(consumer);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static NamedUpdateSql of(Consumer<NamedUpdateSql> consumer) {
        NamedUpdateSql builder = new NamedUpdateSql();
        consumer.accept(builder);
        return builder;
    }

    public static NamedUpdateSql of() {
        return new NamedUpdateSql();
    }
}
