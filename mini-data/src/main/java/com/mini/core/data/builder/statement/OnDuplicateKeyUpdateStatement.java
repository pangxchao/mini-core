package com.mini.core.data.builder.statement;

import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface OnDuplicateKeyUpdateStatement {
    /**
     * 修改字段内容
     *
     * @param set 修改字段内容
     * @return {@code this}
     */
    OnDuplicateKeyUpdateStatement SET(@NotNull String set);

    /**
     * 修改成 Insert 设置的值
     *
     * @param column 字段名称
     * @return {@code this}
     */
    OnDuplicateKeyUpdateStatement FROM_INSERT(@NotNull String column);

    /**
     * 修改成指定值
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    OnDuplicateKeyUpdateStatement EQUALS(@NotNull String column, @NotNull String paramName);

    /**
     * 值自增处理
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    OnDuplicateKeyUpdateStatement INCREASE(@NotNull String column, @NotNull String paramName);


    final class OnDuplicateKeyUpdateStatementImpl extends BaseStatement implements OnDuplicateKeyUpdateStatement {

        public OnDuplicateKeyUpdateStatementImpl() {
            super("\nON DUPLICATE KEY UPDATE ", ",");
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
        public final OnDuplicateKeyUpdateStatement SET(@NotNull String set) {
            this.addValues(set);
            return this;
        }

        @Override
        public final OnDuplicateKeyUpdateStatement FROM_INSERT(@NotNull String column) {
            addValues(format("%s = VALUES(%s)", column, column));
            return this;
        }


        @Override
        public OnDuplicateKeyUpdateStatement EQUALS(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s = %s", column, paramName));
            return this;
        }

        @Override
        public OnDuplicateKeyUpdateStatement INCREASE(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s = %s + %s", column, column, paramName));
            return this;
        }
    }
}
