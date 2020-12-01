package com.mini.core.data;

import com.mini.core.data.builder.AbstractSql;
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

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class MiniBaseRepositoryImpl extends JdbcTemplate implements MiniBaseRepository {
    @NotNull
    private final Dialect jdbcDialect;

    public MiniBaseRepositoryImpl(@NotNull DataSource dataSource, @NotNull Dialect jdbcDialect) {
        super(dataSource);
        this.jdbcDialect = jdbcDialect;
    }

    @NotNull
    protected <T> RowMapper<T> getSingleColumnRowMapper(@NotNull Class<T> requiredType) {
        return new SingleColumnRowMapper<>(requiredType);
    }

    @NotNull
    protected <T> RowMapper<T> getBeanPropertyRowMapper(@NotNull Class<T> requiredType) {
        return new BeanPropertyRowMapper<>(requiredType);
    }

    @NotNull
    protected RowMapper<Map<String, Object>> getColumnMapRowMapper() {
        return new ColumnMapRowMapper();
    }

    @Override
    public int execute(@NotNull String sql, @Nullable Object[] params) {
        return super.update(sql, params);
    }

    @Override
    public final int execute(@NotNull AbstractSql<?> sql) {
        return execute(sql.getSql(), sql.getArgs());
    }

    @NotNull
    @Override
    public final int[] executeBatch(@NotNull String... sql) {
        return super.batchUpdate(sql);
    }

    @NotNull
    @Override
    public int[] executeBatch(@NotNull String sql, List<Object[]> paramsArray) {
        return super.batchUpdate(sql, paramsArray);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull String sql, Object[] params, RowMapper<T> mapper) {
        return super.query(sql, params, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull AbstractSql<?> sql, @NotNull RowMapper<T> mapper) {
        return this.queryList(sql.getSql(), sql.getArgs(), mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryList(sql, params, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryList(sql, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(@NotNull String sql, Object[] params) {
        return queryList(sql, params, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(@NotNull AbstractSql<?> sql) {
        return queryList(sql, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(@NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryList(sql, params, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(@NotNull AbstractSql<?> sql, Class<T> type) {
        return queryList(sql, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull String sql, Object[] params, @NotNull RowMapper<T> mapper) {
        sql = sql + " " + this.jdbcDialect.limit().getLimitOffset(limit, offset);
        return queryList(sql, params, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryList(offset, limit, sql.getSql(), sql.getArgs(), mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, params, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int limit, @NotNull String sql, Object[] params) {
        return queryList(offset, limit, sql, params, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int limit, @NotNull AbstractSql<?> sql) {
        return queryList(offset, limit, sql, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(long offset, int limit, @NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, params, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(long offset, int limit, @NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull String sql, Object[] params, @NotNull RowMapper<T> mapper) {
        return queryList(0, limit, sql, params, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull AbstractSql<?> sql, @NotNull RowMapper<T> mapper) {
        return queryList(0, limit, sql, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, String sql, Object[] params, @NotNull Class<T> type) {
        return queryList(0, limit, sql, params, type);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryList(0, limit, sql, type);
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @NotNull String sql, Object[] params) {
        return queryListMap(0, limit, sql, params);
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @NotNull AbstractSql<?> sql) {
        return queryListMap(0, limit, sql);
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int limit, @NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryListSingle(0, limit, sql, params, type);
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int limit, @NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryListSingle(0, limit, sql, type);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull String sql, Object[] params, @NotNull RowMapper<T> mapper) {
        return queryList(1, sql, params, mapper).stream().findFirst().orElse(null);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull AbstractSql<?> sql, @NotNull RowMapper<T> mapper) {
        return queryObject(sql.getSql(), sql.getArgs(), mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryObject(sql, params, getBeanPropertyRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryObject(sql, getBeanPropertyRowMapper(type));
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@NotNull String sql, Object[] params) {
        return queryObject(sql, params, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@NotNull AbstractSql<?> sql) {
        return queryObject(sql, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryObject(sql, params, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryObject(sql, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final String queryString(String sql, Object[] params) {
        return queryObjectSingle(sql, params, String.class);
    }

    @Nullable
    @Override
    public final String queryString(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, String.class);
    }

    @Nullable
    @Override
    public final Long queryLong(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Long.class);
    }

    @Nullable
    @Override
    public final Long queryLong(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Long.class);
    }

    @Nullable
    @Override
    public final Integer queryInt(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Integer.class);
    }

    @Nullable
    @Override
    public final Integer queryInt(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Integer.class);
    }

    @Nullable
    @Override
    public final Short queryShort(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Short.class);
    }

    @Nullable
    @Override
    public final Short queryShort(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Short.class);
    }

    @Nullable
    @Override
    public final Byte queryByte(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Byte.class);
    }

    @Nullable
    @Override
    public final Byte queryByte(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Byte.class);
    }

    @Nullable
    @Override
    public final Double queryDouble(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Double.class);
    }

    @Nullable
    @Override
    public final Double queryDouble(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Double.class);
    }

    @Nullable
    @Override
    public final Float queryFloat(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Float.class);
    }

    @Nullable
    @Override
    public final Float queryFloat(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Float.class);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Boolean.class);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Boolean.class);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Timestamp.class);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Timestamp.class);
    }

    @Nullable
    @Override
    public final Date queryDate(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Date.class);
    }

    @Nullable
    @Override
    public final Date queryDate(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Date.class);
    }

    @Nullable
    @Override
    public final Time queryTime(@NotNull String sql, Object[] params) {
        return queryObjectSingle(sql, params, Time.class);
    }

    @Nullable
    @Override
    public final Time queryTime(@NotNull AbstractSql<?> sql) {
        return queryObjectSingle(sql, Time.class);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, @NotNull String sql, Object[] params, @NotNull RowMapper<T> mapper) {
        final List<T> content = this.queryList(pageable.getOffset(), pageable.getPageSize(), sql, params, mapper);
        long total = ofNullable(queryLong(format("SELECT COUNT(*) FROM (%s) ", sql), params)).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, @NotNull AbstractSql<?> sql, @NotNull RowMapper<T> mapper) {
        return queryPage(pageable, sql.getSql(), sql.getArgs(), mapper);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, String sql, Object[] params, @NotNull Class<T> type) {
        return queryPage(pageable, sql, params, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, @NotNull AbstractSql<?> sql, Class<T> type) {
        return queryPage(pageable, sql, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final Page<Map<String, Object>> queryPageMap(@NotNull Pageable pageable, @NotNull String sql, Object[] params) {
        return queryPage(pageable, sql, params, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final Page<Map<String, Object>> queryPageMap(@NotNull Pageable pageable, @NotNull AbstractSql<?> sql) {
        return queryPage(pageable, sql, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPageSingle(@NotNull Pageable pageable, @NotNull String sql, Object[] params, @NotNull Class<T> type) {
        return queryPage(pageable, sql, params, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPageSingle(@NotNull Pageable pageable, @NotNull AbstractSql<?> sql, @NotNull Class<T> type) {
        return queryPage(pageable, sql, getSingleColumnRowMapper(type));
    }
}
