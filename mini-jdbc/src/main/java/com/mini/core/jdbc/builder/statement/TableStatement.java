package com.mini.core.jdbc.builder.statement;


import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface TableStatement extends BaseStatement<TableStatement> {

    final class TableStatementImpl extends BaseStatementImpl<TableStatement> implements TableStatement {
        public TableStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "", "");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "";
        }
    }
}
