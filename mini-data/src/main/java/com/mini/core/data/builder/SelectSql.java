package com.mini.core.data.builder;

import com.mini.core.data.builder.fragment.JoinFragment;
import com.mini.core.data.builder.statement.FromStatement.FromStatementImpl;
import com.mini.core.data.builder.statement.GroupByStatement.GroupByStatementImpl;
import com.mini.core.data.builder.statement.HavingStatement;
import com.mini.core.data.builder.statement.HavingStatement.HavingStatementImpl;
import com.mini.core.data.builder.statement.JoinOnStatement;
import com.mini.core.data.builder.statement.OrderByStatement.OrderByStatementImpl;
import com.mini.core.data.builder.statement.SelectStatement;
import com.mini.core.data.builder.statement.SelectStatement.SelectStatementImpl;
import com.mini.core.data.builder.statement.WhereStatement;
import com.mini.core.data.builder.statement.WhereStatement.WhereStatementImpl;
import com.mini.core.data.builder.support.Join;
import com.mini.core.util.holder.ClassHolder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static com.mini.core.util.holder.ClassHolder.create;
import static java.lang.String.format;

@SuppressWarnings({"UnusedReturnValue"})
public abstract class SelectSql extends AbstractSql<SelectSql> implements JoinFragment<SelectSql> {
    private final GroupByStatementImpl groupBy = new GroupByStatementImpl(this);
    private final OrderByStatementImpl orderBy = new OrderByStatementImpl(this);
    private final SelectStatementImpl select = new SelectStatementImpl(this);
    private final HavingStatementImpl having = new HavingStatementImpl(this);
    private final WhereStatementImpl where = new WhereStatementImpl(this);
    private final FromStatementImpl from = new FromStatementImpl(this);
    private final JoinFragmentImpl join = new JoinFragmentImpl(this);

    protected <T> SelectSql(@NotNull Class<T> type) {
        ClassHolder<? extends T> holder = create(type);
        var aTable = holder.getAnnotation(Table.class);
        Objects.requireNonNull(aTable);
        // 生成查询字段
        holder.getFields().values().forEach(it -> {
            var column = it.getAnnotation(Column.class);
            if (column == null) return;

            String columnName = column.value();
            if (!StringUtils.hasText(columnName)) {
                columnName = it.getName();
            }
            this.select(columnName, it.getName());
        });
        // 生成查询表名
        this.from(Optional.of(aTable).map(Table::value)
                .filter(it -> !it.isBlank())
                .orElse(holder.getName()));
        // 生成联合查询表名
        for (Join join : holder.getAnnotationsByType(Join.class)) {
            join.type().exe(this, join.table(), join.on());
        }
    }

    protected SelectSql() {
    }

    public SelectSql selects(String... columns) {
        this.select.addValues(columns);
        return this;
    }

    public SelectSql select(Consumer<SelectStatement> consumer) {
        consumer.accept(this.select);
        return this;
    }

    public SelectSql select(String column, String alias) {
        selects(format("%s AS `%s`", column, alias));
        return this;
    }

    public SelectSql select(String column) {
        return selects(column);
    }

    public SelectSql selectCount(String column, String alias) {
        this.select(column, alias);
        this.select.count(column, alias);
        return this;
    }

    public SelectSql selectCount(String column) {
        this.select.count(column);
        return this;
    }

    public SelectSql selectSum(String column, String alias) {
        this.select.sum(column, alias);
        return this;
    }

    public SelectSql selectSum(String column) {
        this.select.sum(column);
        return this;
    }

    public SelectSql selectAvg(String column, String alias) {
        this.select.avg(column, alias);
        return this;
    }

    public SelectSql selectAvg(String column) {
        this.select.avg(column);
        return this;
    }

    public SelectSql selectMax(String column, String alias) {
        this.select.max(column, alias);
        return this;
    }

    public SelectSql selectMax(String column) {
        this.select.max(column);
        return this;
    }

    public SelectSql selectMin(String column, String alias) {
        this.select.min(column, alias);
        return this;
    }

    public SelectSql selectMin(String column) {
        this.select.min(column);
        return this;
    }

    public SelectSql from(String... tables) {
        this.from.addValues(tables);
        return this;
    }

    @Override
    public SelectSql join(String join) {
        this.join.join(join);
        return this;
    }

    @Override
    public SelectSql join(String table, String column, String target) {
        this.join.join(table, column, table);
        return this;
    }

    @Override
    public SelectSql join(String table, Consumer<JoinOnStatement> consumer) {
        this.join.join(table, consumer);
        return this;
    }

    @Override
    public SelectSql leftJoin(String join) {
        this.join.leftJoin(join);
        return this;
    }

    @Override
    public SelectSql leftJoin(String table, String column, String target) {
        this.join.leftJoin(table, column, target);
        return this;
    }

    @Override
    public SelectSql leftJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSql rightJoin(String join) {
        this.join.rightJoin(join);
        return this;
    }

    @Override
    public SelectSql rightJoin(String table, String column, String target) {
        this.join.rightJoin(table, column, target);
        return this;
    }

    @Override
    public SelectSql rightJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSql leftOuterJoin(String join) {
        this.join.leftOuterJoin(join);
        return this;
    }

    @Override
    public SelectSql leftOuterJoin(String table, String column, String target) {
        this.join.leftOuterJoin(table, column, target);
        return this;
    }

    @Override
    public SelectSql leftOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.leftOuterJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSql rightOuterJoin(String join) {
        this.join.rightOuterJoin(join);
        return this;
    }

    @Override
    public SelectSql rightOuterJoin(String table, String column, String target) {
        this.join.rightOuterJoin(table, column, target);
        return this;
    }

    @Override
    public SelectSql rightOuterJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.rightOuterJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSql crossJoin(String join) {
        this.join.crossJoin(join);
        return this;
    }

    @Override
    public SelectSql crossJoin(String table, String column, String target) {
        this.join.crossJoin(table, column, target);
        return this;
    }

    @Override
    public SelectSql crossJoin(String table, Consumer<JoinOnStatement> consumer) {
        this.join.crossJoin(table, consumer);
        return this;
    }

    public SelectSql where(Consumer<WhereStatement> consumer) {
        consumer.accept(this.where);
        return this;
    }

    public SelectSql groupBy(String... columns) {
        this.groupBy.addValues(columns);
        return this;
    }

    public SelectSql having(Consumer<HavingStatement> consumer) {
        consumer.accept(this.having);
        return this;
    }

    public SelectSql orderBy(String... columns) {
        this.orderBy.addValues(columns);
        return this;
    }

    public SelectSql orderByAsc(String... columns) {
        this.orderBy.asc(columns);
        return this;
    }

    public SelectSql orderByDesc(String... columns) {
        this.orderBy.desc(columns);
        return this;
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
