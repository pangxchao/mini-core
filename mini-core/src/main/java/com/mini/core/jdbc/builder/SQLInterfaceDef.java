package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.annotation.*;
import com.mini.core.util.holder.ClassHolder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Date;
import java.util.EventListener;
import java.util.Objects;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SQLInterfaceDef implements SQLInterface, EventListener, Serializable {

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> void createReplace(SQLBuilder builder, @NotNull T instance) {
        final Class<? extends T> type = (Class<T>) instance.getClass();
        ClassHolder<? extends T> table = ClassHolder.create(type);
        Table aTable = table.getAnnotation(Table.class);
        Objects.requireNonNull(aTable);

        builder.REPLACE_INTO(aTable.value());
        table.getFields().values().forEach(h -> {
            // 带Auto注解的字段不处理
            if (nonNull(h.getAnnotation(Auto.class))) {
                return;
            }
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (isNull(column)) return;
            // 带Lock注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(Lock.class))) {
                builder.VALUES(column.value());
                builder.ARGS(currentTimeMillis());
                return;
            }
            // 带CreateAt注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(CreateAt.class))) {
                builder.VALUES(column.value());
                builder.ARGS(new Date());
                return;
            }
            // 带UpdateAt注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(UpdateAt.class))) {
                builder.VALUES(column.value());
                builder.ARGS(new Date());
                return;
            }
            // 其它字段
            builder.VALUES(column.value());
            builder.ARGS(h.getValue(instance));
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> void createInsert(SQLBuilder builder, @Nonnull T instance) {
        final Class<? extends T> type = (Class<T>) instance.getClass();
        ClassHolder<? extends T> table = ClassHolder.create(type);
        Table aTable = table.getAnnotation(Table.class);
        Objects.requireNonNull(aTable);

        builder.INSERT_INTO(aTable.value());
        table.getFields().values().forEach(h -> {
            // 带Auto注解的字段不处理
            if (nonNull(h.getAnnotation(Auto.class))) {
                return;
            }
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (isNull(column)) return;
            // 带Lock注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(Lock.class))) {
                builder.VALUES(column.value());
                builder.ARGS(currentTimeMillis());
                return;
            }
            // 带CreateAt注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(CreateAt.class))) {
                builder.VALUES(column.value());
                builder.ARGS(new Date());
                return;
            }
            // 带UpdateAt注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(UpdateAt.class))) {
                builder.VALUES(column.value());
                builder.ARGS(new Date());
                return;
            }
            // 其它字段
            builder.VALUES(column.value());
            builder.ARGS(h.getValue(instance));
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> void createDelete(SQLBuilder builder, @Nonnull T instance) {
        final Class<? extends T> type = (Class<T>) instance.getClass();
        ClassHolder<? extends T> table = ClassHolder.create(type);
        Table aTable = table.getAnnotation(Table.class);
        Objects.requireNonNull(aTable);

        if (table.getFields().values().stream().anyMatch(h -> {
            Del del = h.getAnnotation(Del.class);
            return nonNull(del);
        })) {
            // 有字段代表删除状态时，修改该字段为删除状态
            builder.UPDATE(aTable.value());
            table.getFields().values().forEach(h -> {
                // 获取表字段信息
                Column column = h.getAnnotation(Column.class);
                if (isNull(column)) return;
                // 修改字段的删除状态
                Del del = h.getAnnotation(Del.class);
                if (nonNull(del)) {
                    builder.SET_EQUALS(column.value(), del.value());
                }
                // 修改字段的锁数据
                if (nonNull(h.getAnnotation(Lock.class))) {
                    builder.SET_EQUALS(column.value(), currentTimeMillis());
                }
            });
        } else {
            builder.DELETE(aTable.value());
            builder.FROM(aTable.value());
        }

        // 添加修改或者删除的条件
        table.getFields().values().forEach(h -> {
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (column == null) return;
            // 添加字段ID条件
            if (nonNull(h.getAnnotation(Id.class))) {
                builder.WHERE_EQUALS(column.value(), h.getValue(instance));
            }
            // 添加字段锁条件
            if (nonNull(h.getAnnotation(Lock.class))) {
                builder.WHERE_EQUALS(column.value(), h.getValue(instance));
            }
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> void createUpdate(SQLBuilder builder, @Nonnull T instance) {
        final Class<? extends T> type = (Class<T>) instance.getClass();
        ClassHolder<? extends T> table = ClassHolder.create(type);
        Table aTable = table.getAnnotation(Table.class);
        Objects.requireNonNull(aTable);

        builder.UPDATE(aTable.value());
        table.getFields().values().forEach(h -> {
            // 带ID注解的字段不修改
            if (nonNull(h.getAnnotation(Id.class))) {
                return;
            }
            // 带CreateAt注解的字段不修改
            if (nonNull(h.getAnnotation(CreateAt.class))) {
                return;
            }
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (column == null) return;

            // 还UpdateAt注解的字段设置字段值为当前时间
            if (nonNull(h.getAnnotation(UpdateAt.class))) {
                builder.SET(column.value() + " = ?");
                builder.ARGS(new Date());
                return;
            }

            // 带Lock注解的字段修改为当前时间戳值
            if (nonNull(h.getAnnotation(Lock.class))) {
                builder.SET(column.value() + " = ?");
                builder.ARGS(currentTimeMillis());
                return;
            }
            // 其它所有的字段
            builder.SET(column.value() + " = ?");
            builder.ARGS(h.getValue(instance));
        });
        table.getFields().values().forEach(h -> {
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (column == null) return;
            // 添加字段ID条件
            if (nonNull(h.getAnnotation(Id.class))) {
                builder.WHERE(column.value() + "%s = ?");
                builder.ARGS(h.getValue(instance));
                return;
            }
            // 带Lock注解的字段设置修改值和条件
            if (nonNull(h.getAnnotation(Lock.class))) {
                builder.WHERE(column.value() + "%s = ?");
                builder.ARGS(h.getValue(instance));
            }
        });
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public <T> void createInsertOnUpdate(SQLBuilder builder, T instance) {
        final Class<? extends T> type = (Class<T>) instance.getClass();
        ClassHolder<? extends T> table = ClassHolder.create(type);
        Table aTable = table.getAnnotation(Table.class);
        Objects.requireNonNull(aTable);

        builder.INSERT_INTO(aTable.value());
        table.getFields().values().forEach(h -> {
            // 带Auto注解的字段不处理
            if (nonNull(h.getAnnotation(Auto.class))) {
                return;
            }
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (isNull(column)) return;
            // 带Lock注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(Lock.class))) {
                builder.VALUES(column.value());
                builder.ARGS(currentTimeMillis());
                return;
            }
            // 带CreateAt注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(CreateAt.class))) {
                builder.VALUES(column.value());
                builder.ARGS(new Date());
                return;
            }
            // 带UpdateAt注解的字段设置值为当前时间
            if (nonNull(h.getAnnotation(UpdateAt.class))) {
                builder.VALUES(column.value());
                builder.ARGS(new Date());
                return;
            }
            // 其它字段
            builder.VALUES(column.value());
            builder.ARGS(h.getValue(instance));
        });

        table.getFields().values().forEach(h -> {
            // 带ID注解的字段不修改
            if (nonNull(h.getAnnotation(Id.class))) {
                return;
            }
            // 带CreateAt注解的字段不修改
            if (nonNull(h.getAnnotation(CreateAt.class))) {
                return;
            }
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (column == null) return;
            // 还UpdateAt注解的字段设置字段值为当前时间
            if (nonNull(h.getAnnotation(UpdateAt.class))) {
                builder.ON_DUPLICATE_KEY_UPDATE(column.value() + "  = ?");
                builder.ARGS(new Date());
                return;
            }
            // 带Lock注解的字段修改为当前时间戳值
            if (nonNull(h.getAnnotation(Lock.class))) {
                builder.ON_DUPLICATE_KEY_UPDATE(column.value() + "  = ?");
                builder.ARGS(currentTimeMillis());
                return;
            }
            // 其它所有的字段
            builder.ON_DUPLICATE_KEY_UPDATE(format("%s = VALUES(%s)", column.value(), column.value()));
        });
    }

    @Override
    public final <T> void createSelect(SQLBuilder builder, @Nonnull Class<T> type) {
        ClassHolder<? extends T> table = ClassHolder.create(type);
        Table aTable = table.getAnnotation(Table.class);
        Objects.requireNonNull(aTable);

        table.getFields().values().forEach(h -> {
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (column == null) return;
            // 添加查询字段
            String alias = column.alias().isBlank() ? h.getName() : column.alias();
            builder.SELECT(format("%s As `%s`", column.value(), alias));
        });
        // from 表名
        builder.FROM(aTable.value());
        // 处理关联表信息
        Join.List list = table.getAnnotation(Join.List.class);
        if (list != null && list.value().length > 0) {
            for (Join join : list.value()) {
                join.type().execute(builder, join.value());
            }
        }
        // 处理关联表信息
        for (Join join : table.getAnnotationsByType(Join.class)) {
            join.type().execute(builder, join.value());
        }
        // 查询字段信息
        table.getFields().values().forEach(h -> {
            // 获取表字段信息
            Column column = h.getAnnotation(Column.class);
            if (column == null) return;
            // 排除删除的数据
            Del del = h.getAnnotation(Del.class);
            if (nonNull(del)) {
                builder.WHERE(column.value() + " <> ?");
                builder.ARGS(del.value());
            }
        });
    }
}