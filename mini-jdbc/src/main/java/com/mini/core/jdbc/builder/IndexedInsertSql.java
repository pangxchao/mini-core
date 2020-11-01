package com.mini.core.jdbc.builder;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class IndexedInsertSql extends IndexedSql<IndexedInsertSql> implements BaseInsertSql<IndexedInsertSql> {
    private final InsertSql<IndexedInsertSql> impl = new InsertSql<>() {
        protected IndexedInsertSql getInsertSql() {
            return IndexedInsertSql.this;
        }
    };

    private IndexedInsertSql() {
    }

    public IndexedInsertSql INSERT_INTO(String table) {
        return impl.INSERT_INTO(table);
    }

    public IndexedInsertSql VALUES(String column, String value) {
        return impl.VALUES(column, value);
    }

    public IndexedInsertSql VALUES(String column) {
        return impl.VALUES(column);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static IndexedInsertSql of(Consumer<IndexedInsertSql> consumer) {
        IndexedInsertSql builder = new IndexedInsertSql();
        consumer.accept(builder);
        return builder;
    }

    public static IndexedInsertSql of() {
        return new IndexedInsertSql();
    }
}
