package com.mini.core.data.builder;

import com.mini.core.data.builder.statement.ColumnStatement;
import com.mini.core.data.builder.statement.ColumnStatement.ColumnStatementImpl;
import com.mini.core.data.builder.statement.TableStatement;
import com.mini.core.data.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.data.builder.statement.ValuesStatement;
import com.mini.core.data.builder.statement.ValuesStatement.ValuesStatementImpl;

import java.util.EventListener;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public abstract class ReplaceSql extends AbstractSql<ReplaceSql> implements EventListener {
    private final ColumnStatement column = new ColumnStatementImpl(this);
    private final ValuesStatement values = new ValuesStatementImpl(this);
    private final TableStatement table = new TableStatementImpl(this);

    protected ReplaceSql() {
    }

    public ReplaceSql replaceInto(String table) {
        this.table.addValues(table);
        return this;
    }

    public ReplaceSql column(String... columns) {
        this.column.addValues(columns);
        return this;
    }

    public ReplaceSql values(String... values) {
        this.values.addValues(values);
        return this;
    }

    public ReplaceSql setNative(String column, String values, Object... value) {
        return column(column).values(values).args(value);
    }

    public ReplaceSql set(String column, Object value) {
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
