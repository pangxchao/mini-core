package com.mini.core.jdbc.builder.statement;

import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface OnDuplicateKeyUpdateStatement extends BaseStatement<OnDuplicateKeyUpdateStatement> {

    OnDuplicateKeyUpdateStatement setNative(String column, String value, Object... args);

    OnDuplicateKeyUpdateStatement set(String column, Object arg);

    /**
     * 修改成 Insert 设置的值
     *
     * @param column 字段名称
     * @return {@code this}
     */
    OnDuplicateKeyUpdateStatement setFromInsert(@NotNull String column);

    /**
     * 值自增处理
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    OnDuplicateKeyUpdateStatement setIncrease(@NotNull String column, Number arg);


    final class OnDuplicateKeyUpdateStatementImpl extends BaseStatementImpl<OnDuplicateKeyUpdateStatement> implements OnDuplicateKeyUpdateStatement {

        public OnDuplicateKeyUpdateStatementImpl(AbstractSql<?> sql) {
            super(sql, ",", "", "");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nON DUPLICATE KEY UPDATE ";
        }

        @Override
        public OnDuplicateKeyUpdateStatement setNative(String column, String value, Object... args) {
            addValues(format("%s = %s", column, value));
            this.sql.args(args);
            return this;
        }

        @Override
        public OnDuplicateKeyUpdateStatement set(String column, Object arg) {
            return setNative(column, "?", arg);
        }

        @Override
        public final OnDuplicateKeyUpdateStatement setFromInsert(@NotNull String column) {
            return setNative(column, format("VALUES(%s)", column));
        }

        @Override
        public OnDuplicateKeyUpdateStatement setIncrease(@NotNull String column, Number arg) {
            return setNative(column, format("%s + ?", column), arg);
        }
    }
}
