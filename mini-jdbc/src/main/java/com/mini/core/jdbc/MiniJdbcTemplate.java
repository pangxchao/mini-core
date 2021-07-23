package com.mini.core.jdbc;

import com.mini.core.jdbc.wrapper.IndexWrapper;
import com.mini.core.jdbc.wrapper.NamedWrapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MiniJdbcTemplate {
    /**
     * 执行SQL
     *
     * @param creator   SQL预处理语句创建器
     * @param keyHolder SQL执行返回接收器
     * @return 执行结果 - 影响条数
     */
    int execute(PreparedStatementCreator creator, KeyHolder keyHolder);

    /**
     * 执行SQL
     *
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果 - 影响条数
     */
    int execute(String sql, Map<String, ?> params);

    /**
     * 执行SQL
     *
     * @param wrapper SQL包装器
     * @return 执行结果 - 影响条数
     */
    default int execute(@Nonnull NamedWrapper wrapper) {
        return execute(wrapper.sql(), wrapper.args());
    }

    /**
     * 执行SQL
     *
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果 - 影响条数
     */
    int execute(String sql, Object[] params);

    /**
     * 执行SQL
     *
     * @param wrapper SQL包装器
     * @return 执行结果 - 影响条数
     */
    default int execute(@Nonnull IndexWrapper wrapper) {
        return execute(wrapper.sql(), wrapper.args());
    }

    /**
     * 批量执行SQL
     *
     * @param sql        SQL
     * @param paramsList 数据
     * @return 执行结果 - 影响条数
     */
    @Nonnull
    int[] executeBatch(String sql, Map<String, ?>[] paramsList);

    /**
     * 批量执行SQL
     *
     * @param sql        SQL
     * @param paramsList 数据
     * @return 执行结果 - 影响条数
     */
    @Nonnull
    int[] executeBatch(String sql, List<Object[]> paramsList);

    /**
     * 指执行SQL
     *
     * @param sql SQL
     * @return 执行结果 - 影响条数
     */
    @Nonnull
    int[] executeBatch(String... sql);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(String sql, Map<String, ?> params, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @param mapper  映射器
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(NamedWrapper wrapper, RowMapper<T> mapper) {
        return queryList(wrapper.sql(), wrapper.args(), mapper);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(String sql, Object[] params, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @param mapper  映射器
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(IndexWrapper wrapper, RowMapper<T> mapper) {
        return queryList(wrapper.sql(), wrapper.args(), mapper);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(String sql, Map<String, ?> params, Class<T> type);


    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @param type    类型类对象
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(NamedWrapper wrapper, Class<T> type) {
        return queryList(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(String sql, Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @param type    类型类对象
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(IndexWrapper wrapper, Class<T> type) {
        return queryList(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(String sql, Map<String, ?> params);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default List<Map<String, Object>> queryListMap(NamedWrapper wrapper) {
        return queryListMap(wrapper.sql(), wrapper.args());
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(String sql, Object[] params);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default List<Map<String, Object>> queryListMap(IndexWrapper wrapper) {
        return queryListMap(wrapper.sql(), wrapper.args());
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> querySingleList(String sql, Map<String, ?> params, Class<T> type);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> querySingleList(NamedWrapper wrapper, Class<T> type) {
        return querySingleList(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> querySingleList(String sql, Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> querySingleList(IndexWrapper wrapper, Class<T> type) {
        return querySingleList(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @return 查询结果
     */
    @Nullable
    <T> T queryOne(String sql, Map<String, ?> params, RowMapper<T> mapper);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param mapper  映射器
     * @return 查询结果
     */
    @Nullable
    default <T> T queryOne(NamedWrapper wrapper, RowMapper<T> mapper) {
        return queryOne(wrapper.sql(), wrapper.args(), mapper);
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @return 查询结果
     */
    @Nullable
    <T> T queryOne(String sql, Object[] params, RowMapper<T> mapper);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param mapper  映射器
     * @return 查询结果
     */
    @Nullable
    default <T> T queryOne(IndexWrapper wrapper, RowMapper<T> mapper) {
        return queryOne(wrapper.sql(), wrapper.args(), mapper);
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryOne(String sql, Map<String, ?> params, Class<T> type);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable
    default <T> T queryOne(NamedWrapper wrapper, Class<T> type) {
        return queryOne(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryOne(String sql, Object[] params, Class<T> type);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable
    default <T> T queryOne(IndexWrapper wrapper, Class<T> type) {
        return queryOne(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryOneMap(String sql, Map<String, ?> params);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Map<String, Object> queryOneMap(NamedWrapper wrapper) {
        return queryOneMap(wrapper.sql(), wrapper.args());
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryOneMap(String sql, Object[] params);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Map<String, Object> queryOneMap(IndexWrapper wrapper) {
        return queryOneMap(wrapper.sql(), wrapper.args());
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T querySingleOne(String sql, Map<String, ?> params, Class<T> type);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable
    default <T> T querySingleOne(NamedWrapper wrapper, Class<T> type) {
        return querySingleOne(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T querySingleOne(String sql, Object[] params, Class<T> type);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable
    default <T> T querySingleOne(IndexWrapper wrapper, Class<T> type) {
        return querySingleOne(wrapper.sql(), wrapper.args(), type);
    }

    /**
     * 查询 String 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default String queryString(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, String.class);
    }

    /**
     * 查询 String 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default String queryString(NamedWrapper wrapper) {
        return querySingleOne(wrapper, String.class);
    }

    /**
     * 查询 String 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default String queryString(String sql, Object[] params) {
        return querySingleOne(sql, params, String.class);
    }

    /**
     * 查询 String 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default String queryString(IndexWrapper wrapper) {
        return querySingleOne(wrapper, String.class);
    }

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Long.class);
    }

    /**
     * 查询 Long 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Long.class);
    }

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(String sql, Object[] params) {
        return querySingleOne(sql, params, Long.class);
    }

    /**
     * 查询 Long 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Long.class);
    }

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Integer.class);
    }

    /**
     * 查询 Integer 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Integer.class);
    }

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(String sql, Object[] params) {
        return querySingleOne(sql, params, Integer.class);
    }

    /**
     * 查询 Integer 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Integer.class);
    }

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Short.class);
    }

    /**
     * 查询 Short 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Short.class);
    }

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(String sql, Object[] params) {
        return querySingleOne(sql, params, Short.class);
    }

    /**
     * 查询 Short 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Short.class);
    }

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Byte.class);
    }

    /**
     * 查询 Byte 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Byte.class);
    }

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(String sql, Object[] params) {
        return querySingleOne(sql, params, Byte.class);
    }

    /**
     * 查询 Byte 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Byte.class);
    }

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Double.class);
    }

    /**
     * 查询 Double 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Double.class);
    }

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(String sql, Object[] params) {
        return querySingleOne(sql, params, Double.class);
    }

    /**
     * 查询 Double 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Double.class);
    }

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Float.class);
    }

    /**
     * 查询 Float 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Float.class);
    }

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(String sql, Object[] params) {
        return querySingleOne(sql, params, Float.class);
    }

    /**
     * 查询 Float 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Float.class);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Boolean.class);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Boolean.class);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(String sql, Object[] params) {
        return querySingleOne(sql, params, Boolean.class);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Boolean.class);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(String sql, Map<String, ?> params) {
        return querySingleOne(sql, params, Date.class);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(NamedWrapper wrapper) {
        return querySingleOne(wrapper, Date.class);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(String sql, Object[] params) {
        return querySingleOne(sql, params, Date.class);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(IndexWrapper wrapper) {
        return querySingleOne(wrapper, Date.class);
    }
}
