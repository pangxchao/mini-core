package com.mini.core.data.builder.statement;


import com.mini.core.data.builder.AbstractSql;
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
