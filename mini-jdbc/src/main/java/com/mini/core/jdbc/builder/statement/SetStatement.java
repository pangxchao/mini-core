package com.mini.core.jdbc.builder.statement;

import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface SetStatement extends BaseStatement<SetStatement> {

    SetStatement setNative(String column, String value, Object... args);

    SetStatement set(String column, Object arg);

    SetStatement setIncrease(@NotNull String column, Object arg);

    final class SetStatementImpl extends BaseStatementImpl<SetStatement> implements SetStatement {

        public SetStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "", " ");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nSET ";
        }

        @Override
        public SetStatement setNative(String column, String value, Object... args) {
            this.addValues(format("%s = %s", column, value));
            this.sql.args(args);
            return null;
        }

        @Override
        public SetStatement set(String column, Object arg) {
            return setNative(column, "?", arg);
        }

        @Override
        public SetStatement setIncrease(@NotNull String column, Object arg) {
            return setNative(column, column + " + ?", arg);
        }
    }
}
