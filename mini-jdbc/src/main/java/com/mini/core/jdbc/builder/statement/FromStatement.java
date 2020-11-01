package com.mini.core.jdbc.builder.statement;

import javax.annotation.Nonnull;

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

        @Nonnull
        protected final String getOpen() {
            return "";
        }

        @Nonnull
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
