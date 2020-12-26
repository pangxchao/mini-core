package com.mini.core.jdbc.builder.statement;


import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface FromStatement extends BaseStatement<FromStatement> {

    final class FromStatementImpl extends BaseStatementImpl<FromStatement> implements FromStatement {

        public FromStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "", "");
        }

        @NotNull
        protected final String getKeyword() {
            return "\nFROM ";
        }
    }
}
