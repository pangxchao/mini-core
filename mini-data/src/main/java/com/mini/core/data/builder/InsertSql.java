package com.mini.core.data.builder;

import com.mini.core.data.builder.statement.ColumnStatement;
import com.mini.core.data.builder.statement.ColumnStatement.ColumnStatementImpl;
import com.mini.core.data.builder.statement.OnDuplicateKeyUpdateStatement;
import com.mini.core.data.builder.statement.OnDuplicateKeyUpdateStatement.OnDuplicateKeyUpdateStatementImpl;
import com.mini.core.data.builder.statement.TableStatement;
import com.mini.core.data.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.data.builder.statement.ValuesStatement;
import com.mini.core.data.builder.statement.ValuesStatement.ValuesStatementImpl;

import java.util.EventListener;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public final class InsertSql extends AbstractSql<InsertSql> implements EventListener {
    private final OnDuplicateKeyUpdateStatement onDuplicateKeyUpdate = new OnDuplicateKeyUpdateStatementImpl(this);
    private final ColumnStatement column = new ColumnStatementImpl(this);
    private final ValuesStatement values = new ValuesStatementImpl(this);
    private final TableStatement table = new TableStatementImpl(this);

    private String tableName;

    private InsertSql() {
    }

    public InsertSql insertInto(String table) {
        this.table.addValues(table);
        return this;
    }

    public InsertSql column(String... columns) {
        this.column.addValues(columns);
        return this;
    }

    public InsertSql values(String... values) {
        this.values.addValues(values);
        return this;
    }

    public InsertSql setNative(String column, String value, Object... arg) {
        return column(column).values(value).args(arg);
    }

    public InsertSql set(String column, Object arg) {
        return setNative(column, "?", arg);
    }

    public InsertSql onDuplicateKeyUpdate(Consumer<OnDuplicateKeyUpdateStatement> consumer) {
        consumer.accept(onDuplicateKeyUpdate);
        return this;
    }

    @Override
    public final String getSql() {
        var builder = new StringBuilder();
        builder.append("INSERT INTO ");
        this.table.builder(builder);
        this.column.builder(builder);
        this.values.builder(builder);
        onDuplicateKeyUpdate.builder(builder);
        return builder.toString();
    }

    public static InsertSql of(Consumer<InsertSql> consumer) {
        InsertSql builder = new InsertSql();
        consumer.accept(builder);
        return builder;
    }

    public static InsertSql of() {
        return new InsertSql();
    }
}
