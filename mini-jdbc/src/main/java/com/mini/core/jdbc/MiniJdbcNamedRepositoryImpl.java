package com.mini.core.jdbc;

import com.mini.core.data.builder.NamedSql;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNullElse;
import static java.util.Optional.ofNullable;

public class MiniJdbcNamedRepositoryImpl<E, ID> extends MiniJdbcBaseRepositoryImpl<E, ID> implements MiniJdbcNamedRepository<E, ID> {
    @NotNull
    private final NamedParameterJdbcOperations jdbcOperations;
    @NotNull
    private final Dialect jdbcDialect;

    public MiniJdbcNamedRepositoryImpl(@NotNull NamedParameterJdbcOperations jdbcOperations,
                                       @NotNull JdbcAggregateOperations entityOperations,
                                       @NotNull PersistentEntity<E, ?> entity,
                                       @NotNull Dialect jdbcDialect) {
        super(entityOperations, entity);
        this.jdbcOperations = jdbcOperations;
        this.jdbcDialect = jdbcDialect;
    }


    @NotNull
    private <T> RowMapper<T> getSingleColumnRowMapper(@NotNull Class<T> requiredType) {
        return new SingleColumnRowMapper<>(requiredType);
    }

    @NotNull
    private <T> RowMapper<T> getBeanPropertyRowMapper(@NotNull Class<T> requiredType) {
        return new BeanPropertyRowMapper<>(requiredType);
    }

    @NotNull
    private RowMapper<Map<String, Object>> getColumnMapRowMapper() {
        return new ColumnMapRowMapper();
    }

    @Override
    public int execute(@NotNull String sql, @Nullable Map<String, Object> params) {
        return jdbcOperations.update(sql, requireNonNullElse(params, Map.of()));
    }

    @Override
    public final int execute(@NotNull NamedSql<?> sql) {
        return execute(sql.getSql(), sql.getArgs());
    }

