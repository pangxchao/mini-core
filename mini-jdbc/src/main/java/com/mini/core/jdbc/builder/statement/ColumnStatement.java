package com.mini.core.jdbc.builder.statement;


import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface ColumnStatement extends BaseStatement<ColumnStatement> {

    final class ColumnStatementImpl extends BaseStatementImpl<ColumnStatement> implements ColumnStatement {
        public ColumnStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "(", ")");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "";
        }
    }
}
