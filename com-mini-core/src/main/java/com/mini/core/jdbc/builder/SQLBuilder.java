package com.mini.core.jdbc.builder;

import com.mini.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static com.mini.core.jdbc.builder.SQLInterfaceDef.getSQLInterface;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.addAll;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

public class SQLBuilder implements EventListener, Serializable {
	private final OnDuplicateKeyUpdateStatement onDuplicateKeyUpdate = new OnDuplicateKeyUpdateStatement();
	private final OuterJoinStatement outerJoin = new OuterJoinStatement();
	private final RightJoinStatement rightJoin = new RightJoinStatement();
	private final LeftJoinStatement leftJoin = new LeftJoinStatement();
	private final GroupByStatement groupBy = new GroupByStatement();
	private final OrderByStatement orderBy = new OrderByStatement();
	private final ColumnStatement columns = new ColumnStatement();
	private final HavingStatement having = new HavingStatement();
	private final ValuesStatement values = new ValuesStatement();
	private final SelectStatement select = new SelectStatement();
	private final TableStatement table = new TableStatement();
	private final WhereStatement where = new WhereStatement();
	private final FromStatement from = new FromStatement();
	private final JoinStatement join = new JoinStatement();
	private final List<Object> args = new ArrayList<>();
	private final SetStatement set = new SetStatement();
	private WhereStatement last = null;
	private StatementType statement;
	private boolean distinct;
	
	public SQLBuilder() {}
	
	public <T> SQLBuilder(@Nonnull Class<T> type) {
		var inter = getSQLInterface(type);
		inter.createSelect(this, type);
	}
	
	public final SQLBuilder args(Object... args) {
		this.args.addAll(asList(args));
		return this;
	}
	
	public final Object[] args() {
		return args.toArray();
	}
	
	public final SQLBuilder insertInto(String table) {
		this.statement = StatementType.INSERT;
		this.table.addValues(table);
		return this;
	}
	
	public final SQLBuilder replaceInto(String table) {
		this.statement = StatementType.REPLACE;
		this.table.addValues(table);
		return this;
	}
	
	public final SQLBuilder delete(String... tables) {
		this.statement = StatementType.DELETE;
		this.table.addValues(tables);
		return this;
	}
	
	public final SQLBuilder update(String... tables) {
		this.statement = StatementType.UPDATE;
		this.table.addValues(tables);
		return this;
	}
	
	public final SQLBuilder select(String... columns) {
		this.statement = StatementType.SELECT;
		this.select.addSelect(columns);
		return this;
	}
	
