package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.AbstractSql;
import com.mini.core.jdbc.builder.fragment.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.*;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public class MiniRepositoryImpl extends JdbcTemplate implements MiniRepository {

    private final Dialect dialect;

    public MiniRepositoryImpl(DataSource dataSource, Dialect dialect) {
        super(dataSource);
        this.dialect = dialect;
    }

    @NotNull
    protected <T> RowMapper<T> getSingleColumnRowMapper(@NotNull Class<T> requiredType) {
        return new SingleColumnRowMapper<>(requiredType);
    }

    protected <T> RowMapper<T> getBeanMapper(Class<T> requiredType) {
        return new BeanPropertyRowMapper<>(requiredType);
    }

    @NotNull
    protected RowMapper<Map<String, Object>> getColumnMapRowMapper() {
        return new ColumnMapRowMapper();
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
        return queryList(sql, params, getBeanMapper(type));
    }

    @Override
    public final <T> List<T> queryList(AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryList(sql, type);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, RowMapper<T> mapper) {
        return queryList(sql + " " + dialect.limit().getLimitOffset(size, offset), params, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepository.super.queryList(offset, size, sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, Class<T> type) {
        return queryList(offset, size, sql, params, getBeanMapper(type));
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
        return queryList(sql, params, getColumnMapRowMapper());
    }

    @Override
    public final List<Map<String, Object>> queryListMap(AbstractSql<?> sql) {
        return MiniRepository.super.queryListMap(sql);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int size, String sql, Object[] params) {
        return queryList(offset, size, sql, params, getColumnMapRowMapper());
    }

    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int size, AbstractSql<?> sql) {
        return queryList(offset, size, sql, getColumnMapRowMapper());
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
        return super.queryForObject(sql, params, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniRepository.super.queryObject(sql, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String sql, Object[] params, Class<T> type) {
        return queryObject(sql, params, getBeanMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, Class<T> type) {
        return MiniRepository.super.queryObject(sql, type);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(String sql, Object[] params) {
        return queryObject(sql, params, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(AbstractSql<?> sql) {
        return MiniRepository.super.queryObjectMap(sql);
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(String sql, Object[] params, Class<T> type) {
        return queryObject(sql, params, getSingleColumnRowMapper(type));
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
        long total = ofNullable(queryLong("SELECT COUNT(*) FROM (" + sql + ")", params)).orElse(0L);
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
    public final <T> int insertOrUpdate(T entity, boolean includeNull) {
        return MiniRepository.super.insertOrUpdate(entity, includeNull);
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
    public final <T> int replace(T entity, boolean includeNull) {
        return MiniRepository.super.replace(entity, includeNull);
    }

    @Override
    public final <T> int replace(T entity) {
        return MiniRepository.super.replace(entity);
    }

    @Override
    public final <T> int insert(T entity, boolean includeNull) {
        return MiniRepository.super.insert(entity, includeNull);
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
    public final <T> int update(T entity, boolean includeNull) {
        return MiniRepository.super.update(entity, includeNull);
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
        return MiniRepository.super.select(offset, size, type, consumer);
    }

    @Override
    public final <T> Page<T> select(Pageable pageable, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return MiniRepository.super.select(pageable, type, consumer);
    }

    @Override
    public final <T> List<T> select(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return MiniRepository.super.select(type, consumer);
    }

    @Override
    public final <T> Page<T> select(Pageable pageable, Class<T> type) {
        return MiniRepository.super.select(pageable, type);
    }

    @Override
    public final <T> List<T> select(int offset, int size, Class<T> type) {
        return MiniRepository.super.select(offset, size, type);
    }

    @Override
    public final <T> List<T> select(Class<T> type) {
        return MiniRepository.super.select(type);
    }

    @Nullable
    @Override
    public final <T> T selectOne(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return MiniRepository.super.selectOne(type, consumer);
    }

    @Nullable
    @Override
    public final <T> T selectOne(Class<T> type) {
        return MiniRepository.super.selectOne(type);
    }
}
