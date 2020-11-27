package com.mini.core.data.builder.statement;

import com.mini.core.data.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface GroupByStatement extends BaseStatement<GroupByStatement> {

    final class GroupByStatementImpl extends BaseStatementImpl<GroupByStatement> implements GroupByStatement {
        public GroupByStatementImpl(AbstractSql<?> sql) {
            super(sql, ", ", "", "");
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nGROUP BY ";
        }

        @NotNull
        public final String getOpen() {
            return "";
        }

        @NotNull
        public final String getClose() {
            return " ";
        }
    }
}