	/**
	 * {@code select(format("COUNT(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectCount(String column) {
		return select(format("COUNT(%s) AS `%s`", column, column));
	}
	
	/**
	 * {@code select(format("COUNT(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectCount(String column, String alias) {
		return select(format("COUNT(%s) AS `%s`", column, alias));
	}
	
	/**
	 * {@code select(format("SUM(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectSum(String column) {
		return select(format("SUM(%s) AS `%s`", column, column));
	}
	
	/**
	 * {@code select(format("SUM(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectSum(String column, String alias) {
		return select(format("SUM(%s) AS `%s`", column, alias));
	}
	
	/**
	 * {@code select(format("AVG(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectAvg(String column) {
		return select(format("AVG(%s)  AS `%s`", column, column));
	}
	
	/**
	 * {@code select(format("AVG(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectAvg(String column, String alias) {
		return select(format("AVG(%s)  AS `%s`", column, alias));
	}
	
	/**
	 * {@code select(format("MAX(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectMax(String column) {
		return select(format("MAX(%s) AS `%s`", column, column));
	}
	
	/**
	 * {@code select(format("MAX(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectMax(String column, String alias) {
		return select(format("MAX(%s) AS `%s`", column, alias));
	}
	
	/**
	 * {@code select(format("MIN(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectMin(String column) {
		return select(format("MIN(%s)  AS `%s`", column, column));
	}
	
	/**
	 * {@code select(format("MIN(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectMin(String column, String alias) {
		return select(format("MIN(%s)  AS `%s`", column, alias));
	}
	
	public final SQLBuilder selectDistinct(String... columns) {
		SQLBuilder.this.distinct = true;
		this.select(columns);
		return this;
	}
	
	/**
	 * {@code selectDistinct(format("COUNT(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctCount(String column) {
		return selectDistinct(format("COUNT(%s) AS `%s`", column, column));
	}
	
	/**
	 * {@code selectDistinct(format("COUNT(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctCount(String column, String alias) {
		return selectDistinct(format("COUNT(%s) AS `%s`", column, alias));
	}
	
	/**
	 * {@code selectDistinct(format("SUM(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctSum(String column) {
		return selectDistinct(format("SUM(%s)  AS `%s`", column, column));
	}
	
	/**
	 * {@code selectDistinct(format("SUM(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctSum(String column, String alias) {
		return selectDistinct(format("SUM(%s)  AS `%s`", column, alias));
	}
	
	/**
	 * {@code selectDistinct(format("AVG(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctAvg(String column) {
		return selectDistinct(format("AVG(%s)  AS `%s`", column, column));
	}
	
	/**
	 * {@code selectDistinct(format("AVG(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctAvg(String column, String alias) {
		return selectDistinct(format("AVG(%s)  AS `%s`", column, alias));
	}
	
	/**
	 * {@code selectDistinct(format("MAX(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctMax(String column) {
		return selectDistinct(format("MAX(%s)  AS `%s`", column, column));
	}
	
	/**
	 * {@code selectDistinct(format("MAX(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctMax(String column, String alias) {
		return selectDistinct(format("MAX(%s)  AS `%s`", column, alias));
	}
	
	/**
	 * {@code selectDistinct(format("MIN(%s)", column)); }
	 * @param column 字段名称
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctMin(String column) {
		return selectDistinct(format("MIN(%s)  AS `%s`", column, column));
	}
	
	/**
	 * {@code selectDistinct(format("MIN(%s)", column)); }
	 * @param column 字段名称
	 * @param alias  别名
	 * @return {@code this}
	 */
	public final SQLBuilder selectDistinctMin(String column, String alias) {
		return selectDistinct(format("MIN(%s)  AS `%s`", column, alias));
	}
	
	public final SQLBuilder values(String column, String value) {
		this.columns.addValues(column);
		this.values.addValues(value);
		return this;
	}
	
	public final SQLBuilder values(String column) {
		return values(column, "?");
	}
	
	public final SQLBuilder from(String... tables) {
		this.from.addValues(tables);
		return this;
	}
	
	/**
	 * {@code from(builder.toString());}
	 * <p>子查询的SQL中参数无效</p>
	 * @param builder 子查询SQL
	 * @return {@code this}
	 */
	public final SQLBuilder from(SQLBuilder builder) {
		return from(builder.toString());
	}
	
	public final SQLBuilder join(String format, Object... args) {
		join.addValues(format(format, args));
		return this;
	}
	
	/**
	 * {@code join("%s ON %s = %s", table, column, joinColumn); }
	 * @param table      联合表名称
	 * @param column     当前表字段
	 * @param joinColumn 联合表字段
	 * @return {@code this}
	 */
	public final SQLBuilder joinSingle(String table, String column, String joinColumn) {
		return join("%s ON %s = %s", table, column, joinColumn);
	}
	
	public final SQLBuilder leftJoin(String format, Object... args) {
		leftJoin.addValues(format(format, args));
		return this;
	}
	
	/**
	 * {@code leftJoin("%s ON %s = %s", table, column, joinColumn); }
	 * @param table      联合表名称
	 * @param column     当前表字段
	 * @param joinColumn 联合表字段
	 * @return {@code this}
	 */
	public final SQLBuilder leftJoinSingle(String table, String column, String joinColumn) {
		return leftJoin("%s ON %s = %s", table, column, joinColumn);
	}
	
	public final SQLBuilder rightJoin(String format, Object... args) {
		rightJoin.addValues(format(format, args));
		return this;
	}
	
