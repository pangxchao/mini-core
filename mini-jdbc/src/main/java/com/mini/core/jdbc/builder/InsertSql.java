package com.mini.core.jdbc.builder;


import com.mini.core.jdbc.builder.fragment.InsertFragment;

import com.mini.core.jdbc.builder.statement.ColumnStatement;

import com.mini.core.jdbc.builder.statement.ColumnStatement.ColumnStatementImpl;

import com.mini.core.jdbc.builder.statement.OnDuplicateKeyUpdateStatement;

import com.mini.core.jdbc.builder.statement.OnDuplicateKeyUpdateStatement.OnDuplicateKeyUpdateStatementImpl;

import com.mini.core.jdbc.builder.statement.TableStatement;

import com.mini.core.jdbc.builder.statement.TableStatement.TableStatementImpl;

import com.mini.core.jdbc.builder.statement.ValuesStatement;

import com.mini.core.jdbc.builder.statement.ValuesStatement.ValuesStatementImpl;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public class InsertSql extends AbstractSql<InsertSql> implements InsertFragment<InsertSql> {
    private final OnDuplicateKeyUpdateStatement onDuplicateKeyUpdate = new OnDuplicateKeyUpdateStatementImpl(this);
    private final ColumnStatement column = new ColumnStatementImpl(this);
    private final ValuesStatement values = new ValuesStatementImpl(this);
    private final TableStatement table = new TableStatementImpl(this);

    public final InsertSql insertInto(String table) {
        this.table.addValues(table);
        return this;
    }

    public final InsertSql column(String... columns) {
        this.column.addValues(columns);
        return this;
    }

    public final InsertSql values(String... values) {
        this.values.addValues(values);
        return this;
    }

    @Override
    public final InsertSql setNative(String column, String value, Object... arg) {
        return column(column).values(value).args(arg);
    }

    @Override
    public final InsertSql set(String column, Object arg) {
        return setNative(column, "?", arg);
    }

    @Override
    public InsertSql onKeyNative(String column, String value, Object... args) {
        onDuplicateKeyUpdate.setNative(column, value, args);
        return this;
    }

    @Override
    public InsertSql onKey(String column, Object arg) {
        onDuplicateKeyUpdate.set(column, arg);
        return this;
    }

    @Override
    public InsertSql onKeyFromInsert(@NotNull String column) {
        onDuplicateKeyUpdate.setFromInsert(column);
        return this;
    }

    @Override
    public InsertSql onKeyIncrease(@NotNull String column, Number arg) {
        onDuplicateKeyUpdate.setIncrease(column, arg);
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
}
