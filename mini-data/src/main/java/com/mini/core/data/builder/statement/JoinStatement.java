package com.mini.core.data.builder.statement;


import com.mini.core.data.builder.statement.ConditionStatement.ConditionStatementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public interface JoinStatement {

    /**
     * Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement JOIN(String join);

    /**
     * Join
     *
     * @param table    表名称
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement JOIN(String table, Consumer<JoinOnStatement> consumer);

    /**
     * Inner Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement INNER_JOIN(String join);

    /**
     * Inner Join
     *
     * @param table    表名称
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement INNER_JOIN(String table, Consumer<JoinOnStatement> consumer);

    /**
     * Left Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement LEFT_JOIN(String join);

    /**
     * Left Join
     *
     * @param table    表名称
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement LEFT_JOIN(String table, Consumer<JoinOnStatement> consumer);

    /**
     * Right Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement RIGHT_JOIN(String join);

    /**
     * Right Join
     *
     * @param table    表名称
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement RIGHT_JOIN(String table, Consumer<JoinOnStatement> consumer);

    /**
     * Left Outer Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement LEFT_OUTER_JOIN(String join);

    /**
     * Left Outer Join
     *
     * @param table    表名称
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement LEFT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer);

    /**
     * Right Outer Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement RIGHT_OUTER_JOIN(String join);

    /**
     * Right Outer Join
     *
     * @param table    表名称
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement RIGHT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer);

    /**
     * Cross Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement CROSS_JOIN(String join);

    /**
     * Cross Join
     *
     * @param table    表名称
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement CROSS_JOIN(String table, Consumer<JoinOnStatement> consumer);

    interface JoinOnStatement extends ConditionStatement<JoinOnStatement> {
        JoinOnStatement ON(String on);

        JoinOnStatement AND();

        JoinOnStatement OR();
    }

    final class JoinStatementImpl extends BaseStatement implements JoinStatement {
        private String word = "\nJOIN ";

        public JoinStatementImpl() {
            super("", "");
        }

        @NotNull
        protected final String getOpen() {
            return word;
        }

        @NotNull
        protected final String getClose() {
            return "";
        }

        private StringBuilder getJoinString(String table, Consumer<JoinOnStatement> consumer) {
            var statement = new JoinOnStatementImpl();
            var builder = new StringBuilder(table);
            consumer.accept(statement);
            statement.builder(builder);
            return builder;
        }

        @Override
        public final JoinStatement JOIN(String join) {
            this.word = "\nJOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement JOIN(String table, Consumer<JoinOnStatement> consumer) {
            return JOIN(getJoinString(table, consumer).toString());
        }

        @Override
        public final JoinStatement INNER_JOIN(String join) {
            this.word = "\nINNER JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement INNER_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            return INNER_JOIN(getJoinString(table, consumer).toString());
        }

        @Override
        public final JoinStatement LEFT_JOIN(String join) {
            this.word = "\nLEFT JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement LEFT_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            return LEFT_JOIN(getJoinString(table, consumer).toString());
        }

        @Override
        public final JoinStatement RIGHT_JOIN(String join) {
            this.word = "\nRIGHT JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement RIGHT_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            return RIGHT_JOIN(getJoinString(table, consumer).toString());
        }

        @Override
        public final JoinStatement LEFT_OUTER_JOIN(String join) {
            this.word = "\nLEFT OUTER JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement LEFT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            return LEFT_OUTER_JOIN(getJoinString(table, consumer).toString());
        }

        @Override
        public final JoinStatement RIGHT_OUTER_JOIN(String join) {
            this.word = "\nRIGHT OUTER JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement RIGHT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            return RIGHT_OUTER_JOIN(getJoinString(table, consumer).toString());
        }

        @Override
        public final JoinStatement CROSS_JOIN(String join) {
            this.word = "\nCROSS JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement CROSS_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            return CROSS_JOIN(getJoinString(table, consumer).toString());
        }


    }

    final class JoinOnStatementImpl extends ConditionStatementImpl<JoinOnStatement> implements JoinOnStatement {

        public JoinOnStatementImpl() {
            super(" ON ");
        }

        @Override
        protected final JoinOnStatementImpl getThis() {
            return this;
        }

        @Override
        public final JoinOnStatement ON(String on) {
            this.addValues(on);
            return this;
        }
    }
}
