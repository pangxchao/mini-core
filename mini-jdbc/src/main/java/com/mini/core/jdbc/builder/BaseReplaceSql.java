package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.statement.ColumnStatement.ColumnStatementImpl;
import com.mini.core.jdbc.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.jdbc.builder.statement.ValuesStatement.ValuesStatementImpl;

public interface BaseReplaceSql<T extends BaseReplaceSql<T>> {

    T REPLACE_INTO(String table);

    T VALUES(String column, String value);

    T VALUES(String column);

    abstract class ReplaceSql<T extends BaseSql<T> & BaseReplaceSql<T>> extends BaseSql<T> implements BaseReplaceSql<T> {
        private final ColumnStatementImpl column = new ColumnStatementImpl();
        private final ValuesStatementImpl values = new ValuesStatementImpl();
        private final TableStatementImpl table = new TableStatementImpl();

        protected ReplaceSql() {
        }

        protected abstract T getReplaceSql();

        public T REPLACE_INTO(String table) {
            this.table.TABLE(table);
            return getReplaceSql();
        }

        public T VALUES(String column, String value) {
            this.column.COLUMN(column);
            this.values.VALUES(value);
            return getReplaceSql();
        }

        public T VALUES(String column) {
            return VALUES(column, "?");
        }

        @Override
        public final String getSql() {
            var builder = new StringBuilder();
            builder.append("REPLACE INTO ");
            this.table.builder(builder);
            this.column.builder(builder);
            this.values.builder(builder);
            return builder.toString();
        }
    }
}
