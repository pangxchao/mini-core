package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.model.Page;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao implements JdbcInterface {
    protected abstract JdbcTemplate writeTemplate();

    protected abstract JdbcTemplate readTemplate();

    @Override
    public int execute(@NotNull String sql, Object... params) {
        return writeTemplate().execute(sql, params);
    }

    @Override
    public int execute(@NotNull SQLBuilder builder) {
        return writeTemplate().execute(builder);
    }

    @NotNull
    @Override
    public int[] executeBatch(@NotNull String... sql) {
        return writeTemplate().executeBatch(sql);
    }

    @NotNull
    @Override
    public int[] executeBatch(@NotNull String sql, Object[]... paramsArray) {
        return writeTemplate().executeBatch(sql, paramsArray);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(@NotNull String sql, RowMapper<T> mapper, Object... params) {
        return readTemplate().queryList(sql, mapper, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(@NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return readTemplate().queryList(builder, mapper);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryList(sql, type, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(@NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryList(builder, type);
    }

    @NotNull
    @Override
    public List<Map<String, Object>> queryListMap(@NotNull String sql, Object... params) {
        return readTemplate().queryListMap(sql, params);
    }

    @NotNull
    @Override
    public List<Map<String, Object>> queryListMap(@NotNull SQLBuilder builder) {
        return readTemplate().queryListMap(builder);
    }

    @NotNull
    @Override
    public <T> List<T> queryListSingle(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryListSingle(sql, type, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryListSingle(@NotNull SQLBuilder builder, Class<T> type) {
        return readTemplate().queryListSingle(builder, type);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int start, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return readTemplate().queryList(sql, mapper, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int start, int limit, @NotNull SQLBuilder builder, RowMapper<T> mapper) {
        return readTemplate().queryList(start, limit, builder, mapper);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int start, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryList(start, limit, sql, type, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int start, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryList(start, limit, builder, type);
    }

    @NotNull
    @Override
    public List<Map<String, Object>> queryListMap(int start, int limit, @NotNull String sql, Object... params) {
        return readTemplate().queryListMap(start, limit, sql, params);
    }

    @NotNull
    @Override
    public List<Map<String, Object>> queryListMap(int start, int limit, @NotNull SQLBuilder builder) {
        return readTemplate().queryListMap(start, limit, builder);
    }

    @NotNull
    @Override
    public <T> List<T> queryListSingle(int start, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryListSingle(start, limit, sql, type, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryListSingle(int start, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryListSingle(start, limit, builder, type);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return readTemplate().queryList(limit, sql, mapper, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return readTemplate().queryList(limit, builder, mapper);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int limit, String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryList(limit, sql, type, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryList(int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryList(limit, builder, type);
    }

    @NotNull
    @Override
    public List<Map<String, Object>> queryListMap(int limit, @NotNull String sql, Object... params) {
        return readTemplate().queryListMap(limit, sql, params);
    }

    @NotNull
    @Override
    public List<Map<String, Object>> queryListMap(int limit, @NotNull SQLBuilder builder) {
        return readTemplate().queryListMap(limit, builder);
    }

    @NotNull
    @Override
    public <T> List<T> queryListSingle(int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryListSingle(limit, sql, type, params);
    }

    @NotNull
    @Override
    public <T> List<T> queryListSingle(int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryListSingle(limit, builder, type);
    }

    @Nullable
    @Override
    public <T> T queryObject(@NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return readTemplate().queryObject(sql, mapper, params);
    }

    @Nullable
    @Override
    public <T> T queryObject(@NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return readTemplate().queryObject(builder, mapper);
    }

    @Nullable
    @Override
    public <T> T queryObject(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryObject(sql, type, params);
    }

    @Nullable
    @Override
    public <T> T queryObject(@NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryObject(builder, type);
    }

    @Nullable
    @Override
    public Map<String, Object> queryObjectMap(@NotNull String sql, Object... params) {
        return readTemplate().queryObjectMap(sql, params);
    }

    @Nullable
    @Override
    public Map<String, Object> queryObjectMap(@NotNull SQLBuilder builder) {
        return readTemplate().queryObjectMap(builder);
    }

    @Nullable
    @Override
    public <T> T queryObjectSingle(@NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryObjectSingle(sql, type, params);
    }

    @Nullable
    @Override
    public <T> T queryObjectSingle(@NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryObjectSingle(builder, type);
    }

    @Nullable
    @Override
    public String queryString(String sql, Object... params) {
        return readTemplate().queryString(sql, params);
    }

    @Nullable
    @Override
    public String queryString(@NotNull SQLBuilder builder) {
        return readTemplate().queryString(builder);
    }

    @Nullable
    @Override
    public Long queryLong(@NotNull String sql, Object... params) {
        return readTemplate().queryLong(sql, params);
    }

    @Nullable
    @Override
    public Long queryLong(@NotNull SQLBuilder builder) {
        return readTemplate().queryLong(builder);
    }

    @Nullable
    @Override
    public Integer queryInt(@NotNull String sql, Object... params) {
        return readTemplate().queryInt(sql, params);
    }

    @Nullable
    @Override
    public Integer queryInt(@NotNull SQLBuilder builder) {
        return readTemplate().queryInt(builder);
    }

    @Nullable
    @Override
    public Short queryShort(@NotNull String sql, Object... params) {
        return readTemplate().queryShort(sql, params);
    }

    @Nullable
    @Override
    public Short queryShort(@NotNull SQLBuilder builder) {
        return readTemplate().queryShort(builder);
    }

    @Nullable
    @Override
    public Byte queryByte(@NotNull String sql, Object... params) {
        return readTemplate().queryByte(sql, params);
    }

    @Nullable
    @Override
    public Byte queryByte(@NotNull SQLBuilder builder) {
        return readTemplate().queryByte(builder);
    }

    @Nullable
    @Override
    public Double queryDouble(@NotNull String sql, Object... params) {
        return readTemplate().queryDouble(sql, params);
    }

    @Nullable
    @Override
    public Double queryDouble(@NotNull SQLBuilder builder) {
        return readTemplate().queryDouble(builder);
    }

    @Nullable
    @Override
    public Float queryFloat(@NotNull String sql, Object... params) {
        return readTemplate().queryFloat(sql, params);
    }

    @Nullable
    @Override
    public Float queryFloat(@NotNull SQLBuilder builder) {
        return readTemplate().queryFloat(builder);
    }

    @Nullable
    @Override
    public Boolean queryBoolean(@NotNull String sql, Object... params) {
        return readTemplate().queryBoolean(sql, params);
    }

    @Nullable
    @Override
    public Boolean queryBoolean(@NotNull SQLBuilder builder) {
        return readTemplate().queryBoolean(builder);
    }

    @Nullable
    @Override
    public Timestamp queryTimestamp(@NotNull String sql, Object... params) {
        return readTemplate().queryTimestamp(sql, params);
    }

    @Nullable
    @Override
    public Timestamp queryTimestamp(@NotNull SQLBuilder builder) {
        return readTemplate().queryTimestamp(builder);
    }

    @Nullable
    @Override
    public Date queryDate(@NotNull String sql, Object... params) {
        return readTemplate().queryDate(sql, params);
    }

    @Nullable
    @Override
    public Date queryDate(@NotNull SQLBuilder builder) {
        return readTemplate().queryDate(builder);
    }

    @Nullable
    @Override
    public Time queryTime(@NotNull String sql, Object... params) {
        return readTemplate().queryTime(sql, params);
    }

    @Nullable
    @Override
    public Time queryTime(@NotNull SQLBuilder builder) {
        return readTemplate().queryTime(builder);
    }

    @NotNull
    @Override
    public <T> Page<T> queryPage(int page, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return readTemplate().queryPage(page, limit, sql, mapper, params);
    }

    @NotNull
    @Override
    public <T> Page<T> queryPage(int page, int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return readTemplate().queryPage(page, limit, builder, mapper);
    }

    @NotNull
    @Override
    public <T> Page<T> queryPage(int page, int limit, String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryPage(page, limit, sql, type, params);
    }

    @NotNull
    @Override
    public <T> Page<T> queryPage(int page, int limit, @NotNull SQLBuilder builder, Class<T> type) {
        return readTemplate().queryPage(page, limit, builder, type);
    }

    @NotNull
    @Override
    public Page<Map<String, Object>> queryPageMap(int page, int limit, @NotNull String sql, Object... params) {
        return readTemplate().queryPageMap(page, limit, sql, params);
    }

    @NotNull
    @Override
    public Page<Map<String, Object>> queryPageMap(int page, int limit, @NotNull SQLBuilder builder) {
        return readTemplate().queryPageMap(page, limit, builder);
    }

    @NotNull
    @Override
    public <T> Page<T> queryPageSingle(int page, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params) {
        return readTemplate().queryPageSingle(page, limit, sql, type, params);
    }

    @NotNull
    @Override
    public <T> Page<T> queryPageSingle(int page, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type) {
        return readTemplate().queryPageSingle(page, limit, builder, type);
    }
}