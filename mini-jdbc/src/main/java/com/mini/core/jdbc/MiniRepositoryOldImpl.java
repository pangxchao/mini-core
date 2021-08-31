package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.AbstractSql;
import com.mini.core.jdbc.builder.SelectSql;
import com.mini.core.jdbc.builder.fragment.*;
import com.mini.core.jdbc.builder.support.Join;
import com.mini.core.util.holder.ClassHolder;
import com.mini.core.util.holder.FieldHolder;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MiniRepositoryOldImpl extends DefaultMiniJdbcTemplate implements MiniRepositoryOld {
    private final ApplicationContext context;
    private final Dialect dialect;

    public MiniRepositoryOldImpl(ApplicationContext context, JdbcTemplate jdbcTemplate, Dialect dialect) {
        super(jdbcTemplate);
        this.context = context;
        this.dialect = dialect;
    }

    @Override
    public final int execute(String sql, @Nullable Object[] params, GeneratedKeyHolder keyHolder) {
        return getJdbcOperations().update(con -> {
            var statement = con.prepareStatement(sql, RETURN_GENERATED_KEYS);
            var setter = new ArgumentPreparedStatementSetter(params);
            setter.setValues(statement);
            return statement;
        }, keyHolder);
    }

    @Override
    public final int execute(AbstractSql<?> sql, GeneratedKeyHolder keyHolder) {
        return MiniRepositoryOld.super.execute(sql, keyHolder);
    }

    @Override
    public final int execute(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.execute(sql);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepositoryOld.super.queryList(sql, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryList(sql, type);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, RowMapper<T> mapper) {
        return this.queryList(sql + " " + dialect.limit().getLimitOffset(size, offset), params, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepositoryOld.super.queryList(offset, size, sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, Class<T> type) {
        return this.queryList(offset, size, sql, params, getBeanRowMapper(type));
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryList(offset, size, sql, type);
    }

    @Override
    public final <T> List<T> queryList(int size, String sql, Object[] params, RowMapper<T> mapper) {
        return MiniRepositoryOld.super.queryList(size, sql, params, mapper);
    }

    @Override
    public final <T> List<T> queryList(int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepositoryOld.super.queryList(size, sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(int size, String sql, Object[] params, Class<T> type) {
        return MiniRepositoryOld.super.queryList(size, sql, params, type);
    }

    @Override
    public final <T> List<T> queryList(int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryList(size, sql, type);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryListMap(sql);
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int size, String sql, @Nullable Object[] params) {
        return queryList(offset, size, sql, params, getMapRowMapper());
    }

    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int size, AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryListMap(offset, size, sql);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(int size, String sql, Object[] params) {
        return MiniRepositoryOld.super.queryListMap(size, sql, params);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(int size, AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryListMap(size, sql);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryListSingle(String sql, @Nullable Object[] params, Class<T> type) {
        return querySingleList(sql, params, type);
    }

    @Override
    public final <T> List<T> queryListSingle(AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryListSingle(sql, type);
    }

    @Override
    public <T> List<T> queryListSingle(long offset, int size, String sql, @Nullable Object[] params, Class<T> type) {
        return queryList(offset, size, sql, params, getSingleRowMapper(type));
    }

    @Override
    public final <T> List<T> queryListSingle(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryListSingle(offset, size, sql, type);
    }

    @Override
    public final <T> List<T> queryListSingle(int size, String sql, Object[] params, Class<T> type) {
        return MiniRepositoryOld.super.queryListSingle(size, sql, params, type);
    }

    @Override
    public final <T> List<T> queryListSingle(int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryListSingle(size, sql, type);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String sql, Object[] params, RowMapper<T> mapper) {
        return MiniRepositoryOld.super.queryObject(sql, params, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepositoryOld.super.queryObject(sql, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String sql, Object[] params, Class<T> type) {
        return this.queryObject(sql, params, getBeanRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryObject(sql, type);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(String sql, Object[] params) {
        return this.queryObject(sql, params, getMapRowMapper());
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryObjectMap(sql);
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(String sql, Object[] params, Class<T> type) {
        return this.queryObject(sql, params, getSingleRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryObjectSingle(sql, type);
    }

    @Nullable
    @Override
    public final String queryString(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryString(sql);
    }

    @Nullable
    @Override
    public final Long queryLong(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryLong(sql);
    }

    @Nullable
    @Override
    public final Integer queryInt(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryInt(sql);
    }

    @Nullable
    @Override
    public final Short queryShort(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryShort(sql);
    }

    @Nullable
    @Override
    public final Byte queryByte(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryByte(sql);
    }

    @Nullable
    @Override
    public final Double queryDouble(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryDouble(sql);
    }

    @Nullable
    @Override
    public final Float queryFloat(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryFloat(sql);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryBoolean(sql);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(String sql, Object[] params) {
        return MiniRepositoryOld.super.queryTimestamp(sql, params);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryTimestamp(sql);
    }

    @Nullable
    @Override
    public final Date queryDate(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryDate(sql);
    }

    @Nullable
    @Override
    public final Time queryTime(String sql, Object[] params) {
        return MiniRepositoryOld.super.queryTime(sql, params);
    }

    @Nullable
    @Override
    public final Time queryTime(AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryTime(sql);
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, String sql, Object[] params, RowMapper<T> mapper) {
        List<T> content = queryList(pageable.getOffset(), pageable.getPageSize(), sql, params, mapper);
        long total = queryLong("SELECT COUNT(*) FROM (" + sql + ") T ", params).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryPage(pageable, sql.getSql(), sql.getArgs(), mapper);
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, String sql, Object[] params, Class<T> type) {
        return queryPage(pageable, sql, params, getBeanRowMapper(type));
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryPage(pageable, sql, type);
    }

    @Override
    public final Page<Map<String, Object>> queryPageMap(Pageable pageable, String sql, Object[] params) {
        return queryPage(pageable, sql, params, getMapRowMapper());
    }

    @Override
    public final Page<Map<String, Object>> queryPageMap(Pageable pageable, AbstractSql<?> sql) {
        return MiniRepositoryOld.super.queryPageMap(pageable, sql);
    }

    @Override
    public final <T> Page<T> queryPageSingle(Pageable pageable, String sql, Object[] params, Class<T> type) {
        return queryPage(pageable, sql, params, getSingleRowMapper(type));
    }

    @Override
    public final <T> Page<T> queryPageSingle(Pageable pageable, AbstractSql<?> sql, Class<T> type) {
        return MiniRepositoryOld.super.queryPageSingle(pageable, sql, type);
    }

    @Override
    public final int insert(String table, GeneratedKeyHolder keyHolder, Consumer<InsertFragment<?>> consumer) {
        return MiniRepositoryOld.super.insert(table, keyHolder, consumer);
    }

    @Override
    public final int insert(String table, Consumer<InsertFragment<?>> consumer) {
        return MiniRepositoryOld.super.insert(table, consumer);
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> int insertOrUpdate(T entity, boolean includeNull) {
        final Class<? extends T> type = (Class<? extends T>) entity.getClass();
        final ClassHolder<? extends T> holder = ClassHolder.create(type);
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int res = insert(getTableName(holder), keyHolder, builder -> { //
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // ID字段的值的生成
                if (field.getAnnotation(Id.class) != null) {
                    var miniId = new MiniId(field, entity);
                    context.publishEvent(miniId);
                }
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    builder.onKeyFromInsert(column.value());
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    builder.onKeyFromInsert(column.value());
                    return;
                }
                // 其它普通字段处理
                if (otherSetHandler(entity, includeNull, builder, field, column)) {
                    builder.onKeyFromInsert(column.value());
                }
            });
        });
        var autoMiniId = new AutoMiniId(entity, keyHolder);
        this.context.publishEvent(autoMiniId);
        return res;
    }

    @Override
    public final <T> int insertOrUpdate(T entity) {
        return MiniRepositoryOld.super.insertOrUpdate(entity);
    }

    @Override
    public final int replace(String table, GeneratedKeyHolder keyHolder, Consumer<ReplaceFragment<?>> consumer) {
        return MiniRepositoryOld.super.replace(table, keyHolder, consumer);
    }

    @Override
    public final int replace(String table, Consumer<ReplaceFragment<?>> consumer) {
        return MiniRepositoryOld.super.replace(table, consumer);
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> int replace(T entity, boolean includeNull) {
        final Class<? extends T> type = (Class<? extends T>) entity.getClass();
        final ClassHolder<? extends T> holder = ClassHolder.create(type);
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int res = replace(getTableName(holder), builder -> { //
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // ID字段的值的生成
                if (field.getAnnotation(Id.class) != null) {
                    var miniId = new MiniId(field, entity);
                    context.publishEvent(miniId);
                }
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    return;
                }
                // 其它普通字段处理
                otherSetHandler(entity, includeNull, builder, field, column);
            });
        });
        var autoMiniId = new AutoMiniId(entity, keyHolder);
        this.context.publishEvent(autoMiniId);
        return res;
    }

    @Override
    public final <T> int replace(T entity) {
        return MiniRepositoryOld.super.replace(entity);
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> int insert(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return this.insert(getTableName(holder), builder -> { //
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // ID字段的值的生成
                if (field.getAnnotation(Id.class) != null) {
                    var miniId = new MiniId(field, entity);
                    context.publishEvent(miniId);
                }
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    return;
                }
                // 其它普通字段处理
                otherSetHandler(entity, includeNull, builder, field, column);
            });
        });
    }

    @Override
    public final <T> int insert(T entity) {
        return MiniRepositoryOld.super.insert(entity);
    }

    @Override
    public final int update(String table, Consumer<UpdateFragment<?>> consumer) {
        return MiniRepositoryOld.super.update(table, consumer);
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> int update(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return MiniRepositoryOld.super.update(getTableName(holder), builder -> {
            Assert.isTrue(holder.getFields().values().stream().anyMatch(it -> {
                // 为了数据安全，修改时必须有ID字段并且ID字段值不能为空，否则报错
                return Objects.nonNull(it.getAnnotation(Id.class));
            }), "No @Id in the " + type.getName());
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                //  有ID注解的字段，修改时不修改其值
                if (field.getAnnotation(Id.class) != null) {
                    return;
                }
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    return;
                }
                // 其它普通字段处理
                otherSetHandler(entity, includeNull, builder, field, column);
            });
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // 处理ID条件
                if (idWhereHandler(entity, builder, field, column)) {
                    return;
                }
                // 处理版本条件
                versionWhereHandler(builder, entity, field, column);
            });
        });
    }

    @Override
    public final <T> int update(T entity) {
        return MiniRepositoryOld.super.update(entity);
    }

    @Override
    public final int delete(String table, Consumer<DeleteFragment<?>> consumer) {
        return MiniRepositoryOld.super.delete(table, consumer);
    }

    @Override
    public final <T> List<T> select(int offset, int size, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return queryList(offset, size, getSelectSql(type, consumer), type);
    }

    @Override
    public final <T> Page<T> select(Pageable pageable, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return queryPage(pageable, getSelectSql(type, consumer), type);
    }

    @Override
    public final <T> List<T> select(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return queryList(getSelectSql(type, consumer), type);
    }

    @Override
    public final <T> Page<T> select(Pageable pageable, Class<T> type) {
        final SelectSql sql = getSelectSql(type, null);
        return queryPage(pageable, sql, type);
    }

    @Override
    public final <T> List<T> select(int offset, int size, Class<T> type) {
        final SelectSql sql = getSelectSql(type, null);
        return queryList(offset, size, sql, type);
    }

    @Override
    public final <T> List<T> select(Class<T> type) {
        var sql = getSelectSql(type, null);
        return queryList(sql, type);
    }

    @Nullable
    @Override
    public final <T> T selectOne(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return this.queryObject(getSelectSql(type, consumer), type);
    }

    @Nullable
    @Override
    public final <T> T selectOne(Class<T> type) {
        var sql = getSelectSql(type, null);
        return queryObject(sql, type);
    }

    // 获取表名称
    private String getTableName(ClassHolder<?> holder) {
        var table = holder.getAnnotation(Table.class);
        Objects.requireNonNull(table);

        if (StringUtils.hasText(table.value())) {
            return table.value();
        }
        return holder.getName();
    }

    private static boolean checkVersionType(Class<?> type) {
        return type == Long.class || type == long.class || type == Integer.class || type == int.class;
    }

    // 处理设置中的版本字段 @Version
    @SuppressWarnings("DuplicatedCode")
    private boolean versionSetHandler(SetFragment<?> builder, Object entity, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(Version.class))) {
            return false;
        }
        Assert.isTrue(checkVersionType(field.getType()), "@Version must be of type long or int");
        Object value = field.getValue(entity), nextValue = 1;
        if (value instanceof Integer) {
            nextValue = ((Integer) value) + 1;
        } else if (value instanceof Long) {
            nextValue = ((Long) value) + 1;
        }
        builder.set(column.value(), nextValue);
        return true;
    }

    // 处理条件中的版本字段 @Version
    @SuppressWarnings({"DuplicatedCode", "UnusedReturnValue"})
    private boolean versionWhereHandler(WhereFragment<?> builder, Object entity, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(Version.class))) {
            return false;
        }
        Assert.isTrue(checkVersionType(field.getType()), "@Version must be of type long or int");
        Optional.ofNullable(field.getValue(entity)).ifPresentOrElse(it -> builder.whereEq(column.value(), it),
                () -> builder.whereIsNull(column.value()));
        return true;
    }

    // 处理创建时间字段 @CreatedDate
    private boolean createdDateHandler(SetFragment<?> builder, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(CreatedDate.class))) {
            return false;
        }
        builder.set(column.value(), new java.util.Date());
        return true;
    }

    // 处理修改时间字段  @LastModifiedDate
    private boolean lastModifiedDateHandler(SetFragment<?> builder, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(LastModifiedDate.class))) {
            return false;
        }
        builder.set(column.value(), new java.util.Date());
        return true;
    }

    // 其它普通字段处理
    private <T> boolean otherSetHandler(T entity, boolean includeNull, SetFragment<?> builder, FieldHolder<? extends T> field, Column column) {
        final Object value = field.getValue(entity);
        if (includeNull || Objects.nonNull(value)) {
            builder.set(column.value(), value);
            return true;
        }
        return false;
    }

    // 处理ID条件 @ID
    private <T> boolean idWhereHandler(T entity, WhereFragment<?> builder, FieldHolder<? extends T> field, Column column) {
        if (Objects.isNull(field.getAnnotation(Id.class))) {
            return false;
        }
        Object value = field.getValue(entity);
        builder.whereEq(column.value(), value);
        return true;
    }

    // 获取查询Sql
    private <T> SelectSql getSelectSql(Class<T> type, @javax.annotation.Nullable Consumer<SelectFragment<?>> consumer) {
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return new SelectSql() {{
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;

                if (StringUtils.hasText(column.value())) {
                    select(column.value(), field.getName());
                } else select(field.getName());

            });
            // 生成查询表名
            this.from(MiniRepositoryOldImpl.this.getTableName(holder));
            // 生成联合查询表名
            for (Join join : holder.getAnnotationsByType(Join.class)) {
                join.type().exe(this, join.table(), join.on());
            }
            // 添加其它条件
            if (Objects.nonNull(consumer)) {
                consumer.accept(this);
            }
        }};
    }
}
