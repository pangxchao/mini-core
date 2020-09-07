package com.mini.core.jdbc.builder;

import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.mini.core.jdbc.builder.SQLInterface.sqlInterface;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Arrays.stream;
import static java.util.Collections.addAll;
import static java.util.stream.Collectors.joining;

/**
 * SQL构建器
 *
 * @author xchao
 */
@SuppressWarnings("UnusedReturnValue")
public class SQLBuilder implements Serializable {
    private final OnDuplicateKeyUpdateStatement ON_DUPLICATE_KEY_UPDATE = new OnDuplicateKeyUpdateStatement();
    private final OuterJoinStatement OUTER_JOIN = new OuterJoinStatement();
    private final InnerJoinStatement INNER_JOIN = new InnerJoinStatement();
    private final RightJoinStatement RIGHT_JOIN = new RightJoinStatement();
    private final LeftJoinStatement LEFT_JOIN = new LeftJoinStatement();
    private final GroupByStatement GROUP_BY = new GroupByStatement();
    private final OrderByStatement ORDER_BY = new OrderByStatement();
    private final ColumnStatement COLUMNS = new ColumnStatement();
    private final HavingStatement HAVING = new HavingStatement();
    private final ValuesStatement VALUES = new ValuesStatement();
    private final SelectStatement SELECT = new SelectStatement();
    private final TableStatement TABLE = new TableStatement();
    private final WhereStatement WHERE = new WhereStatement();
    private final FromStatement FROM = new FromStatement();
    private final JoinStatement JOIN = new JoinStatement();
    private final SetStatement SET = new SetStatement();
    private StatementType STATEMENT = null;
    private WhereStatement LAST = null;
    private boolean DISTINCT = false;
    private final List<Object> args = new ArrayList<>();

    public SQLBuilder() {
    }

    public SQLBuilder(Consumer<SQLBuilder> consumer) {
        consumer.accept(this);
    }

    public final SQLBuilder ARGS(Object... args) {
        addAll(this.args, args);
        return this;
    }

    public final Object[] getArgs() {
        return args.toArray();
    }

    public final SQLBuilder INSERT_INTO(String table) {
        STATEMENT = StatementType.INSERT;
        TABLE.addValues(table);
        return this;
    }

    public final <T> SQLBuilder INSERT_INTO(T instance) {
        sqlInterface.createInsert(this, instance);
        return this;
    }

    public final SQLBuilder REPLACE_INTO(String table) {
        STATEMENT = StatementType.REPLACE;
        TABLE.addValues(table);
        return this;
    }

    public final <T> SQLBuilder REPLACE_INTO(T instance) {
        sqlInterface.createReplace(this, instance);
        return this;
    }

    public final SQLBuilder DELETE(String... tables) {
        STATEMENT = StatementType.DELETE;
        TABLE.addValues(tables);
        return this;
    }

    public final <T> SQLBuilder DELETE(T instance) {
        sqlInterface.createDelete(this, instance);
        return this;
    }

    public final SQLBuilder UPDATE(String... tables) {
        STATEMENT = StatementType.UPDATE;
        TABLE.addValues(tables);
        return this;
    }

    public final <T> SQLBuilder UPDATE(T instance) {
        sqlInterface.createUpdate(this, instance);
        return this;
    }

    public final <T> SQLBuilder SELECT_FROM_JOIN(Class<T> type) {
        sqlInterface.createSelect(this, type);
        return this;
    }

    public final SQLBuilder SELECT(String... columns) {
        STATEMENT = StatementType.SELECT;
        SELECT.addSelect(columns);
        return this;
    }

    public final SQLBuilder SELECT_DISTINCT(String... columns) {
        SQLBuilder.this.DISTINCT = Boolean.TRUE;
        SQLBuilder.this.SELECT(columns);
        return this;
    }

    public final SQLBuilder VALUES(String column, String value) {
        COLUMNS.addValues(column);
        VALUES.addValues(value);
        return this;
    }

    public final SQLBuilder VALUES(String column) {
        return VALUES(column, "?");
    }


    public final SQLBuilder FROM(String... tables) {
        FROM.addValues(tables);
        return this;
    }

    public final SQLBuilder FROM(SQLBuilder builder) {
        return FROM(builder.toString());
    }

    public final SQLBuilder JOIN(String join) {
        JOIN.addValues(join);
        return this;
    }

