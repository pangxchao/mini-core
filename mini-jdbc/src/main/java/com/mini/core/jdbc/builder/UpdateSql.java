package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.fragment.UpdateFragment;
import com.mini.core.jdbc.builder.statement.JoinOnStatement;
import com.mini.core.jdbc.builder.statement.SetStatement;
import com.mini.core.jdbc.builder.statement.SetStatement.SetStatementImpl;
import com.mini.core.jdbc.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.jdbc.builder.statement.WhereStatement;
import com.mini.core.jdbc.builder.statement.WhereStatement.WhereStatementImpl;

import java.util.Collection;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class UpdateSql extends AbstractSql<UpdateSql> implements UpdateFragment<UpdateSql> {
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
    public final UpdateSql innerJoin(String join) {
        this.join.innerJoin(join);
        return this;
    }

    @Override
    public final UpdateSql innerJoin(String table, String column, String target) {
        this.join.innerJoin(table, column, table);
        return this;
    }

    @Override
    public final UpdateSql innerJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.innerJoin(table, consumer);
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
        this.where.where(consumer);
        return this;
    }

    @Override
    public final UpdateSql and() {
        this.where.and();
        return this;
    }

    @Override
    public final UpdateSql or() {
        this.where.or();
        return this;
    }

    @Override
    public final UpdateSql whereEqNative(String column, String target, Object... args) {
        this.where.eqNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereEq(String column, Object arg) {
        this.where.eq(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereNotEqNative(String column, String target, Object... args) {
        this.where.notEqNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotEq(String column, Object arg) {
        this.where.notEq(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereIsNull(String column) {
        this.where.isNull(column);
        return this;
    }

    @Override
    public final UpdateSql whereIsNotNull(String column) {
        this.where.isNotNull(column);
        return this;
    }

    @Override
    public final UpdateSql whereGtNative(String column, String target, Object... args) {
        this.where.gtNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereGt(String column, Object arg) {
        this.where.gt(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereLtNative(String column, String target, Object... args) {
        this.where.ltNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereLt(String column, Object arg) {
        this.where.lt(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereGteNative(String column, String target, Object... args) {
        this.where.gteNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereGte(String column, Object arg) {
        this.where.gte(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereLteNative(String column, String target, Object... args) {
        this.where.lteNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereLte(String column, Object arg) {
        this.where.lte(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereInNative(String column, String target, Object... args) {
        this.where.inNative(column, target, args);
        return this;
    }

    @Override
    public <O, C extends Collection<O>> UpdateSql whereIn(String column, C args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public <O> UpdateSql whereIn(String column, O[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, long[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, int[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, short[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, byte[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, double[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, float[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, boolean[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereIn(String column, char[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotInNative(String column, String target, Object... args) {
        this.where.notInNative(column, target, args);
        return this;
    }

    @Override
    public <O, C extends Collection<O>> UpdateSql whereNotIn(String column, C args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public <O> UpdateSql whereNotIn(String column, O[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, long[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, int[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, short[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, byte[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, double[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, float[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, boolean[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotIn(String column, char[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final UpdateSql whereLikeNative(String column, String target, Object... args) {
        this.where.likeNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereLike(String column, Object arg) {
        this.where.like(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereContain(String column, Object arg) {
        this.where.contain(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereStartWith(String column, Object arg) {
        this.where.startWith(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereEndWith(String column, Object arg) {
        this.where.endWith(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereNotLikeNative(String column, String target, Object... args) {
        this.where.notLikeNative(column, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotLike(String column, Object arg) {
        this.where.notLike(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereNotContain(String column, Object arg) {
        this.where.notContain(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereNotStartWith(String column, Object arg) {
        this.where.notStartWith(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereNotEndWith(String column, Object arg) {
        this.where.notEndWith(column, arg);
        return this;
    }

    @Override
    public final UpdateSql whereMatchNative(String[] columns, String target, Object... args) {
        this.where.matchNative(columns, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereMatch(String[] columns, Object arg) {
        this.where.match(columns, arg);
        return this;
    }

    @Override
    public final UpdateSql whereNotMatchNative(String[] columns, String target, Object... args) {
        this.where.notMatchInBoolNative(columns, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotMatch(String[] columns, Object arg) {
        this.where.notMatch(columns, arg);
        return this;
    }

    @Override
    public final UpdateSql whereMatchInBoolNative(String[] columns, String target, Object... args) {
        this.where.matchInBoolNative(columns, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereMatchInBool(String[] columns, Object arg) {
        this.where.matchInBool(columns, arg);
        return this;
    }

    @Override
    public final UpdateSql whereNotMatchInBoolNative(String[] columns, String target, Object... args) {
        this.where.notMatchInBoolNative(columns, target, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotMatchInBool(String[] columns, Object arg) {
        this.where.notMatchInBool(columns, arg);
        return this;
    }

    @Override
    public final UpdateSql whereBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.where.betweenNative(column, targetMin, targetMax, args);
        return this;
    }

    @Override
    public final UpdateSql whereBetween(String column, Object min, Object max) {
        this.where.between(column, min, max);
        return this;
    }

    @Override
    public final UpdateSql whereNotBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.where.notBetweenNative(column, targetMin, targetMax, args);
        return this;
    }

    @Override
    public final UpdateSql whereNotBetween(String column, Object min, Object max) {
        this.where.notBetween(column, min, max);
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
