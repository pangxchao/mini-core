package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.builder.fragment.SelectFragment;
import com.mini.core.jdbc.builder.statement.*;
import com.mini.core.jdbc.builder.statement.FromStatement.FromStatementImpl;
import com.mini.core.jdbc.builder.statement.GroupByStatement.GroupByStatementImpl;
import com.mini.core.jdbc.builder.statement.HavingStatement.HavingStatementImpl;
import com.mini.core.jdbc.builder.statement.OrderByStatement.OrderByStatementImpl;
import com.mini.core.jdbc.builder.statement.SelectStatement.SelectStatementImpl;
import com.mini.core.jdbc.builder.statement.WhereStatement.WhereStatementImpl;

import java.util.Collection;
import java.util.function.Consumer;

import static java.lang.String.format;

@SuppressWarnings({"UnusedReturnValue"})
public class SelectSql extends AbstractSql<SelectSql> implements SelectFragment<SelectSql> {
    private final GroupByStatementImpl groupBy = new GroupByStatementImpl(this);
    private final OrderByStatementImpl orderBy = new OrderByStatementImpl(this);
    private final SelectStatementImpl select = new SelectStatementImpl(this);
    private final HavingStatementImpl having = new HavingStatementImpl(this);
    private final WhereStatementImpl where = new WhereStatementImpl(this);
    private final FromStatementImpl from = new FromStatementImpl(this);
    private final JoinFragmentImpl join = new JoinFragmentImpl(this);
    private FilterStatement<?> filter = null;

    public final SelectSql selects(String... columns) {
        this.select.addValues(columns);
        return this;
    }

    public final SelectSql select(Consumer<SelectStatement> consumer) {
        consumer.accept(this.select);
        return this;
    }

    public final SelectSql select(String column, String alias) {
        selects(format("%s AS `%s`", column, alias));
        return this;
    }

    public final SelectSql select(String column) {
        return selects(column);
    }

    public final SelectSql selectCount(String column, String alias) {
        this.select(column, alias);
        this.select.count(column, alias);
        return this;
    }

    public final SelectSql selectCount(String column) {
        this.select.count(column);
        return this;
    }

    public final SelectSql selectSum(String column, String alias) {
        this.select.sum(column, alias);
        return this;
    }

    public final SelectSql selectSum(String column) {
        this.select.sum(column);
        return this;
    }

    public final SelectSql selectAvg(String column, String alias) {
        this.select.avg(column, alias);
        return this;
    }

    public final SelectSql selectAvg(String column) {
        this.select.avg(column);
        return this;
    }

    public final SelectSql selectMax(String column, String alias) {
        this.select.max(column, alias);
        return this;
    }

    public final SelectSql selectMax(String column) {
        this.select.max(column);
        return this;
    }

    public final SelectSql selectMin(String column, String alias) {
        this.select.min(column, alias);
        return this;
    }

    public final SelectSql selectMin(String column) {
        this.select.min(column);
        return this;
    }

    public final SelectSql from(String... tables) {
        this.from.addValues(tables);
        return this;
    }

    @Override
    public final SelectSql join(String join) {
        this.join.join(join);
        return this;
    }

    @Override
    public final SelectSql join(String table, String column, String target) {
        this.join.join(table, column, table);
        return this;
    }

    @Override
    public final SelectSql join(String table, Consumer<JoinOnStatement> consumer) {
        this.join.join(table, consumer);
        return this;
    }

    @Override
    public final SelectSql innerJoin(String join) {
        this.join.innerJoin(join);
        return this;
    }

    @Override
    public final SelectSql innerJoin(String table, String column, String target) {
        this.join.innerJoin(table, column, table);
        return this;
    }

