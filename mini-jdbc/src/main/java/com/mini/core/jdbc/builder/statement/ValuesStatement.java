package com.mini.core.jdbc.builder.statement;


import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface ValuesStatement extends BaseStatement<ValuesStatement> {
    final class ValuesStatementImpl extends BaseStatementImpl<ValuesStatement> implements ValuesStatement {
        public ValuesStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "(", ") ");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nVALUES ";
        }
    }
}
