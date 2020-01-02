package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.Mapper;
import com.mini.core.jdbc.model.Paging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.*;
import java.util.EventListener;
import java.util.List;

/**
 * 数据库操作对象
 * @author xchao
 */
public interface JdbcInterface extends EventListener {
	/**
	 * 指执行SQL
	 * @param str    SQL
	 * @param setter 预处理设置器
	 * @return 执行结果
	 */
	int[] executeBatch(String str, PreparedStatementSetter setter);

	/**
	 * 执行SQL
	 * @param str    SQL
	 * @param params 参数
	 * @return 执行结果
	 */
	int execute(String str, Object... params);

	/**
	 * 执行SQL
	 * @param builder SQLBuilder 对象
	 * @return 执行结果
	 */
	int execute(SQLBuilder builder);

	/**
	 * 执行SQL
	 * @param holder 执行返回数据
	 * @param str    SQL
	 * @param params 参数
	 * @return 执行结果
	 */
	int execute(HolderGenerated holder, String str, Object... params);

	/**
	 * 执行SQL
	 * @param holder  执行返回数据
	 * @param builder SQLBuilder 对象
	 * @return 执行结果
	 */
	int execute(HolderGenerated holder, SQLBuilder builder);

	/**
	 * 查询结果
	 * @param str      查询SQL
	 * @param callback 查询回调方法
	 * @param params   查询SQL中的参数
	 * @param <T>      查询结果返回类型
	 * @return 查询结果
	 */
	<T> T query(String str, ResultSetCallback<T> callback, Object... params);

	/**
	 * 查询结果
	 * @param builder  查询SQLBuilder对象
	 * @param callback 查询回调方法
	 * @param <T>      查询结果返回类型
	 * @return 查询结果
	 */
	<T> T query(SQLBuilder builder, ResultSetCallback<T> callback);

