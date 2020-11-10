package com.mini.core.data.builder.statement;

import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface SetStatement {
    /**
     * 修改字段内容
     *
     * @param set 修改字段内容
     * @return {@code this}
     */
    SetStatement SET(@NotNull String set);

    /**
     * 修改成指定值
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    SetStatement EQUALS(@NotNull String column, String paramName);

    /**
     * 值自增处理
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    SetStatement INCREASE(@NotNull String column, String paramName);

    final class SetStatementImpl extends BaseStatement implements SetStatement {

        public SetStatementImpl() {
            super("\nSET ", ", ");
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
        public final SetStatement SET(@NotNull String set) {
            this.addValues(set);
            return this;
        }

        @Override
        public SetStatement EQUALS(@NotNull String column, String paramName) {
            addValues(format("%s = %s", column, paramName));
            return this;
        }

        @Override
        public SetStatement INCREASE(@NotNull String column, String paramName) {
            addValues(format("%s = %s + %s", column, column, paramName));
            return this;
        }
    }
}
