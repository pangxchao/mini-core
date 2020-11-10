package com.mini.core.data.builder.statement;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface ColumnStatement {
    /**
     * 新增字段
     *
     * @param columns 字段列表
     * @return {@code this}
     */
    ColumnStatement COLUMN(String... columns);

    final class ColumnStatementImpl extends BaseStatement implements ColumnStatement {
        public ColumnStatementImpl() {
            super("", ", ");
        }

        @NotNull
        public final String getOpen() {
            return "(";
        }

        @NotNull
        public final String getClose() {
            return ") ";
        }

        @Override
        public final ColumnStatement COLUMN(String... columns) {
            addValues(columns);
            return this;
        }
    }
}
