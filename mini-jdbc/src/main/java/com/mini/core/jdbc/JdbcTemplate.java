package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.model.Page;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作对象
 *
 * @author xchao
 */
public abstract class JdbcTemplate extends JdbcAccessor implements JdbcInterface {

    public JdbcTemplate() {
    }

    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public JdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    @Override
    public final int execute(@NotNull String sql, Object... params) {
        return executeUpdate(sql, params);
    }

    @Override
    public final int execute(@NotNull SQLBuilder builder) {
        return executeUpdate(builder);
    }

    @NotNull
    @Override
    public final int[] executeBatch(@NotNull String... sql) {
        return super.batchUpdate(sql);
    }

    @NotNull
    @Override
    public final int[] executeBatch(@NotNull String sql, Object[]... paramsArray) {
        return super.batchUpdate(sql, paramsArray);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull String sql, RowMapper<T> mapper, Object... params) {
        return executeQueryList(sql, mapper, params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return executeQueryList(builder, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryList(sql, getBeanPropertyRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(@NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryList(builder, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(@NotNull String sql, Object... params) {
        return executeQueryList(sql, getColumnMapRowMapper(), params);
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(@NotNull SQLBuilder builder) {
        return executeQueryList(builder, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryList(sql, getSingleColumnRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(@NotNull SQLBuilder builder, Class<T> type) {
        return executeQueryList(builder, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int start, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return executeQueryList(start, limit, sql, mapper, params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int start, int limit, @NotNull SQLBuilder builder, RowMapper<T> mapper) {
        return executeQueryList(start, limit, builder, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int start, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryList(start, limit, sql, getBeanPropertyRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int start, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryList(start, limit, builder, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int start, int limit, @NotNull String sql, Object... params) {
        return executeQueryList(start, limit, sql, getColumnMapRowMapper(), params);
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int start, int limit, @NotNull SQLBuilder builder) {
        return executeQueryList(start, limit, builder, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int start, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryList(start, limit, sql, getSingleColumnRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int start, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryList(start, limit, builder, getSingleColumnRowMapper(type));
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return executeQueryList(0, limit, sql, mapper, params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return executeQueryList(0, limit, builder, mapper);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryList(0, limit, sql, getBeanPropertyRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryList(int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryList(0, limit, builder, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @NotNull String sql, Object... params) {
        return executeQueryList(0, limit, sql, getColumnMapRowMapper(), params);
    }

    @NotNull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @NotNull SQLBuilder builder) {
        return executeQueryList(0, limit, builder, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryList(0, limit, sql, getSingleColumnRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> List<T> queryListSingle(int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryList(0, limit, builder, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return executeQueryObject(sql, mapper, params);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return executeQueryObject(builder, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryObject(sql, getBeanPropertyRowMapper(type), params);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryObject(builder, getBeanPropertyRowMapper(type));
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@NotNull String sql, Object... params) {
        return executeQueryObject(sql, getColumnMapRowMapper(), params);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@NotNull SQLBuilder builder) {
        return executeQueryObject(builder, getColumnMapRowMapper());
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryObject(sql, getSingleColumnRowMapper(type), params);
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryObject(builder, getSingleColumnRowMapper(type));
    }

    @Nullable
    @Override
    public final String queryString(String sql, Object... params) {
        return queryObjectSingle(sql, String.class, params);
    }

    @Nullable
    @Override
    public final String queryString(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, String.class);
    }

    @Nullable
    @Override
    public final Long queryLong(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Long.class, params);
    }

    @Nullable
    @Override
    public final Long queryLong(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Long.class);
    }

    @Nullable
    @Override
    public final Integer queryInt(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Integer.class, params);
    }

    @Nullable
    @Override
    public final Integer queryInt(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Integer.class);
    }

    @Nullable
    @Override
    public final Short queryShort(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Short.class, params);
    }

    @Nullable
    @Override
    public final Short queryShort(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Short.class);
    }

    @Nullable
    @Override
    public final Byte queryByte(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Byte.class, params);
    }

    @Nullable
    @Override
    public final Byte queryByte(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Byte.class);
    }

    @Nullable
    @Override
    public final Double queryDouble(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Double.class, params);
    }

    @Nullable
    @Override
    public final Double queryDouble(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Double.class);
    }

    @Nullable
    @Override
    public final Float queryFloat(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Float.class, params);
    }

    @Nullable
    @Override
    public final Float queryFloat(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Float.class);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Boolean.class, params);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Boolean.class);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Timestamp.class, params);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Timestamp.class);
    }

    @Nullable
    @Override
    public final Date queryDate(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Date.class, params);
    }

    @Nullable
    @Override
    public final Date queryDate(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Date.class);
    }

    @Nullable
    @Override
    public final Time queryTime(@NotNull String sql, Object... params) {
        return queryObjectSingle(sql, Time.class, params);
    }

    @Nullable
    @Override
    public final Time queryTime(@NotNull SQLBuilder builder) {
        return queryObjectSingle(builder, Time.class);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return executeQueryPage(page, limit, sql, mapper, params);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return executeQueryPage(page, limit, builder, mapper);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryPage(page, limit, sql, getBeanPropertyRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, @NotNull SQLBuilder builder, Class<T> type) {
        return executeQueryPage(page, limit, builder, getBeanPropertyRowMapper(type));
    }

    @NotNull
    @Override
    public final Page<Map<String, Object>> queryPageMap(int page, int limit, @NotNull String sql, Object... params) {
        return executeQueryPage(page, limit, sql, getColumnMapRowMapper(), params);
    }

    @NotNull
    @Override
    public final Page<Map<String, Object>> queryPageMap(int page, int limit, @NotNull SQLBuilder builder) {
        return executeQueryPage(page, limit, builder, getColumnMapRowMapper());
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPageSingle(int page, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return executeQueryPage(page, limit, sql, getSingleColumnRowMapper(type), params);
    }

    @NotNull
    @Override
    public final <T> Page<T> queryPageSingle(int page, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return executeQueryPage(page, limit, builder, getSingleColumnRowMapper(type));
    }
}