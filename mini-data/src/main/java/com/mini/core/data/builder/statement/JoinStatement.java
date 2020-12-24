package com.mini.core.data.builder.statement;


import com.mini.core.data.builder.AbstractSql;
import com.mini.core.data.builder.statement.JoinOnStatement.JoinOnStatementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface JoinStatement extends BaseStatement<JoinStatement> {

    /**
     * Join 默认连接
     *
     * @param table  目标表名
     * @param column 字段名
     * @param target 目标字段名
     * @return {@code this}
     */
    JoinStatement join(String table, String column, String target);

    /**
     * Join
     *
     * @param table    目标表名
     * @param consumer 回调方法
     * @return {@code this}
     */
    JoinStatement join(String table, Consumer<JoinOnStatement> consumer);


    abstract class JoinStatementBase extends BaseStatementImpl<JoinStatement> implements JoinStatement {
        public JoinStatementBase(AbstractSql<?> sql) {
            super(sql, "", "", "");
        }

        @Override
        public JoinStatement join(String table, String column, String target) {
            addValues(format("%s ON %s = %s", table, column, target));
            return this;
        }

        @Override
        public JoinStatement join(String table, Consumer<JoinOnStatement> consumer) {
            var statement = new JoinOnStatementImpl(sql);
            var builder = new StringBuilder(table);
            consumer.accept(statement);
            statement.builder(builder);
            return addValues(builder.toString());
        }
    }

    final class JoinStatementImpl extends JoinStatementBase implements JoinStatement {

        public JoinStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nJOIN ";
        }
    }

    final class InnerJoinStatementImpl extends JoinStatementBase implements JoinStatement {

        public InnerJoinStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nINNER JOIN ";
        }
    }

    final class LeftJoinStatementImpl extends JoinStatementBase implements JoinStatement {

        public LeftJoinStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nLEFT JOIN ";
        }
    }

    final class RightJoinStatementImpl extends JoinStatementBase implements JoinStatement {

        public RightJoinStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nRIGHT JOIN ";
        }
    }

    final class LeftOuterJoinStatementImpl extends JoinStatementBase implements JoinStatement {

        public LeftOuterJoinStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nLEFT OUTER JOIN ";
        }
    }

    final class RightOuterJoinStatementImpl extends JoinStatementBase implements JoinStatement {

        public RightOuterJoinStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nRIGHT OUTER JOIN ";
        }
    }

    final class CrossJoinStatementImpl extends JoinStatementBase implements JoinStatement {

        public CrossJoinStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return "\nCROSS JOIN ";
        }
    }
}
