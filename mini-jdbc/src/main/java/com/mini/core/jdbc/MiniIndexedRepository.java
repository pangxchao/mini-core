package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.IndexedSql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface MiniIndexedRepository {
    /**
     * 执行SQL
     *
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果 - 影响条数
     */
    int execute(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 执行SQL
     *
     * @param sql SQL和参数
     * @return 执行结果 - 影响条数
     */
    int execute(@Nonnull IndexedSql<?> sql);

    /**
     * 指执行SQL
     *
     * @param sql SQL
     * @return 执行结果 - 影响条数
     */
    @Nonnull
    int[] executeBatch(@Nonnull String... sql);

    /**
     * 指执行SQL
     *
     * @param sql        SQL
     * @param paramsList 数据
     * @return 执行结果 - 影响条数
     */
    @Nonnull
    int[] executeBatch(@Nonnull String sql, List<Object[]> paramsList);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(@Nonnull String sql, @Nullable List<Object> params, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(@Nonnull IndexedSql<?> sql, @Nonnull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(@Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(@Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询列表
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(@Nonnull IndexedSql<?> sql);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryListSingle(@Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryListSingle(@Nonnull IndexedSql<?> sql, Class<T> type);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(long offset, int limit, @Nonnull String sql, @Nullable List<Object> params, @Nonnull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(long offset, int limit, @Nonnull IndexedSql<?> sql, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(long offset, int limit, @Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(long offset, int limit, @Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(long offset, int limit, @Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(long offset, int limit, @Nonnull IndexedSql<?> sql);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryListSingle(long offset, int limit, @Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryListSingle(long offset, int limit, @Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(int limit, @Nonnull String sql, @Nullable List<Object> params, @Nonnull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(int limit, @Nonnull IndexedSql<?> sql, @Nonnull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(int limit, String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param limit 获取指定的条数
     * @param sql   SQL
     * @param type  类型类对象
     * @param <T>   解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(int limit, @Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(int limit, @Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询列表
     *
     * @param limit 获取指定的条数
     * @param sql   SQL
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(int limit, @Nonnull IndexedSql<?> sql);


    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryListSingle(int limit, @Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param limit 获取指定的条数
     * @param sql   SQL
     * @param type  类型类对象
     * @param <T>   解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryListSingle(int limit, @Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@Nonnull String sql, @Nullable List<Object> params, @Nonnull RowMapper<T> mapper);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@Nonnull IndexedSql<?> sql, @Nonnull RowMapper<T> mapper);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql  SQL
     * @param type 值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryObjectMap(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryObjectMap(@Nonnull IndexedSql<?> sql);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObjectSingle(@Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql  SQL
     * @param type 值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObjectSingle(@Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);

    /**
     * 查询 String 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    String queryString(String sql, @Nullable List<Object> params);

    /**
     * 查询 String 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    String queryString(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Long queryLong(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Long 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Long queryLong(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Integer queryInt(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Integer 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Integer queryInt(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Short queryShort(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Short 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Short queryShort(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Byte queryByte(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Byte 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Byte queryByte(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Double queryDouble(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Double 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Double queryDouble(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Float queryFloat(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Float 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Float queryFloat(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Boolean queryBoolean(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Boolean 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Boolean queryBoolean(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Timestamp queryTimestamp(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Timestamp 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Timestamp queryTimestamp(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Date 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Date queryDate(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Date 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Date queryDate(@Nonnull IndexedSql<?> sql);

    /**
     * 查询 Time 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Time queryTime(@Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询 Time 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    Time queryTime(@Nonnull IndexedSql<?> sql);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param params   参数
     * @param mapper   映射器
     * @param <T>      解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> Page<T> queryPage(@Nonnull Pageable pageable, @Nonnull String sql, @Nullable List<Object> params, @Nonnull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param mapper   映射器
     * @param <T>      解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> Page<T> queryPage(@Nonnull Pageable pageable, @Nonnull IndexedSql<?> sql, @Nonnull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param params   参数
     * @param type     类型类对象
     * @param <T>      解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> Page<T> queryPage(@Nonnull Pageable pageable, String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param type     类型类对象
     * @param <T>      解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> Page<T> queryPage(@Nonnull Pageable pageable, @Nonnull IndexedSql<?> sql, Class<T> type);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param params   参数
     * @return 查询结果
     */
    @Nonnull
    Page<Map<String, Object>> queryPageMap(@Nonnull Pageable pageable, @Nonnull String sql, @Nullable List<Object> params);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @return 查询结果
     */
    @Nonnull
    Page<Map<String, Object>> queryPageMap(@Nonnull Pageable pageable, @Nonnull IndexedSql<?> sql);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param params   参数
     * @param type     类型类对象
     * @param <T>      解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> Page<T> queryPageSingle(@Nonnull Pageable pageable, @Nonnull String sql, @Nullable List<Object> params, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param type     类型类对象
     * @param <T>      解析器类型
     * @return 查询结果
     */
    @Nonnull
    <T> Page<T> queryPageSingle(@Nonnull Pageable pageable, @Nonnull IndexedSql<?> sql, @Nonnull Class<T> type);
}
