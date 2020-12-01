package com.mini.core.data.builder;

import com.mini.core.data.builder.fragment.JoinFragment;
import com.mini.core.data.builder.statement.FromStatement.FromStatementImpl;
import com.mini.core.data.builder.statement.JoinOnStatement;
import com.mini.core.data.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.data.builder.statement.WhereStatement;
import com.mini.core.data.builder.statement.WhereStatement.WhereStatementImpl;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class DeleteSql extends AbstractSql<DeleteSql> implements JoinFragment<DeleteSql> {
    private final WhereStatementImpl where = new WhereStatementImpl(this);
    private final TableStatementImpl table = new TableStatementImpl(this);
    private final FromStatementImpl from = new FromStatementImpl(this);
    private final JoinFragmentImpl join = new JoinFragmentImpl(this);

    public final DeleteSql delete(String... tables) {
        this.table.addValues(tables);
        return this;
    }

    public final DeleteSql from(String... tables) {
        this.from.addValues(tables);
        return this;
    }

    @Override
    public final DeleteSql join(String join) {
        this.join.join(join);
        return this;
    }

    @Override
    public final DeleteSql join(String table, String column, String target) {
        this.join.join(table, column, table);
        return this;
    }

    @Override
    public final DeleteSql join(String table, Consumer<JoinOnStatement> consumer) {
        this.join.join(table, consumer);
        return this;
    }

    @Override
    public final DeleteSql leftJoin(String join) {
        this.join.leftJoin(join);
        return this;
    }

    @Override
    public final DeleteSql leftJoin(String table, String column, String target) {
        this.join.leftJoin(table, column, target);
        return this;
    }

    @Override
    public final DeleteSql leftJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftJoin(table, consumer);
        return this;
    }

    @Override
    public final DeleteSql rightJoin(String join) {
        this.join.rightJoin(join);
        return this;
    }

    @Override
    public final DeleteSql rightJoin(String table, String column, String target) {
        this.join.rightJoin(table, column, target);
        return this;
    }

    @Override
    public final DeleteSql rightJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightJoin(table, consumer);
        return this;
    }

    @Override
    public final DeleteSql leftOuterJoin(String join) {
        this.join.leftOuterJoin(join);
        return this;
    }

    @Override
    public final DeleteSql leftOuterJoin(String table, String column, String target) {
        this.join.leftOuterJoin(table, column, target);
        return this;
    }

    @Override
    public final DeleteSql leftOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftOuterJoin(table, consumer);
        return this;
    }

    @Override
    public final DeleteSql rightOuterJoin(String join) {
        this.join.rightOuterJoin(join);
        return this;
    }

    @Override
    public final DeleteSql rightOuterJoin(String table, String column, String target) {
        this.join.rightOuterJoin(table, column, target);
        return this;
    }

    @Override
    public final DeleteSql rightOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightOuterJoin(table, consumer);
        return this;
    }

    @Override
    public final DeleteSql crossJoin(String join) {
        this.join.crossJoin(join);
        return this;
    }

    @Override
    public final DeleteSql crossJoin(String table, String column, String target) {
        this.join.crossJoin(table, column, target);
        return this;
    }

    @Override
    public final DeleteSql crossJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.crossJoin(table, consumer);
        return this;
    }

    public final DeleteSql where(Consumer<WhereStatement> consumer) {
        consumer.accept(this.where);
        return this;
    }

    @Override
    public final String getSql() {
        var builder = new StringBuilder("DELETE ");
        this.table.builder(builder);
        this.from.builder(builder);
        this.join.builder(builder);
        this.where.builder(builder);
        return builder.toString();
    }


}
