package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.Mapper;
import com.mini.core.jdbc.model.Page;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao implements JdbcInterface {
    protected abstract JdbcTemplate writeTemplate();

    protected abstract JdbcTemplate readTemplate();

    @Override
    public int execute(@Nonnull String sql, Object... params) {
        return writeTemplate().execute(sql, params);
    }

    @Override
    public int execute(@Nonnull SQLBuilder builder) {
        return writeTemplate().execute(builder);
    }

    @Override
    public int[] executeBatch(@Nonnull String... sql) {
        return writeTemplate().executeBatch(sql);
    }

    @Override
    public int[] executeBatch(@Nonnull String sql, Object[]... paramsArray) {
        return writeTemplate().executeBatch(sql, paramsArray);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(@Nonnull String sql, Mapper<T> mapper, Object... params) {
        return readTemplate().queryList(sql, mapper, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(@Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return readTemplate().queryList(builder, mapper);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryList(sql, type, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(@Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryList(builder, type);
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> queryListMap(@Nonnull String sql, Object... params) {
        return readTemplate().queryListMap(sql, params);
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> queryListMap(@Nonnull SQLBuilder builder) {
        return readTemplate().queryListMap(builder);
    }

    @Nonnull
    @Override
    public <T> List<T> queryListSingle(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryListSingle(sql, type, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryListSingle(@Nonnull SQLBuilder builder, Class<T> type) {
        return readTemplate().queryListSingle(builder, type);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int start, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return readTemplate().queryList(sql, mapper, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int start, int limit, @Nonnull SQLBuilder builder, Mapper<T> mapper) {
        return readTemplate().queryList(start, limit, builder, mapper);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int start, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryList(start, limit, sql, type, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int start, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryList(start, limit, builder, type);
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> queryListMap(int start, int limit, @Nonnull String sql, Object... params) {
        return readTemplate().queryListMap(start, limit, sql, params);
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> queryListMap(int start, int limit, @Nonnull SQLBuilder builder) {
        return readTemplate().queryListMap(start, limit, builder);
    }

    @Nonnull
    @Override
    public <T> List<T> queryListSingle(int start, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryListSingle(start, limit, sql, type, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryListSingle(int start, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryListSingle(start, limit, builder, type);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return readTemplate().queryList(limit, sql, mapper, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return readTemplate().queryList(limit, builder, mapper);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int limit, String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryList(limit, sql, type, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryList(limit, builder, type);
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> queryListMap(int limit, @Nonnull String sql, Object... params) {
        return readTemplate().queryListMap(limit, sql, params);
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> queryListMap(int limit, @Nonnull SQLBuilder builder) {
        return readTemplate().queryListMap(limit, builder);
    }

    @Nonnull
    @Override
    public <T> List<T> queryListSingle(int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryListSingle(limit, sql, type, params);
    }

    @Nonnull
    @Override
    public <T> List<T> queryListSingle(int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryListSingle(limit, builder, type);
    }

    @Nullable
    @Override
    public <T> T queryObject(@Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return readTemplate().queryObject(sql, mapper, params);
    }

    @Nullable
    @Override
    public <T> T queryObject(@Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return readTemplate().queryObject(builder, mapper);
    }

    @Nullable
    @Override
    public <T> T queryObject(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryObject(sql, type, params);
    }

    @Nullable
    @Override
    public <T> T queryObject(@Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryObject(builder, type);
    }

    @Nullable
    @Override
    public Map<String, Object> queryObjectMap(@Nonnull String sql, Object... params) {
        return readTemplate().queryObjectMap(sql, params);
    }

    @Nullable
    @Override
    public Map<String, Object> queryObjectMap(@Nonnull SQLBuilder builder) {
        return readTemplate().queryObjectMap(builder);
    }

    @Nullable
    @Override
    public <T> T queryObjectSingle(@Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryObjectSingle(sql, type, params);
    }

    @Nullable
    @Override
    public <T> T queryObjectSingle(@Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryObjectSingle(builder, type);
    }

    @Nullable
    @Override
    public String queryString(String sql, Object... params) {
        return readTemplate().queryString(sql, params);
    }

    @Nullable
    @Override
    public String queryString(@Nonnull SQLBuilder builder) {
        return readTemplate().queryString(builder);
    }

    @Nullable
    @Override
    public Long queryLong(@Nonnull String sql, Object... params) {
        return readTemplate().queryLong(sql, params);
    }

    @Nullable
    @Override
    public Long queryLong(@Nonnull SQLBuilder builder) {
        return readTemplate().queryLong(builder);
    }

    @Nullable
    @Override
    public Integer queryInt(@Nonnull String sql, Object... params) {
        return readTemplate().queryInt(sql, params);
    }

    @Nullable
    @Override
    public Integer queryInt(@Nonnull SQLBuilder builder) {
        return readTemplate().queryInt(builder);
    }

    @Nullable
    @Override
    public Short queryShort(@Nonnull String sql, Object... params) {
        return readTemplate().queryShort(sql, params);
    }

    @Nullable
    @Override
    public Short queryShort(@Nonnull SQLBuilder builder) {
        return readTemplate().queryShort(builder);
    }

    @Nullable
    @Override
    public Byte queryByte(@Nonnull String sql, Object... params) {
        return readTemplate().queryByte(sql, params);
    }

    @Nullable
    @Override
    public Byte queryByte(@Nonnull SQLBuilder builder) {
        return readTemplate().queryByte(builder);
    }

    @Nullable
    @Override
    public Double queryDouble(@Nonnull String sql, Object... params) {
        return readTemplate().queryDouble(sql, params);
    }

    @Nullable
    @Override
    public Double queryDouble(@Nonnull SQLBuilder builder) {
        return readTemplate().queryDouble(builder);
    }

    @Nullable
    @Override
    public Float queryFloat(@Nonnull String sql, Object... params) {
        return readTemplate().queryFloat(sql, params);
    }

    @Nullable
    @Override
    public Float queryFloat(@Nonnull SQLBuilder builder) {
        return readTemplate().queryFloat(builder);
    }

    @Nullable
    @Override
    public Boolean queryBoolean(@Nonnull String sql, Object... params) {
        return readTemplate().queryBoolean(sql, params);
    }

    @Nullable
    @Override
    public Boolean queryBoolean(@Nonnull SQLBuilder builder) {
        return readTemplate().queryBoolean(builder);
    }

    @Nullable
    @Override
    public Timestamp queryTimestamp(@Nonnull String sql, Object... params) {
        return readTemplate().queryTimestamp(sql, params);
    }

    @Nullable
    @Override
    public Timestamp queryTimestamp(@Nonnull SQLBuilder builder) {
        return readTemplate().queryTimestamp(builder);
    }

    @Nullable
    @Override
    public Date queryDate(@Nonnull String sql, Object... params) {
        return readTemplate().queryDate(sql, params);
    }

    @Nullable
    @Override
    public Date queryDate(@Nonnull SQLBuilder builder) {
        return readTemplate().queryDate(builder);
    }

    @Nullable
    @Override
    public Time queryTime(@Nonnull String sql, Object... params) {
        return readTemplate().queryTime(sql, params);
    }

    @Nullable
    @Override
    public Time queryTime(@Nonnull SQLBuilder builder) {
        return readTemplate().queryTime(builder);
    }

    @Nonnull
    @Override
    public <T> Page<T> queryPage(int page, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return readTemplate().queryPage(page, limit, sql, mapper, params);
    }

    @Nonnull
    @Override
    public <T> Page<T> queryPage(int page, int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return readTemplate().queryPage(page, limit, builder, mapper);
    }

    @Nonnull
    @Override
    public <T> Page<T> queryPage(int page, int limit, String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryPage(page, limit, sql, type, params);
    }

    @Nonnull
    @Override
    public <T> Page<T> queryPage(int page, int limit, @Nonnull SQLBuilder builder, Class<T> type) {
        return readTemplate().queryPage(page, limit, builder, type);
    }

    @Nonnull
    @Override
    public Page<Map<String, Object>> queryPageMap(int page, int limit, @Nonnull String sql, Object... params) {
        return readTemplate().queryPageMap(page, limit, sql, params);
    }

    @Nonnull
    @Override
    public Page<Map<String, Object>> queryPageMap(int page, int limit, @Nonnull SQLBuilder builder) {
        return readTemplate().queryPageMap(page, limit, builder);
    }

    @Nonnull
    @Override
    public <T> Page<T> queryPageSingle(int page, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params) {
        return readTemplate().queryPageSingle(page, limit, sql, type, params);
    }

    @Nonnull
    @Override
    public <T> Page<T> queryPageSingle(int page, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type) {
        return readTemplate().queryPageSingle(page, limit, builder, type);
    }
}