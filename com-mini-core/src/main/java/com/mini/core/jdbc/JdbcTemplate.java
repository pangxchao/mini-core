package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.builder.SQLInterfaceDef;
import com.mini.core.jdbc.mapper.BeanMapper;
import com.mini.core.jdbc.mapper.Mapper;
import com.mini.core.jdbc.mapper.SingleMapper;
import com.mini.core.jdbc.model.Paging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.mini.core.jdbc.util.JdbcUtil.full;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Optional.ofNullable;

/**
 * 数据库操作对象
 * @author xchao
 */
public abstract class JdbcTemplate extends JdbcAccessor implements JdbcInterface {
	public JdbcTemplate(@Nonnull DataSource dataSource) {
		super(dataSource);
	}
	
	/**
	 * 指执行SQL
	 * @param str    SQL
	 * @param setter 预处理设置器
	 * @return 执行结果
	 */
	@Override
	public final int[] executeBatch(String str, PreparedStatementSetter setter) {
		return this.execute((ConnectionCallback<int[]>) (connection) -> {
			PreparedStatement statement = connection.prepareStatement(str);
			for (int i = 0, size = setter.getBatchSize(); i < size; i++) {
				setter.setValues(statement, i);
				statement.addBatch();
			}
			return statement.executeBatch();
		});
	}
	
	@Override
	public final int execute(String str, Object... params) {
		return execute((PreparedStatementCreator) con -> {
			return con.prepareStatement(str); //
		}, stm -> full(stm, params).executeUpdate());
	}
	
	@Override
	public final int execute(SQLBuilder builder) {
		return execute(builder.toString(), builder.toArray());
	}
	
	@Override
	public final int execute(HolderGenerated holder, String str, Object... params) {
		return execute((PreparedStatementCreator) con -> con.prepareStatement( //
			str, RETURN_GENERATED_KEYS), stm -> {
			int result = full(stm, params).executeUpdate();
			try (ResultSet rs = stm.getGeneratedKeys()) {
				holder.setValue(rs);
			}
			return result;
		});
	}
	
