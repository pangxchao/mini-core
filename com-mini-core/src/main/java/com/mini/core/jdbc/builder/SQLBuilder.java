package com.mini.core.jdbc.builder;

import com.mini.core.util.Assert;
import com.mini.core.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

import static com.mini.core.jdbc.builder.SQLInterfaceDef.getSQLInterface;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.addAll;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

/**
 * SQL构建器
 * <p>构建结果会根据最后一次提交更改的类型构建，其它与该构建类型不相关的子句将会被忽略<p/>
 * @author xchao
 */
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
	
	/**
	 * 创建一个空的SQL构建语句
	 */
	public SQLBuilder() {}
	
	/**
	 * 根据实体类对象类型创建个包括实体注解支持的基础查询语句
	 * @param type 实体类对象
	 * @param <T>  实体类型
	 */
	public <T> SQLBuilder(@Nonnull Class<T> type) {
		var inter = getSQLInterface(type);
		inter.createSelect(this, type);
	}
	
	/**
	 * 新增SQL注入中的参数
	 * <p>SQL注入参数需要根据构建SQL的顺序一一对应</p>
	 * @param args 新增参数列表
	 * @return {@code this}
	 */
	public final SQLBuilder args(@Nonnull Object... args) {
		this.args.addAll(asList(args));
		return this;
	}
	
	/**
	 * 获取SQL中注入的参数列表
	 * @return 注入参数列表
	 */
	public final Object[] args() {
		return args.toArray();
	}
	
	/**
	 * 根据列名称将SQL语句类型更改为INSERT INTO语句
	 * <p>该语句不能重复构建，后一次会覆盖前一次的表，但不会清空之前VALUE中的字段与值</p>
	 * <p>delete、update、select、from、join、set、where、groupBy、orderBy、having等与新增操作无关的SQL子句无效</p>
	 * @param table INSERT INTO 操作的列名称
	 * @return {@code this}
	 * @see #replaceInto(String)
	 */
	public final SQLBuilder insertInto(@Nonnull String table) {
		this.statement = StatementType.INSERT;
		this.table.addValues(table);
		return this;
	}
	
	/**
	 * 根据列名称将SQL语句类型更改为REPLACE INTO语句
	 * <p>该语句不能重复构建，后一次会覆盖前一次的表，但不会清空之前VALUE中的字段与值</p>
	 * <p>delete、update、select、from、join、set、where、groupBy、orderBy、having、onDuplicateKeyUpdate等与替换操作无关的SQL子句无效</p>
	 * @param table REPLACE INTO 操作的列名称
	 * @return {@code this}
	 * @see #insertInto(String)
	 */
	public final SQLBuilder replaceInto(@Nonnull String table) {
		this.statement = StatementType.REPLACE;
		this.table.addValues(table);
		return this;
	}
	
	/**
	 * 根据指定的表名称列表将构建的SQL语句更改为DELETE语句
	 * <p>该语句可以重复构建，后一次的表会追加到之前的表列表之后，最后构建时，表名列表中可以没有表名，将由from子名表名确定</p>
	 * <p>insertInto、replaceInto、update、select、values、set、groupBy、orderBy、having、onDuplicateKeyUpdate等与删除操作无关的SQL子句无效</p>
	 * @param tables 删除的表列表，该列表中的表名不能是子查询SQL语句
	 * @return {@code this}
	 */
	public final SQLBuilder delete(@Nonnull String... tables) {
		this.statement = StatementType.DELETE;
		this.table.addValues(tables);
		return this;
	}
	
	/**
	 * 根据指定的表名列表将构建的SQL语句更改为UPDATE语句
	 * <p>该语句可以重复构建，后一次的表会追加到之前的表列表之后，最后构建时，表名列表中必须至少有一个表名</p>
	 * <p>insertInto、replaceInto、delete、select、values、groupBy、orderBy、having、onDuplicateKeyUpdate等与修改操作无关的SQL子句无效</p>
	 * @param tables 修改的表列表，该列表中的表名不能是子查询SQL语句
	 * @return {@code this}
	 */
	public final SQLBuilder update(@Nonnull String... tables) {
		this.statement = StatementType.UPDATE;
		this.table.addValues(tables);
		return this;
	}
	
	/**
	 * 根据指定查询的字段列表将构建的SQL语句更改为SELECT语句
	 * <p>该语句可以重复构建，后一次的字段会追加到前一次字段列表之后，在构建时会用’,‘分隔生成正确的SQL字段拼接格式</p>
	 * <p>insertInto、replaceInto、delete、update、set、onDuplicateKeyUpdate等与删查询作无关的SQL子句无效</p>
	 * @param columns 想要查询的字段列表，该字段可以写可“字段名 AS `别名`”的形式
	 * @return {@code this}
	 */
	public final SQLBuilder select(@Nonnull String... columns) {
		this.statement = StatementType.SELECT;
		this.select.addSelect(columns);
		return this;
	}
	
	/**
	 * SELECT方法的快捷操作，调用代码为：
	 * <br />
	 * {@code String.select(format("COUNT(%s) AS `%s`", column, alias)) }
	 * @param column 字段名称; 必须符合查询字段名的写法，或者通配置符写法，如：user, *
	 * @param alias  别名; 必须符合字段别名的命名规则
	 * @return {@code this}
	 * @see String#format(String, Object...)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectCount(@Nonnull String column, @Nonnull String alias) {
		return select(format("COUNT(%s) AS `%s`", column, alias));
	}
	
	/**
	 * selectCount方法的快捷操作，调用代码为：
	 * <br />
	 * {@code selectCount(column, column) }
	 * @param column 字段名称 字段名称; 必须符合查询字段名的写法，或者通配置符写法，如：user, *
	 * @return {@code this}
	 * @see #selectCount(String, String)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectCount(@Nonnull String column) {
		return selectCount(column, column);
	}
	
	/**
	 * select方法的快捷操作，调用代码为：
	 * <br/>
	 * {@code select(format("SUM(%s) AS `%s`", column, alias)) }
	 * @param column 字段名称; 必须符合查询字段名的写法，如：user
	 * @param alias  别名; 必须符合字段别名的命名规则
	 * @return {@code this}
	 * @see String#format(String, Object...)
	 * #select(String...)
	 */
	public final SQLBuilder selectSum(@Nonnull String column, @Nonnull String alias) {
		return select(format("SUM(%s) AS `%s`", column, alias));
	}
	
	/**
	 * selectSum方法的快捷操作，调用代码为：
	 * <br />
	 * {@code selectSum(column, column) }
	 * @param column 字段名称
	 * @return {@code this}
	 * @see #selectSum(String, String)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectSum(@Nonnull String column) {
		return selectSum(column, column);
	}
	
	/**
	 * select方法的快捷操作，调用代码为：
	 * <br/>
	 * {@code select(format("AVG(%s) AS `%s`", column, alias)) }
	 * @param column 字段名称; 必须符合查询字段名的写法，如：user
	 * @param alias  别名; 必须符合字段别名的命名规则
	 * @return {@code this}
	 * @see String#format(String, Object...)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectAvg(@Nonnull String column, @Nonnull String alias) {
		return select(format("AVG(%s) AS `%s`", column, alias));
	}
	
	/**
	 * selectAvg方法的快捷操作，调用代码为：
	 * <br />
	 * {@code selectAvg(column, column) }
	 * @param column 字段名称; 必须符合查询字段名的写法，如：user
	 * @return {@code this}
	 * @see #selectAvg(String, String)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectAvg(@Nonnull String column) {
		return selectAvg(column, column);
	}
	
	/**
	 * select方法的快捷操作，调用代码为：
	 * <br/>
	 * {@code select(format("MAX(%s) AS `%s`", column, alias)) }
	 * @param column 字段名称; 必须符合查询字段名的写法，如：user
	 * @param alias  别名; 必须符合字段别名的命名规则
	 * @return {@code this}
	 * @see String#format(String, Object...)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectMax(@Nonnull String column, @Nonnull String alias) {
		return select(format("MAX(%s) AS `%s`", column, alias));
	}
	
	/**
	 * selectMax方法的快捷操作，调用代码为：
	 * <br />
	 * {@code selectMax(column, column) }
	 * @param column 字段名称; 必须符合查询字段名的写法，如：user
	 * @return {@code this}
	 * @see #selectMax(String, String)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectMax(@Nonnull String column) {
		return selectMax(column, column);
	}
	
	
	/**
	 * select方法的快捷操作，调用代码为：
	 * <br/>
	 * {@code select(format("MIN(%s) AS `%s`", column, alias)) }
	 * @param column 字段名称; 必须符合查询字段名的写法，如：user
	 * @param alias  别名; 必须符合字段别名的命名规则
	 * @return {@code this}
	 * @see String#format(String, Object...)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectMin(@Nonnull String column, @Nonnull String alias) {
		return select(format("MIN(%s) AS `%s`", column, alias));
	}
	
	/**
	 * selectMin方法的快捷操作，调用代码为：
	 * <br />
	 * {@code selectMin(column, column) }
	 * @param column 字段名称; 必须符合查询字段名的写法，如：user
	 * @return {@code this}
	 * @see #selectMin(String, String)
	 * @see #select(String...)
	 */
	public final SQLBuilder selectMin(@Nonnull String column) {
		return selectMin(column, column);
	}
	
	/**
	 * 根据指定查询的字段列表将构建的SQL语句更改为SELECT语句并设置整个查询结果去重
	 * <p>该语句使用方法同{@code select(String...)}，调用过该方法会在构建结果SELECT关键字后添加DISTINCT关键字</p>
	 * @param columns 想要查询的字段列表，该字段可以写可“字段名 AS `别名`”的形式
	 * @return @return {@code this}
	 * @see #select(String...)
	 */
	public final SQLBuilder selectDistinct(@Nonnull String... columns) {
		SQLBuilder.this.distinct = true;
		this.select(columns);
		return this;
	}
	
	/**
	 * INSERT INTO与REPLACE INTO语句字段设置
	 * <p>该方法的仅仅设置INSERT INTO与REPLACE INTO的字段，value参数只是VALUES子句的占位符、函数、属性等<p/>
	 * @param column 字段名-必须是数据库存在的字段名称
	 * @param value  字段占位符；比如：“?”、point(?, ?)、NOW()、CURRENT_TIMESTAMP等
	 * @return @return {@code this}
	 */
	public final SQLBuilder values(@Nonnull String column, @Nonnull String value) {
		this.columns.addValues(column);
		this.values.addValues(value);
		return this;
	}
	
	/**
	 * INSERT INTO与REPLACE INTO语句字段设置，默认占位符为“?”
	 * @param column 字段名-必须是数据库存在的字段名称
	 * @return @return {@code this}
	 * @see #values(String, String)
	 */
	public final SQLBuilder values(@Nonnull String column) {
		return values(column, "?");
	}
	
	/**
	 * DELETE、UPDATE、SELECT语句中的FROM子句
	 * <P>该方法可以重复调用，表列表会追加到之前的列表之后</P>
	 * <p>insertInto、replaceInto、values、onDuplicateKeyUpdate等SQL中没有FROM子句的SQL无效</p>
	 * @param tables FROM子句中的表列表
	 * @return @return {@code this}
	 */
	public final SQLBuilder from(@Nonnull String... tables) {
		this.from.addValues(tables);
		return this;
	}
	
	/**
	 * DELETE、UPDATE、SELECT语句中的FROM子句
	 * <P>该方法调用的FROM子句中是一个了查询</P>
	 * <P>该方法可以重复调用，表列表会追加到之前的列表之后</P>
	 * <p>insertInto、replaceInto、values、onDuplicateKeyUpdate等SQL中没有FROM子句的SQL无效</p>
	 * <p>该方法构建的FROM子句不能用于DELETE、UPDATE语句的FROM子名构建</p>
	 * @param builder 子查询SQL；该SQL的注入参数无效，需要将参数添加到主SQL的对应位置
	 * @return {@code this}
	 */
	public final SQLBuilder from(@Nonnull SQLBuilder builder) {
		return from(builder.toString());
	}
	
	/**
	 * DELETE、UPDATE、SELECT语句中的JOIN子句
	 * <P>格式化字符串参数可以是一个子查询</P>
	 * <P>该方法可以多次调用，创建多个JOIN子句</P>
	 * <p>insertInto、replaceInto、values、onDuplicateKeyUpdate等SQL中没有JOIN子句的SQL无效</p>
	 * <p>该方法构建的JOIN子句如果是一个子查询语句，不能用于DELETE、UPDATE语句的JOIN子名构建</p>
	 * @param format 子句内容格式化字符串
	 * @param args   内容格式化字符串中的参数
	 * @return @return {@code this}
	 */
	public final SQLBuilder join(@Nonnull String format, @Nonnull Object... args) {
		join.addValues(format(format, args));
		return this;
	}
	
	/**
	 * JOIN子句的单表联合简便方式，调用代码为：
	 * <br />
	 * {@code join("%s ON %s = %s", table, column, joinColumn) }
	 * @param table      联合表名称-格式化字符串的第一个参数
	 * @param column     当前表字段-格式化字符串的第二个参数
	 * @param joinColumn 联合表字段-格式化字符串的第三个参数
	 * @return {@code this}
	 * @see #join(String, Object...)
	 */
	public final SQLBuilder joinSingle(@Nonnull String table, @Nonnull String column, @Nonnull String joinColumn) {
		return join("%s ON %s = %s", table, column, joinColumn);
	}
	
	/**
	 * DELETE、UPDATE、SELECT语句中的LEFT JOIN子句
	 * <P>格式化字符串参数可以是一个子查询</P>
	 * <P>该方法可以多次调用，创建多个LEFT JOIN子句</P>
	 * <p>insertInto、replaceInto、values、onDuplicateKeyUpdate等SQL中没有LEFT JOIN子句的SQL无效</p>
	 * <p>该方法构建的LEFT JOIN子句如果是一个子查询语句，不能用于DELETE、UPDATE语句的JOIN子名构建</p>
	 * @param format 子句内容格式化字符串
	 * @param args   内容格式化字符串中的参数
	 * @return @return {@code this}
	 */
	public final SQLBuilder leftJoin(@Nonnull String format, Object... args) {
		leftJoin.addValues(format(format, args));
		return this;
	}
	
	/**
	 * LEFT JOIN子句的单表联合简便方式，调用代码为：
	 * <br />
	 * {@code leftJoin("%s ON %s = %s", table, column, joinColumn) }
	 * @param table      联合表名称-格式化字符串的第一个参数
	 * @param column     当前表字段-格式化字符串的第二个参数
	 * @param joinColumn 联合表字段-格式化字符串的第三个参数
	 * @return {@code this}
	 * @see #leftJoin(String, Object...)
	 */
	public final SQLBuilder leftJoinSingle(@Nonnull String table, @Nonnull String column, @Nonnull String joinColumn) {
		return leftJoin("%s ON %s = %s", table, column, joinColumn);
	}
	
	/**
	 * DELETE、UPDATE、SELECT语句中的RIGHT JOIN子句
	 * <P>格式化字符串参数可以是一个子查询</P>
	 * <P>该方法可以多次调用，创建多个RIGHT JOIN子句</P>
	 * <p>insertInto、replaceInto、values、onDuplicateKeyUpdate等SQL中没有RIGHT JOIN子句的SQL无效</p>
	 * <p>该方法构建的RIGHT JOIN子句如果是一个子查询语句，不能用于DELETE、UPDATE语句的JOIN子名构建</p>
	 * @param format 子句内容格式化字符串
	 * @param args   内容格式化字符串中的参数
	 * @return @return {@code this}
	 */
	public final SQLBuilder rightJoin(@Nonnull String format, Object... args) {
		rightJoin.addValues(format(format, args));
		return this;
	}
	
	/**
	 * RIGHT JOIN子句的单表联合简便方式，调用代码为：
	 * <br />
	 * {@code rightJoin("%s ON %s = %s", table, column, joinColumn) }
	 * @param table      联合表名称-格式化字符串的第一个参数
	 * @param column     当前表字段-格式化字符串的第二个参数
	 * @param joinColumn 联合表字段-格式化字符串的第三个参数
	 * @return {@code this}
	 * @see #rightJoin(String, Object...)
	 */
	public final SQLBuilder rightJoinSingle(@Nonnull String table, @Nonnull String column, @Nonnull String joinColumn) {
		return rightJoin("%s ON %s = %s", table, column, joinColumn);
	}
	
	/**
	 * DELETE、UPDATE、SELECT语句中的OUTER JOIN子句
	 * <P>格式化字符串参数可以是一个子查询</P>
	 * <P>该方法可以多次调用，创建多个OUTER JOIN子句</P>
	 * <p>insertInto、replaceInto、values、onDuplicateKeyUpdate等SQL中没有OUTER JOIN子句的SQL无效</p>
	 * <p>该方法构建的OUTER JOIN子句如果是一个子查询语句，不能用于DELETE、UPDATE语句的JOIN子名构建</p>
	 * @param format 子句内容格式化字符串
	 * @param args   内容格式化字符串中的参数
	 * @return @return {@code this}
	 */
	public final SQLBuilder outerJoin(@Nonnull String format, Object... args) {
		outerJoin.addValues(format(format, args));
		return this;
	}
	
	/**
	 * OUTER JOIN子句的单表联合简便方式，调用代码为：
	 * <br />
	 * {@code outerJoin("%s ON %s = %s", table, column, joinColumn) }
	 * @param table      联合表名称-格式化字符串的第一个参数
	 * @param column     当前表字段-格式化字符串的第二个参数
	 * @param joinColumn 联合表字段-格式化字符串的第三个参数
	 * @return {@code this}
	 * @see #outerJoin(String, Object...)
	 */
	public final SQLBuilder outerJoinSingle(@Nonnull String table, @Nonnull String column, @Nonnull String joinColumn) {
		return outerJoin("%s ON %s = %s", table, column, joinColumn);
	}
	
	/**
	 * UPDATE语句中的SET子句
	 * <P>该方法可以多次调用，会将子句内容追加到之前的内容后面</P>
	 * @param format 子句内容格式化字条串
	 * @param args   格式化字条串中的参数
	 * @return {@code this}
	 */
	public final SQLBuilder set(@Nonnull String format, @Nullable Object... args) {
		this.set.addValues(format(format, args));
		return this;
	}
	
	/**
	 * UPDATE语句中的SET子句简便写法，调用代码为：
	 * <br/>
	 * {@code set("%s = ?", column).args(arg)}
	 * @param column 要修改的字段名；格式化字符串中的参数同该值替换
	 * @param arg    修改字段对应的值，该值不是占位符，是数据库修改后的实际值
	 * @return {@code this}
	 * @see #set(String, Object...)
	 */
	public final SQLBuilder setEquals(@Nonnull String column, @Nullable Object arg) {
		return set("%s = ?", column).args(arg);
	}
	
	/**
	 * UPDATE语句中的SET子句简便写法，调用代码为：
	 * <br/>
	 * {@code set("%s = %s + ?", column, column).args(arg) }
	 * @param column 要修改的字段名；两个格式化字符串的参数都由该值替换
	 * @param arg    修改字段对应的值，该值不是占位符，是数据库修改后的实际值
	 * @return {@code this}
	 * @see #set(String, Object...)
	 */
	public final SQLBuilder setIncrease(@Nonnull String column, @Nullable Object arg) {
		return set("%s = %s + ?", column, column).args(arg);
	}
	
	/**
	 * INSERT INTO语句中的ON DUPLICATE KEY UPDATE子句
	 * <P>该方法可以多次调用，会将子句内容追加到之前的内容后面</P>
	 * @param format 子句内容格式化字条串
	 * @param args   格式化字条串中的参数
	 * @return {@code this}
	 */
	public final SQLBuilder onDuplicateKeyUpdate(@Nonnull String format, @Nullable Object... args) {
		this.onDuplicateKeyUpdate.addValues(format(format, args));
		this.statement = StatementType.INSERT_UPDATE;
		return this;
	}
	
	/**
	 * INSERT INTO语句中的ON DUPLICATE KEY UPDATE子句简便写法，调用代码为：
	 * <br/>
	 * {@code onDuplicateKeyUpdate("%s = VALUES(%s)", column, column) }
	 * @param column 修改字段名；格式化字符串中的两个参数都由该值替换
	 * @return {@code this}
	 * @see #onDuplicateKeyUpdate(String, Object...)
	 */
	public final SQLBuilder onDuplicateKeyUpdateFromInsert(@Nonnull String column) {
		return onDuplicateKeyUpdate("%s = VALUES(%s)", column, column);
	}
	
	/**
	 * INSERT INTO语句中的ON DUPLICATE KEY UPDATE子句简便写法，调用代码为：
	 * <br/>
	 * {@code onDuplicateKeyUpdate("%s = ?", column).args(arg) }
	 * @param column 修改字段名；格式化字符串中的参数由该值替换
	 * @param arg    修改字段对应的值，该值不是占位符，是数据库修改后的实际值
	 * @return {@code this}
	 * @see #onDuplicateKeyUpdate(String, Object...)
	 */
	public final SQLBuilder onDuplicateKeyUpdateEquals(@Nonnull String column, @Nullable Object arg) {
		return onDuplicateKeyUpdate("%s = ?", column).args(arg);
	}
	
	/**
	 * INSERT INTO语句中的ON DUPLICATE KEY UPDATE子句简便写法，调用代码为：
	 * <br/>
	 * {@code onDuplicateKeyUpdate("%s = %s + ?", column, column).args(arg) }
	 * @param column 修改字段名；格式化字符串中的两个参数都由该值替换
	 * @param arg    修改字段对应的值，该值不是占位符，是数据库修改后的实际值
	 * @return {@code this}
	 * @see #onDuplicateKeyUpdate(String, Object...)
	 */
	public final SQLBuilder onDuplicateKeyUpdateIncrease(@Nonnull String column, @Nullable Object arg) {
		return onDuplicateKeyUpdate("%s = %s + ?", column, column).args(arg);
	}
	
	/**
	 * 将WHERE子句、HAVING子句的连接条件改为AND
	 * @return {@code this}
	 */
	public final SQLBuilder and() {
		if (last != null) {
			last.addAND();
		}
		return this;
	}
	
	/**
	 * 将WHERE子句、HAVING子句的连接条件改为OR
	 * @return {@code this}
	 */
	public final SQLBuilder or() {
		if (last != null) {
			last.addOR();
		}
		return this;
	}
	
	/**
	 * DELETE、UPDATE、SELECT语句的WHERE子句
	 * <P>该方法可以多次调用，会将子句内容追加到之前的内容后面</P>
	 * @param format 格式化字符串
	 * @param args   格式化字符串参数列表
	 * @return {@code this}
	 */
	public final SQLBuilder where(@Nonnull String format, @Nonnull Object... args) {
		this.where.addValues(format(format, args));
		this.last = this.where;
		return this;
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? where("%s IS NULL", column) : where("%s = ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereEquals(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? where("%s IS NULL", column) : where("%s = ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : where("%s = ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereEqualsIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : where("%s = ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? where("%s IS NOT NULL", column) : where("%s <> ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereNotEqual(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? where("%s IS NOT NULL", column) : where("%s <> ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : where("%s <> ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereNotEqualIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : where("%s <> ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("%s > ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereGreaterThan(@Nonnull String column, @Nullable Object arg) {
		return where("%s > ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : whereGreaterThan(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereGreaterThan(String, Object arg)
	 */
	public final SQLBuilder whereGreaterThanIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : whereGreaterThan(column, arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("%s < ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereLessThan(@Nonnull String column, @Nullable Object arg) {
		return where("%s < ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : whereLessThan(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereLessThan(String, Object)
	 */
	public final SQLBuilder whereLessThanIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : whereLessThan(column, arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("%s >= ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereGreaterThanOrEquals(@Nonnull String column, @Nullable Object arg) {
		return where("%s >= ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : whereGreaterThanOrEquals(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereGreaterThanOrEquals(String, Object)
	 */
	public final SQLBuilder whereGreaterThanOrEqualsIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : whereGreaterThanOrEquals(column, arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("%s <= ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereLessThanOrEquals(@Nonnull String column, @Nullable Object arg) {
		return where("%s <= ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : whereLessThanOrEquals(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereLessThanOrEquals(String, Object)
	 */
	public final SQLBuilder whereLessThanOrEqualsIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : whereLessThanOrEquals(column, arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("%s IN (%s)", column, of(args).map(o -> "?").collect(joining(", "))).args(args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.stream.Stream#of(Object[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull Object[] args) {
		Assert.isTrue(args.length > 0, "WhereIn args can not be empty.");
		return where("%s IN (%s)", column, of(args).map(o -> "?")
				.collect(joining(", "))).args(args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, Object[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable Object[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, stream(args).boxed().toArray()) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.Arrays#stream(int[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull double[] args) {
		return whereIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, double[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable double[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull float[] args) {
		return whereIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, float[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable float[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, stream(args).boxed().toArray()) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.Arrays#stream(int[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull long[] args) {
		return whereIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, long[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable long[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, stream(args).boxed().toArray()) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.Arrays#stream(int[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull int[] args) {
		return whereIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, int[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable int[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull short[] args) {
		return whereIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, short[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable short[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull byte[] args) {
		return whereIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, byte[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable byte[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull char[] args) {
		return whereIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, char[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable char[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code whereIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereIn(@Nonnull String column, @Nonnull boolean[] args) {
		return whereIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see #whereIn(String, boolean[])
	 */
	public final SQLBuilder whereInIfNotEmpty(@Nonnull String column, @Nullable boolean[] args) {
		return isNull(args) || args.length <= 0 ? this : whereIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("%s LIKE ?", column).args(arg+ "%") }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereLike(@Nonnull String column, @Nonnull String arg) {
		return where("%s LIKE ?", column).args(arg + "%");
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code  StringUtil.isBlank(arg) ? this : whereLike(column, arg+ "%") }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see StringUtil#isBlank(CharSequence)
	 * @see #whereLike(String, String)
	 */
	public final SQLBuilder whereLikeIfNotBlank(@Nonnull String column, @Nullable String arg) {
		return StringUtil.isBlank(arg) ? this : whereLike(column, arg + "%");
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil.join(columns, ", ")).args(arg) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see StringUtil#join(Object[], String)
	 */
	public final SQLBuilder whereMatchInBooleanMode(@Nullable String arg, @Nonnull String... columns) {
		Assert.isTrue(columns.length > 0, "whereMatchInBooleanMode columns can not be empty.");
		return where("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil.join(columns, ", ")).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code  StringUtil.isBlank(arg) ? this : whereMatchInBooleanMode(arg, columns) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see StringUtil#isBlank(CharSequence)
	 * @see StringUtil#join(Object[], String)
	 */
	public final SQLBuilder whereMatchInBooleanModeIfNotBlank(@Nullable String arg, @Nonnull String... columns) {
		return StringUtil.isBlank(arg) ? this : whereMatchInBooleanMode(arg, columns);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("MATCH(%s) AGAINST(?)", StringUtil.join(columns, ',')).args(arg) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see StringUtil#join(Object[], String)
	 */
	public final SQLBuilder whereMatch(@Nullable String arg, @Nonnull String... columns) {
		Assert.isTrue(columns.length > 0, "whereMatch columns can not be empty.");
		return where("MATCH(%s) AGAINST(?)", StringUtil.join(columns, ", ")).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code StringUtil.isBlank(arg) ? this : whereMatch(arg, columns) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #where(String, Object...)
	 * @see StringUtil#isBlank(CharSequence)
	 */
	public final SQLBuilder whereMatchIfNotBlank(@Nullable String arg, @Nonnull String... columns) {
		return StringUtil.isBlank(arg) ? this : whereMatch(arg, columns);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("%s BETWEEN ? AND ?", column).args(min, max) }
	 * @param column 条件字段
	 * @param min    参数最小值
	 * @param max    参数最大值
	 * @return {@code this}
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder whereBetweenAnd(@Nonnull String column, @Nonnull Object min, @Nonnull Object max) {
		return where("%s BETWEEN ? AND ?", column).args(min, max);
	}
	
	/**
	 * SELECT语句中的GROUP BY子句
	 * @param columns GROUP BY子句后面的字段名称，符合SQL中GROUP BY子句字段标准
	 * @return {@code this}
	 */
	public final SQLBuilder groupBy(@Nonnull String... columns) {
		groupBy.addValues(columns);
		return this;
	}
	
	/**
	 * SELECT语句的HAVING子句
	 * <P>该方法可以多次调用，会将子句内容追加到之前的内容后面</P>
	 * @param format 格式化字符串
	 * @param args   格式化字符串参数列表
	 * @return {@code this}
	 */
	public final SQLBuilder having(@Nonnull String format, @Nonnull Object... args) {
		this.having.addValues(format(format, args));
		this.last = this.having;
		return this;
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? having("%s IS NULL", column) : having("%s = ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingEquals(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? having("%s IS NULL", column) : having("%s = ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : having("%s = ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingEqualsIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : having("%s = ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? having("%s IS NOT NULL", column) : having("%s <> ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingNotEqual(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? having("%s IS NOT NULL", column) : having("%s <> ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : having("%s <> ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingNotEqualIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : having("%s <> ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code  having("%s > ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingGreaterThan(@Nonnull String column, @Nullable Object arg) {
		return having("%s > ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : havingGreaterThan(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingGreaterThan(String, Object arg)
	 */
	public final SQLBuilder havingGreaterThanIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : havingGreaterThan(column, arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code having("%s < ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingLessThan(@Nonnull String column, @Nullable Object arg) {
		return having("%s < ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : havingLessThan(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingLessThan(String, Object)
	 */
	public final SQLBuilder havingLessThanIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : havingLessThan(column, arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code having("%s >= ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingGreaterThanOrEquals(@Nonnull String column, @Nullable Object arg) {
		return having("%s >= ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : havingGreaterThanOrEquals(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingGreaterThanOrEquals(String, Object)
	 */
	public final SQLBuilder havingGreaterThanOrEqualsIfNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : havingGreaterThanOrEquals(column, arg);
	}
	
	/**
	 * having子句简便写法，调用代码为：
	 * <br/>
	 * {@code having("%s <= ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingLessThanOrEquals(@Nonnull String column, @Nullable Object arg) {
		return having("%s <= ?", column).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(arg) ? this : havingLessThanOrEquals(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingLessThanOrEquals(String, Object)
	 */
	public final SQLBuilder havingLessThanOrEqualsIfNotNull(@Nonnull String column, @Nullable Object arg) {
		return isNull(arg) ? this : havingLessThanOrEquals(column, arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code having("%s IN (%s)", column, of(args).map(o -> "?").collect(joining(", "))).args(args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.stream.Stream#of(Object[])
	 * @see #where(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull Object[] args) {
		Assert.isTrue(args.length > 0, "havingIn args can not be empty.");
		return having("%s IN (%s)", column, of(args).map(o -> "?")
				.collect(joining(", "))).args(args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, Object[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable Object[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, stream(args).boxed().toArray()) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.Arrays#stream(int[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull double[] args) {
		return havingIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, double[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable double[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull float[] args) {
		return havingIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, float[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable float[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, stream(args).boxed().toArray()) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.Arrays#stream(int[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull long[] args) {
		return havingIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : whereIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, long[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable long[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, stream(args).boxed().toArray()) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see java.util.Arrays#stream(int[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull int[] args) {
		return havingIn(column, stream(args).boxed().toArray());
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, int[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable int[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull short[] args) {
		return havingIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, short[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable short[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull byte[] args) {
		return havingIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, byte[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable byte[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull char[] args) {
		return havingIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, char[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable char[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code havingIn(column, ArrayUtils.toObject(args)) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see ArrayUtils#toObject(float[])
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingIn(@Nonnull String column, @Nonnull boolean[] args) {
		return havingIn(column, ArrayUtils.toObject(args));
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code isNull(args) || args.length <= 0 ? this : havingIn(column, args) }
	 * @param column 条件字段
	 * @param args   参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see #havingIn(String, boolean[])
	 */
	public final SQLBuilder havingInIfNotEmpty(@Nonnull String column, @Nullable boolean[] args) {
		return isNull(args) || args.length <= 0 ? this : havingIn(column, args);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code having("%s LIKE ?", column).args(arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingLike(@Nonnull String column, @Nonnull String arg) {
		return having("%s LIKE ?", column).args(arg);
	}
	
	/**
	 * WHERE子句简便写法，调用代码为：
	 * <br/>
	 * {@code  StringUtil.isBlank(arg) ? this : havingLike(column, arg) }
	 * @param column 条件字段
	 * @param arg    参数，该参数不是占位符，是数据库中修改的目标值
	 * @return {@code this}
	 * @see StringUtil#isBlank(CharSequence)
	 * @see #havingLike(String, String)
	 */
	public final SQLBuilder havingLikeIfNotBlank(@Nonnull String column, @Nullable String arg) {
		return StringUtil.isBlank(arg) ? this : havingLike(column, arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil.join(columns, ", ")).args(arg) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see StringUtil#join(Object[], String)
	 */
	public final SQLBuilder havingMatchInBooleanMode(@Nullable String arg, @Nonnull String... columns) {
		Assert.isTrue(columns.length > 0, "whereMatchInBooleanMode columns can not be empty.");
		return having("MATCH(%s) AGAINST(? in BOOLEAN MODE)", StringUtil //
				.join(columns, ", ")).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code StringUtil.isBlank(arg) ? this : havingMatchInBooleanMode(arg, columns) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see StringUtil#isBlank(CharSequence)
	 */
	public final SQLBuilder havingMatchInBooleanModeIfNotBlank(@Nullable String arg, @Nonnull String... columns) {
		return StringUtil.isBlank(arg) ? this : havingMatchInBooleanMode(arg, columns);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code where("MATCH(%s) AGAINST(?)", StringUtil.join(columns, ',')).args(arg) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see StringUtil#join(Object[], String)
	 */
	public final SQLBuilder havingMatch(@Nullable String arg, @Nonnull String... columns) {
		Assert.isTrue(columns.length > 0, "whereMatch columns can not be empty.");
		return having("MATCH(%s) AGAINST(?)", StringUtil.join(columns, ',')).args(arg);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code StringUtil.isBlank(arg) ? this : havingMatch(arg, columns) }
	 * @param arg     参数，该参数不是占位符，是数据库中修改的目标值
	 * @param columns 字段名称列表
	 * @return {@code this}
	 * @see #having(String, Object...)
	 * @see StringUtil#isBlank(CharSequence)
	 */
	public final SQLBuilder havingMatchIfNotBlank(@Nullable String arg, @Nonnull String... columns) {
		return StringUtil.isBlank(arg) ? this : havingMatch(arg, columns);
	}
	
	/**
	 * HAVING子句简便写法，调用代码为：
	 * <br/>
	 * {@code having("%s BETWEEN ? AND ?", column).args(min, max) }
	 * @param column 条件字段
	 * @param min    参数最小值
	 * @param max    参数最大值
	 * @return {@code this}
	 * @see #having(String, Object...)
	 */
	public final SQLBuilder havingBetweenAnd(@Nonnull String column, @Nonnull Object min, @Nonnull Object max) {
		return having("%s BETWEEN ? AND ?", column).args(min, max);
	}
	
	/**
	 * SELECT语句中的ORDER BY子句
	 * @param format 子句格式化字符串
	 * @param args   格式化字符串中的参数
	 * @return {@code this}
	 */
	public final SQLBuilder orderBy(@Nonnull String format, @Nonnull Object... args) {
		orderBy.addValues(format(format, args));
		return this;
	}
	
	/**
	 * ORDER BY子句的简便写法，调用代码为：
	 * <br />
	 * {@code of(columns).forEach(column -> orderBy("%s ASC", column)); }
	 * @param columns 排序字段
	 * @return {@code this}
	 */
	public final SQLBuilder orderByAsc(@Nonnull String... columns) {
		of(columns).forEach(column -> orderBy("%s ASC", column));
		return this;
	}
	
	/**
	 * ORDER BY子句的简便写法，调用代码为：
	 * <br />
	 * {@code of(columns).forEach(column -> orderBy("%s DESC", column)); }
	 * @param columns 排序字段
	 * @return {@code this}
	 */
	public final SQLBuilder orderByDesc(@Nonnull String... columns) {
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
		return builder.toString();
	}
	
	// Replace Into
	private String replaceString() throws Error {
		StringBuilder builder = new StringBuilder();
		builder.append("REPLACE INTO ");
		table.builder(builder);
		columns.builder(builder);
		values.builder(builder);
		return builder.toString();
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
		return builder.toString();
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
		return builder.toString();
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
		return builder.toString();
	}
	
	private String insertOnUpdateString() throws Error {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO ");
		table.builder(builder);
		columns.builder(builder);
		values.builder(builder);
		onDuplicateKeyUpdate.builder(builder);
		return builder.toString();
	}
	
	/**
	 * 获取SQL完整内容
	 * @return SQL完整内容
	 */
	public synchronized final String toSQL() {
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
		// INSERT INTO ON DUPLICATE KEY UPDATE
		if (statement == StatementType.INSERT_UPDATE) {
			return this.insertOnUpdateString();
		}
		// statement为空，语句错误
		throw new RuntimeException("SQL ERROR!");
	}
	
	@Override
	public synchronized final String toString() {
		return format("%s\n%s", this.toSQL(), //
				Arrays.toString(args()));
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