    @NotNull
    @Override
    public int[] executeBatch(@NotNull String sql, Map<String, Object>[] paramsList) {
        return jdbcOperations.batchUpdate(sql, paramsList);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull String sql, Map<String, Object> params, RowMapper<T> mapper) {
        return jdbcOperations.query(sql, requireNonNullElse(params, Map.of()), mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull NamedSql<?> sql, @NotNull RowMapper<T> mapper) {
        return this.queryList(sql.getSql(), sql.getArgs(), mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryList(sql, params, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryList(sql, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(@NotNull String sql, Map<String, Object> params) {
        return queryList(sql, params, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(@NotNull NamedSql<?> sql) {
        return queryList(sql, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(@NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryList(sql, params, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(@NotNull NamedSql<?> sql, Class<T> type) {
        return queryList(sql, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull String sql, Map<String, Object> params, @NotNull RowMapper<T> mapper) {
        sql = sql + this.jdbcDialect.limit().getLimitOffset(limit, offset);
        return queryList(sql, params, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull NamedSql<?> sql, RowMapper<T> mapper) {
        return queryList(offset, limit, sql.getSql(), sql.getArgs(), mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, params, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(long offset, int limit, @NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int limit, @NotNull String sql, Map<String, Object> params) {
        return queryList(offset, limit, sql, params, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(long offset, int limit, @NotNull NamedSql<?> sql) {
        return queryList(offset, limit, sql, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(long offset, int limit, @NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, params, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(long offset, int limit, @NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryList(offset, limit, sql, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull String sql, Map<String, Object> params, @NotNull RowMapper<T> mapper) {
        return queryList(0, limit, sql, params, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull NamedSql<?> sql, @NotNull RowMapper<T> mapper) {
        return queryList(0, limit, sql, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryList(0, limit, sql, params, type);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryList(0, limit, sql, type);
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @NotNull String sql, Map<String, Object> params) {
        return queryListMap(0, limit, sql, params);
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @NotNull NamedSql<?> sql) {
        return queryListMap(0, limit, sql);
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int limit, @NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryListSingle(0, limit, sql, params, type);
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int limit, @NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryListSingle(0, limit, sql, type);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull String sql, Map<String, Object> params, @NotNull RowMapper<T> mapper) {
        return queryList(1, sql, params, mapper).stream().findFirst().orElse(null);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull NamedSql<?> sql, @NotNull RowMapper<T> mapper) {
        return queryObject(sql.getSql(), sql.getArgs(), mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryObject(sql, params, getBeanPropertyRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryObject(sql, getBeanPropertyRowMapper(type));
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@NotNull String sql, Map<String, Object> params) {
        return queryObject(sql, params, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@NotNull NamedSql<?> sql) {
        return queryObject(sql, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryObject(sql, params, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryObject(sql, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final String queryString(String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, String.class);
    }

    @Nullable
    @Override
    public final String queryString(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, String.class);
    }

    @Nullable
    @Override
    public final Long queryLong(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Long.class);
    }

    @Nullable
    @Override
    public final Long queryLong(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Long.class);
    }

    @Nullable
    @Override
    public final Integer queryInt(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Integer.class);
    }

    @Nullable
    @Override
    public final Integer queryInt(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Integer.class);
    }

    @Nullable
    @Override
    public final Short queryShort(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Short.class);
    }

    @Nullable
    @Override
    public final Short queryShort(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Short.class);
    }

    @Nullable
    @Override
    public final Byte queryByte(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Byte.class);
    }

    @Nullable
    @Override
    public final Byte queryByte(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Byte.class);
    }

    @Nullable
    @Override
    public final Double queryDouble(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Double.class);
    }

    @Nullable
    @Override
    public final Double queryDouble(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Double.class);
    }

    @Nullable
    @Override
    public final Float queryFloat(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Float.class);
    }

    @Nullable
    @Override
    public final Float queryFloat(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Float.class);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Boolean.class);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Boolean.class);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Timestamp.class);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Timestamp.class);
    }

    @Nullable
    @Override
    public final Date queryDate(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Date.class);
    }

    @Nullable
    @Override
    public final Date queryDate(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Date.class);
    }

    @Nullable
    @Override
    public final Time queryTime(@NotNull String sql, Map<String, Object> params) {
        return queryObjectSingle(sql, params, Time.class);
    }

    @Nullable
    @Override
    public final Time queryTime(@NotNull NamedSql<?> sql) {
        return queryObjectSingle(sql, Time.class);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, @NotNull String sql, Map<String, Object> params, @NotNull RowMapper<T> mapper) {
        final List<T> content = this.queryList(pageable.getOffset(), pageable.getPageSize(), sql, params, mapper);
        long total = ofNullable(queryLong(format("SELECT COUNT(*) FROM (%s) ", sql), params)).orElse(0L);
        return new PageImpl<>(content, pageable, total);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, @NotNull NamedSql<?> sql, @NotNull RowMapper<T> mapper) {
        return queryPage(pageable, sql.getSql(), sql.getArgs(), mapper);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryPage(pageable, sql, params, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(@NotNull Pageable pageable, @NotNull NamedSql<?> sql, Class<T> type) {
        return queryPage(pageable, sql, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final Page<Map<String, Object>> queryPageMap(@NotNull Pageable pageable, @NotNull String sql, Map<String, Object> params) {
        return queryPage(pageable, sql, params, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final Page<Map<String, Object>> queryPageMap(@NotNull Pageable pageable, @NotNull NamedSql<?> sql) {
        return queryPage(pageable, sql, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPageSingle(@NotNull Pageable pageable, @NotNull String sql, Map<String, Object> params, @NotNull Class<T> type) {
        return queryPage(pageable, sql, params, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPageSingle(@NotNull Pageable pageable, @NotNull NamedSql<?> sql, @NotNull Class<T> type) {
        return queryPage(pageable, sql, getSingleColumnRowMapper(type));
    }
}
