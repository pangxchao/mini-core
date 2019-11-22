package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.IMapper;
import com.mini.core.jdbc.mapper.IMapperSingle;
import com.mini.core.jdbc.model.Paging;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static java.util.Optional.ofNullable;

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
    default int execute(SQLBuilder builder) {
        return execute(builder.toString(), builder.toArray());
    }

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
    default int execute(HolderGenerated holder, SQLBuilder builder) {
        return execute(holder, builder.toString(), builder.toArray());
    }

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
    default <T> T query(SQLBuilder builder, ResultSetCallback<T> callback) {
        return query(builder.toString(), callback, builder.toArray());
    }

    /**
     * 查询列表
     * @param str    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(String str, IMapper<T> m, Object... params) {
        return JdbcInterface.this.query(str, rs -> {
            List<T> result = new ArrayList<>();
            while (rs != null && rs.next()) {
                result.add(m.get(rs, rs.getRow()));
            }
            return result;
        }, params);
    }

    /**
     * 查询列表
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(SQLBuilder builder, IMapper<T> m) {
        return queryList(builder.toString(), m, builder.toArray());
    }

    /**
     * 查询列表
     * @param str    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(String str, Class<T> type, Object... params) {
        return queryList(str, new IMapperSingle<>(type), params);
    }

    /**
     * 查询列表
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(SQLBuilder builder, Class<T> type) {
        return queryList(builder, new IMapperSingle<>(type));
    }

    /**
     * 查询列表
     * @param skip   跳过的数据条数
     * @param limit  获取指定的条数
     * @param str    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(int skip, int limit, String str, IMapper<T> m, Object... params);

    /**
     * 查询列表
     * @param skip    跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(int skip, int limit, SQLBuilder builder, IMapper<T> m) {
        return queryList(skip, limit, builder.toString(), m, builder.toArray());
    }

    /**
     * 查询列表
     * @param skip   跳过的数据条数
     * @param limit  获取指定的条数
     * @param str    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(int skip, int limit, String str, Class<T> type, Object[] params) {
        return queryList(skip, limit, str, new IMapperSingle<>(type), params);
    }

    /**
     * 查询列表
     * @param skip    跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(int skip, int limit, SQLBuilder builder, Class<T> type) {
        return queryList(skip, limit, builder, new IMapperSingle<>(type));
    }

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
    default <T> List<T> queryList(int limit, String str, IMapper<T> m, Object... params) {
        return queryList(0, limit, str, m, params);
    }

    /**
     * 查询列表
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(int limit, SQLBuilder builder, IMapper<T> m) {
        return queryList(0, limit, builder.toString(), m, builder.toArray());
    }

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
    default <T> List<T> queryList(int limit, String str, Class<T> type, Object... params) {
        return queryList(0, limit, str, new IMapperSingle<>(type), params);
    }

    /**
     * 查询列表
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(int limit, SQLBuilder builder, Class<T> type) {
        return queryList(0, limit, builder, new IMapperSingle<>(type));
    }

    /**
     * 查询列表
     * @param paging 分页器
     * @param str    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(Paging paging, String str, IMapper<T> m, Object... params);

    /**
     * 查询列表
     * @param paging  分页器
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(Paging paging, SQLBuilder builder, IMapper<T> m) {
        return queryList(paging, builder.toString(), m, builder.toArray());
    }

    /**
     * 查询列表
     * @param paging 分页器
     * @param str    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(Paging paging, String str, Class<T> type, Object... params) {
        return queryList(paging, str, new IMapperSingle<>(type), params);
    }

    /**
     * 查询列表
     * @param paging  分页器
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(Paging paging, SQLBuilder builder, Class<T> type) {
        return queryList(paging, builder, new IMapperSingle<>(type));
    }

    /**
     * 查询对象
     * @param str    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(String str, IMapper<T> m, Object... params);

    /**
     * 查询对象
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nullable
    default <T> T queryObject(SQLBuilder builder, IMapper<T> m) {
        return queryObject(builder.toString(), m, builder.toArray());
    }

    /**
     * 查询对象
     * @param str    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default <T> T queryObject(String str, Class<T> type, Object... params) {
        return queryObject(str, new IMapperSingle<>(type), params);
    }

    /**
     * 查询对象
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable
    default <T> T queryObject(SQLBuilder builder, Class<T> type) {
        return queryObject(builder, new IMapperSingle<>(type));
    }

    /**
     * 查询 String 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default String queryString(String str, Object[] params) {
        return queryObject(str, String.class, params);
    }

    /**
     * 查询 String 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default String queryString(SQLBuilder builder) {
        return queryString(builder.toString(), builder.toArray());
    }

    /**
     * 查询 Long 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(String str, Object[] params) {
        return queryObject(str, Long.class, params);
    }

    /**
     * 查询 Long 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(SQLBuilder builder) {
        return queryLong(builder.toString(), builder.toArray());
    }

    /**
     * 查询 long 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default long queryLongVal(String str, Object[] params) {
        return ofNullable(queryObject(str, Long.class, params)).orElse(0L);
    }

    /**
     * 查询 long 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    default long queryLongVal(SQLBuilder builder) {
        return ofNullable(queryObject(builder, Long.class)).orElse(0L);
    }

    /**
     * 查询 Integer 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(String str, Object[] params) {
        return queryObject(str, Integer.class, params);
    }

    /**
     * 查询 Integer 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(SQLBuilder builder) {
        return queryInt(builder.toString(), builder.toArray());
    }

    /**
     * 查询 int 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default int queryIntVal(String str, Object[] params) {
        return ofNullable(queryObject(str, Integer.class, params)).orElse(0);
    }

    /**
     * 查询 int 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    default int queryIntVal(SQLBuilder builder) {
        return ofNullable(queryObject(builder, Integer.class)).orElse(0);
    }

    /**
     * 查询 Short 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(String str, Object[] params) {
        return queryObject(str, Short.class, params);
    }

    /**
     * 查询 Short 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(SQLBuilder builder) {
        return queryShort(builder.toString(), builder.toArray());
    }

    /**
     * 查询 short 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default short queryShortVal(String str, Object[] params) {
        return ofNullable(queryObject(str, Short.class, params)).orElse((short) 0);
    }

    /**
     * 查询 short 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    default short queryShortVal(SQLBuilder builder) {
        return ofNullable(queryObject(builder, Short.class)).orElse((short) 0);
    }

    /**
     * 查询 Byte 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(String str, Object[] params) {
        return queryObject(str, Byte.class, params);
    }

    /**
     * 查询 Byte 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(SQLBuilder builder) {
        return queryByte(builder.toString(), builder.toArray());
    }

    /**
     * 查询 byte 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default byte queryByteVal(String str, Object[] params) {
        return ofNullable(queryObject(str, Byte.class, params)).orElse((byte) 0);
    }

    /**
     * 查询 byte 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    default byte queryByteVal(SQLBuilder builder) {
        return ofNullable(queryObject(builder, Byte.class)).orElse((byte) 0);
    }

    /**
     * 查询 Double 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(String str, Object[] params) {
        return queryObject(str, Double.class, params);
    }

    /**
     * 查询 Double 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(SQLBuilder builder) {
        return queryDouble(builder.toString(), builder.toArray());
    }

    /**
     * 查询 double 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default double queryDoubleVal(String str, Object[] params) {
        return ofNullable(queryDouble(str, params)).orElse(0D);
    }

    /**
     * 查询 double 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    default double queryDoubleVal(SQLBuilder builder) {
        return ofNullable(queryDouble(builder)).orElse(0D);
    }

    /**
     * 查询 Float 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(String str, Object[] params) {
        return queryObject(str, Float.class, params);
    }

    /**
     * 查询 Float 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(SQLBuilder builder) {
        return queryFloat(builder.toString(), builder.toArray());
    }

    /**
     * 查询 float 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default float queryFloatVal(String str, Object[] params) {
        return ofNullable(queryFloat(str, params)).orElse(0F);
    }

    /**
     * 查询 float 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    default float queryFloatVal(SQLBuilder builder) {
        return ofNullable(queryFloat(builder)).orElse(0F);
    }

    /**
     * 查询 Boolean 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(String str, Object[] params) {
        return queryObject(str, Boolean.class, params);
    }

    /**
     * 查询 Boolean 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(SQLBuilder builder) {
        return queryBoolean(builder.toString(), builder.toArray());
    }

    /**
     * 查询 boolean 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    default boolean queryBooleanVal(String str, Object[] params) {
        return ofNullable(queryBoolean(str, params)).orElse(false);
    }

    /**
     * 查询 boolean 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    default boolean queryBooleanVal(SQLBuilder builder) {
        return ofNullable(queryBoolean(builder)).orElse(false);
    }

    /**
     * 查询 Timestamp 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Timestamp queryTimestamp(String str, Object[] params) {
        return queryObject(str, Timestamp.class, params);
    }

    /**
     * 查询 Timestamp 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Timestamp queryTimestamp(SQLBuilder builder) {
        return queryTimestamp(builder.toString(), builder.toArray());
    }

    /**
     * 查询 Date 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(String str, Object[] params) {
        return queryObject(str, Date.class, params);
    }

    /**
     * 查询 Date 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(SQLBuilder builder) {
        return queryDate(builder.toString(), builder.toArray());
    }

    /**
     * 查询 Time 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Time queryTime(String str, Object[] params) {
        return queryObject(str, Time.class, params);
    }


    /**
     * 查询 Time 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    default Time queryTime(SQLBuilder builder) {
        return queryTime(builder.toString(), builder.toArray());
    }

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