    public final SQLBuilder INNER_JOIN(String join) {
        INNER_JOIN.addValues(join);
        return this;
    }

    public final SQLBuilder LEFT_JOIN(String join) {
        LEFT_JOIN.addValues(join);
        return this;
    }

    public final SQLBuilder RIGHT_JOIN(String join) {
        RIGHT_JOIN.addValues(join);
        return this;
    }

    public final SQLBuilder OUTER_JOIN(String join) {
        OUTER_JOIN.addValues(join);
        return this;
    }

    public final SQLBuilder SET(String set) {
        SET.addValues(set);
        return this;
    }

    public final SQLBuilder SET_EQUALS(String column, @Nullable Object arg) {
        return SET(column + " = ?").ARGS(arg);
    }

    public final SQLBuilder SET_INCREASE(String column, @Nullable Object arg) {
        return SET(column + " = " + column + " + ?").ARGS(arg);
    }

    public final <T> SQLBuilder ON_DUPLICATE_KEY_UPDATE(T instance) {
        sqlInterface.createInsertOnUpdate(this, instance);
        return this;
    }

    public final SQLBuilder ON_DUPLICATE_KEY_UPDATE(String set) {
        this.STATEMENT = StatementType.INSERT_UPDATE;
        ON_DUPLICATE_KEY_UPDATE.addValues(set);
        return this;
    }

    public final SQLBuilder ON_DUPLICATE_KEY_UPDATE_FROM_INSERT(String column) {
        return ON_DUPLICATE_KEY_UPDATE(column + " VALUES(" + column + ")");
    }

    public final SQLBuilder ON_DUPLICATE_KEY_UPDATE_EQUALS(String column, @Nullable Object arg) {
        return ON_DUPLICATE_KEY_UPDATE(column + " = ?").ARGS(arg);
    }

    public final SQLBuilder ON_DUPLICATE_KEY_UPDATE_INCREASE(String column, @Nullable Object arg) {
        return ON_DUPLICATE_KEY_UPDATE(column + " = " + column + " + ?").ARGS(arg);
    }


    public final SQLBuilder AND() {
        if (this.LAST != null) {
            LAST.addAND();
        }
        return this;
    }

    public final SQLBuilder OR() {
        if (this.LAST != null) {
            LAST.addOR();
        }
        return this;
    }

    public final SQLBuilder WHERE(String where) {
        this.WHERE.addValues(where);
        this.LAST = this.WHERE;
        return this;
    }

    public final SQLBuilder WHERE_IN(String column, Object... args) {
        Assert.notEmpty(args, "WHERE_IN args cannot empty");
        WHERE(format("%s IN(%s)", column, stream(args).map(it -> "?").collect(joining(", "))));
        return ARGS(args);
    }

    public final SQLBuilder WHERE_EQUALS(String column, @Nullable Object arg) {
        if (arg == null) return WHERE(column + " IS NULL");
        return WHERE(column + " = ?").ARGS(arg);
    }

    public final SQLBuilder WHERE_NOT_EQUALS(String column, @Nullable Object arg) {
        if (arg == null) return WHERE(column + " IS NOT NULL");
        return WHERE(column + " <> ?").ARGS(arg);
    }

    public final SQLBuilder WHERE_MATCH(String[] columns, String args) {
        Assert.notEmpty(columns, "WHERE_MATCH args cannot empty");
        return WHERE("MATCH(" + join(", ", columns) + ") AGAINST(?)").ARGS(args);
    }

    public final SQLBuilder WHERE_MATCH_IN_BOOL(String[] columns, String args) {
        Assert.notEmpty(columns, "WHERE_MATCH args cannot empty");
        return WHERE("MATCH(" + join(", ", columns) + ") AGAINST(? in BOOLEAN MODE)").ARGS(args);
    }

    public final SQLBuilder GROUP_BY(String... columns) {
        GROUP_BY.addValues(columns);
        return this;
    }

    public final SQLBuilder HAVING(String having) {
        this.HAVING.addValues(having);
        this.LAST = this.HAVING;
        return this;
    }

    public final SQLBuilder HAVING_IN(String column, Object... args) {
        Assert.notEmpty(args, "HAVING_IN args cannot empty");
        HAVING(format("%s IN(%s)", column, stream(args).map(it -> "?").collect(joining(", "))));
        return ARGS(args);
    }

