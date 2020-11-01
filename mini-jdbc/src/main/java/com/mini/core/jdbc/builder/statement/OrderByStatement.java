package com.mini.core.jdbc.builder.statement;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static java.util.Arrays.stream;

@SuppressWarnings("UnusedReturnValue")
public interface OrderByStatement {
    /**
     * 排序
     *
     * @param column 排序字段
     * @return {@code this}
     */
    OrderByStatement ORDER_BY(@NotNull String... column);

    /**
     * 正顺排序
     *
     * @param column 排序字段
     * @return {@code this}
     */
    OrderByStatement ASC(@NotNull String... column);

    /**
     * 倒序排序
     *
     * @param column 排序字段
     * @return {@code this}
     */
    OrderByStatement DESC(@NotNull String... column);


    final class OrderByStatementImpl extends BaseStatement implements OrderByStatement {
        public OrderByStatementImpl() {
            super("\nORDER BY ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }

        @Override
        public final OrderByStatement ORDER_BY(@NotNull String... column) {
            this.addValues(column);
            return this;
        }

        @Override
        public OrderByStatement ASC(@NotNull String... column) {
            stream(column).forEach(it -> addValues(it + " ASC"));
            return this;
        }

        @Override
        public OrderByStatement DESC(@NotNull String... column) {
            stream(column).forEach(it -> addValues(it + " DESC"));
            return this;
        }
    }
}
