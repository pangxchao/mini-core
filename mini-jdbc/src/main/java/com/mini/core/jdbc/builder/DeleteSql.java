package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.fragment.DeleteFragment;
import com.mini.core.jdbc.builder.statement.FromStatement.FromStatementImpl;
import com.mini.core.jdbc.builder.statement.JoinOnStatement;
import com.mini.core.jdbc.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.jdbc.builder.statement.WhereStatement;
import com.mini.core.jdbc.builder.statement.WhereStatement.WhereStatementImpl;

import java.util.Collection;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class DeleteSql extends AbstractSql<DeleteSql> implements DeleteFragment<DeleteSql> {
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
    public final DeleteSql innerJoin(String join) {
        this.join.innerJoin(join);
        return this;
    }

    @Override
    public final DeleteSql innerJoin(String table, String column, String target) {
        this.join.innerJoin(table, column, table);
        return this;
    }

    @Override
    public final DeleteSql innerJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.innerJoin(table, consumer);
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
        this.where.where(consumer);
        return this;
    }

    @Override
    public final DeleteSql and() {
        this.where.and();
        return this;
    }

    @Override
    public final DeleteSql or() {
        this.where.or();
        return this;
    }

    @Override
    public final DeleteSql whereEqNative(String column, String target, Object... args) {
        this.where.eqNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereEq(String column, Object arg) {
        this.where.eq(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereNotEqNative(String column, String target, Object... args) {
        this.where.notEqNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotEq(String column, Object arg) {
        this.where.notEq(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereIsNull(String column) {
        this.where.isNull(column);
        return this;
    }

    @Override
    public final DeleteSql whereIsNotNull(String column) {
        this.where.isNotNull(column);
        return this;
    }

    @Override
    public final DeleteSql whereGtNative(String column, String target, Object... args) {
        this.where.gtNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereGt(String column, Object arg) {
        this.where.gt(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereLtNative(String column, String target, Object... args) {
        this.where.ltNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereLt(String column, Object arg) {
        this.where.lt(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereGteNative(String column, String target, Object... args) {
        this.where.gteNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereGte(String column, Object arg) {
        this.where.gte(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereLteNative(String column, String target, Object... args) {
        this.where.lteNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereLte(String column, Object arg) {
        this.where.lte(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereInNative(String column, String target, Object... args) {
        this.where.inNative(column, target, args);
        return this;
    }

    @Override
    public <O, C extends Collection<O>> DeleteSql whereIn(String column, C args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public <O> DeleteSql whereIn(String column, O[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, long[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, int[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, short[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, byte[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, double[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, float[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, boolean[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereIn(String column, char[] args) {
        this.where.in(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotInNative(String column, String target, Object... args) {
        this.where.notInNative(column, target, args);
        return this;
    }

    @Override
    public <O, C extends Collection<O>> DeleteSql whereNotIn(String column, C args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public <O> DeleteSql whereNotIn(String column, O[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, long[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, int[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, short[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, byte[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, double[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, float[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, boolean[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotIn(String column, char[] args) {
        this.where.notIn(column, args);
        return this;
    }

    @Override
    public final DeleteSql whereLikeNative(String column, String target, Object... args) {
        this.where.likeNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereLike(String column, Object arg) {
        this.where.like(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereContain(String column, Object arg) {
        this.where.contain(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereStartWith(String column, Object arg) {
        this.where.startWith(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereEndWith(String column, Object arg) {
        this.where.endWith(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereNotLikeNative(String column, String target, Object... args) {
        this.where.notLikeNative(column, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotLike(String column, Object arg) {
        this.where.notLike(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereNotContain(String column, Object arg) {
        this.where.notContain(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereNotStartWith(String column, Object arg) {
        this.where.notStartWith(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereNotEndWith(String column, Object arg) {
        this.where.notEndWith(column, arg);
        return this;
    }

    @Override
    public final DeleteSql whereMatchNative(String[] columns, String target, Object... args) {
        this.where.matchNative(columns, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereMatch(String[] columns, Object arg) {
        this.where.match(columns, arg);
        return this;
    }

    @Override
    public final DeleteSql whereNotMatchNative(String[] columns, String target, Object... args) {
        this.where.notMatchInBoolNative(columns, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotMatch(String[] columns, Object arg) {
        this.where.notMatch(columns, arg);
        return this;
    }

    @Override
    public final DeleteSql whereMatchInBoolNative(String[] columns, String target, Object... args) {
        this.where.matchInBoolNative(columns, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereMatchInBool(String[] columns, Object arg) {
        this.where.matchInBool(columns, arg);
        return this;
    }

    @Override
    public final DeleteSql whereNotMatchInBoolNative(String[] columns, String target, Object... args) {
        this.where.notMatchInBoolNative(columns, target, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotMatchInBool(String[] columns, Object arg) {
        this.where.notMatchInBool(columns, arg);
        return this;
    }

    @Override
    public final DeleteSql whereBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.where.betweenNative(column, targetMin, targetMax, args);
        return this;
    }

    @Override
    public final DeleteSql whereBetween(String column, Object min, Object max) {
        this.where.between(column, min, max);
        return this;
    }

    @Override
    public final DeleteSql whereNotBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.where.notBetweenNative(column, targetMin, targetMax, args);
        return this;
    }

    @Override
    public final DeleteSql whereNotBetween(String column, Object min, Object max) {
        this.where.notBetween(column, min, max);
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
