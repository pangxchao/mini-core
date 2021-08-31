package com.mini.core.jdbc;

import com.mini.core.jdbc.mapper.MiniBeanRowMapper;
import com.mini.core.jdbc.wrapper.IndexWrapper;
import com.mini.core.jdbc.wrapper.NamedWrapper;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Nonnull;
import java.sql.DatabaseMetaData;
import java.util.*;

public class DefaultMiniJdbcTemplate implements MiniJdbcTemplate {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcOperations jdbcOperations;

    public DefaultMiniJdbcTemplate(@Nonnull final JdbcTemplate jdbcTemplate) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        jdbcOperations = namedJdbcTemplate.getJdbcOperations();
    }

    /**
     * 获取NamedParameterJdbcTemplate对象
     *
     * @return NamedParameterJdbcTemplate对象
     */
    public final NamedParameterJdbcTemplate getNamedJdbcTemplate() {
        return namedJdbcTemplate;
    }

    /**
     * 获取JdbcOperations对象
     *
     * @return JdbcOperations对象
     */
    public final JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }

    @Override
    public final <T> T execute(ConnectionCallback<T> callback) {
        return jdbcOperations.execute(callback);
    }

    @Override
    public final void execute(String sql) {
        jdbcOperations.execute(sql);
    }

    @Override
    @SneakyThrows
    public final boolean hasTable(String tableName) {
        return Objects.requireNonNullElse(this.execute(connection -> {
            final DatabaseMetaData md = connection.getMetaData();
            try (var rs = md.getTables(connection.getCatalog(),
                    connection.getSchema(), tableName, null)) {
                return rs.next();
            }
        }), false);

    }

    @Override
    @SneakyThrows
    public final boolean hasColumn(String tableName, String columnName) {
        return Objects.requireNonNullElse(this.execute(connection -> {
            final DatabaseMetaData md = connection.getMetaData();
            try (var rs = md.getColumns(connection.getCatalog(),
                    connection.getSchema(), tableName,
                    columnName)) {
                return rs.next();
            }
        }), false);
    }

    @Override
    public final int update(PreparedStatementCreator creator, KeyHolder keyHolder) {
        return jdbcOperations.update(creator, keyHolder);
    }

    @Override
    public final int update(String sql, Map<String, ?> params) {
        return namedJdbcTemplate.update(sql, params);
    }

    @Override
    public final int update(@Nonnull NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.update(wrapper);
    }

    @Override
    public final int update(String sql, Object... params) {
        return jdbcOperations.update(sql, params);
    }

    @Override
    public final int update(@Nonnull IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.update(wrapper);
    }

    @Nonnull
    @Override
    public final int[] executeBatch(String sql, Map<String, ?>[] paramsList) {
        return namedJdbcTemplate.batchUpdate(sql, paramsList);
    }

    @Nonnull
    @Override
    public final int[] executeBatch(String sql, List<Object[]> paramsList) {
        return jdbcOperations.batchUpdate(sql, paramsList);
    }

    @Nonnull
    @Override
    public final int[] executeBatch(String... sql) {
        return jdbcOperations.batchUpdate(sql);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(String sql, Map<String, ?> params, RowMapper<T> mapper) {
        return namedJdbcTemplate.query(sql, params, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(NamedWrapper wrapper, RowMapper<T> mapper) {
        return MiniJdbcTemplate.super.queryList(wrapper, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(String sql, Object[] params, RowMapper<T> mapper) {
        return jdbcOperations.query(sql, params, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(IndexWrapper wrapper, RowMapper<T> mapper) {
        return MiniJdbcTemplate.super.queryList(wrapper, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(String sql, Map<String, ?> params, Class<T> type) {
        return namedJdbcTemplate.query(sql, params, getBeanRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(NamedWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.queryList(wrapper, type);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(String sql, Object[] params, Class<T> type) {
        return jdbcOperations.query(sql, params, getBeanRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(IndexWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.queryList(wrapper, type);
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(String sql, Map<String, ?> params) {
        return namedJdbcTemplate.query(sql, params, getMapRowMapper());
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryListMap(wrapper);
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(String sql, Object[] params) {
        return jdbcOperations.query(sql, params, getMapRowMapper());
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryListMap(wrapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> querySingleList(String sql, Map<String, ?> params, Class<T> type) {
        return namedJdbcTemplate.query(sql, params, getSingleRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> List<T> querySingleList(NamedWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.querySingleList(wrapper, type);
    }

    @Nonnull
    @Override
    public final <T> List<T> querySingleList(String sql, Object[] params, Class<T> type) {
        return jdbcOperations.query(sql, params, getSingleRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> List<T> querySingleList(IndexWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.querySingleList(wrapper, type);
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(String sql, Map<String, ?> params, RowMapper<T> mapper) {
        return Optional.ofNullable(namedJdbcTemplate.query(sql, params, rs -> { //
            return rs.next() ? mapper.mapRow(rs, 0) : null;
        }));
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(NamedWrapper wrapper, RowMapper<T> mapper) {
        return MiniJdbcTemplate.super.queryOne(wrapper, mapper);
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(String sql, Object[] params, RowMapper<T> mapper) {
        return Optional.ofNullable(this.jdbcOperations.query(sql, params, rs -> { //
            return rs.next() ? mapper.mapRow(rs, 0) : null;
        }));
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(IndexWrapper wrapper, RowMapper<T> mapper) {
        return MiniJdbcTemplate.super.queryOne(wrapper, mapper);
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(String sql, Map<String, ?> params, Class<T> type) {
        return this.queryOne(sql, params, getBeanRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(NamedWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.queryOne(wrapper, type);
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(String sql, Object[] params, Class<T> type) {
        return this.queryOne(sql, params, getBeanRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> Optional<T> queryOne(IndexWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.queryOne(wrapper, type);
    }

    @Nonnull
    @Override
    public final Optional<Map<String, Object>> queryOneMap(String sql, Map<String, ?> params) {
        return this.queryOne(sql, params, getMapRowMapper());
    }

    @Nonnull
    @Override
    public final Optional<Map<String, Object>> queryOneMap(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryOneMap(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Map<String, Object>> queryOneMap(String sql, Object[] params) {
        return this.queryOne(sql, params, getMapRowMapper());
    }

    @Nonnull
    @Override
    public final Optional<Map<String, Object>> queryOneMap(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryOneMap(wrapper);
    }

    @Nonnull
    @Override
    public final <T> Optional<T> querySingleOne(String sql, Map<String, ?> params, Class<T> type) {
        return this.queryOne(sql, params, getSingleRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> Optional<T> querySingleOne(NamedWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.querySingleOne(wrapper, type);
    }

    @Nonnull
    @Override
    public final <T> Optional<T> querySingleOne(String sql, Object[] params, Class<T> type) {
        return this.queryOne(sql, params, getSingleRowMapper(type));
    }

    @Nonnull
    @Override
    public final <T> Optional<T> querySingleOne(IndexWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.querySingleOne(wrapper, type);
    }

    @Nonnull
    @Override
    public final Optional<String> queryString(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryString(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<String> queryString(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryString(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<String> queryString(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryString(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<String> queryString(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryString(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Long> queryLong(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryLong(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Long> queryLong(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryLong(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Long> queryLong(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryLong(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Long> queryLong(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryLong(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Integer> queryInt(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryInt(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Integer> queryInt(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryInt(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Integer> queryInt(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryInt(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Integer> queryInt(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryInt(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Short> queryShort(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryShort(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Short> queryShort(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryShort(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Short> queryShort(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryShort(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Short> queryShort(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryShort(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Byte> queryByte(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryByte(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Byte> queryByte(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryByte(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Byte> queryByte(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryByte(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Byte> queryByte(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryByte(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Double> queryDouble(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryDouble(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Double> queryDouble(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDouble(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Double> queryDouble(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryDouble(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Double> queryDouble(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDouble(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Float> queryFloat(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryFloat(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Float> queryFloat(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryFloat(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Float> queryFloat(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryFloat(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Float> queryFloat(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryFloat(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Boolean> queryBoolean(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryBoolean(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Boolean> queryBoolean(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryBoolean(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Boolean> queryBoolean(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryBoolean(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Boolean> queryBoolean(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryBoolean(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Date> queryDate(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryDate(sql, params);
    }

    @Nonnull
    @Override
    public final Optional<Date> queryDate(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDate(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Date> queryDate(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDate(wrapper);
    }

    @Nonnull
    @Override
    public final Optional<Date> queryDate(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryDate(sql, params);
    }

    @Nonnull
    protected <T> RowMapper<T> getSingleRowMapper(Class<T> requiredType) {
        return new SingleColumnRowMapper<>(requiredType);
    }

    @Nonnull
    protected <T> RowMapper<T> getBeanRowMapper(Class<T> requiredType) {
        return MiniBeanRowMapper.create(requiredType);
    }

    @Nonnull
    protected RowMapper<Map<String, Object>> getMapRowMapper() {
        return new ColumnMapRowMapper();
    }
}