    public final SQLBuilder HAVING_EQUALS(String column, @Nullable Object arg) {
        if (arg == null) return HAVING(column + " IS NULL");
        return HAVING(column + " = ?").ARGS(arg);
    }

    public final SQLBuilder HAVING_NOT_EQUALS(String column, @Nullable Object arg) {
        if (arg == null) return HAVING(column + " IS NOT NULL");
        return HAVING(column + " <> ?").ARGS(arg);
    }

    public final SQLBuilder HAVING_MATCH(String[] columns, String args) {
        Assert.notEmpty(columns, "WHERE_MATCH args cannot empty");
        return HAVING("MATCH(" + join(", ", columns) + ") AGAINST(?)").ARGS(args);
    }

    public final SQLBuilder HAVING_MATCH_IN_BOOL(String[] columns, String args) {
        Assert.notEmpty(columns, "WHERE_MATCH args cannot empty");
        return HAVING("MATCH(" + join(", ", columns) + ") AGAINST(? in BOOLEAN MODE)").ARGS(args);
    }

    public final SQLBuilder ORDER_BY(String orderBy) {
        ORDER_BY.addValues(orderBy);
        return this;
    }

    // Insert Into
    private String INSERT_STRING() {
        var builder = new StringBuilder();
        builder.append("INSERT INTO ");
        TABLE.builder(builder);
        COLUMNS.builder(builder);
        VALUES.builder(builder);
        return builder.toString();
    }

    // Replace Into
    private String REPLACE_STRING() {
        var builder = new StringBuilder();
        builder.append("REPLACE INTO ");
        TABLE.builder(builder);
        COLUMNS.builder(builder);
        VALUES.builder(builder);
        return builder.toString();
    }

    // Delete Into
    private String DELETE_STRING() {
        var builder = new StringBuilder();
        TABLE.builder(builder.append("DELETE "));
        FROM.builder(builder);
        JOIN.builder(builder);
        LEFT_JOIN.builder(builder);
        RIGHT_JOIN.builder(builder);
        OUTER_JOIN.builder(builder);
        WHERE.builder(builder);
        return builder.toString();
    }

    // Update Into
    private String UPDATE_STRING() {
        var builder = new StringBuilder();
        TABLE.builder(builder.append("UPDATE "));
        JOIN.builder(builder);
        LEFT_JOIN.builder(builder);
        RIGHT_JOIN.builder(builder);
        OUTER_JOIN.builder(builder);
        SET.builder(builder);
        WHERE.builder(builder);
        return builder.toString();
    }

    // Select Into
    private String SELECT_STRING() {
        var builder = new StringBuilder("SELECT ");
        if (SQLBuilder.this.DISTINCT) {
            builder.append("DISTINCT ");
        }
        SELECT.builder(builder);
        FROM.builder(builder);
        JOIN.builder(builder);
        LEFT_JOIN.builder(builder);
        RIGHT_JOIN.builder(builder);
        OUTER_JOIN.builder(builder);
        WHERE.builder(builder);
        GROUP_BY.builder(builder);
        HAVING.builder(builder);
        ORDER_BY.builder(builder);
        return builder.toString();
    }

    private String INSERT_ON_UPDATE_STRING() {
        var builder = new StringBuilder();
        builder.append("INSERT INTO ");
        TABLE.builder(builder);
        COLUMNS.builder(builder);
        VALUES.builder(builder);
        ON_DUPLICATE_KEY_UPDATE.builder(builder);
        return builder.toString();
    }

    public final String getSql() {
        //  INSERT 语句
        if (STATEMENT == StatementType.INSERT) {
            return INSERT_STRING();
        }
        // REPLACE 语句
        if (STATEMENT == StatementType.REPLACE) {
            return REPLACE_STRING();
        }
        // DELETE 语句
        if (STATEMENT == StatementType.DELETE) {
            return DELETE_STRING();
        }
        // UPDATE 语句
        if (STATEMENT == StatementType.UPDATE) {
            return UPDATE_STRING();
        }
        // SELECT 语句
        if (STATEMENT == StatementType.SELECT) {
            return SELECT_STRING();
        }
        // INSERT INTO ON DUPLICATE KEY UPDATE
        if (STATEMENT == StatementType.INSERT_UPDATE) {
            return INSERT_ON_UPDATE_STRING();
        }
        throw new RuntimeException("SQL ERROR!");
    }

