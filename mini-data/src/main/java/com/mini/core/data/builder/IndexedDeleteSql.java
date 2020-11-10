package com.mini.core.data.builder;

import com.mini.core.data.builder.statement.JoinStatement;
import com.mini.core.data.builder.statement.WhereStatement;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class IndexedDeleteSql extends IndexedSql<IndexedDeleteSql> implements BaseDeleteSql<IndexedDeleteSql> {
    private final DeleteSqlImpl<IndexedDeleteSql> impl = new DeleteSqlImpl<>() {
        protected IndexedDeleteSql getDeleteSql() {
            return IndexedDeleteSql.this;
        }
    };

    public IndexedDeleteSql() {
    }

    public final IndexedDeleteSql DELETE(String... tables) {
        return impl.DELETE(tables);
    }

    public IndexedDeleteSql FROM(String... tables) {
        return impl.FROM(tables);
    }

    public IndexedDeleteSql JOIN(String join) {
        return impl.JOIN(join);
    }

    public IndexedDeleteSql INNER_JOIN(String join) {
        return impl.INNER_JOIN(join);
    }

    public IndexedDeleteSql LEFT_JOIN(String join) {
        return impl.LEFT_JOIN(join);
    }

    public IndexedDeleteSql RIGHT_JOIN(String join) {
        return impl.RIGHT_JOIN(join);
    }

    public IndexedDeleteSql LEFT_OUTER_JOIN(String join) {
        return impl.LEFT_OUTER_JOIN(join);
    }

    public IndexedDeleteSql RIGHT_OUTER_JOIN(String join) {
        return impl.RIGHT_OUTER_JOIN(join);
    }

    public IndexedDeleteSql CROSS_JOIN(String join) {
        return impl.CROSS_JOIN(join);
    }

    public IndexedDeleteSql JOIN(Consumer<JoinStatement> consumer) {
        return impl.JOIN(consumer);
    }

    public IndexedDeleteSql WHERE(String where) {
        return impl.WHERE(where);
    }

    public IndexedDeleteSql WHERE(Consumer<WhereStatement> consumer) {
        return impl.WHERE(consumer);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static IndexedDeleteSql of(Consumer<IndexedDeleteSql> consumer) {
        IndexedDeleteSql builder = new IndexedDeleteSql();
        consumer.accept(builder);
        return builder;
    }

    public static IndexedDeleteSql of() {
        return new IndexedDeleteSql();
    }
}
