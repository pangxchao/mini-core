package com.mini.core.data.builder.statement;

import com.mini.core.data.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface SelectStatement extends BaseStatement<SelectStatement> {


    /**
     * 去重查询
     *
     * @param columns 字段名称
     * @return {@code this}
     */
    SelectStatement distinct(String... columns);

    /**
     * 查询条数
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement count(@NotNull String column, @NotNull String alias);

    /**
     * 查询条数
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement count(@NotNull String column);

    /**
     * 查询和值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement sum(@NotNull String column, @NotNull String alias);

    /**
     * 查询和值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement sum(@NotNull String column);

    /**
     * 查询平均值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement avg(@NotNull String column, @NotNull String alias);

    /**
     * 查询平均值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement avg(@NotNull String column);

    /**
     * 查询最大值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement max(@NotNull String column, @NotNull String alias);

    /**
     * 查询最大值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement max(@NotNull String column);

    /**
     * 查询最小值
     *
     * @param column 字段名称
     * @param alias  字段别名
     * @return @return {@code this}
     */
    SelectStatement min(@NotNull String column, @NotNull String alias);

    /**
     * 查询最小值
     *
     * @param column 字段名称
     * @return @return {@code this}
     */
    SelectStatement min(@NotNull String column);


    final class SelectStatementImpl extends BaseStatementImpl<SelectStatement> implements SelectStatement {
        private String keyword = "SELECT \n";

        public SelectStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "", "");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return keyword;
        }

        @Override
        public final SelectStatement distinct(String... columns) {
            this.keyword = "SELECT DISTINCT \n";
            return addValues(columns);
        }

        @Override
        public final SelectStatement count(@NotNull String column, @NotNull String alias) {
            return addValues(format("COUNT(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement count(@NotNull String column) {
            return count(column, column);
        }

        @Override
        public final SelectStatement sum(@NotNull String column, @NotNull String alias) {
            return addValues(format("SUM(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement sum(@NotNull String column) {
            return sum(column, column);
        }

        @Override
        public final SelectStatement avg(@NotNull String column, @NotNull String alias) {
            return addValues(format("AVG(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement avg(@NotNull String column) {
            return avg(column, column);
        }

        @Override
        public SelectStatement max(@NotNull String column, @NotNull String alias) {
            return addValues(format("MAX(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement max(@NotNull String column) {
            return max(column, column);
        }

        @Override
        public final SelectStatement min(@NotNull String column, @NotNull String alias) {
            return addValues(format("MIN(%s) as `%s`", column, alias));
        }

        @Override
        public final SelectStatement min(@NotNull String column) {
            return min(column, column);
        }
    }
}
