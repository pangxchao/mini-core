package com.mini.core.data.builder.statement;


import com.mini.core.data.builder.AbstractSql;
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
