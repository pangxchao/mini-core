package com.mini.core.data.builder.statement;


import org.jetbrains.annotations.NotNull;

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

        @NotNull
        protected final String getOpen() {
            return "";
        }

        @NotNull
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
