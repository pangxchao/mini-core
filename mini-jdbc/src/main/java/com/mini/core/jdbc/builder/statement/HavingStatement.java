package com.mini.core.jdbc.builder.statement;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public interface HavingStatement extends ConditionStatement<HavingStatement> {
    /**
     * 子条件
     *
     * @param consumer 回调
     * @return {@code this}
     */
    HavingStatement HAVING(Consumer<HavingStatement> consumer);

    /**
     * 子条件
     *
     * @param children 子条件
     * @return {@code this}
     */
    HavingStatement HAVING(String children);

    final class HavingStatementImpl extends ConditionStatementImpl<HavingStatement> implements HavingStatement {

        protected HavingStatementImpl(String word) {
            super(word);
        }

        public HavingStatementImpl() {
            super("\nHAVING ");
        }

        @Override
        protected final HavingStatement getThis() {
            return this;
        }

        @Override
        public final HavingStatement HAVING(Consumer<HavingStatement> consumer) {
            HavingStatementImpl where = new HavingStatementImpl("");
            final StringBuilder builder = new StringBuilder();
            addValues(where.builder(builder).toString());
            return getThis();
        }

        @Override
        public final HavingStatement HAVING(String children) {
            this.addValues(children);
            return getThis();
        }
    }
}