	/**
	 * 查询列表
	 * @param str    SQL
	 * @param m      映射器
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(String str, Mapper<T> m, Object... params);

	/**
	 * 查询列表
	 * @param builder SQL和参数
	 * @param m       映射器
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(SQLBuilder builder, Mapper<T> m);

	/**
	 * 查询列表
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(String str, Class<T> type, Object... params);

	/**
	 * 查询列表
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(SQLBuilder builder, Class<T> type);

	/**
	 * 查询列表
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryListSingle(String str, Class<T> type, Object... params);

	/**
	 * 查询列表
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryListSingle(SQLBuilder builder, Class<T> type);

	/**
	 * 查询列表
	 * @param start   跳过的数据条数
	 * @param limit  获取指定的条数
	 * @param str    SQL
	 * @param m      映射器
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int start, int limit, String str, Mapper<T> m, Object... params);

	/**
	 * 查询列表
	 * @param start    跳过的数据条数
	 * @param limit   获取指定的条数
	 * @param builder SQL和参数
	 * @param m       映射器
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int start, int limit, SQLBuilder builder, Mapper<T> m);

	/**
	 * 查询列表
	 * @param start   跳过的数据条数
	 * @param limit  获取指定的条数
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int start, int limit, String str, Class<T> type, Object[] params);

	/**
	 * 查询列表
	 * @param start    跳过的数据条数
	 * @param limit   获取指定的条数
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int start, int limit, SQLBuilder builder, Class<T> type);

	/**
	 * 查询列表
	 * @param start   跳过的数据条数
	 * @param limit  获取指定的条数
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryListSingle(int start, int limit, String str, Class<T> type, Object[] params);

	/**
	 * 查询列表
	 * @param start    跳过的数据条数
	 * @param limit   获取指定的条数
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryListSingle(int start, int limit, SQLBuilder builder, Class<T> type);

	/**
	 * 查询列表
	 * @param limit  获取指定的条数
	 * @param str    SQL
	 * @param m      映射器
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int limit, String str, Mapper<T> m, Object... params);

	/**
	 * 查询列表
	 * @param limit   获取指定的条数
	 * @param builder SQL和参数
	 * @param m       映射器
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int limit, SQLBuilder builder, Mapper<T> m);

	/**
	 * 查询列表
	 * @param limit  获取指定的条数
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int limit, String str, Class<T> type, Object... params);

	/**
	 * 查询列表
	 * @param limit   获取指定的条数
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryList(int limit, SQLBuilder builder, Class<T> type);

	/**
	 * 查询列表
	 * @param limit  获取指定的条数
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryListSingle(int limit, String str, Class<T> type, Object... params);

	/**
	 * 查询列表
	 * @param limit   获取指定的条数
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> List<T> queryListSingle(int limit, SQLBuilder builder, Class<T> type);

	/**
	 * 查询列表
	 * @param page   当前页数
	 * @param limit  每页条数
	 * @param str    SQL
	 * @param m      映射器
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> Paging<T> queryPaging(int page, int limit, String str, Mapper<T> m, Object... params);

	/**
	 * 查询列表
	 * @param page    当前页数
	 * @param limit   每页条数
	 * @param builder SQL和参数
	 * @param m       映射器
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> Paging<T> queryPaging(int page, int limit, SQLBuilder builder, Mapper<T> m);

	/**
	 * 查询列表
	 * @param page   当前页数
	 * @param limit  每页条数
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> Paging<T> queryPaging(int page, int limit, String str, Class<T> type, Object... params);

	/**
	 * 查询列表
	 * @param page    当前页数
	 * @param limit   每页条数
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> Paging<T> queryPaging(int page, int limit, SQLBuilder builder, Class<T> type);

	/**
	 * 查询列表
	 * @param page   当前页数
	 * @param limit  每页条数
	 * @param str    SQL
	 * @param type   类型类对象
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> Paging<T> queryPagingSingle(int page, int limit, String str, Class<T> type, Object... params);

	/**
	 * 查询列表
	 * @param page    当前页数
	 * @param limit   每页条数
	 * @param builder SQL和参数
	 * @param type    类型类对象
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nonnull
	<T> Paging<T> queryPagingSingle(int page, int limit, SQLBuilder builder, Class<T> type);

	/**
	 * 查询对象
	 * @param str    SQL
	 * @param m      映射器
	 * @param params 参数
	 * @param <T>    解析器类型
	 * @return 查询结果
	 */
	@Nullable
	<T> T queryObject(String str, Mapper<T> m, Object... params);

	/**
	 * 查询对象
	 * @param builder SQL和参数
	 * @param m       映射器
	 * @param <T>     解析器类型
	 * @return 查询结果
	 */
	@Nullable
	<T> T queryObject(SQLBuilder builder, Mapper<T> m);

	/**
	 * 查询对象
	 * @param str    SQL
	 * @param type   值的类型
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	<T> T queryObject(String str, Class<T> type, Object... params);

	/**
	 * 查询对象
	 * @param builder SQL和参数
	 * @param type    值的类型
	 * @return 查询结果
	 */
	@Nullable
	<T> T queryObject(SQLBuilder builder, Class<T> type);

	/**
	 * 查询对象
	 * @param str    SQL
	 * @param type   值的类型
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	<T> T queryObjectSingle(String str, Class<T> type, Object... params);

	/**
	 * 查询对象
	 * @param builder SQL和参数
	 * @param type    值的类型
	 * @return 查询结果
	 */
	@Nullable
	<T> T queryObjectSingle(SQLBuilder builder, Class<T> type);

