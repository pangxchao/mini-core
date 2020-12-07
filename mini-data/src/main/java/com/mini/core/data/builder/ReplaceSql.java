package com.mini.core.data.builder;

import com.mini.core.data.builder.fragment.SetFragment;
import com.mini.core.data.builder.statement.ColumnStatement;
import com.mini.core.data.builder.statement.ColumnStatement.ColumnStatementImpl;
import com.mini.core.data.builder.statement.TableStatement;
import com.mini.core.data.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.data.builder.statement.ValuesStatement;
import com.mini.core.data.builder.statement.ValuesStatement.ValuesStatementImpl;

@SuppressWarnings("UnusedReturnValue")
public class ReplaceSql extends AbstractSql<ReplaceSql> implements SetFragment<ReplaceSql> {
    private final ColumnStatement column = new ColumnStatementImpl(this);
    private final ValuesStatement values = new ValuesStatementImpl(this);
    private final TableStatement table = new TableStatementImpl(this);

    public final ReplaceSql replaceInto(String table) {
        this.table.addValues(table);
        return this;
    }

    public final ReplaceSql column(String... columns) {
        this.column.addValues(columns);
        return this;
    }

    public final ReplaceSql values(String... values) {
        this.values.addValues(values);
        return this;
    }

    @Override
    public final ReplaceSql setNative(String column, String values, Object... value) {
        return column(column).values(values).args(value);
    }

    @Override
    public final ReplaceSql set(String column, Object value) {
        return setNative(column, "?", value);
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
