package com.mini.core.data.builder.statement;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public interface WhereStatement extends ConditionStatement<WhereStatement> {
    /**
     * 子条件
     *
     * @param consumer 回调
     * @return {@code this}
     */
    WhereStatement WHERE(Consumer<WhereStatement> consumer);

    /**
     * 子条件
     *
     * @param children 子条件
     * @return {@code this}
     */
    WhereStatement WHERE(String children);

    final class WhereStatementImpl extends ConditionStatementImpl<WhereStatement> implements WhereStatement {

        private WhereStatementImpl(String word) {
            super(word);
        }

        public WhereStatementImpl() {
            super("\nWHERE ");
        }

        @Override
        protected final WhereStatementImpl getThis() {
            return this;
        }

        @Override
        public final WhereStatement WHERE(Consumer<WhereStatement> consumer) {
            var where = new WhereStatementImpl("");
            var builder = new StringBuilder();
            consumer.accept(where);
            where.builder(builder);
            return WHERE(builder);
        }

        private WhereStatement WHERE(StringBuilder children) {
            return WHERE(children.toString());
        }

        @Override
        public final WhereStatement WHERE(String children) {
            this.addValues(children);
            return this;
        }
    }
}
