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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public class MiniRepositoryImpl extends JdbcTemplate implements MiniRepository {
    private final ApplicationContext context;
    private final Dialect dialect;

    public MiniRepositoryImpl(ApplicationContext context, DataSource dataSource, Dialect dialect) {
        super(dataSource);
        this.context = context;
        this.dialect = dialect;
    }

    protected <T> RowMapper<T> getBeanMapper(Class<T> requiredType) {
        var mapper = new BeanPropertyRowMapper<>(requiredType);
        mapper.setPrimitivesDefaultedForNullValue(true);
        return mapper;
    }

    @Override
    public final int execute(String sql, @Nullable Object[] params) {
        return super.update(sql, params);
    }

    @Override
    public final int execute(AbstractSql<?> sql) {
        return MiniRepository.super.execute(sql);
    }

    @Override
    public final int[] executeBatch(String... sql) {
        return super.batchUpdate(sql);
    }

    @Override
    public final int[] executeBatch(String sql, List<Object[]> paramsArray) {
        return super.batchUpdate(sql, paramsArray);
    }

    @Override
    public final <T> List<T> queryList(String sql, Object[] params, RowMapper<T> mapper) {
        return super.query(sql, params, mapper);
    }

    @Override
    public final <T> List<T> queryList(AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepository.super.queryList(sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(String sql, Object[] params, Class<T> type) {
        return this.queryList(sql, params, getBeanMapper(type));
    }

    @Override
    public final <T> List<T> queryList(AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryList(sql, type);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, RowMapper<T> mapper) {
        return this.queryList(sql + " " + dialect.limit().getLimitOffset(size, offset), params, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepository.super.queryList(offset, size, sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, Class<T> type) {
        return this.queryList(offset, size, sql, params, getBeanMapper(type));
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryList(offset, size, sql, type);
    }

    @Override
    public final <T> List<T> queryList(int size, String sql, Object[] params, RowMapper<T> mapper) {
        return MiniRepository.super.queryList(size, sql, params, mapper);
    }

    @Override
    public final <T> List<T> queryList(int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepository.super.queryList(size, sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(int size, String sql, Object[] params, Class<T> type) {
        return MiniRepository.super.queryList(size, sql, params, type);
    }

    @Override
    public final <T> List<T> queryList(int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryList(size, sql, type);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(String sql, Object[] params) {
        return this.queryList(sql, params, getColumnMapRowMapper());
    }

    @Override
    public final List<Map<String, Object>> queryListMap(AbstractSql<?> sql) {
        return MiniRepository.super.queryListMap(sql);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int size, String sql, Object[] params) {
        return this.queryList(offset, size, sql, params, getColumnMapRowMapper());
    }

    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int size, AbstractSql<?> sql) {
        return MiniRepository.super.queryListMap(offset, size, sql);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(int size, String sql, Object[] params) {
        return MiniRepository.super.queryListMap(size, sql, params);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(int size, AbstractSql<?> sql) {
        return MiniRepository.super.queryListMap(size, sql);
    }

    @Override
    public final <T> List<T> queryListSingle(String sql, Object[] params, Class<T> type) {
        return queryList(sql, params, getSingleColumnRowMapper(type));
    }

    @Override
    public final <T> List<T> queryListSingle(AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryListSingle(sql, type);
    }

    @Override
    public final <T> List<T> queryListSingle(long offset, int size, String sql, Object[] params, Class<T> type) {
        return queryList(offset, size, sql, params, getSingleColumnRowMapper(type));
    }

    @Override
    public final <T> List<T> queryListSingle(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryListSingle(offset, size, sql, type);
    }

    @Override
    public final <T> List<T> queryListSingle(int size, String sql, Object[] params, Class<T> type) {
        return MiniRepository.super.queryListSingle(size, sql, params, type);
    }

    @Override
    public final <T> List<T> queryListSingle(int size, AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryListSingle(size, sql, type);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String sql, Object[] params, RowMapper<T> mapper) {
        return MiniRepository.super.queryObject(sql, params, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepository.super.queryObject(sql, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String sql, Object[] params, Class<T> type) {
        return this.queryObject(sql, params, getBeanMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryObject(sql, type);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(String sql, Object[] params) {
        return this.queryObject(sql, params, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(AbstractSql<?> sql) {
        return MiniRepository.super.queryObjectMap(sql);
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(String sql, Object[] params, Class<T> type) {
        return this.queryObject(sql, params, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryObjectSingle(sql, type);
    }

    @Nullable
    @Override
    public final String queryString(String sql, Object[] params) {
        return MiniRepository.super.queryString(sql, params);
    }

    @Nullable
    @Override
    public final String queryString(AbstractSql<?> sql) {
        return MiniRepository.super.queryString(sql);
    }

    @Nullable
    @Override
    public final Long queryLong(String sql, Object[] params) {
        return MiniRepository.super.queryLong(sql, params);
    }

    @Nullable
    @Override
    public final Long queryLong(AbstractSql<?> sql) {
        return MiniRepository.super.queryLong(sql);
    }

    @Nullable
    @Override
    public final Integer queryInt(String sql, Object[] params) {
        return MiniRepository.super.queryInt(sql, params);
    }

    @Nullable
    @Override
    public final Integer queryInt(AbstractSql<?> sql) {
        return MiniRepository.super.queryInt(sql);
    }

    @Nullable
    @Override
    public final Short queryShort(String sql, Object[] params) {
        return MiniRepository.super.queryShort(sql, params);
    }

    @Nullable
    @Override
    public final Short queryShort(AbstractSql<?> sql) {
        return MiniRepository.super.queryShort(sql);
    }

    @Nullable
    @Override
    public final Byte queryByte(String sql, Object[] params) {
        return MiniRepository.super.queryByte(sql, params);
    }

    @Nullable
    @Override
    public final Byte queryByte(AbstractSql<?> sql) {
        return MiniRepository.super.queryByte(sql);
    }

    @Nullable
    @Override
    public final Double queryDouble(String sql, Object[] params) {
        return MiniRepository.super.queryDouble(sql, params);
    }

    @Nullable
    @Override
    public final Double queryDouble(AbstractSql<?> sql) {
        return MiniRepository.super.queryDouble(sql);
    }

    @Nullable
    @Override
    public final Float queryFloat(String sql, Object[] params) {
        return MiniRepository.super.queryFloat(sql, params);
    }

    @Nullable
    @Override
    public final Float queryFloat(AbstractSql<?> sql) {
        return MiniRepository.super.queryFloat(sql);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(String sql, Object[] params) {
        return MiniRepository.super.queryBoolean(sql, params);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(AbstractSql<?> sql) {
        return MiniRepository.super.queryBoolean(sql);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(String sql, Object[] params) {
        return MiniRepository.super.queryTimestamp(sql, params);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(AbstractSql<?> sql) {
        return MiniRepository.super.queryTimestamp(sql);
    }

    @Nullable
    @Override
    public final Date queryDate(String sql, Object[] params) {
        return MiniRepository.super.queryDate(sql, params);
    }

    @Nullable
    @Override
    public final Date queryDate(AbstractSql<?> sql) {
        return MiniRepository.super.queryDate(sql);
    }

    @Nullable
    @Override
    public final Time queryTime(String sql, Object[] params) {
        return MiniRepository.super.queryTime(sql, params);
    }

    @Nullable
    @Override
    public final Time queryTime(AbstractSql<?> sql) {
        return MiniRepository.super.queryTime(sql);
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, String sql, Object[] params, RowMapper<T> mapper) {
        List<T> content = queryList(pageable.getOffset(), pageable.getPageSize(), sql, params, mapper);
        long total = ofNullable(queryLong("SELECT COUNT(*) FROM (" + sql + ") T ", params)).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryPage(pageable, sql.getSql(), sql.getArgs(), mapper);
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, String sql, Object[] params, Class<T> type) {
        return queryPage(pageable, sql, params, getBeanMapper(type));
    }

    @Override
    public final <T> Page<T> queryPage(Pageable pageable, AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryPage(pageable, sql, type);
    }

    @Override
    public final Page<Map<String, Object>> queryPageMap(Pageable pageable, String sql, Object[] params) {
        return queryPage(pageable, sql, params, getColumnMapRowMapper());
    }

    @Override
    public final Page<Map<String, Object>> queryPageMap(Pageable pageable, AbstractSql<?> sql) {
        return MiniRepository.super.queryPageMap(pageable, sql);
    }

    @Override
    public final <T> Page<T> queryPageSingle(Pageable pageable, String sql, Object[] params, Class<T> type) {
        return queryPage(pageable, sql, params, getSingleColumnRowMapper(type));
    }

    @Override
    public final <T> Page<T> queryPageSingle(Pageable pageable, AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryPageSingle(pageable, sql, type);
    }

    @Override
    public final int insert(String table, Consumer<InsertFragment<?>> consumer) {
        return MiniRepository.super.insert(table, consumer);
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> int insertOrUpdate(T entity, boolean includeNull) {
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
                    builder.onKeyFromInsert(column.value());
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    builder.onKeyFromInsert(column.value());
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
    }

    @Override
    public final <T> int insertOrUpdate(T entity) {
        return MiniRepository.super.insertOrUpdate(entity);
    }

    @Override
    public final int replace(String table, Consumer<ReplaceFragment<?>> consumer) {
        return MiniRepository.super.replace(table, consumer);
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> int replace(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return this.replace(getTableName(holder), builder -> { //
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
    public final <T> int replace(T entity) {
        return MiniRepository.super.replace(entity);
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
        return MiniRepository.super.insert(entity);
    }

    @Override
    public final int update(String table, Consumer<UpdateFragment<?>> consumer) {
        return MiniRepository.super.update(table, consumer);
    }

    @Override
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    public final <T> int update(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return MiniRepository.super.update(getTableName(holder), builder -> {
            Assert.isTrue(holder.getFields().values().stream().noneMatch(it -> {
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
        return MiniRepository.super.update(entity);
    }

    @Override
    public final int delete(String table, Consumer<DeleteFragment<?>> consumer) {
        return MiniRepository.super.delete(table, consumer);
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
            this.from(MiniRepositoryImpl.this.getTableName(holder));
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
