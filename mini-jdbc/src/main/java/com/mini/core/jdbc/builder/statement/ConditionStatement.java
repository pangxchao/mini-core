package com.mini.core.jdbc.builder.statement;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static java.lang.String.format;
import static java.lang.String.join;

@SuppressWarnings("UnusedReturnValue")
public interface ConditionStatement<T extends ConditionStatement<T>> {

    /**
     * 添加“AND”连接符
     *
     * @return {@code this}
     */
    T AND();

    /**
     * 添加“OR”连接符
     *
     * @return {@code this}
     */
    T OR();

    /**
     * “=”条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T EQUALS(@NotNull String column, @NotNull String paramName);


    /**
     * “<>”条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T NOT_EQUALS(@NotNull String column, @NotNull String paramName);

    /**
     * “>”条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T GREATER_THAN(@NotNull String column, @NotNull String paramName);


    /**
     * “<”条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T LESS_THAN(@NotNull String column, @NotNull String paramName);

    /**
     * “>=”条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T GREATER_THAN_EQUALS(@NotNull String column, @NotNull String paramName);

    /**
     * “<=”条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T LESS_THAN_EQUALS(@NotNull String column, @NotNull String paramName);


    /**
     * "IN" 条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T IN(@NotNull String column, @NotNull String paramName);


    /**
     * "LIKE" 条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T LIKE(@NotNull String column, @NotNull String paramName);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T MATCH(@NotNull String[] column, @NotNull String paramName);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param column    字段名称
     * @param paramName 参数名称
     * @return {@code this}
     */
    T MATCH_IN_BOOLEAN_MODE(@NotNull String[] column, @NotNull String paramName);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column       字段名称
     * @param minParamName 小参数名称
     * @param maxParamName 大参数名称
     * @return {@code this}
     */
    T BETWEEN_AND(@NotNull String column, @NotNull String minParamName, @NotNull String maxParamName);


    abstract class ConditionStatementImpl<T extends ConditionStatement<T>> extends BaseStatement implements ConditionStatement<T> {
        protected ConditionStatementImpl(String word) {
            super(word, AND);
        }

        @Nonnull
        protected final String getOpen() {
            return "(";
        }

        @Nonnull
        protected final String getClose() {
            return ") ";
        }

        protected abstract T getThis();

        @Override
        public final T AND() {
            if (!values.isEmpty()) {
                addValues(AND);
            }
            return getThis();
        }

        @Override
        public final T OR() {
            if (!this.values.isEmpty()) {
                this.addValues(OR);
            }
            return getThis();
        }

        @Override
        public T EQUALS(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s = %s", column, paramName));
            return getThis();
        }

        @Override
        public T NOT_EQUALS(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s <> %s", column, paramName));
            return getThis();
        }

        @Override
        public T GREATER_THAN(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s > %s", column, paramName));
            return getThis();
        }

        @Override
        public T LESS_THAN(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s < %s", column, paramName));
            return getThis();
        }

        @Override
        public T GREATER_THAN_EQUALS(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s >= %s", column, paramName));
            return getThis();
        }

        @Override
        public T LESS_THAN_EQUALS(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s <= %s", column, paramName));
            return getThis();
        }

        @Override
        public T IN(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s IN (%s)", column, paramName));
            return getThis();
        }

        @Override
        public T LIKE(@NotNull String column, @NotNull String paramName) {
            addValues(format("%s LIKE %s", column, paramName));
            return getThis();
        }

        @Override
        public T MATCH(@NotNull String[] column, @NotNull String paramName) {
            addValues(format("MATCH(%s) AGAINST(%s)", join(",", column), paramName));
            return getThis();
        }

        @Override
        public T MATCH_IN_BOOLEAN_MODE(@NotNull String[] column, @NotNull String paramName) {
            addValues(format("MATCH(%s) AGAINST(%s IN BOOLEAN MODE)", join(",", column), paramName));
            return getThis();
        }

        @Override
        public T BETWEEN_AND(@NotNull String column, @NotNull String minParamName, @NotNull String maxParamName) {
            addValues(format("%s BETWEEN %s AND %s", column, minParamName, maxParamName));
            return getThis();
        }
    }
}
