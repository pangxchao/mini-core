package com.mini.core.data.builder.statement;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface FromStatement {
    /**
     * 表名称
     *
     * @param tables 表名称列表
     * @return {@code this}
     */
    FromStatement FROM(String... tables);

    final class FromStatementImpl extends BaseStatement implements FromStatement {

        public FromStatementImpl() {
            super("\nFROM ", ", ");
        }

        @NotNull
        protected final String getOpen() {
            return "";
        }

        @NotNull
        protected final String getClose() {
            return " ";
        }

        @Override
        public final FromStatement FROM(String... tables) {
            this.addValues(tables);
            return this;
        }
    }
}
