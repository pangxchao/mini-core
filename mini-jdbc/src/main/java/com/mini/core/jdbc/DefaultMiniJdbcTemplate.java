package com.mini.core.jdbc;

import com.mini.core.jdbc.mapper.MiniBeanRowMapper;
import com.mini.core.jdbc.wrapper.IndexWrapper;
import com.mini.core.jdbc.wrapper.NamedWrapper;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.DatabaseMetaData;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public <T> List<T> queryList(NamedWrapper wrapper, RowMapper<T> mapper) {
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

    @Nullable
    @Override
    public final <T> T queryOne(String sql, Map<String, ?> params, RowMapper<T> mapper) {
        return namedJdbcTemplate.query(sql, params, rs -> { //
            return rs.next() ? mapper.mapRow(rs, 0) : null;
        });
    }

    @Nullable
    @Override
    public final <T> T queryOne(NamedWrapper wrapper, RowMapper<T> mapper) {
        return MiniJdbcTemplate.super.queryOne(wrapper, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryOne(String sql, Object[] params, RowMapper<T> mapper) {
        return this.jdbcOperations.query(sql, params, rs -> { //
            return rs.next() ? mapper.mapRow(rs, 0) : null;
        });
    }

    @Nullable
    @Override
    public <T> T queryOne(IndexWrapper wrapper, RowMapper<T> mapper) {
        return MiniJdbcTemplate.super.queryOne(wrapper, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryOne(String sql, Map<String, ?> params, Class<T> type) {
        return this.queryOne(sql, params, getBeanRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryOne(NamedWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.queryOne(wrapper, type);
    }

    @Nullable
    @Override
    public final <T> T queryOne(String sql, Object[] params, Class<T> type) {
        return this.queryOne(sql, params, getBeanRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryOne(IndexWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.queryOne(wrapper, type);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryOneMap(String sql, Map<String, ?> params) {
        return this.queryOne(sql, params, getMapRowMapper());
    }

    @Nullable
    @Override
    public Map<String, Object> queryOneMap(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryOneMap(wrapper);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryOneMap(String sql, Object[] params) {
        return this.queryOne(sql, params, getMapRowMapper());
    }

    @Nullable
    @Override
    public final Map<String, Object> queryOneMap(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryOneMap(wrapper);
    }

    @Nullable
    @Override
    public final <T> T querySingleOne(String sql, Map<String, ?> params, Class<T> type) {
        return this.queryOne(sql, params, getSingleRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T querySingleOne(NamedWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.querySingleOne(wrapper, type);
    }

    @Nullable
    @Override
    public final <T> T querySingleOne(String sql, Object[] params, Class<T> type) {
        return this.queryOne(sql, params, getSingleRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T querySingleOne(IndexWrapper wrapper, Class<T> type) {
        return MiniJdbcTemplate.super.querySingleOne(wrapper, type);
    }

    @Nullable
    @Override
    public final String queryString(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryString(sql, params);
    }

    @Nullable
    @Override
    public final String queryString(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryString(wrapper);
    }

    @Nullable
    @Override
    public final String queryString(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryString(sql, params);
    }

    @Nullable
    @Override
    public final String queryString(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryString(wrapper);
    }

    @Nullable
    @Override
    public final Long queryLong(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryLong(sql, params);
    }

    @Nullable
    @Override
    public final Long queryLong(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryLong(wrapper);
    }

    @Nullable
    @Override
    public final Long queryLong(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryLong(sql, params);
    }

    @Nullable
    @Override
    public final Long queryLong(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryLong(wrapper);
    }

    @Nullable
    @Override
    public final Integer queryInt(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryInt(sql, params);
    }

    @Nullable
    @Override
    public final Integer queryInt(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryInt(wrapper);
    }

    @Nullable
    @Override
    public final Integer queryInt(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryInt(sql, params);
    }

    @Nullable
    @Override
    public final Integer queryInt(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryInt(wrapper);
    }

    @Nullable
    @Override
    public final Short queryShort(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryShort(sql, params);
    }

    @Nullable
    @Override
    public final Short queryShort(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryShort(wrapper);
    }

    @Nullable
    @Override
    public final Short queryShort(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryShort(sql, params);
    }

    @Nullable
    @Override
    public final Short queryShort(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryShort(wrapper);
    }

    @Nullable
    @Override
    public final Byte queryByte(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryByte(sql, params);
    }

    @Nullable
    @Override
    public final Byte queryByte(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryByte(wrapper);
    }

    @Nullable
    @Override
    public final Byte queryByte(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryByte(sql, params);
    }

    @Nullable
    @Override
    public final Byte queryByte(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryByte(wrapper);
    }

    @Nullable
    @Override
    public final Double queryDouble(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryDouble(sql, params);
    }

    @Nullable
    @Override
    public final Double queryDouble(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDouble(wrapper);
    }

    @Nullable
    @Override
    public final Double queryDouble(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryDouble(sql, params);
    }

    @Nullable
    @Override
    public final Double queryDouble(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDouble(wrapper);
    }

    @Nullable
    @Override
    public final Float queryFloat(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryFloat(sql, params);
    }

    @Nullable
    @Override
    public final Float queryFloat(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryFloat(wrapper);
    }

    @Nullable
    @Override
    public final Float queryFloat(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryFloat(sql, params);
    }

    @Nullable
    @Override
    public final Float queryFloat(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryFloat(wrapper);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryBoolean(sql, params);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryBoolean(wrapper);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(String sql, Object... params) {
        return MiniJdbcTemplate.super.queryBoolean(sql, params);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryBoolean(wrapper);
    }

    @Nullable
    @Override
    public final Date queryDate(String sql, Map<String, ?> params) {
        return MiniJdbcTemplate.super.queryDate(sql, params);
    }

    @Nullable
    @Override
    public final Date queryDate(NamedWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDate(wrapper);
    }

    @Nullable
    @Override
    public final Date queryDate(IndexWrapper wrapper) {
        return MiniJdbcTemplate.super.queryDate(wrapper);
    }

    @Nullable
    @Override
    public final Date queryDate(String sql, Object... params) {
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
