package com.mini.core.jdbc.builder;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class NamedInsertSql extends NamedSql<NamedInsertSql> implements BaseInsertSql<NamedInsertSql> {
    private final InsertSql<NamedInsertSql> impl = new InsertSql<>() {
        protected NamedInsertSql getInsertSql() {
            return NamedInsertSql.this;
        }
    };

    private NamedInsertSql() {
    }

    public NamedInsertSql INSERT_INTO(String table) {
        return impl.INSERT_INTO(table);
    }

    public NamedInsertSql VALUES(String column, String value) {
        return impl.VALUES(column, value);
    }

    public NamedInsertSql VALUES(String column) {
        return impl.VALUES(column);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static NamedInsertSql of(Consumer<NamedInsertSql> consumer) {
        NamedInsertSql builder = new NamedInsertSql();
        consumer.accept(builder);
        return builder;
    }

    public static NamedInsertSql of() {
        return new NamedInsertSql();
    }
}
