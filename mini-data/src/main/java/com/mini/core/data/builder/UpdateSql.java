package com.mini.core.data.builder;

import com.mini.core.data.builder.fragment.JoinFragment;
import com.mini.core.data.builder.statement.JoinOnStatement;
import com.mini.core.data.builder.statement.SetStatement;
import com.mini.core.data.builder.statement.SetStatement.SetStatementImpl;
import com.mini.core.data.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.data.builder.statement.WhereStatement;
import com.mini.core.data.builder.statement.WhereStatement.WhereStatementImpl;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class UpdateSql extends AbstractSql<UpdateSql> implements JoinFragment<UpdateSql> {
    private final WhereStatementImpl where = new WhereStatementImpl(this);
    private final TableStatementImpl table = new TableStatementImpl(this);
    private final JoinFragmentImpl join = new JoinFragmentImpl(this);
    private final SetStatementImpl set = new SetStatementImpl(this);

    public final UpdateSql update(String... tables) {
        this.table.addValues(tables);
        return this;
    }

    @Override
    public final UpdateSql join(String join) {
        this.join.join(join);
        return this;
    }

    @Override
    public final UpdateSql join(String table, String column, String target) {
        this.join.join(table, column, table);
        return this;
    }

    @Override
    public final UpdateSql join(String table, Consumer<JoinOnStatement> consumer) {
        this.join.join(table, consumer);
        return this;
    }

    @Override
    public final UpdateSql leftJoin(String join) {
        this.join.leftJoin(join);
        return this;
    }

    @Override
    public final UpdateSql leftJoin(String table, String column, String target) {
        this.join.leftJoin(table, column, target);
        return this;
    }

    @Override
    public final UpdateSql leftJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftJoin(table, consumer);
        return this;
    }

    @Override
    public final UpdateSql rightJoin(String join) {
        this.join.rightJoin(join);
        return this;
    }

    @Override
    public final UpdateSql rightJoin(String table, String column, String target) {
        this.join.rightJoin(table, column, target);
        return this;
    }

    @Override
    public final UpdateSql rightJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightJoin(table, consumer);
        return this;
    }

    @Override
    public final UpdateSql leftOuterJoin(String join) {
        this.join.leftOuterJoin(join);
        return this;
    }

    @Override
    public final UpdateSql leftOuterJoin(String table, String column, String target) {
        this.join.leftOuterJoin(table, column, target);
        return this;
    }

    @Override
    public final UpdateSql leftOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftOuterJoin(table, consumer);
        return this;
    }

    @Override
    public final UpdateSql rightOuterJoin(String join) {
        this.join.rightOuterJoin(join);
        return this;
    }

    @Override
    public final UpdateSql rightOuterJoin(String table, String column, String target) {
        this.join.rightOuterJoin(table, column, target);
        return this;
    }

    @Override
    public final UpdateSql rightOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightOuterJoin(table, consumer);
        return this;
    }

    @Override
    public final UpdateSql crossJoin(String join) {
        this.join.crossJoin(join);
        return this;
    }

    @Override
    public final UpdateSql crossJoin(String table, String column, String target) {
        this.join.crossJoin(table, column, target);
        return this;
    }

    @Override
    public final UpdateSql crossJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.crossJoin(table, consumer);
        return this;
    }

    public final UpdateSql set(Consumer<SetStatement> consumer) {
        consumer.accept(this.set);
        return this;
    }

    public final UpdateSql setNative(String column, String value, Object... args) {
        this.set.setNative(column, value, args);
        return this;
    }

    public final UpdateSql set(String column, Object arg) {
        this.set.set(column, arg);
        return this;
    }

    public final UpdateSql setIncrease(String column, Object arg) {
        this.set.setIncrease(column, arg);
        return this;
    }

    public final UpdateSql where(Consumer<WhereStatement> consumer) {
        consumer.accept(this.where);
        return this;
    }

    @Override
    public final String getSql() {
        var builder = new StringBuilder();
        builder.append("UPDATE ");
        table.builder(builder);
        join.builder(builder);
        set.builder(builder);
        where.builder(builder);
        return builder.toString();
    }

}
