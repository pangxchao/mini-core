package com.mini.core.data.builder.statement;

import com.mini.core.data.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import static java.util.Arrays.stream;

@SuppressWarnings("UnusedReturnValue")
public interface OrderByStatement extends BaseStatement<OrderByStatement> {

    /**
     * 正顺排序
     *
     * @param column 排序字段
     * @return {@code this}
     */
    OrderByStatement asc(@NotNull String... column);

    /**
     * 倒序排序
     *
     * @param column 排序字段
     * @return {@code this}
     */
    OrderByStatement desc(@NotNull String... column);


    final class OrderByStatementImpl extends BaseStatementImpl<OrderByStatement> implements OrderByStatement {
        public OrderByStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "", "");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nORDER BY ";
        }

        @Override
        public OrderByStatement asc(@NotNull String... column) {
            stream(column).forEach(it -> addValues(it + " ASC"));
            return this;
        }

        @Override
        public OrderByStatement desc(@NotNull String... column) {
            stream(column).forEach(it -> addValues(it + " DESC"));
            return this;
        }
    }
}