    @Override
    public final SelectSql innerJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.innerJoin(table, consumer);
        return this;
    }

    @Override
    public final SelectSql leftJoin(String join) {
        this.join.leftJoin(join);
        return this;
    }

    @Override
    public final SelectSql leftJoin(String table, String column, String target) {
        this.join.leftJoin(table, column, target);
        return this;
    }

    @Override
    public final SelectSql leftJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftJoin(table, consumer);
        return this;
    }

    @Override
    public final SelectSql rightJoin(String join) {
        this.join.rightJoin(join);
        return this;
    }

    @Override
    public final SelectSql rightJoin(String table, String column, String target) {
        this.join.rightJoin(table, column, target);
        return this;
    }

    @Override
    public final SelectSql rightJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightJoin(table, consumer);
        return this;
    }

    @Override
    public final SelectSql leftOuterJoin(String join) {
        this.join.leftOuterJoin(join);
        return this;
    }

    @Override
    public final SelectSql leftOuterJoin(String table, String column, String target) {
        this.join.leftOuterJoin(table, column, target);
        return this;
    }

    @Override
    public final SelectSql leftOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftOuterJoin(table, consumer);
        return this;
    }

    @Override
    public final SelectSql rightOuterJoin(String join) {
        this.join.rightOuterJoin(join);
        return this;
    }

    @Override
    public final SelectSql rightOuterJoin(String table, String column, String target) {
        this.join.rightOuterJoin(table, column, target);
        return this;
    }

    @Override
    public final SelectSql rightOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightOuterJoin(table, consumer);
        return this;
    }

    @Override
    public final SelectSql crossJoin(String join) {
        this.join.crossJoin(join);
        return this;
    }

    @Override
    public final SelectSql crossJoin(String table, String column, String target) {
        this.join.crossJoin(table, column, target);
        return this;
    }

    @Override
    public final SelectSql crossJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.crossJoin(table, consumer);
        return this;
    }

    public final SelectSql where(Consumer<WhereStatement> consumer) {
        consumer.accept(this.where);
        return this;
    }

    @Override
    public final SelectSql and() {
        if (this.filter != null) {
            filter.and();
        }
        return this;
    }

    @Override
    public final SelectSql or() {
        if (this.filter != null) {
            filter.or();
        }
        return this;
    }

    @Override
    public final SelectSql whereEqNative(String column, String target, Object... args) {
        this.where.eqNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereEq(String column, Object arg) {
        this.where.eq(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotEqNative(String column, String target, Object... args) {
        this.where.notEqNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotEq(String column, Object arg) {
        this.where.notEq(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIsNull(String column) {
        this.where.isNull(column);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIsNotNull(String column) {
        this.where.isNotNull(column);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereGtNative(String column, String target, Object... args) {
        this.where.gtNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereGt(String column, Object arg) {
        this.where.gt(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereLtNative(String column, String target, Object... args) {
        this.where.ltNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereLt(String column, Object arg) {
        this.where.lt(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereGteNative(String column, String target, Object... args) {
        this.where.gteNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereGte(String column, Object arg) {
        this.where.gte(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereLteNative(String column, String target, Object... args) {
        this.where.lteNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereLte(String column, Object arg) {
        this.where.lte(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereInNative(String column, String target, Object... args) {
        this.where.inNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public <O, C extends Collection<O>> SelectSql whereIn(String column, C args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public <O> SelectSql whereIn(String column, O[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, long[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, int[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, short[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, byte[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, double[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, float[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, boolean[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereIn(String column, char[] args) {
        this.where.in(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotInNative(String column, String target, Object... args) {
        this.where.notInNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public <O, C extends Collection<O>> SelectSql whereNotIn(String column, C args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public <O> SelectSql whereNotIn(String column, O[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, long[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, int[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, short[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, byte[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, double[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, float[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, boolean[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotIn(String column, char[] args) {
        this.where.notIn(column, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereLikeNative(String column, String target, Object... args) {
        this.where.likeNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereLike(String column, Object arg) {
        this.where.like(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereContain(String column, Object arg) {
        this.where.contain(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereStartWith(String column, Object arg) {
        this.where.startWith(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereEndWith(String column, Object arg) {
        this.where.endWith(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotLikeNative(String column, String target, Object... args) {
        this.where.notLikeNative(column, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotLike(String column, Object arg) {
        this.where.notLike(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotContain(String column, Object arg) {
        this.where.notContain(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotStartWith(String column, Object arg) {
        this.where.notStartWith(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotEndWith(String column, Object arg) {
        this.where.notEndWith(column, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereMatchNative(String[] columns, String target, Object... args) {
        this.where.matchNative(columns, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereMatch(String[] columns, Object arg) {
        this.where.match(columns, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotMatchNative(String[] columns, String target, Object... args) {
        this.where.notMatchInBoolNative(columns, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotMatch(String[] columns, Object arg) {
        this.where.notMatch(columns, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereMatchInBoolNative(String[] columns, String target, Object... args) {
        this.where.matchInBoolNative(columns, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereMatchInBool(String[] columns, Object arg) {
        this.where.matchInBool(columns, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotMatchInBoolNative(String[] columns, String target, Object... args) {
        this.where.notMatchInBoolNative(columns, target, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotMatchInBool(String[] columns, Object arg) {
        this.where.notMatchInBool(columns, arg);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.where.betweenNative(column, targetMin, targetMax, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereBetween(String column, Object min, Object max) {
        this.where.between(column, min, max);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.where.notBetweenNative(column, targetMin, targetMax, args);
        this.filter = this.where;
        return this;
    }

    @Override
    public final SelectSql whereNotBetween(String column, Object min, Object max) {
        this.where.notBetween(column, min, max);
        this.filter = this.where;
        return this;
    }

    public final SelectSql groupBy(String... columns) {
        this.groupBy.addValues(columns);
        return this;
    }

    public final SelectSql having(Consumer<HavingStatement> consumer) {
        consumer.accept(this.having);
        return this;
    }

    @Override
    public final SelectSql havingEqNative(String column, String target, Object... args) {
        this.having.eqNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingEq(String column, Object arg) {
        this.having.eq(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotEqNative(String column, String target, Object... args) {
        this.having.notEqNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotEq(String column, Object arg) {
        this.having.notEq(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIsNull(String column) {
        this.having.isNull(column);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIsNotNull(String column) {
        this.having.isNotNull(column);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingGtNative(String column, String target, Object... args) {
        this.having.gtNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingGt(String column, Object arg) {
        this.having.gt(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingLtNative(String column, String target, Object... args) {
        this.having.ltNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingLt(String column, Object arg) {
        this.having.lt(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingGteNative(String column, String target, Object... args) {
        this.having.gteNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingGte(String column, Object arg) {
        this.having.gte(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingLteNative(String column, String target, Object... args) {
        this.having.lteNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingLte(String column, Object arg) {
        this.having.lte(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingInNative(String column, String target, Object... args) {
        this.having.inNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public <O, C extends Collection<O>> SelectSql havingIn(String column, C args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public <O> SelectSql havingIn(String column, O[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, long[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, int[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, short[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, byte[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, double[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, float[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, boolean[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingIn(String column, char[] args) {
        this.having.in(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotInNative(String column, String target, Object... args) {
        this.having.notInNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public <O, C extends Collection<O>> SelectSql havingNotIn(String column, C args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public <O> SelectSql havingNotIn(String column, O[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, long[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, int[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, short[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, byte[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, double[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, float[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, boolean[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotIn(String column, char[] args) {
        this.having.notIn(column, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingLikeNative(String column, String target, Object... args) {
        this.having.likeNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingLike(String column, Object arg) {
        this.having.like(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingContain(String column, Object arg) {
        this.having.contain(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingStartWith(String column, Object arg) {
        this.having.startWith(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingEndWith(String column, Object arg) {
        this.having.endWith(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotLikeNative(String column, String target, Object... args) {
        this.having.notLikeNative(column, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotLike(String column, Object arg) {
        this.having.notLike(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotContain(String column, Object arg) {
        this.having.notContain(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotStartWith(String column, Object arg) {
        this.having.notStartWith(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotEndWith(String column, Object arg) {
        this.having.notEndWith(column, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingMatchNative(String[] columns, String target, Object... args) {
        this.having.matchNative(columns, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingMatch(String[] columns, Object arg) {
        this.having.match(columns, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotMatchNative(String[] columns, String target, Object... args) {
        this.having.notMatchInBoolNative(columns, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotMatch(String[] columns, Object arg) {
        this.having.notMatch(columns, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingMatchInBoolNative(String[] columns, String target, Object... args) {
        this.having.matchInBoolNative(columns, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingMatchInBool(String[] columns, Object arg) {
        this.having.matchInBool(columns, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotMatchInBoolNative(String[] columns, String target, Object... args) {
        this.having.notMatchInBoolNative(columns, target, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotMatchInBool(String[] columns, Object arg) {
        this.having.notMatchInBool(columns, arg);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.having.betweenNative(column, targetMin, targetMax, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingBetween(String column, Object min, Object max) {
        this.having.between(column, min, max);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotBetweenNative(String column, String targetMin, String targetMax, Object... args) {
        this.having.notBetweenNative(column, targetMin, targetMax, args);
        this.filter = this.having;
        return this;
    }

    @Override
    public final SelectSql havingNotBetween(String column, Object min, Object max) {
        this.having.notBetween(column, min, max);
        this.filter = this.having;
        return this;
    }

    public final SelectSql orderBy(String... columns) {
        this.orderBy.addValues(columns);
        return this;
    }

    public final SelectSql orderByAsc(String... columns) {
        this.orderBy.asc(columns);
        return this;
    }

    public final SelectSql orderByDesc(String... columns) {
        this.orderBy.desc(columns);
        return this;
    }

    @Override
    public final String getSql() {
        var builder = new StringBuilder();
        this.select.builder(builder);
        this.from.builder(builder);
        this.join.builder(builder);
        this.where.builder(builder);
        this.groupBy.builder(builder);
        this.having.builder(builder);
        this.orderBy.builder(builder);
        return builder.toString();
    }


}
