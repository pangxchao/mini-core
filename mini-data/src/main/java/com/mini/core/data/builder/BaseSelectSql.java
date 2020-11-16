package com.mini.core.data.builder;

import com.mini.core.data.builder.statement.FromStatement.FromStatementImpl;
import com.mini.core.data.builder.statement.GroupByStatement.GroupByStatementImpl;
import com.mini.core.data.builder.statement.*;
import com.mini.core.data.builder.statement.HavingStatement.HavingStatementImpl;
import com.mini.core.data.builder.statement.JoinStatement.JoinOnStatement;
import com.mini.core.data.builder.statement.JoinStatement.JoinStatementImpl;
import com.mini.core.data.builder.statement.OrderByStatement.OrderByStatementImpl;
import com.mini.core.data.builder.statement.SelectStatement.SelectStatementImpl;
import com.mini.core.data.builder.statement.WhereStatement.WhereStatementImpl;
import com.mini.core.data.builder.support.Join;
import com.mini.core.util.holder.ClassHolder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@SuppressWarnings("UnusedReturnValue")
public interface BaseSelectSql<T extends BaseSelectSql<T>> {

    T SELECT(String... column);

    T SELECT(Consumer<SelectStatement> consumer);

    T FROM(String... tables);

    T JOIN(String join);

    T JOIN(String table, Consumer<JoinOnStatement> consumer);

    T INNER_JOIN(String join);

    T INNER_JOIN(String table, Consumer<JoinOnStatement> consumer);

    T LEFT_JOIN(String join);

    T LEFT_JOIN(String table, Consumer<JoinOnStatement> consumer);

    T RIGHT_JOIN(String join);

    T RIGHT_JOIN(String table, Consumer<JoinOnStatement> consumer);

    T LEFT_OUTER_JOIN(String join);