	/**
	 * 查询 String 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	String queryString(String str, Object[] params);

	/**
	 * 查询 String 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	String queryString(SQLBuilder builder);

	/**
	 * 查询 Long 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Long queryLong(String str, Object[] params);

	/**
	 * 查询 Long 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Long queryLong(SQLBuilder builder);

	/**
	 * 查询 long 值
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	long queryLongVal(String str, Object[] params);

	/**
	 * 查询 long 值
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	long queryLongVal(SQLBuilder builder);

	/**
	 * 查询 Integer 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Integer queryInt(String str, Object[] params);

	/**
	 * 查询 Integer 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Integer queryInt(SQLBuilder builder);

	/**
	 * 查询 int 值
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	int queryIntVal(String str, Object[] params);

	/**
	 * 查询 int 值
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	int queryIntVal(SQLBuilder builder);

	/**
	 * 查询 Short 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Short queryShort(String str, Object[] params);

	/**
	 * 查询 Short 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Short queryShort(SQLBuilder builder);

	/**
	 * 查询 short 值
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	short queryShortVal(String str, Object[] params);

	/**
	 * 查询 short 值
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	short queryShortVal(SQLBuilder builder);

	/**
	 * 查询 Byte 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Byte queryByte(String str, Object[] params);

	/**
	 * 查询 Byte 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Byte queryByte(SQLBuilder builder);

	/**
	 * 查询 byte 值
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	byte queryByteVal(String str, Object[] params);

	/**
	 * 查询 byte 值
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	byte queryByteVal(SQLBuilder builder);

	/**
	 * 查询 Double 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Double queryDouble(String str, Object[] params);

	/**
	 * 查询 Double 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Double queryDouble(SQLBuilder builder);

	/**
	 * 查询 double 值
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	double queryDoubleVal(String str, Object[] params);

	/**
	 * 查询 double 值
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	double queryDoubleVal(SQLBuilder builder);

	/**
	 * 查询 Float 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Float queryFloat(String str, Object[] params);

	/**
	 * 查询 Float 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Float queryFloat(SQLBuilder builder);

	/**
	 * 查询 float 值
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	float queryFloatVal(String str, Object[] params);

	/**
	 * 查询 float 值
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	float queryFloatVal(SQLBuilder builder);

	/**
	 * 查询 Boolean 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Boolean queryBoolean(String str, Object[] params);

	/**
	 * 查询 Boolean 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Boolean queryBoolean(SQLBuilder builder);

	/**
	 * 查询 boolean 值
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	boolean queryBooleanVal(String str, Object[] params);

	/**
	 * 查询 boolean 值
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	boolean queryBooleanVal(SQLBuilder builder);

	/**
	 * 查询 Timestamp 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Timestamp queryTimestamp(String str, Object[] params);

	/**
	 * 查询 Timestamp 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Timestamp queryTimestamp(SQLBuilder builder);

	/**
	 * 查询 Date 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Date queryDate(String str, Object[] params);

	/**
	 * 查询 Date 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Date queryDate(SQLBuilder builder);

	/**
	 * 查询 Time 对象
	 * @param str    SQL
	 * @param params 参数
	 * @return 查询结果
	 */
	@Nullable
	Time queryTime(String str, Object[] params);


	/**
	 * 查询 Time 对象
	 * @param builder SQL和参数
	 * @return 查询结果
	 */
	@Nullable
	Time queryTime(SQLBuilder builder);

	/**
	 * 查询结果集回调处理
	 * @param <T> 返回类型
	 * @author xchao
	 */
	@FunctionalInterface
	interface ResultSetCallback<T> {
		T apply(ResultSet rs) throws SQLException;
	}

	/**
	 * 指处理参数设置
	 * @author xchao
	 */
	interface PreparedStatementSetter {
		void setValues(PreparedStatement ps, int i);

		int getBatchSize();
	}

	/**
	 * 执行获取返回值处理
	 * @param <T>
	 * @author xchao
	 */
	interface Holder<T> extends EventListener {
		void setValue(ResultSet rs) throws SQLException;

		T getValue();
	}

	/**
	 * 执行获取自增长ID返回
	 * @author xchao
	 */
	class HolderGenerated implements Holder<Long> {
		private Long value = 0L;

		@Override
		public void setValue(ResultSet rs) throws SQLException {
			value = rs.next() ? rs.getLong(1) : 0;
		}

		public void setValue(Long value) {
			this.value = value;
		}

		@Override
		public Long getValue() {
			return value;
		}
	}
}