	/**
	 * {@code rightJoin("%s ON %s = %s", table, column, joinColumn); }
	 * @param table      联合表名称
	 * @param column     当前表字段
	 * @param joinColumn 联合表字段
	 * @return {@code this}
	 */
	public final SQLBuilder rightJoinSingle(String table, String column, String joinColumn) {
		return rightJoin("%s ON %s = %s", table, column, joinColumn);
	}
	
	public final SQLBuilder outerJoin(String format, Object... args) {
		outerJoin.addValues(format(format, args));
		return this;
	}
	
	/**
	 * {@code outerJoin("%s ON %s = %s", table, column, joinColumn); }
	 * @param table      联合表名称
	 * @param column     当前表字段
	 * @param joinColumn 联合表字段
	 * @return {@code this}
	 */
	public final SQLBuilder outerJoinSingle(String table, String column, String joinColumn) {
		return outerJoin("%s ON %s = %s", table, column, joinColumn);
	}
	
	public final SQLBuilder set(String format, Object... args) {
		this.set.addValues(format(format, args));
		return this;
	}
	
	/**
	 * {@code set("%s = ?", column).args(arg); }
	 * @param column 修改字段名
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder setEquals(String column, Object arg) {
		return set("%s = ?", column).args(arg);
	}
	
	/**
	 * {@code set("%s = %s + ?", column, column).args(arg); }
	 * @param column 修改字段名
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder setIncrease(String column, Object arg) {
		return set("%s = %s + ?", column, column).args(arg);
	}
	
	public final SQLBuilder onDuplicateKeyUpdate(String format, Object... args) {
		this.onDuplicateKeyUpdate.addValues(format(format, args));
		this.statement = StatementType.INSERT_UPDATE;
		return this;
	}
	
	/**
	 * {@code onDuplicateKeyUpdate("%s = VALUES(%s)", column, column); }
	 * @param column 修改字段名
	 * @return ｛@code this｝
	 */
	public final SQLBuilder onDuplicateKeyUpdateFromInsert(String column) {
		return onDuplicateKeyUpdate("%s = VALUES(%s)", column, column);
	}
	
	/**
	 * {@code onDuplicateKeyUpdateEquals("%s = ?", column).args(arg); }
	 * @param column 修改字段名
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder onDuplicateKeyUpdateEquals(String column, Object arg) {
		return onDuplicateKeyUpdate("%s = ?", column).args(arg);
	}
	
	/**
	 * {@code onDuplicateKeyUpdate("%s = %s + ?", column, column).args(arg); }
	 * @param column 修改字段名
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder onDuplicateKeyUpdateIncrease(String column, Object arg) {
		return onDuplicateKeyUpdate("%s = %s + ?", column, column).args(arg);
	}
	
	public final SQLBuilder where(String format, Object... args) {
		this.where.addValues(format(format, args));
		this.last = this.where;
		return this;
	}
	
	/**
	 * {@code where("%s = ?", column).args(arg); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereEquals(String column, Object arg) {
		return where("%s = ?", column).args(arg);
	}
	
	/**
	 * {@code where("%s <> ?", column).args(arg); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereNotEqual(String column, Object arg) {
		return where("%s <> ?", column).args(arg);
	}
	
	/**
	 * {@code where("%s > ?", column).args(arg); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereGreaterThan(String column, Object arg) {
		return where("%s > ?", column).args(arg);
	}
	
	/**
	 * {@code where("%s <=> ?", column).args(arg); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereLessThan(String column, Object arg) {
		return where("%s < ?", column).args(arg);
	}
	
	/**
	 * {@code where("%s >= ?", column).args(arg); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereGreaterThanOrEquals(String column, Object arg) {
		return where("%s >= ?", column).args(arg);
	}
	
	/**
	 * {@code where("%s <= ?", column).args(arg); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereLessThanOrEquals(String column, Object arg) {
		return where("%s <= ?", column).args(arg);
	}
	
	/**
	 * {@code var p = of(args).map(o -> "?").collect(joining(", ")); }
	 * <br/>
	 * {@code return where("%s IN (%s)", column, p).args(args); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 * @see java.util.stream.Stream#of(Object[])
	 */
	public final SQLBuilder whereIn(String column, Object[] args) {
		var p = of(args).map(o -> "?").collect(joining(", "));
		return where("%s IN (%s)", column, p).args(args);
	}
	