	@Override
	public final int execute(HolderGenerated holder, SQLBuilder builder) {
		return execute(holder, builder.toString(), builder.toArray());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> int replace(T instance) {
		return JdbcTemplate.this.execute(new SQLBuilder() {{
			var type = (Class<? extends T>) instance.getClass();
			var inter = SQLInterfaceDef.getSQLInterface(type);
			inter.createReplace(this, instance);
		}});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> int replace(HolderGenerated holder, T instance) {
		return JdbcTemplate.this.execute(holder, new SQLBuilder() {{
			var type = (Class<? extends T>) instance.getClass();
			var inter = SQLInterfaceDef.getSQLInterface(type);
			inter.createReplace(this, instance);
		}});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> int insert(T instance) {
		return JdbcTemplate.this.execute(new SQLBuilder() {{
			var type = (Class<? extends T>) instance.getClass();
			var inter = SQLInterfaceDef.getSQLInterface(type);
			inter.createInsert(this, instance);
		}});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> int insert(HolderGenerated holder, T instance) {
		return JdbcTemplate.this.execute(holder, new SQLBuilder() {{
			var type = (Class<? extends T>) instance.getClass();
			var inter = SQLInterfaceDef.getSQLInterface(type);
			inter.createInsert(this, instance);
		}});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> int delete(T instance) {
		return JdbcTemplate.this.execute(new SQLBuilder() {{
			var type = (Class<? extends T>) instance.getClass();
			var inter = SQLInterfaceDef.getSQLInterface(type);
			inter.createDelete(this, instance);
		}});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> int update(T instance) {
		return JdbcTemplate.this.execute(new SQLBuilder() {{
			var type = (Class<? extends T>) instance.getClass();
			var inter = SQLInterfaceDef.getSQLInterface(type);
			inter.createUpdate(this, instance);
		}});
	}
	
	@Override
	public final <T> T query(String str, ResultSetCallback<T> callback, Object... params) {
		return execute((PreparedStatementCreator) con -> con.prepareStatement(str, //
			TYPE_FORWARD_ONLY, CONCUR_READ_ONLY), statement -> {
			try (ResultSet rs = full(statement, params).executeQuery()) {
				return callback.apply(rs);
			}
		});
	}
	
	@Override
	public final <T> T query(SQLBuilder builder, ResultSetCallback<T> callback) {
		return query(builder.toString(), callback, builder.toArray());
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(String str, Mapper<T> m, Object... params) {
		return JdbcTemplate.this.query(str, rs -> {
			List<T> result = new ArrayList<>();
			while (rs != null && rs.next()) {
				result.add(m.get(rs, rs.getRow()));
			}
			return result;
		}, params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(SQLBuilder builder, Mapper<T> m) {
		return queryList(builder.toString(), m, builder.toArray());
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(String str, Class<T> type, Object... params) {
		return queryList(str, BeanMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(SQLBuilder builder, Class<T> type) {
		return queryList(builder, BeanMapper.create(type));
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(String str, Class<T> type, Object... params) {
		return queryList(str, SingleMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(SQLBuilder builder, Class<T> type) {
		return queryList(builder, SingleMapper.create(type));
	}
	
	/**
	 * 返回查询分页总条数的SQL
	 * @param str SQL
	 * @return 查询总条数的SQL
	 */
	@Nonnull
	protected abstract String totals(String str);
	
	/**
	 * 根据分页参数，组装分页查询SQL
	 * @param start 查询起始位置
	 * @param limit 查询条数
	 * @param str   基础查询SQL
	 * @return 分页查询SQL
	 */
	@Nonnull
	protected abstract String paging(int start, int limit, String str);
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, String str, Mapper<T> m, Object... params) {
		return queryList(paging(start, limit, str), m, params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, SQLBuilder builder, Mapper<T> m) {
		return queryList(start, limit, builder.toString(), m, builder.toArray());
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, String str, Class<T> type, Object[] params) {
		return queryList(start, limit, str, BeanMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int start, int limit, SQLBuilder builder, Class<T> type) {
		return queryList(start, limit, builder, BeanMapper.create(type));
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int start, int limit, String str, Class<T> type, Object[] params) {
		return queryList(start, limit, str, SingleMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int start, int limit, SQLBuilder builder, Class<T> type) {
		return queryList(start, limit, builder, SingleMapper.create(type));
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, String str, Mapper<T> m, Object... params) {
		return queryList(0, limit, str, m, params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, SQLBuilder builder, Mapper<T> m) {
		return queryList(0, limit, builder.toString(), m, builder.toArray());
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, String str, Class<T> type, Object... params) {
		return queryList(limit, str, BeanMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryList(int limit, SQLBuilder builder, Class<T> type) {
		return queryList(limit, builder, BeanMapper.create(type));
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int limit, String str, Class<T> type, Object... params) {
		return queryList(0, limit, str, SingleMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> List<T> queryListSingle(int limit, SQLBuilder builder, Class<T> type) {
		return queryList(0, limit, builder, SingleMapper.create(type));
	}
	
	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, String str, Mapper<T> m, Object... params) {
		com.mini.core.jdbc.model.Paging<T> paging = new com.mini.core.jdbc.model.Paging<>(page, limit);
		paging.addRows(queryList(paging(paging.getStart(), paging.getLimit(), str), m, params));
		paging.setTotal(query(totals(str), (rs -> rs.next() ? rs.getInt(1) : 0), params));
		return paging;
	}
	
	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, SQLBuilder builder, Mapper<T> m) {
		return queryPaging(page, limit, builder.toString(), m, builder.toArray());
	}
	
	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, String str, Class<T> type, Object... params) {
		return queryPaging(page, limit, str, BeanMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> Paging<T> queryPaging(int page, int limit, SQLBuilder builder, Class<T> type) {
		return queryPaging(page, limit, builder, BeanMapper.create(type));
	}
	
	@Nonnull
	@Override
	public final <T> Paging<T> queryPagingSingle(int page, int limit, String str, Class<T> type, Object... params) {
		return queryPaging(page, limit, str, SingleMapper.create(type), params);
	}
	
	@Nonnull
	@Override
	public final <T> Paging<T> queryPagingSingle(int page, int limit, SQLBuilder builder, Class<T> type) {
		return queryPaging(page, limit, builder, SingleMapper.create(type));
	}
	
	@Nullable
	@Override
	public final <T> T queryObject(String str, Mapper<T> m, Object... params) {
		return query(paging(0, 1, str), rs -> rs.next() ? m.get(rs, //
			rs.getRow()) : null, params);
	}
	
	@Nullable
	@Override
	public final <T> T queryObject(SQLBuilder builder, Mapper<T> m) {
		return queryObject(builder.toString(), m, builder.toArray());
	}
	
	@Nullable
	@Override
	public final <T> T queryObject(String str, Class<T> type, Object... params) {
		return queryObject(str, BeanMapper.create(type), params);
	}
	
	@Nullable
	@Override
	public final <T> T queryObject(SQLBuilder builder, Class<T> type) {
		return queryObject(builder, BeanMapper.create(type));
	}
	
	@Nullable
	@Override
	public final <T> T queryObjectSingle(String str, Class<T> type, Object... params) {
		return queryObject(str, SingleMapper.create(type), params);
	}
	
	@Nullable
	@Override
	public final <T> T queryObjectSingle(SQLBuilder builder, Class<T> type) {
		return queryObject(builder, SingleMapper.create(type));
	}
	
	@Nullable
	@Override
	public final String queryString(String str, Object[] params) {
		return queryObjectSingle(str, String.class, params);
	}
	
	@Nullable
	@Override
	public final String queryString(SQLBuilder builder) {
		return queryObjectSingle(builder, String.class);
	}
	
	@Nullable
	@Override
	public final Long queryLong(String str, Object[] params) {
		return queryObjectSingle(str, Long.class, params);
	}
	
	@Nullable
	@Override
	public final Long queryLong(SQLBuilder builder) {
		return queryObjectSingle(builder, Long.class);
	}
	
	@Override
	public final long queryLongVal(String str, Object[] params) {
		Long value = this.queryLong(str, params);
		return ofNullable(value).orElse(0L);
	}
	
	@Override
	public final long queryLongVal(SQLBuilder builder) {
		Long value = this.queryLong(builder);
		return ofNullable(value).orElse(0L);
	}
	
	@Nullable
	@Override
	public final Integer queryInt(String str, Object[] params) {
		return queryObjectSingle(str, Integer.class, params);
	}
	
	@Nullable
	@Override
	public final Integer queryInt(SQLBuilder builder) {
		return queryObjectSingle(builder, Integer.class);
	}
	
	@Override
	public final int queryIntVal(String str, Object[] params) {
		Integer value = this.queryInt(str, params);
		return ofNullable(value).orElse(0);
	}
	
	@Override
	public final int queryIntVal(SQLBuilder builder) {
		Integer value = this.queryInt(builder);
		return ofNullable(value).orElse(0);
	}
	
	@Nullable
	@Override
	public final Short queryShort(String str, Object[] params) {
		return queryObjectSingle(str, Short.class, params);
	}
	
	@Nullable
	@Override
	public final Short queryShort(SQLBuilder builder) {
		return queryObjectSingle(builder, Short.class);
	}
	
	@Override
	public final short queryShortVal(String str, Object[] params) {
		Short value = this.queryShort(str, params);
		return ofNullable(value).orElse((short) 0);
	}
	
	@Override
	public final short queryShortVal(SQLBuilder builder) {
		Short value = this.queryShort(builder);
		return ofNullable(value).orElse((short) 0);
	}
	
	@Nullable
	@Override
	public final Byte queryByte(String str, Object[] params) {
		return queryObjectSingle(str, Byte.class, params);
	}
	
	@Nullable
	@Override
	public final Byte queryByte(SQLBuilder builder) {
		return queryObjectSingle(builder, Byte.class);
	}
	
	@Override
	public final byte queryByteVal(String str, Object[] params) {
		Byte value = this.queryByte(str, params);
		return ofNullable(value).orElse((byte) 0);
	}
	
	@Override
	public final byte queryByteVal(SQLBuilder builder) {
		Byte value = this.queryByte(builder);
		return ofNullable(value).orElse((byte) 0);
	}
	
	@Nullable
	@Override
	public final Double queryDouble(String str, Object[] params) {
		return queryObjectSingle(str, Double.class, params);
	}
	
	@Nullable
	@Override
	public final Double queryDouble(SQLBuilder builder) {
		return queryObjectSingle(builder, Double.class);
	}
	
	@Override
	public final double queryDoubleVal(String str, Object[] params) {
		Double value = this.queryDouble(str, params);
		return ofNullable(value).orElse(0D);
	}
	
	@Override
	public final double queryDoubleVal(SQLBuilder builder) {
		Double value = this.queryDouble(builder);
		return ofNullable(value).orElse(0D);
	}
	
	@Nullable
	@Override
	public final Float queryFloat(String str, Object[] params) {
		return queryObjectSingle(str, Float.class, params);
	}
	
	@Nullable
	@Override
	public final Float queryFloat(SQLBuilder builder) {
		return queryObjectSingle(builder, Float.class);
	}
	
	@Override
	public final float queryFloatVal(String str, Object[] params) {
		Float value = this.queryFloat(str, params);
		return ofNullable(value).orElse(0F);
	}
	
	@Override
	public final float queryFloatVal(SQLBuilder builder) {
		Float value = this.queryFloat(builder);
		return ofNullable(value).orElse(0F);
	}
	
	@Nullable
	@Override
	public final Boolean queryBoolean(String str, Object[] params) {
		return queryObjectSingle(str, Boolean.class, params);
	}
	
	@Nullable
	@Override
	public final Boolean queryBoolean(SQLBuilder builder) {
		return queryObjectSingle(builder, Boolean.class);
	}
	
	@Override
	public final boolean queryBooleanVal(String str, Object[] params) {
		Boolean value = this.queryBoolean(str, params);
		return ofNullable(value).orElse(false);
	}
	
	@Override
	public final boolean queryBooleanVal(SQLBuilder builder) {
		Boolean value = this.queryBoolean(builder);
		return ofNullable(value).orElse(false);
	}
	
	@Nullable
	@Override
	public final Timestamp queryTimestamp(String str, Object[] params) {
		return queryObjectSingle(str, Timestamp.class, params);
	}
	
	@Nullable
	@Override
	public final Timestamp queryTimestamp(SQLBuilder builder) {
		return queryObjectSingle(builder, Timestamp.class);
	}
	
	@Nullable
	@Override
	public final Date queryDate(String str, Object[] params) {
		return queryObjectSingle(str, Date.class, params);
	}
	
	@Nullable
	@Override
	public final Date queryDate(SQLBuilder builder) {
		return queryObjectSingle(builder, Date.class);
	}
	
	@Nullable
	@Override
	public final Time queryTime(String str, Object[] params) {
		return queryObjectSingle(str, Time.class, params);
	}
	
	@Nullable
	@Override
	public final Time queryTime(SQLBuilder builder) {
		return queryObjectSingle(builder, Time.class);
	}
}