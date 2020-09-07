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

/**
 * 数据库操作对象
 *
 * @author xchao
 */
public interface JdbcInterface {
    /**
     * 执行SQL
     *
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果 - 影响条数
     */
    int execute(@NotNull String sql, Object... params);

    /**
     * 执行SQL
     *
     * @param builder SQLBuilder 对象
     * @return 执行结果 - 影响条数
     */
    int execute(@NotNull SQLBuilder builder);

    /**
     * 指执行SQL
     *
     * @param sql SQL
     * @return 执行结果 - 影响条数
     */
    @NotNull
    int[] executeBatch(@NotNull String... sql);

    /**
     * 指执行SQL
     *
     * @param sql         SQL
     * @param paramsArray 数据
     * @return 执行结果 - 影响条数
     */
    @NotNull
    int[] executeBatch(@NotNull String sql, Object[]... paramsArray);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(@NotNull String sql, RowMapper<T> mapper, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(@NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(@NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(@NotNull SQLBuilder builder, @NotNull Class<T> type);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @NotNull
    List<Map<String, Object>> queryListMap(@NotNull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @NotNull
    List<Map<String, Object>> queryListMap(@NotNull SQLBuilder builder);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryListSingle(@NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryListSingle(@NotNull SQLBuilder builder, Class<T> type);

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int start, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params);

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int start, int limit, @NotNull SQLBuilder builder, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int start, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int start, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type);

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @NotNull
    List<Map<String, Object>> queryListMap(int start, int limit, @NotNull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @return 查询结果
     */
    @NotNull
    List<Map<String, Object>> queryListMap(int start, int limit, @NotNull SQLBuilder builder);

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryListSingle(int start, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryListSingle(int start, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int limit, String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryList(int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @NotNull
    List<Map<String, Object>> queryListMap(int limit, @NotNull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @return 查询结果
     */
    @NotNull
    List<Map<String, Object>> queryListMap(int limit, @NotNull SQLBuilder builder);


    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryListSingle(int limit, @NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> List<T> queryListSingle(int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@NotNull String sql, @NotNull RowMapper<T> mapper, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObject(@NotNull SQLBuilder builder, @NotNull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryObjectMap(@NotNull String sql, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryObjectMap(@NotNull SQLBuilder builder);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    <T> T queryObjectSingle(@NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable
    <T> T queryObjectSingle(@NotNull SQLBuilder builder, @NotNull Class<T> type);

    /**
     * 查询 String 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    String queryString(String sql, Object... params);

    /**
     * 查询 String 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    String queryString(@NotNull SQLBuilder builder);

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Long queryLong(@NotNull String sql, Object... params);

    /**
     * 查询 Long 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Long queryLong(@NotNull SQLBuilder builder);

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Integer queryInt(@NotNull String sql, Object... params);

    /**
     * 查询 Integer 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Integer queryInt(@NotNull SQLBuilder builder);

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Short queryShort(@NotNull String sql, Object... params);

    /**
     * 查询 Short 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Short queryShort(@NotNull SQLBuilder builder);

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Byte queryByte(@NotNull String sql, Object... params);

    /**
     * 查询 Byte 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Byte queryByte(@NotNull SQLBuilder builder);

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Double queryDouble(@NotNull String sql, Object... params);

    /**
     * 查询 Double 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Double queryDouble(@NotNull SQLBuilder builder);

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Float queryFloat(@NotNull String sql, Object... params);

    /**
     * 查询 Float 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Float queryFloat(@NotNull SQLBuilder builder);

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Boolean queryBoolean(@NotNull String sql, Object... params);

    /**
     * 查询 Boolean 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Boolean queryBoolean(@NotNull SQLBuilder builder);

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Timestamp queryTimestamp(@NotNull String sql, Object... params);

    /**
     * 查询 Timestamp 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Timestamp queryTimestamp(@NotNull SQLBuilder builder);

    /**
     * 查询 Date 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Date queryDate(@NotNull String sql, Object... params);

    /**
     * 查询 Date 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Date queryDate(@NotNull SQLBuilder builder);

    /**
     * 查询 Time 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Time queryTime(@NotNull String sql, Object... params);

    /**
     * 查询 Time 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Time queryTime(@NotNull SQLBuilder builder);

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> Page<T> queryPage(int page, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params);

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> Page<T> queryPage(int page, int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> Page<T> queryPage(int page, int limit, String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> Page<T> queryPage(int page, int limit, @NotNull SQLBuilder builder, Class<T> type);

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @NotNull
    Page<Map<String, Object>> queryPageMap(int page, int limit, @NotNull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @return 查询结果
     */
    @NotNull
    Page<Map<String, Object>> queryPageMap(int page, int limit, @NotNull SQLBuilder builder);

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> Page<T> queryPageSingle(int page, int limit, @NotNull String sql, @NotNull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @NotNull
    <T> Page<T> queryPageSingle(int page, int limit, @NotNull SQLBuilder builder, @NotNull Class<T> type);

}