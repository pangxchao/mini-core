package com.mini.core.data.builder;


import com.mini.core.data.builder.statement.JoinStatement;
import com.mini.core.data.builder.statement.JoinStatement.JoinStatementImpl;
import com.mini.core.data.builder.statement.SetStatement;
import com.mini.core.data.builder.statement.SetStatement.SetStatementImpl;
import com.mini.core.data.builder.statement.TableStatement.TableStatementImpl;
import com.mini.core.data.builder.statement.WhereStatement;
import com.mini.core.data.builder.statement.WhereStatement.WhereStatementImpl;

import java.util.function.Consumer;

public interface BaseUpdateSql<T extends BaseUpdateSql<T>> {

    T UPDATE(String... tables);

    T JOIN(String join);

    T INNER_JOIN(String join);

    T LEFT_JOIN(String join);

    T RIGHT_JOIN(String join);

    T LEFT_OUTER_JOIN(String join);

    T RIGHT_OUTER_JOIN(String join);

    T CROSS_JOIN(String join);

    T JOIN(Consumer<JoinStatement> consumer);

    T SET(String set);

    T SET(Consumer<SetStatement> consumer);

    T WHERE(String where);

    T WHERE(Consumer<WhereStatement> consumer);

    abstract class UpdateSql<T extends BaseSql<T> & BaseUpdateSql<T>> extends BaseSql<T> implements BaseUpdateSql<T> {
        private final WhereStatementImpl where = new WhereStatementImpl();
        private final TableStatementImpl table = new TableStatementImpl();
        private final JoinStatementImpl join = new JoinStatementImpl();
        private final SetStatementImpl set = new SetStatementImpl();

        protected UpdateSql() {
        }

        protected abstract T getUpdateSql();

        public T UPDATE(String... tables) {
            this.table.TABLE(tables);
            return getUpdateSql();
        }

        public final T JOIN(String join) {
            this.join.JOIN(join);
            return getUpdateSql();
        }

        public final T INNER_JOIN(String join) {
            this.join.INNER_JOIN(join);
            return getUpdateSql();
        }

        public final T LEFT_JOIN(String join) {
            this.join.LEFT_JOIN(join);
            return getUpdateSql();
        }

        public final T RIGHT_JOIN(String join) {
            this.join.RIGHT_JOIN(join);
            return getUpdateSql();
        }

        public final T LEFT_OUTER_JOIN(String join) {
            this.join.LEFT_OUTER_JOIN(join);
            return getUpdateSql();
        }

        public final T RIGHT_OUTER_JOIN(String join) {
            this.join.RIGHT_OUTER_JOIN(join);
            return getUpdateSql();
        }

        public final T CROSS_JOIN(String join) {
            this.join.CROSS_JOIN(join);
            return getUpdateSql();
        }

        public final T JOIN(Consumer<JoinStatement> consumer) {
            consumer.accept(this.join);
            return getUpdateSql();
        }

        public final T SET(String set) {
            this.set.SET(set);
            return getUpdateSql();
        }

        public final T SET(Consumer<SetStatement> consumer) {
            consumer.accept(this.set);
            return getUpdateSql();
        }

        public final T WHERE(String where) {
            this.where.WHERE(where);
            return getUpdateSql();
        }

        public final T WHERE(Consumer<WhereStatement> consumer) {
            consumer.accept(this.where);
            return getUpdateSql();
        }

        @Override
        public final String getSql() {
            var builder = new StringBuilder();
            builder.append("UPDATE ");
            this.table.builder(builder);
            this.join.builder(builder);
            this.set.builder(builder);
            this.where.builder(builder);
            return builder.toString();
        }
    }
}
