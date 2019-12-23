package com.mini.core.jdbc.builder;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.addAll;

public class SQLBuilder {
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
	private final List<Object> params = new ArrayList<>();
	private final SetStatement set = new SetStatement();
	private WhereStatement last = null;
	private StatementType statement;
	private boolean distinct;

	public SQLBuilder() {}

	public <T> SQLBuilder(Class<T> type) {

	}

	public final SQLBuilder params(Object... param) {
		params.addAll(asList(param));
		return this;
	}

	public final Object[] toArray() {
		return params.toArray();
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

	public final SQLBuilder select_distinct(String... columns) {
		SQLBuilder.this.distinct = true;
		this.select(columns);
		return this;
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

	public final SQLBuilder join(String format, Object... args) {
		join.addValues(format(format, args));
		return this;
	}

	public final SQLBuilder leftJoin(String format, Object... args) {
		leftJoin.addValues(format(format, args));
		return this;
	}

	public final SQLBuilder rightJoin(String format, Object... args) {
		rightJoin.addValues(format(format, args));
		return this;
	}

	public final SQLBuilder outerJoin(String format, Object... args) {
		outerJoin.addValues(format(format, args));
		return this;
	}

	public final SQLBuilder set(String format, Object... args) {
		this.set.addValues(format(format, args));
		return this;
	}

	public final SQLBuilder where(String format, Object... args) {
		this.where.addValues(format(format, args));
		this.last = this.where;
		return this;
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

	public final SQLBuilder orderBy(String format, Object... args) {
		orderBy.addValues(format(format, args));
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

	/**
	 * 获取SQL完整内容
	 * @return SQL完整内容
	 */
	public synchronized final String toString() {
		//  Insert 语句
		if (statement == StatementType.INSERT) {
			return this.insertString();
		}

		// Replace 语句
		if (statement == StatementType.REPLACE) {
			return this.replaceString();
		}

		// Delete 语句
		if (statement == StatementType.DELETE) {
			return this.deleteString();
		}

		// Update 语句
		if (statement == StatementType.UPDATE) {
			return this.updateString();
		}

		// Select 语句
		if (statement == StatementType.SELECT) {
			return this.selectString();
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
			this.join    = join;
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
		INSERT, REPLACE, DELETE, UPDATE, SELECT
	}
}
