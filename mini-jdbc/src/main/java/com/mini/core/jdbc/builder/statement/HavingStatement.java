package com.mini.core.jdbc.builder.statement;

import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public interface HavingStatement extends FilterStatement<HavingStatement> {
    /**
     * 子条件
     *
     * @param consumer 回调
     * @return {@code this}
     */
    HavingStatement having(Consumer<HavingStatement> consumer);

    /**
     * 子条件
     *
     * @param children 子条件
     * @return {@code this}
     */
    HavingStatement having(String children);

    final class HavingStatementImpl extends FilterStatementImpl<HavingStatement> implements HavingStatement {
        private final String keyword;

        private HavingStatementImpl(AbstractSql<?> sql, String keyword) {
            super(sql);
            this.keyword = keyword;
        }

        public HavingStatementImpl(AbstractSql<?> sql) {
            this(sql, "\nHAVING ");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return keyword;
        }

        @Override
        protected final HavingStatement self() {
            return this;
        }

        @Override
        public final HavingStatement having(Consumer<HavingStatement> consumer) {
            var having = new HavingStatementImpl(sql, "");
            var builder = new StringBuilder();
            consumer.accept(having);
            having.builder(builder);
            return having(builder);
        }

        private HavingStatement having(StringBuilder children) {
            return having(children.toString());
        }

        @Override
        public final HavingStatement having(String children) {
            this.addValues(children);
            return self();
        }
    }
}
