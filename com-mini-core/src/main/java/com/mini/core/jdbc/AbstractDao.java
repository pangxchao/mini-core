package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.Mapper;
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
	public final <T> int replace(T instance) {
		return writeTemplate().replace(instance);
	}

	@Override
	public final <T> int replace(HolderGenerated holder, T instance) {
		return writeTemplate().replace(holder, instance);
	}

	@Override
	public final <T> int insert(T instance) {
		return writeTemplate().insert(instance);
	}

	@Override
	public final <T> int insert(HolderGenerated holder, T instance) {
		return writeTemplate().insert(holder, instance);
	}

	@Override
	public final <T> int delete(T instance) {
		return writeTemplate().delete(instance);
	}

	@Override
	public final <T> int update(T instance) {
		return writeTemplate().update(instance);
	}

	@Override
	public final <T> int insertOnUpdate(T instance) {
		return writeTemplate().insertOnUpdate(instance);
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
	public final <T> List<T> queryList(String str, Mapper<T> m, Object... params) {
		return readTemplate().queryList(str, m, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(SQLBuilder builder, Mapper<T> m) {
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
	public final <T> List<T> queryListSingle(String str, Class<T> type, Object... params) {
		return readTemplate().queryListSingle(str, type, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(SQLBuilder builder, Class<T> type) {
		return readTemplate().queryListSingle(builder, type);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, String str, Mapper<T> m, Object... params) {
		return readTemplate().queryList(start, limit, str, m, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, SQLBuilder builder, Mapper<T> m) {
		return readTemplate().queryList(start, limit, builder, m);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, String str, Class<T> type, Object[] params) {
		return readTemplate().queryList(start, limit, str, type, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, SQLBuilder builder, Class<T> type) {
		return readTemplate().queryList(start, limit, builder, type);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int start, int limit, String str, Class<T> type, Object[] params) {
		return readTemplate().queryListSingle(start, limit, str, type, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int start, int limit, SQLBuilder builder, Class<T> type) {
		return readTemplate().queryListSingle(start, limit, builder, type);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, String str, Mapper<T> m, Object... params) {
		return readTemplate().queryList(limit, str, m, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, SQLBuilder builder, Mapper<T> m) {
		return readTemplate().queryList(limit, builder, m);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, String str, Class<T> type, Object... params) {
		return readTemplate().queryList(limit, str, type, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, SQLBuilder builder, Class<T> type) {
		return readTemplate().queryList(limit, builder, type);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int limit, String str, Class<T> type, Object... params) {
		return readTemplate().queryListSingle(limit, str, type, params);
	}

	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int limit, SQLBuilder builder, Class<T> type) {
		return readTemplate().queryListSingle(limit, builder, type);
	}

	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, String str, Mapper<T> m, Object... params) {
		return readTemplate().queryPaging(page, limit, str, m, params);
	}

	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, SQLBuilder builder, Mapper<T> m) {
		return readTemplate().queryPaging(page, limit, builder, m);
	}

	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, String str, Class<T> type, Object... params) {
		return readTemplate().queryPaging(page, limit, str, type, params);
	}

	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, SQLBuilder builder, Class<T> type) {
		return readTemplate().queryPaging(page, limit, builder, type);
	}

	@Nonnull
	@Override
	public final <T> Paging<T> queryPagingSingle(int page, int limit, String str, Class<T> type, Object... params) {
		return readTemplate().queryPagingSingle(page, limit, str, type, params);
	}

	@Nonnull
	@Override
	public final <T> Paging<T> queryPagingSingle(int page, int limit, SQLBuilder builder, Class<T> type) {
		return readTemplate().queryPagingSingle(page, limit, builder, type);
	}

	@Nullable
	@Override
	public final <T> T queryObject(String str, Mapper<T> m, Object... params) {
		return readTemplate().queryObject(str, m, params);
	}

	@Nullable
	@Override
	public final <T> T queryObject(SQLBuilder builder, Mapper<T> m) {
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
	public final <T> T queryObjectSingle(String str, Class<T> type, Object... params) {
		return readTemplate().queryObjectSingle(str, type, params);
	}

	@Nullable
	@Override
	public final <T> T queryObjectSingle(SQLBuilder builder, Class<T> type) {
		return readTemplate().queryObjectSingle(builder, type);
	}

	@Nullable
	@Override
	public final String queryString(String str, Object[] params) {
		return readTemplate().queryString(str, params);
	}

	@Nullable
	@Override
	public final String queryString(SQLBuilder builder) {
		return readTemplate().queryString(builder);
	}

	@Nullable
	@Override
	public final Long queryLong(String str, Object[] params) {
		return readTemplate().queryLong(str, params);
	}

	@Nullable
	@Override
	public final Long queryLong(SQLBuilder builder) {
		return readTemplate().queryLong(builder);
	}

	@Override
	public final long queryLongVal(String str, Object[] params) {
		return readTemplate().queryLongVal(str, params);
	}

	@Override
	public final long queryLongVal(SQLBuilder builder) {
		return readTemplate().queryLongVal(builder);
	}

	@Nullable
	@Override
	public final Integer queryInt(String str, Object[] params) {
		return readTemplate().queryInt(str, params);
	}

	@Nullable
	@Override
	public final Integer queryInt(SQLBuilder builder) {
		return readTemplate().queryInt(builder);
	}

	@Override
	public final int queryIntVal(String str, Object[] params) {
		return readTemplate().queryIntVal(str, params);
	}

	@Override
	public final int queryIntVal(SQLBuilder builder) {
		return readTemplate().queryIntVal(builder);
	}

	@Nullable
	@Override
	public final Short queryShort(String str, Object[] params) {
		return readTemplate().queryShort(str, params);
	}

	@Nullable
	@Override
	public final Short queryShort(SQLBuilder builder) {
		return readTemplate().queryShort(builder);
	}

	@Override
	public final short queryShortVal(String str, Object[] params) {
		return readTemplate().queryShortVal(str, params);
	}

	@Override
	public final short queryShortVal(SQLBuilder builder) {
		return readTemplate().queryShortVal(builder);
	}

	@Nullable
	@Override
	public final Byte queryByte(String str, Object[] params) {
		return readTemplate().queryByte(str, params);
	}

	@Nullable
	@Override
	public final Byte queryByte(SQLBuilder builder) {
		return readTemplate().queryByte(builder);
	}

	@Override
	public final byte queryByteVal(String str, Object[] params) {
		return readTemplate().queryByteVal(str, params);
	}

	@Override
	public final byte queryByteVal(SQLBuilder builder) {
		return readTemplate().queryByteVal(builder);
	}

	@Nullable
	@Override
	public final Double queryDouble(String str, Object[] params) {
		return readTemplate().queryDouble(str, params);
	}

	@Nullable
	@Override
	public final Double queryDouble(SQLBuilder builder) {
		return readTemplate().queryDouble(builder);
	}

	@Override
	public final double queryDoubleVal(String str, Object[] params) {
		return readTemplate().queryDoubleVal(str, params);
	}

	@Override
	public final double queryDoubleVal(SQLBuilder builder) {
		return readTemplate().queryDoubleVal(builder);
	}

	@Nullable
	@Override
	public final Float queryFloat(String str, Object[] params) {
		return readTemplate().queryFloat(str, params);
	}

	@Nullable
	@Override
	public final Float queryFloat(SQLBuilder builder) {
		return readTemplate().queryFloat(builder);
	}

	@Override
	public final float queryFloatVal(String str, Object[] params) {
		return readTemplate().queryFloatVal(str, params);
	}

	@Override
	public final float queryFloatVal(SQLBuilder builder) {
		return readTemplate().queryFloatVal(builder);
	}

	@Nullable
	@Override
	public final Boolean queryBoolean(String str, Object[] params) {
		return readTemplate().queryBoolean(str, params);
	}

	@Nullable
	@Override
	public final Boolean queryBoolean(SQLBuilder builder) {
		return readTemplate().queryBoolean(builder);
	}

	@Override
	public final boolean queryBooleanVal(String str, Object[] params) {
		return readTemplate().queryBooleanVal(str, params);
	}

	@Override
	public final boolean queryBooleanVal(SQLBuilder builder) {
		return readTemplate().queryBooleanVal(builder);
	}

	@Nullable
	@Override
	public final Timestamp queryTimestamp(String str, Object[] params) {
		return readTemplate().queryTimestamp(str, params);
	}

	@Nullable
	@Override
	public final Timestamp queryTimestamp(SQLBuilder builder) {
		return readTemplate().queryTimestamp(builder);
	}

	@Nullable
	@Override
	public final Date queryDate(String str, Object[] params) {
		return readTemplate().queryDate(str, params);
	}

	@Nullable
	@Override
	public final Date queryDate(SQLBuilder builder) {
		return readTemplate().queryDate(builder);
	}

	@Nullable
	@Override
	public final Time queryTime(String str, Object[] params) {
		return readTemplate().queryTime(str, params);
	}

	@Nullable
	@Override
	public final Time queryTime(SQLBuilder builder) {
		return readTemplate().queryTime(builder);
	}
}