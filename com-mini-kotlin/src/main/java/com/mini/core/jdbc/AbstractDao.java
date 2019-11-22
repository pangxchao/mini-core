package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.IMapper;
import com.mini.core.jdbc.model.Paging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public abstract class AbstractDao implements JdbcInterface {

    protected abstract JdbcTemplate writeTemplate();

    protected abstract JdbcTemplate readTemplate();

    @Override
    public final int[] executeBatch(String str, PreparedStatementSetter setter) {
        return writeTemplate().executeBatch(str, setter);
    }

    @Override
    public final int execute(String str, Object... params) {
        return writeTemplate().execute(str, params);
    }

    @Override
    public final int execute(SQLBuilder builder) {
        return writeTemplate().execute(builder);
    }

    @Override
    public final int execute(HolderGenerated holder, String str, Object... params) {
        return writeTemplate().execute(holder, str, params);
    }

    @Override
    public final int execute(HolderGenerated holder, SQLBuilder builder) {
        return writeTemplate().execute(holder, builder);
    }

    @Override
    public final <T> T query(String str, ResultSetCallback<T> callback, Object... params) {
        return readTemplate().query(str, callback, params);
    }

    @Override
    public final <T> T query(SQLBuilder builder, ResultSetCallback<T> callback) {
        return readTemplate().query(builder, callback);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(String str, IMapper<T> m, Object... params) {
        return readTemplate().queryList(str, m, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(SQLBuilder builder, IMapper<T> m) {
        return readTemplate().queryList(builder, m);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(String str, Class<T> type, Object... params) {
        return readTemplate().queryList(str, type, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(SQLBuilder builder, Class<T> type) {
        return readTemplate().queryList(builder, type);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int start, int limit, String str, IMapper<T> m, Object... params) {
        return readTemplate().queryList(start, limit, str, m, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int start, int limit, SQLBuilder builder, IMapper<T> m) {
        return readTemplate().queryList(start, limit, builder, m);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int limit, String str, IMapper<T> m, Object... params) {
        return readTemplate().queryList(limit, str, m, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(int limit, SQLBuilder builder, IMapper<T> m) {
        return readTemplate().queryList(limit, builder, m);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(Paging paging, String str, IMapper<T> m, Object... params) {
        return readTemplate().queryList(paging, str, m, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(Paging paging, SQLBuilder builder, IMapper<T> m) {
        return readTemplate().queryList(paging, builder, m);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(Paging paging, String str, Class<T> type, Object... params) {
        return readTemplate().queryList(paging, str, type, params);
    }

    @Nonnull
    @Override
    public final <T> List<T> queryList(Paging paging, SQLBuilder builder, Class<T> type) {
        return readTemplate().queryList(paging, builder, type);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String str, IMapper<T> m, Object... params) {
        return readTemplate().queryObject(str, m, params);
    }

    @Nullable
    @Override
    public final <T> T queryObject(SQLBuilder builder, IMapper<T> m) {
        return readTemplate().queryObject(builder, m);
    }

    @Nullable
    @Override
    public final <T> T queryObject(String str, Class<T> type, Object... params) {
        return readTemplate().queryObject(str, type, params);
    }

    @Nullable
    @Override
    public final <T> T queryObject(SQLBuilder builder, Class<T> type) {
        return readTemplate().queryObject(builder, type);
    }

    @Nullable
    @Override
    public final String queryString(String str, Object... params) {
        return readTemplate().queryString(str, params);
    }

    @Nullable
    @Override
    public final String queryString(SQLBuilder builder) {
        return readTemplate().queryString(builder);
    }

    @Nullable
    @Override
    public final Long queryLong(String str, Object... params) {
        return readTemplate().queryLong(str, params);
    }

    @Nullable
    @Override
    public final Long queryLong(SQLBuilder builder) {
        return readTemplate().queryLong(builder);
    }

    @Override
    public final long queryLongVal(String str, Object... params) {
        return readTemplate().queryLongVal(str, params);
    }

    @Override
    public final long queryLongVal(SQLBuilder builder) {
        return readTemplate().queryLongVal(builder);
    }

    @Nullable
    @Override
    public final Integer queryInt(String str, Object... params) {
        return readTemplate().queryInt(str, params);
    }

    @Nullable
    @Override
    public final Integer queryInt(SQLBuilder builder) {
        return readTemplate().queryInt(builder);
    }

    @Override
    public final int queryIntVal(String str, Object... params) {
        return readTemplate().queryIntVal(str, params);
    }

    @Override
    public final int queryIntVal(SQLBuilder builder) {
        return readTemplate().queryIntVal(builder);
    }

    @Nullable
    @Override
    public final Short queryShort(String str, Object... params) {
        return readTemplate().queryShort(str, params);
    }

    @Nullable
    @Override
    public final Short queryShort(SQLBuilder builder) {
        return readTemplate().queryShort(builder);
    }

    @Override
    public final short queryShortVal(String str, Object... params) {
        return readTemplate().queryShortVal(str, params);
    }

    @Override
    public final short queryShortVal(SQLBuilder builder) {
        return readTemplate().queryShortVal(builder);
    }

    @Nullable
    @Override
    public final Byte queryByte(String str, Object... params) {
        return readTemplate().queryByte(str, params);
    }

    @Nullable
    @Override
    public final Byte queryByte(SQLBuilder builder) {
        return readTemplate().queryByte(builder);
    }

    @Override
    public final byte queryByteVal(String str, Object... params) {
        return readTemplate().queryByteVal(str, params);
    }

    @Override
    public final byte queryByteVal(SQLBuilder builder) {
        return readTemplate().queryByteVal(builder);
    }

    @Nullable
    @Override
    public final Double queryDouble(String str, Object... params) {
        return readTemplate().queryDouble(str, params);
    }

    @Nullable
    @Override
    public final Double queryDouble(SQLBuilder builder) {
        return readTemplate().queryDouble(builder);
    }

    @Override
    public final double queryDoubleVal(String str, Object... params) {
        return readTemplate().queryDoubleVal(str, params);
    }

    @Override
    public final double queryDoubleVal(SQLBuilder builder) {
        return readTemplate().queryDoubleVal(builder);
    }

    @Nullable
    @Override
    public final Float queryFloat(String str, Object... params) {
        return readTemplate().queryFloat(str, params);
    }

    @Nullable
    @Override
    public final Float queryFloat(SQLBuilder builder) {
        return readTemplate().queryFloat(builder);
    }

    @Override
    public final float queryFloatVal(String str, Object... params) {
        return readTemplate().queryFloatVal(str, params);
    }

    @Override
    public final float queryFloatVal(SQLBuilder builder) {
        return readTemplate().queryFloatVal(builder);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(String str, Object... params) {
        return readTemplate().queryBoolean(str, params);
    }

    @Nullable
    @Override
    public final Boolean queryBoolean(SQLBuilder builder) {
        return readTemplate().queryBoolean(builder);
    }

    @Override
    public final boolean queryBooleanVal(String str, Object... params) {
        return readTemplate().queryBooleanVal(str, params);
    }

    @Override
    public final boolean queryBooleanVal(SQLBuilder builder) {
        return readTemplate().queryBooleanVal(builder);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(String str, Object... params) {
        return readTemplate().queryTimestamp(str, params);
    }

    @Nullable
    @Override
    public final Timestamp queryTimestamp(SQLBuilder builder) {
        return readTemplate().queryTimestamp(builder);
    }

    @Nullable
    @Override
    public final Date queryDate(String str, Object... params) {
        return readTemplate().queryDate(str, params);
    }

    @Nullable
    @Override
    public final Date queryDate(SQLBuilder builder) {
        return readTemplate().queryDate(builder);
    }

    @Nullable
    @Override
    public final Time queryTime(String str, Object... params) {
        return readTemplate().queryTime(str, params);
    }

    @Nullable
    @Override
    public final Time queryTime(SQLBuilder builder) {
        return readTemplate().queryTime(builder);
    }

}
