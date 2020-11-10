package com.mini.core.data.builder;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class IndexedReplaceSql extends IndexedSql<IndexedReplaceSql> implements BaseReplaceSql<IndexedReplaceSql> {
    private final ReplaceSql<IndexedReplaceSql> impl = new ReplaceSql<>() {
        protected IndexedReplaceSql getReplaceSql() {
            return IndexedReplaceSql.this;
        }
    };

    private IndexedReplaceSql() {
    }

    public IndexedReplaceSql REPLACE_INTO(String table) {
        return impl.REPLACE_INTO(table);
    }

    public IndexedReplaceSql VALUES(String column, String value) {
        return impl.VALUES(column, value);
    }

    public IndexedReplaceSql VALUES(String column) {
        return impl.VALUES(column);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static IndexedReplaceSql of(Consumer<IndexedReplaceSql> consumer) {
        IndexedReplaceSql builder = new IndexedReplaceSql();
        consumer.accept(builder);
        return builder;
    }

    public static IndexedReplaceSql of() {
        return new IndexedReplaceSql();
    }
}
