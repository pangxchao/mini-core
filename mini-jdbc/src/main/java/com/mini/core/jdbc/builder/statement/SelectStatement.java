package com.mini.core.jdbc.builder.statement;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface SelectStatement {

    /**
     * 去重查询
     *
     * @return {@code this}
     */
    SelectStatement DISTINCT();

    /**
     * 指定查询字段
     *
     * @param columns 字段名称
     * @return @return {@code this}
     */
    SelectStatement SELECT(String... columns);

    /**
     * 查询条数
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement COUNT(@NotNull String column, @NotNull String alias);

    /**
     * 查询条数
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement COUNT(@NotNull String column);

    /**
     * 查询和值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement SUM(@NotNull String column, @Nonnull String alias);

    /**
     * 查询和值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement SUM(@NotNull String column);

    /**
     * 查询平均值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement AVG(@NotNull String column, @NotNull String alias);

    /**
     * 查询平均值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement AVG(@NotNull String column);

    /**
     * 查询最大值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement MAX(@NotNull String column, @NotNull String alias);

    /**
     * 查询最大值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement MAX(@NotNull String column);

    /**
     * 查询最小值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement MIN(@NotNull String column, @NotNull String alias);

    /**
     * 查询最小值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement MIN(@NotNull String column);


    final class SelectStatementImpl extends BaseStatement implements SelectStatement {
        private boolean distinct = false;

        public SelectStatementImpl() {
            super("SELECT ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return distinct ? "DISTINCT " : "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }

        @Override
        public final SelectStatement DISTINCT() {
            distinct = true;
            return this;
        }

        @Override
        public final SelectStatement SELECT(String... columns) {
            if (columns != null && columns.length > 0) {
                columns[0] = "\n\t" + columns[0];
                super.addValues(columns);
            }
            return this;
        }

        @Override
        public final SelectStatement COUNT(@NotNull String column, @NotNull String alias) {
            return SELECT(format("COUNT(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement COUNT(@NotNull String column) {
            return COUNT(column, column);
        }

        @Override
        public final SelectStatement SUM(@NotNull String column, @NotNull String alias) {
            return SELECT(format("SUM(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement SUM(@NotNull String column) {
            return SUM(column, column);
        }

        @Override
        public final SelectStatement AVG(@NotNull String column, @NotNull String alias) {
            return SELECT(format("AVG(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement AVG(@NotNull String column) {
            return AVG(column, column);
        }

        @Override
        public SelectStatement MAX(@NotNull String column, @NotNull String alias) {
            return SELECT(format("MAX(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement MAX(@NotNull String column) {
            return MAX(column, column);
        }

        @Override
        public final SelectStatement MIN(@NotNull String column, @NotNull String alias) {
            return SELECT(format("MIN(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement MIN(@NotNull String column) {
            return MIN(column, column);
        }
    }
}
