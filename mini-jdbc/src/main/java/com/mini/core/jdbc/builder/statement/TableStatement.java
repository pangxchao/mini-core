package com.mini.core.jdbc.builder.statement;

import javax.annotation.Nonnull;

@SuppressWarnings("UnusedReturnValue")
public interface TableStatement {
    /**
     * 表名称
     *
     * @param tables 表名称列表
     * @return {@code this}
     */
    TableStatement TABLE(String... tables);

    final class TableStatementImpl extends BaseStatement implements TableStatement {

        public TableStatementImpl() {
            super("", ", ");
        }

        @Nonnull
        protected final String getOpen() {
            return "";
        }

        @Nonnull
        protected final String getClose() {
            return "";
        }

        @Override
        public final TableStatement TABLE(String... tables) {
            this.addValues(tables);
            return this;
        }
    }
}