	/**
	 * {@code whereIn(column, stream(args).boxed().toArray()); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 * @see java.util.Arrays#stream(int[])
	 */
	public final SQLBuilder whereIn(String column, double[] args) {
		return whereIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * {@code where("%s IN (%s)", column, StringUtil.join(args, ',')) }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereIn(String column, float[] args) {
		final String params = StringUtil.join(args, ',');
		return where("%s IN (%s)", column, params);
	}
	
	/**
	 * {@code whereIn(column, stream(args).boxed().toArray()); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 * @see java.util.Arrays#stream(int[])
	 */
	public final SQLBuilder whereIn(String column, long[] args) {
		return whereIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * {@code whereIn(column, stream(args).boxed().toArray()); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 * @see java.util.Arrays#stream(int[])
	 */
	public final SQLBuilder whereIn(String column, int[] args) {
		return whereIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * {@code where("%s IN (%s)", column, StringUtil.join(args, ',')) }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereIn(String column, short[] args) {
		final String params = StringUtil.join(args, ',');
		return where("%s IN (%s)", column, params);
	}
	
	/**
	 * {@code where("%s IN (%s)", column, StringUtil.join(args, ',')) }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereIn(String column, byte[] args) {
		final String params = StringUtil.join(args, ',');
		return where("%s IN (%s)", column, params);
	}
	
	/**
	 * {@code where("%s IN (%s)", column, StringUtil.join(args, ',')) }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereIn(String column, char[] args) {
		final String params = StringUtil.join(args, ',');
		return where("%s IN (%s)", column, params);
	}
	
	/**
	 * {@code where("%s LIKE ?").args(arg); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereLike(String column, String arg) {
		return where("%s LIKE ?", column).args(arg);
	}
	
	/**
	 * {@code where("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil.join(columns, ',')).args(arg); }
	 * @param columns 搜索的字段
	 * @param arg     参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereMatchInBooleanMode(String[] columns, Object arg) {
		return where("MATCH(%s) AGAINST(? in BOOLEAN MODE)", //
				StringUtil.join(columns, ',')) //
				.args(arg);
	}
	
	/**
	 * {@code where("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil.join(columns, ',')).args(arg); }
	 * @param columns 搜索的字段
	 * @param arg     参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereMatch(String[] columns, Object arg) {
		final String string = StringUtil.join(columns, ',');
		return where("MATCH(%s) AGAINST(?)", string)//
				.args(arg);
	}
	
	/**
	 * {@code where("%s BETWEEN ? AND ?", column).args(min, max); }
	 * @param column 条件字段
	 * @param min    参数最小值
	 * @param max    参数最大值
	 * @return ｛@code this｝
	 */
	public final SQLBuilder whereBetweenAnd(String column, Object min, Object max) {
		return where("%s BETWEEN ? AND ?", column).args(min, max);
	}
	
	public final SQLBuilder and() {
		if (last != null) {
			last.addAND();
		}
		return this;
	}
	
	public final SQLBuilder or() {
		if (last != null) {
			last.addOR();
		}
		return this;
	}
	
	public final SQLBuilder groupBy(String... columns) {
		groupBy.addValues(columns);
		return this;
	}
	
	public final SQLBuilder having(String format, Object... args) {
		this.having.addValues(format(format, args));
		this.last = this.having;
		return this;
	}
	
	/**
	 * {@code having("%s = ?", column).args(param); }
	 * @param column 条件字段
	 * @param arg    参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingEquals(String column, Object arg) {
		return having("%s = ?", column).args(arg);
	}
	
	/**
	 * {@code having("%s <> ?", column).args(param); }
	 * @param column 条件字段
	 * @param param  参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingNotEqual(String column, Object param) {
		return having("%s <> ?", column).args(param);
	}
	
	/**
	 * {@code having("%s > ?", column).args(param); }
	 * @param column 条件字段
	 * @param param  参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingGreaterThan(String column, Object param) {
		return having("%s > ?", column).args(param);
	}
	
	/**
	 * {@code having("%s <=> ?", column).args(param); }
	 * @param column 条件字段
	 * @param param  参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingLess(String column, Object param) {
		return having("%s < ?", column).args(param);
	}
	
	/**
	 * {@code having("%s >= ?", column).args(param); }
	 * @param column 条件字段
	 * @param param  参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingGreaterThanOrEquals(String column, Object param) {
		return having("%s >= ?", column).args(param);
	}
	
	/**
	 * {@code having("%s <= ?", column).args(param); }
	 * @param column 条件字段
	 * @param param  参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingLessOrEquals(String column, Object param) {
		return having("%s <= ?", column).args(param);
	}
	
	
	/**
	 * {@code var p = of(args).map(o -> "?").collect(joining(", ")); }
	 * <br/>
	 * {@code having("%s IN (%s)", column, p).args(args) }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, Object[] args) {
		var p = of(args).map(o -> "?").collect(joining(", "));
		return having("%s IN (%s)", column, p).args(args);
	}
	
	/**
	 * {@code havingIn(column, stream(args).boxed().toArray()) }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, double[] args) {
		return havingIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * {@code having("%s IN (%s)", column,  StringUtil.join(args, ',')); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, float[] args) {
		return having("%s IN (%s)", StringUtil.join(args, ','));
	}
	
	/**
	 * {@code havingIn(column, stream(args).boxed().toArray()); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, long[] args) {
		return havingIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * {@code havingIn(column, stream(args).boxed().toArray()); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, int[] args) {
		return havingIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * {@code having("%s IN (%s)", column,  StringUtil.join(args, ',')); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, short[] args) {
		return having("%s IN (%s)", StringUtil.join(args, ','));
	}
	
	/**
	 * {@code having("%s IN (%s)", column,  StringUtil.join(args, ',')); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, byte[] args) {
		return having("%s IN (%s)", StringUtil.join(args, ','));
	}
	
	/**
	 * {@code having("%s IN (%s)", column,  StringUtil.join(args, ',')); }
	 * @param column 条件字段
	 * @param args   参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingIn(String column, char[] args) {
		return having("%s IN (%s)", StringUtil.join(args, ','));
	}
	
	/**
	 * {@code having("%s LIKE ?").args(param); }
	 * @param column 条件字段
	 * @param param  参数
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingLike(String column, String param) {
		return having("%s LIKE ?").args(param);
	}
	
	/**
	 * {@code having("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil.join(columns, ',')).args(param); }
	 * @param columns 搜索的字段
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingMatchInBooleanMode(String[] columns, Object param) {
		return having("MATCH(%s) AGAINST(? in BOOLEAN MODE)", //
				StringUtil.join(columns, ',')) //
				.args(param);
	}
	
	/**
	 * {@code having("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil.join(columns, ',')).args(param); }
	 * @param columns 搜索的字段
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingMatch(String[] columns, Object param) {
		String string = StringUtil.join(columns, ',');
		return having("MATCH(%s) AGAINST(?)", string)//
				.args(param);
	}
	
	/**
	 * {@code where("%s BETWEEN ? AND ?", column).args(min, max); }
	 * @param column 条件字段
	 * @param min    参数最小值
	 * @param max    参数最大值
	 * @return ｛@code this｝
	 */
	public final SQLBuilder havingBetweenAnd(String column, Object min, Object max) {
		return having("%s BETWEEN ? AND ?", column).args(min, max);
	}
	
	public final SQLBuilder orderBy(String format, Object... args) {
		orderBy.addValues(format(format, args));
		return this;
	}
	
	/**
	 * {@code of(columns).forEach(column -> orderBy("%s ASC", column)); }
	 * @param columns 排序字段
	 * @return ｛@code this｝
	 */
	public final SQLBuilder orderByAsc(String... columns) {
		of(columns).forEach(column -> orderBy("%s ASC", column));
		return this;
	}
	
	/**
	 * {@code of(columns).forEach(column -> orderBy("%s DESC", column)); }
	 * @param columns 排序字段
	 * @return ｛@code this｝
	 */
	public final SQLBuilder orderByDesc(String... columns) {
		of(columns).forEach(column -> orderBy("%s DESC", column));
		return this;
	}
	
	// Insert Into
	private String insertString() throws Error {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ");
		table.builder(builder);
		columns.builder(builder);
		values.builder(builder);
		return builder.toString(); //
	}
	
	// Replace Into
	private String replaceString() throws Error {
		StringBuilder builder = new StringBuilder();
		builder.append("REPLACE INTO ");
		table.builder(builder);
		columns.builder(builder);
		values.builder(builder);
		return builder.toString(); //
	}
	
	// Delete Into
	private String deleteString() throws Error {
		StringBuilder builder = new StringBuilder();
		table.builder(builder.append("DELETE "));
		this.from.builder(builder);
		this.join.builder(builder);
		leftJoin.builder(builder);
		rightJoin.builder(builder);
		outerJoin.builder(builder);
		this.where.builder(builder);
		return builder.toString(); //
	}
	
	// Update Into
	private String updateString() throws Error {
		StringBuilder builder = new StringBuilder();
		table.builder(builder.append("UPDATE "));
		this.join.builder(builder);
		leftJoin.builder(builder);
		rightJoin.builder(builder);
		outerJoin.builder(builder);
		this.set.builder(builder);
		this.where.builder(builder);
		return builder.toString(); //
	}
	
	// Select Into
	private String selectString() throws Error {
		StringBuilder builder = new StringBuilder("SELECT ");
		builder.append(distinct ? "DISTINCT " : "");
		this.select.builder(builder);
		this.from.builder(builder);
		this.join.builder(builder);
		leftJoin.builder(builder);
		rightJoin.builder(builder);
		outerJoin.builder(builder);
		this.where.builder(builder);
		this.groupBy.builder(builder);
		this.having.builder(builder);
		this.orderBy.builder(builder);
		return builder.toString(); //
	}
	
	private String insertOnUpdateString() throws Error {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ");
		table.builder(builder);
		columns.builder(builder);
		values.builder(builder);
		onDuplicateKeyUpdate.builder(builder);
		return builder.toString(); //
	}
	
	/**
	 * 获取SQL完整内容
	 * @return SQL完整内容
	 */
	public synchronized final String toString() {
		//  INSERT 语句
		if (statement == StatementType.INSERT) {
			return this.insertString();
		}
		
		// REPLACE 语句
		if (statement == StatementType.REPLACE) {
			return this.replaceString();
		}
		
		// DELETE 语句
		if (statement == StatementType.DELETE) {
			return this.deleteString();
		}
		
		// UPDATE 语句
		if (statement == StatementType.UPDATE) {
			return this.updateString();
		}
		
		// SELECT 语句
		if (statement == StatementType.SELECT) {
			return this.selectString();
		}
		
		// INSERT INTO OR ON DUPLICATE KEY UPDATE
		if (statement == StatementType.INSERT_UPDATE) {
			return this.insertOnUpdateString();
		}
		
		// statement为空，语句错误
		throw new RuntimeException("SQL ERROR!");
	}
	
	// Base Statement
	private static abstract class BaseStatement {
		final List<String> values = new ArrayList<>();
		static final String AND = ") AND (";
		static final String OR = ") OR (";
		final String keyWord, join;
		
		private BaseStatement(String keyWord, String join) {
			this.keyWord = keyWord;
			this.join = join;
		}
		
		@Nonnull
		protected String getOpen() {
			return "";
		}
		
		@Nonnull
		protected String getClose() {
			return "";
		}
		
		final void addValues(String... values) {
			if (values != null && values.length > 0) {
				addAll(this.values, values);
			}
		}
		
		protected final void builder(StringBuilder builder) {
			if (BaseStatement.this.values.isEmpty()) return;
			builder.append(keyWord).append(getOpen());
			String last = "_________________________";
			for (int i = 0; i < values.size(); i++) {
				String part = this.values.get(i);
				if (this.isJoin(i, part, last)) {
					builder.append(join);
				}
				builder.append(part);
				last = part;
			}
			builder.append(getClose());
		}
		
		private boolean isJoin(int index, String part, String last) {
			return index > 0 && !StringUtils.equals(AND, part) && //
					!StringUtils.equals(AND, last) &&   //
					!StringUtils.equals(OR, part) &&    //
					!StringUtils.equals(OR, last);
		}
	}
	
	// Table Statement
	private static class TableStatement extends BaseStatement {
		private TableStatement() {
			super("", ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
	}
	
	// Column Statement
	private static class ColumnStatement extends BaseStatement {
		private ColumnStatement() {
			super("", ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "(";
		}
		
		@Nonnull
		protected final String getClose() {
			return ") ";
		}
	}
	
	// Values Statement
	private static class ValuesStatement extends BaseStatement {
		private static final String VALUES = "\nVALUES ";
		
		private ValuesStatement() {
			super(VALUES, ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "(";
		}
		
		@Nonnull
		protected final String getClose() {
			return ")";
		}
	}
	
	// Field Statement
	private static class SelectStatement extends BaseStatement {
		private SelectStatement() {
			super("", ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
		
		private void addSelect(String... columns) {
			if (columns != null && columns.length > 0) {
				columns[0] = "\n\t" + columns[0];
				super.addValues(columns);
			}
		}
	}
	
	// From Statement
	private static class FromStatement extends BaseStatement {
		private static final String FROM = "\nFROM ";
		
		private FromStatement() {
			super(FROM, ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
	}
	
	// Join Statement
	private static class JoinStatement extends BaseStatement {
		private static final String JOIN = "\nJOIN ";
		
		private JoinStatement() {
			this(JOIN);
		}
		
		private JoinStatement(String word) {
			super(word, word);
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
		
	}
	
	// Left Join Statement
	private static class LeftJoinStatement extends JoinStatement {
		private static final String L_JOIN = "\nLEFT JOIN ";
		
		private LeftJoinStatement() {
			super(L_JOIN);
		}
	}
	
	// Right Join Statement
	private static class RightJoinStatement extends JoinStatement {
		private static final String R_JOIN = "\nRIGHT JOIN ";
		
		private RightJoinStatement() {
			super(R_JOIN);
		}
	}
	
	// Outer Join Statement
	private static class OuterJoinStatement extends JoinStatement {
		private static final String O_JOIN = "\nOUTER JOIN ";
		
		private OuterJoinStatement() {
			super(O_JOIN);
		}
	}
	
	// Set Statement
	private static class SetStatement extends BaseStatement {
		private static final String SET = "\nSET ";
		
		private SetStatement() {
			super(SET, ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
	}
	
	private static class OnDuplicateKeyUpdateStatement extends BaseStatement {
		protected OnDuplicateKeyUpdateStatement() {
			super("\nON DUPLICATE KEY UPDATE ", ",");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
	}
	
	// Where Statement
	private static class WhereStatement extends BaseStatement {
		private static final String WHERE = "\nWHERE ";
		
		private WhereStatement() {
			this(WHERE);
		}
		
		private WhereStatement(String word) {
			super(word, AND);
		}
		
		@Nonnull
		protected final String getOpen() {
			return "(";
		}
		
		@Nonnull
		protected final String getClose() {
			return ") ";
		}
		
		private void addAND() {
			values.add(AND);
		}
		
		private void addOR() {
			values.add(OR);
		}
	}
	
	// Group By Statement
	private static class GroupByStatement extends BaseStatement {
		private static final String GROUP_BY = "\nGROUP BY ";
		
		private GroupByStatement() {
			super(GROUP_BY, ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
	}
	
	private static class HavingStatement extends WhereStatement {
		private static final String HAVING = "\nHAVING ";
		
		private HavingStatement() {
			super(HAVING);
		}
	}
	
	// Order By Statement
	private static class OrderByStatement extends BaseStatement {
		private static final String ORDER_BY = "\nORDER BY ";
		
		private OrderByStatement() {
			super(ORDER_BY, ", ");
		}
		
		@Nonnull
		protected final String getOpen() {
			return "";
		}
		
		@Nonnull
		protected final String getClose() {
			return " ";
		}
	}
	
	private enum StatementType {
		INSERT, REPLACE, DELETE, UPDATE, SELECT, INSERT_UPDATE
	}
}
