package com.mini.jdbc;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.mini.util.StringUtil.eq;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.addAll;

public class SQLBuilder {
    private final OuterJoinStatement outer_join = new OuterJoinStatement();
    private final RightJoinStatement right_join = new RightJoinStatement();
    private final LeftJoinStatement left_join = new LeftJoinStatement();
    private final GroupByStatement group_by = new GroupByStatement();
    private final OrderByStatement order_by = new OrderByStatement();
    private final ColumnStatement _column = new ColumnStatement();
    private final HavingStatement _having = new HavingStatement();
    private final ValuesStatement _values = new ValuesStatement();
    private final SelectStatement _select = new SelectStatement();
    private final TableStatement _table = new TableStatement();
    private final WhereStatement _where = new WhereStatement();
    private final FromStatement _from = new FromStatement();
    private final JoinStatement _join = new JoinStatement();
    private final List<Object> _params = new ArrayList<>();
    private final SetStatement _set = new SetStatement();
    private WhereStatement _last = null;
    private StatementType _statement;
    private boolean _distinct;

    public final SQLBuilder params(Object... param) {
        _params.addAll(asList(param));
        return this;
    }

    public final Object[] toArray() {
        return _params.toArray();
    }

    public final SQLBuilder insert_into(String table) {
        this._statement = StatementType.INSERT;
        this._table.addValues(table);
        return this;
    }

    public final SQLBuilder replace_into(String table) {
        this._statement = StatementType.REPLACE;
        this._table.addValues(table);
        return this;
    }

    public final SQLBuilder delete(String... tables) {
        this._statement = StatementType.DELETE;
        this._table.addValues(tables);
        return this;
    }

    public final SQLBuilder update(String... tables) {
        this._statement = StatementType.UPDATE;
        this._table.addValues(tables);
        return this;
    }

    public final SQLBuilder select(String... columns) {
        this._statement = StatementType.SELECT;
        this._select.addSelect(columns);
        return this;
    }

    public final SQLBuilder select_distinct(String... columns) {
        SQLBuilder.this._distinct = true;
        this.select(columns);
        return this;
    }

    public final SQLBuilder values(String column, String value) {
        this._column.addValues(column);
        this._values.addValues(value);
        return this;
    }

    public final SQLBuilder values(String column) {
        return values(column, "?");
    }

    public final SQLBuilder from(String... tables) {
        this._from.addValues(tables);
        return this;
    }

    public final SQLBuilder join(String format, Object... args) {
        _join.addValues(format(format, args));
        return this;
    }

    public final SQLBuilder left_join(String format, Object... args) {
        left_join.addValues(format(format, args));
        return this;
    }

    public final SQLBuilder right_join(String format, Object... args) {
        right_join.addValues(format(format, args));
        return this;
    }

    public final SQLBuilder outer_join(String format, Object... args) {
        outer_join.addValues(format(format, args));
        return this;
    }

    public final SQLBuilder set(String format, Object... args) {
        this._set.addValues(format(format, args));
        return this;
    }

    public final SQLBuilder where(String format, Object... args) {
        this._where.addValues(format(format, args));
        this._last = this._where;
        return this;
    }

    public final SQLBuilder and() {
        if (_last != null) {
            _last.addAND();
        }
        return this;
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    public final SQLBuilder or() {
        if (_last != null) {
            _last.addOR();
        }
        return this;
    }

    public final SQLBuilder group_by(String... columns) {
        group_by.addValues(columns);
        return this;
    }

    public final SQLBuilder having(String format, Object... args) {
        this._having.addValues(format(format, args));
        this._last = this._having;
        return this;
    }

    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    public final SQLBuilder order_by(String format, Object... args) {
        order_by.addValues(format(format, args));
        return this;
    }

    // Insert Into
    private String insertString() throws Error {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        _table.builder(builder);
        _column.builder(builder);
        _values.builder(builder);
        return builder.toString(); //
    }

    // Replace Into
    private String replaceString() throws Error {
        StringBuilder builder = new StringBuilder();
        builder.append("REPLACE INTO ");
        _table.builder(builder);
        _column.builder(builder);
        _values.builder(builder);
        return builder.toString(); //
    }

    // Delete Into
    private String deleteString() throws Error {
        StringBuilder builder = new StringBuilder();
        _table.builder(builder.append("DELETE "));
        this._from.builder(builder);
        this._join.builder(builder);
        left_join.builder(builder);
        right_join.builder(builder);
        outer_join.builder(builder);
        this._where.builder(builder);
        return builder.toString(); //
    }

    // Update Into
    private String updateString() throws Error {
        StringBuilder builder = new StringBuilder();
        _table.builder(builder.append("UPDATE "));
        this._join.builder(builder);
        left_join.builder(builder);
        right_join.builder(builder);
        outer_join.builder(builder);
        this._set.builder(builder);
        this._where.builder(builder);
        return builder.toString(); //
    }

    // Select Into
    private String selectString() throws Error {
        StringBuilder builder = new StringBuilder("SELECT ");
        builder.append(_distinct ? "DISTINCT " : "");
        this._select.builder(builder);
        this._from.builder(builder);
        this._join.builder(builder);
        left_join.builder(builder);
        right_join.builder(builder);
        outer_join.builder(builder);
        this._where.builder(builder);
        this.group_by.builder(builder);
        this._having.builder(builder);
        this.order_by.builder(builder);
        return builder.toString(); //
    }

    /**
     * 获取SQL完整内容
     * @return SQL完整内容
     */
    public synchronized final String toString() {
        //  Insert 语句
        if (_statement == StatementType.INSERT) {
            return this.insertString();
        }

        // Replace 语句
        if (_statement == StatementType.REPLACE) {
            return this.replaceString();
        }

        // Delete 语句
        if (_statement == StatementType.DELETE) {
            return this.deleteString();
        }

        // Update 语句
        if (_statement == StatementType.UPDATE) {
            return this.updateString();
        }

        // Select 语句
        if (_statement == StatementType.SELECT) {
            return this.selectString();
        }
        // statement为空，语句错误
        throw new RuntimeException("SQL ERROR!");
    }

    // Base Statement
    private static abstract class BaseStatement {
        final List<String> values = new ArrayList<>();
        static final String _AND = ") AND (";
        static final String _OR = ") OR (";
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
            return index > 0 && !eq(_AND, part) && !eq(_OR, part) //
                    && !eq(_AND, last) && !eq(_OR, last);
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
            super(word, _AND);
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
            this.values.add(_AND);
        }

        private void addOR() {
            this.values.add(_OR);
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
