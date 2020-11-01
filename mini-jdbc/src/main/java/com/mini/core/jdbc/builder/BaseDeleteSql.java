package com.mini.core.jdbc.builder;


import com.mini.core.jdbc.builder.statement.FromStatement.FromStatementImpl;
import com.mini.core.jdbc.builder.statement.JoinStatement;
import com.mini.core.jdbc.builder.statement.JoinStatement.JoinStatementImpl;
import com.mini.core.jdbc.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.jdbc.builder.statement.WhereStatement;
import com.mini.core.jdbc.builder.statement.WhereStatement.WhereStatementImpl;

import java.util.function.Consumer;

public interface BaseDeleteSql<T extends BaseDeleteSql<T>> {

    T DELETE(String... tables);

    T FROM(String... tables);

    T JOIN(String join);

    T INNER_JOIN(String join);

    T LEFT_JOIN(String join);

    T RIGHT_JOIN(String join);

    T LEFT_OUTER_JOIN(String join);

    T RIGHT_OUTER_JOIN(String join);

    T CROSS_JOIN(String join);

    T JOIN(Consumer<JoinStatement> consumer);

     T WHERE(String where);

    T WHERE(Consumer<WhereStatement> consumer);


    abstract class DeleteSqlImpl<T extends BaseSql<T> & BaseDeleteSql<T>> extends BaseSql<T> implements BaseDeleteSql<T> {
        private final WhereStatementImpl where = new WhereStatementImpl();
        private final TableStatementImpl table = new TableStatementImpl();
        private final FromStatementImpl from = new FromStatementImpl();
        private final JoinStatementImpl join = new JoinStatementImpl();

        protected DeleteSqlImpl() {
        }

        protected abstract T getDeleteSql();

        public final T DELETE(String... tables) {
            this.table.TABLE(tables);
            return getDeleteSql();
        }

        public T FROM(String... tables) {
            this.from.FROM(tables);
            return getDeleteSql();
        }

        public T JOIN(String join) {
            this.join.JOIN(join);
            return getDeleteSql();
        }

        public T INNER_JOIN(String join) {
            this.join.INNER_JOIN(join);
            return getDeleteSql();
        }

        public T LEFT_JOIN(String join) {
            this.join.LEFT_JOIN(join);
            return getDeleteSql();
        }

        public T RIGHT_JOIN(String join) {
            this.join.RIGHT_JOIN(join);
            return getDeleteSql();
        }

        public T LEFT_OUTER_JOIN(String join) {
            this.join.LEFT_OUTER_JOIN(join);
            return getDeleteSql();
        }

        public T RIGHT_OUTER_JOIN(String join) {
            this.join.RIGHT_OUTER_JOIN(join);
            return getDeleteSql();
        }

        public T CROSS_JOIN(String join) {
            this.join.CROSS_JOIN(join);
            return getDeleteSql();
        }

        public T JOIN(Consumer<JoinStatement> consumer) {
            consumer.accept(this.join);
            return getDeleteSql();
        }

        public T WHERE(String where) {
            this.where.WHERE(where);
            return getDeleteSql();
        }

        public T WHERE(Consumer<WhereStatement> consumer) {
            consumer.accept(this.where);
            return getDeleteSql();
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
}