    T LEFT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer);

    T RIGHT_OUTER_JOIN(String join);

    T RIGHT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer);

    T CROSS_JOIN(String join);

    T CROSS_JOIN(String table, Consumer<JoinOnStatement> consumer);

    T JOIN(Consumer<JoinStatement> consumer);

    T WHERE(String where);

    T WHERE(Consumer<WhereStatement> consumer);

    T GROUP_BY(String... columns);

    T HAVING(Consumer<HavingStatement> consumer);

    T ORDER_BY(String... columns);

    T ORDER_BY_ASC(String... columns);

    T ORDER_BY_DESC(String... columns);

    T ORDER_BY(Consumer<OrderByStatement> consumer);

    <E> T SELECT_FROM_JOIN(@NotNull Class<E> type);

    abstract class SelectSql<T extends BaseSql<T> & BaseSelectSql<T>> extends BaseSql<T> implements BaseSelectSql<T> {
        private final GroupByStatementImpl groupBy = new GroupByStatementImpl();
        private final OrderByStatementImpl orderBy = new OrderByStatementImpl();
        private final SelectStatementImpl select = new SelectStatementImpl();
        private final HavingStatementImpl having = new HavingStatementImpl();
        private final WhereStatementImpl where = new WhereStatementImpl();
        private final FromStatementImpl from = new FromStatementImpl();
        private final JoinStatementImpl join = new JoinStatementImpl();

        protected SelectSql() {
        }

        protected abstract T getSelectSql();

        public T SELECT(String... column) {
            this.select.SELECT(column);
            return getSelectSql();
        }


        public T SELECT(Consumer<SelectStatement> consumer) {
            consumer.accept(this.select);
            return getSelectSql();
        }

        public T FROM(String... tables) {
            this.from.FROM(tables);
            return getSelectSql();
        }


        public T JOIN(String join) {
            this.join.JOIN(join);
            return getSelectSql();
        }

        public T JOIN(String table, Consumer<JoinOnStatement> consumer) {
            this.join.JOIN(table, consumer);
            return getSelectSql();
        }

        public T INNER_JOIN(String join) {
            this.join.INNER_JOIN(join);
            return getSelectSql();
        }

        public T INNER_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            this.join.INNER_JOIN(table, consumer);
            return getSelectSql();
        }

        public T LEFT_JOIN(String join) {
            this.join.LEFT_JOIN(join);
            return getSelectSql();
        }

        public T LEFT_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            this.join.LEFT_JOIN(table, consumer);
            return getSelectSql();
        }

        public T RIGHT_JOIN(String join) {
            this.join.RIGHT_JOIN(join);
            return getSelectSql();
        }

        public T RIGHT_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            this.join.RIGHT_JOIN(table, consumer);
            return getSelectSql();
        }

        public T LEFT_OUTER_JOIN(String join) {
            this.join.LEFT_OUTER_JOIN(join);
            return getSelectSql();
        }

        public T LEFT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            this.join.LEFT_OUTER_JOIN(table, consumer);
            return getSelectSql();
        }

        public T RIGHT_OUTER_JOIN(String join) {
            this.join.RIGHT_OUTER_JOIN(join);
            return getSelectSql();
        }

        public T RIGHT_OUTER_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            this.join.RIGHT_OUTER_JOIN(table, consumer);
            return getSelectSql();
        }

        public T CROSS_JOIN(String join) {
            this.join.CROSS_JOIN(join);
            return getSelectSql();
        }

        public T CROSS_JOIN(String table, Consumer<JoinOnStatement> consumer) {
            this.join.CROSS_JOIN(table, consumer);
            return getSelectSql();
        }

        public T JOIN(Consumer<JoinStatement> consumer) {
            consumer.accept(this.join);
            return getSelectSql();
        }

        public T WHERE(String where) {
            this.where.WHERE(where);
            return getSelectSql();
        }

        public T WHERE(Consumer<WhereStatement> consumer) {
            consumer.accept(this.where);
            return getSelectSql();
        }

        public T GROUP_BY(String... columns) {
            this.groupBy.GROUP_BY(columns);
            return getSelectSql();
        }

        public T HAVING(Consumer<HavingStatement> consumer) {
            consumer.accept(this.having);
            return getSelectSql();
        }

        public T ORDER_BY(String... columns) {
            this.orderBy.ORDER_BY(columns);
            return getSelectSql();
        }

        public T ORDER_BY_ASC(String... columns) {
            this.orderBy.ASC(columns);
            return getSelectSql();
        }

        public T ORDER_BY_DESC(String... columns) {
            this.orderBy.DESC(columns);
            return getSelectSql();
        }

        public T ORDER_BY(Consumer<OrderByStatement> consumer) {
            consumer.accept(this.orderBy);
            return getSelectSql();
        }

        public final <E> T SELECT_FROM_JOIN(@NotNull Class<E> type) {
            ClassHolder<E> holder = ClassHolder.create(type);
            holder.getFields().values().forEach(h -> {
                Class<Column> clazz = Column.class;
                var column = h.getAnnotation(clazz);
                if (Objects.isNull(column)) {
                    SELECT(h.getName());
                    return;
                }
                SELECT(format("%s AS `%s`", //
                        column.value(),  //
                        h.getName()));
            });
            // 处理 From 语句
            var table = holder.getAnnotation(Table.class);
            FROM(ofNullable(table).map(Table::value)
                    .orElse(holder.getName()));
            // 处理Join语句
            ofNullable(holder.getAnnotationsByType(Join.class)).stream()
                    .flatMap(Arrays::stream).forEach(it -> //
                    it.type().execute(this, it));
            return getSelectSql();
        }


        @Override
        public final String getSql() {
            var builder = new StringBuilder();
            this.select.builder(builder);
            this.from.builder(builder);
            this.join.builder(builder);
            this.where.builder(builder);
            this.groupBy.builder(builder);
            this.having.builder(builder);
            this.orderBy.builder(builder);
            return builder.toString();
        }
    }
}