    @Override
    public final String toString() {
        return getSql() + "\n" + Arrays.toString(getArgs());
    }

    // Base Statement
    private static abstract class BaseStatement implements Serializable {
        protected final List<String> values = new ArrayList<>();
        public static final String AND = ") AND (";
        public static final String OR = ") OR (";
        final String keyWord, join;

        public BaseStatement(String keyWord, String join) {
            this.keyWord = keyWord;
            this.join = join;
        }

        @Nonnull
        public String getOpen() {
            return "";
        }

        @Nonnull
        public String getClose() {
            return "";
        }

        public final void addValues(String... values) {
            if (values != null && values.length > 0) {
                addAll(this.values, values);
            }
        }

        public final void builder(StringBuilder builder) {
            if (BaseStatement.this.values.isEmpty()) {
                return;
            }
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
            var b = index > 0 && !OR.equals(part) && !OR.equals(last);
            return b && !AND.equals(part) && !AND.equals(last);
        }
    }

    // Table Statement
    private static class TableStatement extends BaseStatement {
        public TableStatement() {
            super("", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }
    }

    // Column Statement
    private static class ColumnStatement extends BaseStatement {
        public ColumnStatement() {
            super("", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "(";
        }

        @Nonnull
        public final String getClose() {
            return ") ";
        }
    }

    // Values Statement
    private static class ValuesStatement extends BaseStatement {
        public ValuesStatement() {
            super("\nVALUES ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "(";
        }

        @Nonnull
        public final String getClose() {
            return ")";
        }
    }

    // Field Statement
    private static class SelectStatement extends BaseStatement {
        public SelectStatement() {
            super("", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }

        public final void addSelect(String... columns) {
            if (columns != null && columns.length > 0) {
                columns[0] = "\n\t" + columns[0];
                super.addValues(columns);
            }
        }
    }

    // From Statement
    private static class FromStatement extends BaseStatement {
        public FromStatement() {
            super("\nFROM ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }
    }

    // Join Statement
    private static class JoinStatement extends BaseStatement {

        public JoinStatement(String word) {
            super(word, word);
        }

        public JoinStatement() {
            this("\nJOIN ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }

    }

    // inner Join Statement
    private static class InnerJoinStatement extends JoinStatement {
        public InnerJoinStatement() {
            super("\nINNER JOIN ");
        }
    }

    // Left Join Statement
    private static class LeftJoinStatement extends JoinStatement {
        public LeftJoinStatement() {
            super("\nLEFT JOIN ");
        }
    }

    // Right Join Statement
    private static class RightJoinStatement extends JoinStatement {
        public RightJoinStatement() {
            super("\nRIGHT JOIN ");
        }
    }

    // Outer Join Statement
    private static class OuterJoinStatement extends JoinStatement {
        public OuterJoinStatement() {
            super("\nOUTER JOIN ");
        }
    }

    // Set Statement
    private static class SetStatement extends BaseStatement {
        public SetStatement() {
            super("\nSET ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }
    }

    private static class OnDuplicateKeyUpdateStatement extends BaseStatement {
        public OnDuplicateKeyUpdateStatement() {
            super("\nON DUPLICATE KEY UPDATE ", ",");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }
    }

    // Where Statement
    private static class WhereStatement extends BaseStatement {
        public WhereStatement(String word) {
            super(word, AND);
        }

        public WhereStatement() {
            this("\nWHERE ");
        }

        @Nonnull
        public final String getOpen() {
            return "(";
        }

        @Nonnull
        public final String getClose() {
            return ") ";
        }

        public final void addAND() {
            values.add(AND);
        }

        public final void addOR() {
            values.add(OR);
        }
    }

    // Group By Statement
    private static class GroupByStatement extends BaseStatement {
        public GroupByStatement() {
            super("\nGROUP BY ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }
    }

    private static class HavingStatement extends WhereStatement {
        public HavingStatement() {
            super("\nHAVING ");
        }
    }

    // Order By Statement
    private static class OrderByStatement extends BaseStatement {
        public OrderByStatement() {
            super("\nORDER BY ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }
    }

    private enum StatementType {
        INSERT, REPLACE, DELETE, UPDATE, SELECT, INSERT_UPDATE
    }
}