package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.statement.ColumnStatement.ColumnStatementImpl;
import com.mini.core.jdbc.builder.statement.OnDuplicateKeyUpdateStatement.OnDuplicateKeyUpdateStatementImpl;
import com.mini.core.jdbc.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.jdbc.builder.statement.ValuesStatement.ValuesStatementImpl;

public interface BaseInsertSql<T extends BaseInsertSql<T>> {

    T INSERT_INTO(String table);

    T VALUES(String column, String value);

    T VALUES(String column);

    abstract class InsertSql<T extends BaseSql<T> & BaseInsertSql<T>> extends BaseSql<T> implements BaseInsertSql<T> {
        private final OnDuplicateKeyUpdateStatementImpl onDuplicateKeyUpdateStatement = new OnDuplicateKeyUpdateStatementImpl();
        private final ColumnStatementImpl column = new ColumnStatementImpl();
        private final ValuesStatementImpl values = new ValuesStatementImpl();
        private final TableStatementImpl table = new TableStatementImpl();

        protected InsertSql() {
        }

        protected abstract T getInsertSql();

        public T INSERT_INTO(String table) {
            this.table.TABLE(table);
            return getInsertSql();
        }

        public T VALUES(String column, String value) {
            this.column.COLUMN(column);
            this.values.VALUES(value);
            return getInsertSql();
        }

        public T VALUES(String column) {
            return VALUES(column, "?");
        }

        @Override
        public final String getSql() {
            var builder = new StringBuilder();
            builder.append("INSERT INTO ");
            this.table.builder(builder);
            this.column.builder(builder);
            this.values.builder(builder);
            onDuplicateKeyUpdateStatement.builder(builder);
            return builder.toString();
        }
    }
}
