package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.Mapper;
import com.mini.core.jdbc.model.Page;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public final int execute(@Nonnull String sql, Object... params) {
        return executeUpdate(sql, params);
    }

    @Override
    public final int execute(@Nonnull SQLBuilder builder) {
        return executeUpdate(builder);
    }

    @Nonnull
    @Override
    public final int[] executeBatch(@Nonnull String... sql) {
        return super.batchUpdate(sql);
    }

    @Nonnull
    @Override
    public final int[] executeBatch(@Nonnull String sql, Object[]... paramsArray) {
        return super.batchUpdate(sql, paramsArray);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(@Nonnull String sql, Mapper<T> mapper, Object... params) {
        return executeQueryList(sql, mapper, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(@Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return executeQueryList(builder, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryList(sql, getBeanPropertyMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(@Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryList(builder, getBeanPropertyMapper(type));
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(@Nonnull String sql, Object... params) {
        return executeQueryList(sql, getColumnMapMapper(), params);
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(@Nonnull SQLBuilder builder) {
        return executeQueryList(builder, getColumnMapMapper());
    }

    @Nonnull
    @Override
    public final <T> List<T> queryListSingle(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryList(sql, getSingleColumnMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryListSingle(@Nonnull SQLBuilder builder, Class<T> type) {
        return executeQueryList(builder, getSingleColumnMapper(type));
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int start, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return executeQueryList(start, limit, sql, mapper, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int start, int limit, @Nonnull SQLBuilder builder, Mapper<T> mapper) {
        return executeQueryList(start, limit, builder, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int start, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryList(start, limit, sql, getBeanPropertyMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int start, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryList(start, limit, builder, getBeanPropertyMapper(type));
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(int start, int limit, @Nonnull String sql, Object... params) {
        return executeQueryList(start, limit, sql, getColumnMapMapper(), params);
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(int start, int limit, @Nonnull SQLBuilder builder) {
        return executeQueryList(start, limit, builder, getColumnMapMapper());
    }

    @Nonnull
    @Override
    public final <T> List<T> queryListSingle(int start, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryList(start, limit, sql, getSingleColumnMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryListSingle(int start, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryList(start, limit, builder, getSingleColumnMapper(type));
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return executeQueryList(0, limit, sql, mapper, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return executeQueryList(0, limit, builder, mapper);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int limit, String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryList(0, limit, sql, getBeanPropertyMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryList(0, limit, builder, getBeanPropertyMapper(type));
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @Nonnull String sql, Object... params) {
        return executeQueryList(0, limit, sql, getColumnMapMapper(), params);
    }

    @Nonnull
    @Override
    public final List<Map<String, Object>> queryListMap(int limit, @Nonnull SQLBuilder builder) {
        return executeQueryList(0, limit, builder, getColumnMapMapper());
    }

    @Nonnull
    @Override
    public final <T> List<T> queryListSingle(int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryList(0, limit, sql, getSingleColumnMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryListSingle(int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryList(0, limit, builder, getSingleColumnMapper(type));
    }

    @Nullable
    @Override
    public final <T> T queryObject(@Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return executeQueryObject(sql, mapper, params);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return executeQueryObject(builder, mapper);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryObject(sql, getBeanPropertyMapper(type), params);
    }

    @Nullable
    @Override
    public final <T> T queryObject(@Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryObject(builder, getBeanPropertyMapper(type));
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@Nonnull String sql, Object... params) {
        return executeQueryObject(sql, getColumnMapMapper(), params);
    }

    @Nullable
    @Override
    public final Map<String, Object> queryObjectMap(@Nonnull SQLBuilder builder) {
        return executeQueryObject(builder, getColumnMapMapper());
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryObject(sql, getSingleColumnMapper(type), params);
    }

    @Nullable
    @Override
    public final <T> T queryObjectSingle(@Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryObject(builder, getSingleColumnMapper(type));
    }

    @Nullable
    @Override
    public final String queryString(String sql, Object... params) {
        return queryObjectSingle(sql, String.class, params);
    }

    @Nullable
    @Override
    public final String queryString(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, String.class);
    }

    @Nullable
    @Override
    public final Long queryLong(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Long.class, params);
    }

    @Nullable
    @Override
    public final Long queryLong(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Long.class);
    }

    @Nullable
    @Override
    public final Integer queryInt(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Integer.class, params);
    }

    @Nullable
    @Override
    public final Integer queryInt(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Integer.class);
    }

    @Nullable
    @Override
    public final Short queryShort(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Short.class, params);
    }

    @Nullable
    @Override
    public final Short queryShort(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Short.class);
    }

    @Nullable
    @Override
    public final Byte queryByte(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Byte.class, params);
    }

    @Nullable
    @Override
    public final Byte queryByte(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Byte.class);
    }

    @Nullable
    @Override
    public final Double queryDouble(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Double.class, params);
    }

    @Nullable
    @Override
    public final Double queryDouble(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Double.class);
    }

    @Nullable
    @Override
    public final Float queryFloat(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Float.class, params);
    }

    @Nullable
    @Override
    public final Float queryFloat(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Float.class);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Boolean.class, params);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Boolean.class);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Timestamp.class, params);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Timestamp.class);
    }

    @Nullable
    @Override
    public final Date queryDate(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Date.class, params);
    }

    @Nullable
    @Override
    public final Date queryDate(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Date.class);
    }

    @Nullable
    @Override
    public final Time queryTime(@Nonnull String sql, Object... params) {
        return queryObjectSingle(sql, Time.class, params);
    }

    @Nullable
    @Override
    public final Time queryTime(@Nonnull SQLBuilder builder) {
        return queryObjectSingle(builder, Time.class);
    }

    @Nonnull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return executeQueryPage(page, limit, sql, mapper, params);
    }

    @Nonnull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return executeQueryPage(page, limit, builder, mapper);
    }

    @Nonnull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryPage(page, limit, sql, getBeanPropertyMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> Page<T> queryPage(int page, int limit, @Nonnull SQLBuilder builder, Class<T> type) {
        return executeQueryPage(page, limit, builder, getBeanPropertyMapper(type));
    }

    @Nonnull
    @Override
    public final Page<Map<String, Object>> queryPageMap(int page, int limit, @Nonnull String sql, Object... params) {
        return executeQueryPage(page, limit, sql, getColumnMapMapper(), params);
    }

    @Nonnull
    @Override
    public final Page<Map<String, Object>> queryPageMap(int page, int limit, @Nonnull SQLBuilder builder) {
        return executeQueryPage(page, limit, builder, getColumnMapMapper());
    }

    @Nonnull
    @Override
    public final <T> Page<T> queryPageSingle(int page, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return executeQueryPage(page, limit, sql, getSingleColumnMapper(type), params);
    }

    @Nonnull
    @Override
    public final <T> Page<T> queryPageSingle(int page, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return executeQueryPage(page, limit, builder, getSingleColumnMapper(type));
    }
}