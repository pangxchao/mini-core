package com.mini.core.data;

import com.mini.core.data.builder.AbstractSql;
import com.mini.core.data.builder.fragment.*;
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

public class MiniBaseRepositoryImpl extends JdbcTemplate implements MiniBaseRepository {

    private final Dialect dialect;

    public MiniBaseRepositoryImpl(@NotNull DataSource dataSource, Dialect dialect) {
        super(dataSource);
        this.dialect = dialect;
    }

    @NotNull
    protected <T> RowMapper<T> getSingleColumnRowMapper(@NotNull Class<T> requiredType) {
        return new SingleColumnRowMapper<>(requiredType);
    }

    @NotNull
    protected <T> RowMapper<T> getBeanMapper(@NotNull Class<T> requiredType) {
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
        return MiniBaseRepository.super.execute(sql);
    }

    @Override
    public final int[] executeBatch(String... sql) {
        return super.batchUpdate(sql);
    }

    @Override
    public int[] executeBatch(String sql, List<Object[]> paramsArray) {
        return super.batchUpdate(sql, paramsArray);
    }

    @Override
    public final <T> List<T> queryList(String sql, Object[] params, RowMapper<T> mapper) {
        return super.query(sql, params, mapper);
    }

    @Override
    public final <T> List<T> queryList(AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniBaseRepository.super.queryList(sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(String sql, Object[] params, Class<T> type) {
        return queryList(sql, params, getBeanMapper(type));
    }

    @Override
    public final <T> List<T> queryList(AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryList(sql, type);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, RowMapper<T> mapper) {
        return queryList(sql + " " + dialect.limit().getLimitOffset(size, offset), params, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniBaseRepository.super.queryList(offset, size, sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, String sql, Object[] params, Class<T> type) {
        return queryList(offset, size, sql, params, getBeanMapper(type));
    }

    @Override
    public final <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryList(offset, size, sql, type);
    }

    @Override
    public final <T> List<T> queryList(int size, String sql, Object[] params, RowMapper<T> mapper) {
        return MiniBaseRepository.super.queryList(size, sql, params, mapper);
    }

    @Override
    public final <T> List<T> queryList(int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniBaseRepository.super.queryList(size, sql, mapper);
    }

    @Override
    public final <T> List<T> queryList(int size, String sql, Object[] params, Class<T> type) {
        return MiniBaseRepository.super.queryList(size, sql, params, type);
    }

    @Override
    public final <T> List<T> queryList(int size, AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryList(size, sql, type);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(String sql, Object[] params) {
        return queryList(sql, params, getColumnMapRowMapper());
    }

    @Override
    public final List<Map<String, Object>> queryListMap(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryListMap(sql);
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
        return MiniBaseRepository.super.queryListMap(size, sql, params);
    }

    @Override
    public final List<Map<String, Object>> queryListMap(int size, AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryListMap(size, sql);
    }

    @Override
    public final <T> List<T> queryListSingle(String sql, Object[] params, Class<T> type) {
        return queryList(sql, params, getSingleColumnRowMapper(type));
    }

    @Override
    public final <T> List<T> queryListSingle(AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryListSingle(sql, type);
    }

    @Override
    public final <T> List<T> queryListSingle(long offset, int size, String sql, Object[] params, Class<T> type) {
        return queryList(offset, size, sql, params, getSingleColumnRowMapper(type));
    }

    @Override
    public final <T> List<T> queryListSingle(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryListSingle(offset, size, sql, type);
    }

    @Override
    public final <T> List<T> queryListSingle(int size, String sql, Object[] params, Class<T> type) {
        return MiniBaseRepository.super.queryListSingle(size, sql, params, type);
    }

    @Override
    public final <T> List<T> queryListSingle(int size, AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryListSingle(size, sql, type);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String sql, Object[] params, RowMapper<T> mapper) {
        return super.queryForObject(sql, params, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniBaseRepository.super.queryObject(sql, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String sql, Object[] params, Class<T> type) {
        return queryObject(sql, params, getBeanMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryObject(sql, type);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(String sql, Object[] params) {
        return queryObject(sql, params, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryObjectMap(sql);
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(String sql, Object[] params, Class<T> type) {
        return queryObject(sql, params, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryObjectSingle(sql, type);
    }

    @Nullable
    @Override
    public final String queryString(String sql, Object[] params) {
        return MiniBaseRepository.super.queryString(sql, params);
    }

    @Nullable
    @Override
    public final String queryString(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryString(sql);
    }

    @Nullable
    @Override
    public final Long queryLong(String sql, Object[] params) {
        return MiniBaseRepository.super.queryLong(sql, params);
    }

    @Nullable
    @Override
    public final Long queryLong(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryLong(sql);
    }

    @Nullable
    @Override
    public final Integer queryInt(String sql, Object[] params) {
        return MiniBaseRepository.super.queryInt(sql, params);
    }

    @Nullable
    @Override
    public final Integer queryInt(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryInt(sql);
    }

    @Nullable
    @Override
    public final Short queryShort(String sql, Object[] params) {
        return MiniBaseRepository.super.queryShort(sql, params);
    }

    @Nullable
    @Override
    public final Short queryShort(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryShort(sql);
    }

    @Nullable
    @Override
    public final Byte queryByte(String sql, Object[] params) {
        return MiniBaseRepository.super.queryByte(sql, params);
    }

    @Nullable
    @Override
    public final Byte queryByte(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryByte(sql);
    }

    @Nullable
    @Override
    public final Double queryDouble(String sql, Object[] params) {
        return MiniBaseRepository.super.queryDouble(sql, params);
    }

    @Nullable
    @Override
    public final Double queryDouble(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryDouble(sql);
    }

    @Nullable
    @Override
    public final Float queryFloat(String sql, Object[] params) {
        return MiniBaseRepository.super.queryFloat(sql, params);
    }

    @Nullable
    @Override
    public final Float queryFloat(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryFloat(sql);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(String sql, Object[] params) {
        return MiniBaseRepository.super.queryBoolean(sql, params);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryBoolean(sql);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(String sql, Object[] params) {
        return MiniBaseRepository.super.queryTimestamp(sql, params);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryTimestamp(sql);
    }

    @Nullable
    @Override
    public final Date queryDate(String sql, Object[] params) {
        return MiniBaseRepository.super.queryDate(sql, params);
    }

    @Nullable
    @Override
    public final Date queryDate(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryDate(sql);
    }

    @Nullable
    @Override
    public final Time queryTime(String sql, Object[] params) {
        return MiniBaseRepository.super.queryTime(sql, params);
    }

    @Nullable
    @Override
    public final Time queryTime(AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryTime(sql);
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
        return MiniBaseRepository.super.queryPage(pageable, sql, type);
    }

    @Override
    public <T> Page<T> queryPage(int page, int size, String sql, @Nullable Object[] params, RowMapper<T> mapper) {
        return MiniBaseRepository.super.queryPage(page, size, sql, params, mapper);
    }

    @Override
    public <T> Page<T> queryPage(int page, int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return MiniBaseRepository.super.queryPage(page, size, sql, mapper);
    }

    @Override
    public <T> Page<T> queryPage(int page, int size, String sql, @Nullable Object[] params, Class<T> type) {
        return MiniBaseRepository.super.queryPage(page, size, sql, params, type);
    }

    @Override
    public <T> Page<T> queryPage(int page, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryPage(page, size, sql, type);
    }

    @Override
    public final Page<Map<String, Object>> queryPageMap(Pageable pageable, String sql, Object[] params) {
        return queryPage(pageable, sql, params, getColumnMapRowMapper());
    }

    @Override
    public final Page<Map<String, Object>> queryPageMap(Pageable pageable, AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryPageMap(pageable, sql);
    }

    @Override
    public Page<Map<String, Object>> queryPageMap(int page, int size, String sql, @Nullable Object[] params) {
        return MiniBaseRepository.super.queryPageMap(page, size, sql, params);
    }

    @Override
    public Page<Map<String, Object>> queryPageMap(int page, int size, AbstractSql<?> sql) {
        return MiniBaseRepository.super.queryPageMap(page, size, sql);
    }

    @Override
    public final <T> Page<T> queryPageSingle(Pageable pageable, String sql, Object[] params, Class<T> type) {
        return queryPage(pageable, sql, params, getSingleColumnRowMapper(type));
    }

    @Override
    public final <T> Page<T> queryPageSingle(Pageable pageable, AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryPageSingle(pageable, sql, type);
    }

    @Override
    public <T> Page<T> queryPageSingle(int page, int size, String sql, @Nullable Object[] params, Class<T> type) {
        return MiniBaseRepository.super.queryPageSingle(page, size, sql, params, type);
    }

    @Override
    public <T> Page<T> queryPageSingle(int page, int size, AbstractSql<?> sql, Class<T> type) {
        return MiniBaseRepository.super.queryPageSingle(page, size, sql, type);
    }

    @Override
    public final int insert(String table, Consumer<InsertFragment<?>> consumer) {
        return MiniBaseRepository.super.insert(table, consumer);
    }

    @Override
    public final int replace(String table, Consumer<SetFragment<?>> consumer) {
        return MiniBaseRepository.super.replace(table, consumer);
    }

    @Override
    public final int delete(String table, Consumer<DeleteFragment<?>> consumer) {
        return MiniBaseRepository.super.delete(table, consumer);
    }

    @Override
    public final int update(String table, Consumer<UpdateFragment<?>> consumer) {
        return MiniBaseRepository.super.update(table, consumer);
    }

    @Override
    public final <T> Page<T> select(Pageable pageable, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return MiniBaseRepository.super.select(pageable, type, consumer);
    }

    @Override
    public final <T> Page<T> select(int page, int size, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return MiniBaseRepository.super.select(page, size, type, consumer);
    }

    @Override
    public final <T> List<T> select(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return MiniBaseRepository.super.select(type, consumer);
    }

    @Override
    public final <T> Page<T> select(Pageable pageable, Class<T> type) {
        return MiniBaseRepository.super.select(pageable,type);
    }

    @Override
    public final <T> Page<T> select(int page, int size, Class<T> type) {
        return MiniBaseRepository.super.select(page,size,type);
    }

    @Override
    public final <T> List<T> select(Class<T> type) {
        return MiniBaseRepository.super.select(type);
    }
}
