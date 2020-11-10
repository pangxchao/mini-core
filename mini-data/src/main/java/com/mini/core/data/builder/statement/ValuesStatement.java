package com.mini.core.data.builder.statement;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface ValuesStatement {
    /**
     * 新增字段
     *
     * @param values 字段列表
     * @return {@code this}
     */
    ValuesStatement VALUES(String... values);

    final class ValuesStatementImpl extends BaseStatement implements ValuesStatement {
        public ValuesStatementImpl() {
            super("\nVALUES ", ", ");
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
        public final ValuesStatement VALUES(String... values) {
            addValues(values);
            return this;
        }
    }
}
