package com.mini.core.jdbc.builder.statement;

import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public interface WhereStatement extends FilterStatement<WhereStatement> {
    /**
     * 子条件
     *
     * @param consumer 回调
     * @return {@code this}
     */
    WhereStatement where(Consumer<WhereStatement> consumer);

    /**
     * 子条件
     *
     * @param children 子条件
     * @return {@code this}
     */
    WhereStatement where(String children);

    final class WhereStatementImpl extends FilterStatementImpl<WhereStatement> implements WhereStatement {
        private final String keyword;

        private WhereStatementImpl(AbstractSql<?> sql, String keyword) {
            super(sql);
            this.keyword = keyword;
        }

        public WhereStatementImpl(AbstractSql<?> sql) {
            this(sql, "\nWHERE ");
        }

        @NotNull
        @Override
        public final String getKeyword() {
            return keyword;
        }

        @Override
        protected final WhereStatementImpl self() {
            return this;
        }

        @Override
        public final WhereStatement where(Consumer<WhereStatement> consumer) {
            var where = new WhereStatementImpl(sql, "");
            var builder = new StringBuilder();
            consumer.accept(where);
            where.builder(builder);
            return where(builder);
        }

        private WhereStatement where(StringBuilder children) {
            return where(children.toString());
        }

        @Override
        public final WhereStatement where(String children) {
            this.addValues(children);
            return this;
        }
    }
}
