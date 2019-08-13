package com.mini.jdbc.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSQL  {

    private static final String AND = ") \nAND (";
    private static final String OR = ") \nOR (";

    private final SQLStatement statement = new SQLStatement();


    public AbstractSQL UPDATE(String table) {
        statement.statementType = StatementType.UPDATE;
        statement.tables.add(table);
        return this;
    }

    public AbstractSQL SET(String sets) {
        statement.sets.add(sets);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL SET(String... sets) {
        statement.sets.addAll(Arrays.asList(sets));
        return this;
    }

    public AbstractSQL INSERT_INTO(String tableName) {
        statement.statementType = StatementType.INSERT;
        statement.tables.add(tableName);
        return this;
    }

    public AbstractSQL VALUES(String columns, String values) {
        INTO_COLUMNS(columns);
        INTO_VALUES(values);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL INTO_COLUMNS(String... columns) {
        statement.columns.addAll(Arrays.asList(columns));
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL INTO_VALUES(String... values) {
        List<String> list = statement.valuesList.get(statement.valuesList.size() - 1);
        list.addAll(Arrays.asList(values));
        return this;
    }

    public AbstractSQL SELECT(String columns) {
        statement.statementType = StatementType.SELECT;
        statement.select.add(columns);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL SELECT(String... columns) {
        statement.statementType = StatementType.SELECT;
        statement.select.addAll(Arrays.asList(columns));
        return this;
    }

    public AbstractSQL SELECT_DISTINCT(String columns) {
        statement.distinct = true;
        SELECT(columns);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL SELECT_DISTINCT(String... columns) {
        statement.distinct = true;
        SELECT(columns);
        return this;
    }

    public AbstractSQL DELETE_FROM(String table) {
        statement.statementType = StatementType.DELETE;
        statement.tables.add(table);
        return this;
    }

    public AbstractSQL FROM(String table) {
        statement.tables.add(table);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL FROM(String... tables) {
        statement.tables.addAll(Arrays.asList(tables));
        return this;
    }

    public AbstractSQL JOIN(String join) {
        statement.join.add(join);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL JOIN(String... joins) {
        statement.join.addAll(Arrays.asList(joins));
        return this;
    }

    public AbstractSQL INNER_JOIN(String join) {
        statement.innerJoin.add(join);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL INNER_JOIN(String... joins) {
        statement.innerJoin.addAll(Arrays.asList(joins));
        return this;
    }

    public AbstractSQL LEFT_OUTER_JOIN(String join) {
        statement.leftOuterJoin.add(join);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL LEFT_OUTER_JOIN(String... joins) {
        statement.leftOuterJoin.addAll(Arrays.asList(joins));
        return this;
    }

    public AbstractSQL RIGHT_OUTER_JOIN(String join) {
        statement.rightOuterJoin.add(join);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL RIGHT_OUTER_JOIN(String... joins) {
        statement.rightOuterJoin.addAll(Arrays.asList(joins));
        return this;
    }

    public AbstractSQL OUTER_JOIN(String join) {
        statement.outerJoin.add(join);
        return this;
    }

    /**
     * @since 3.4.2
     */
    public AbstractSQL OUTER_JOIN(String... joins) {
        statement.outerJoin.addAll(Arrays.asList(joins));
        return this;
    }

    public AbstractSQL WHERE(String conditions) {
        statement.where.add(conditions);
        statement.lastList = statement.where;
        return this;
    }

    public AbstractSQL WHERE(String... conditions) {
        statement.where.addAll(Arrays.asList(conditions));
        statement.lastList = statement.where;
        return this;
    }

    public AbstractSQL OR() {
        statement.lastList.add(OR);
        return this;
    }

    public AbstractSQL AND() {
        statement.lastList.add(AND);
        return this;
    }

    public AbstractSQL GROUP_BY(String columns) {
        statement.groupBy.add(columns);
        return this;
    }

    public AbstractSQL GROUP_BY(String... columns) {
        statement.groupBy.addAll(Arrays.asList(columns));
        return this;
    }

    public AbstractSQL HAVING(String conditions) {
        statement.having.add(conditions);
        statement.lastList = statement.having;
        return this;
    }


    public AbstractSQL HAVING(String... conditions) {
        statement.having.addAll(Arrays.asList(conditions));
        statement.lastList = statement.having;
        return this;
    }

    public AbstractSQL ORDER_BY(String columns) {
        statement.orderBy.add(columns);
        return this;
    }


    public AbstractSQL ORDER_BY(String... columns) {
        statement.orderBy.addAll(Arrays.asList(columns));
        return this;
    }

    public AbstractSQL ADD_ROW() {
        statement.valuesList.add(new ArrayList<>());
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        statement.sql(sb);
        return sb.toString();
    }

    private static class SQLStatement {
        StatementType statementType;
        List<String> sets = new ArrayList<>();
        List<String> select = new ArrayList<>();
        List<String> tables = new ArrayList<>();
        List<String> join = new ArrayList<>();
        List<String> innerJoin = new ArrayList<>();
        List<String> outerJoin = new ArrayList<>();
        List<String> leftOuterJoin = new ArrayList<>();
        List<String> rightOuterJoin = new ArrayList<>();
        List<String> where = new ArrayList<>();
        List<String> having = new ArrayList<>();
        List<String> groupBy = new ArrayList<>();
        List<String> orderBy = new ArrayList<>();
        List<String> lastList = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        List<List<String>> valuesList = new ArrayList<>();
        boolean distinct;
        //String offset;
        //String limit;
        //LimitingRowsStrategy limitingRowsStrategy = LimitingRowsStrategy.NOP;

        public SQLStatement() {
            valuesList.add(new ArrayList<>());
        }

        private void sqlClause(StringBuilder builder, String keyword, List<String> parts, String open, String close,
                String conjunction) {
            if (!parts.isEmpty()) {
                builder.append(" ");
                builder.append(keyword);
                builder.append(" ");
                builder.append(open);
                String last = "________";
                for (int i = 0, n = parts.size(); i < n; i++) {
                    String part = parts.get(i);
                    if (i > 0 && !part.equals(AND) && !part.equals(OR) && !last.equals(AND) && !last.equals(OR)) {
                        builder.append(conjunction);
                    }
                    builder.append(part);
                    last = part;
                }
                builder.append(close);
            }
        }

        private String selectSQL(StringBuilder builder) {
            if (distinct) {
                sqlClause(builder, "SELECT DISTINCT", select, "", "", ", ");
            } else {
                sqlClause(builder, "SELECT", select, "", "", ", ");
            }

            sqlClause(builder, "FROM", tables, "", "", ", ");
            joins(builder);
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            sqlClause(builder, "GROUP BY", groupBy, "", "", ", ");
            sqlClause(builder, "HAVING", having, "(", ")", " AND ");
            sqlClause(builder, "ORDER BY", orderBy, "", "", ", ");
            return builder.toString();
        }

        private void joins(StringBuilder builder) {
            sqlClause(builder, "JOIN", join, "", "", "\nJOIN ");
            sqlClause(builder, "INNER JOIN", innerJoin, "", "", "\nINNER JOIN ");
            sqlClause(builder, "OUTER JOIN", outerJoin, "", "", "\nOUTER JOIN ");
            sqlClause(builder, "LEFT OUTER JOIN", leftOuterJoin, "", "", "\nLEFT OUTER JOIN ");
            sqlClause(builder, "RIGHT OUTER JOIN", rightOuterJoin, "", "", "\nRIGHT OUTER JOIN ");
        }

        private String insertSQL(StringBuilder builder) {
            sqlClause(builder, "INSERT INTO", tables, "", "", "");
            sqlClause(builder, "", columns, "(", ")", ", ");
            for (int i = 0; i < valuesList.size(); i++) {
                sqlClause(builder, i > 0 ? "," : "VALUES", valuesList.get(i), "(", ")", ", ");
            }
            return builder.toString();
        }

        private String deleteSQL(StringBuilder builder) {
            sqlClause(builder, "DELETE FROM", tables, "", "", "");
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            return builder.toString();
        }

        private String updateSQL(StringBuilder builder) {
            sqlClause(builder, "UPDATE", tables, "", "", "");
            joins(builder);
            sqlClause(builder, "SET", sets, "", "", ", ");
            sqlClause(builder, "WHERE", where, "(", ")", " AND ");
            return builder.toString();
        }

        public String sql(StringBuilder builder) {
            if (statementType == null) {
                return null;
            }

            String answer;

            switch (statementType) {
                case DELETE:
                    answer = deleteSQL(builder);
                    break;

                case INSERT:
                    answer = insertSQL(builder);
                    break;

                case SELECT:
                    answer = selectSQL(builder);
                    break;

                case UPDATE:
                    answer = updateSQL(builder);
                    break;

                default:
                    answer = null;
            }

            return answer;
        }
    }

    public enum StatementType {
        DELETE, INSERT, SELECT, UPDATE
    }
}