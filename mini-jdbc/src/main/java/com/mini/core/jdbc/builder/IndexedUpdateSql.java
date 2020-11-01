package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.statement.JoinStatement;
import com.mini.core.jdbc.builder.statement.SetStatement;
import com.mini.core.jdbc.builder.statement.WhereStatement;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class IndexedUpdateSql extends IndexedSql<IndexedUpdateSql> implements BaseUpdateSql<IndexedUpdateSql> {
    private final UpdateSql<IndexedUpdateSql> impl = new UpdateSql<>() {
        protected IndexedUpdateSql getUpdateSql() {
            return IndexedUpdateSql.this;
        }
    };

    private IndexedUpdateSql() {
    }

    public IndexedUpdateSql UPDATE(String... tables) {
        return impl.UPDATE(tables);
    }

    public IndexedUpdateSql JOIN(String join) {
        return impl.JOIN(join);
    }

    public IndexedUpdateSql INNER_JOIN(String join) {
        return impl.INNER_JOIN(join);
    }

    public IndexedUpdateSql LEFT_JOIN(String join) {
        return impl.LEFT_JOIN(join);
    }

    public IndexedUpdateSql RIGHT_JOIN(String join) {
        return impl.RIGHT_JOIN(join);
    }

    public IndexedUpdateSql LEFT_OUTER_JOIN(String join) {
        return impl.LEFT_OUTER_JOIN(join);
    }

    public IndexedUpdateSql RIGHT_OUTER_JOIN(String join) {
        return impl.RIGHT_OUTER_JOIN(join);
    }

    public IndexedUpdateSql CROSS_JOIN(String join) {
        return impl.CROSS_JOIN(join);
    }

    public IndexedUpdateSql JOIN(Consumer<JoinStatement> consumer) {
        return impl.JOIN(consumer);
    }

    public IndexedUpdateSql SET(String set) {
        return impl.SET(set);
    }

    public IndexedUpdateSql SET(Consumer<SetStatement> consumer) {
        return impl.SET(consumer);
    }

    public IndexedUpdateSql WHERE(String where) {
        return impl.WHERE(where);
    }

    public IndexedUpdateSql WHERE(Consumer<WhereStatement> consumer) {
        return impl.WHERE(consumer);
    }

    @Override
    public final String getSql() {
        return impl.getSql();
    }

    public static IndexedUpdateSql of(Consumer<IndexedUpdateSql> consumer) {
        IndexedUpdateSql builder = new IndexedUpdateSql();
        consumer.accept(builder);
        return builder;
    }

    public static IndexedUpdateSql of() {
        return new IndexedUpdateSql();
    }
}
