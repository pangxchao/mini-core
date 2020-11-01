package com.mini.core.jdbc.builder;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class NamedReplaceSql extends NamedSql<NamedReplaceSql> implements BaseReplaceSql<NamedReplaceSql> {
    private final ReplaceSql<NamedReplaceSql> impl = new ReplaceSql<>() {
        protected NamedReplaceSql getReplaceSql() {
            return NamedReplaceSql.this;
        }
    };

    private NamedReplaceSql() {
    }

    public NamedReplaceSql REPLACE_INTO(String table) {
        return impl.REPLACE_INTO(table);
    }

    public NamedReplaceSql VALUES(String column, String value) {
        return impl.VALUES(column, value);
    }

    public NamedReplaceSql VALUES(String column) {
        return impl.VALUES(column);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static NamedReplaceSql of(Consumer<NamedReplaceSql> consumer) {
        NamedReplaceSql builder = new NamedReplaceSql();
        consumer.accept(builder);
        return builder;
    }

    public static NamedReplaceSql of() {
        return new NamedReplaceSql();
    }
}
