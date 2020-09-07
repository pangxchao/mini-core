package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.Mapper;
import com.mini.core.jdbc.model.Page;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
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
    int execute(@Nonnull String sql, Object... params);

    /**
     * 执行SQL
     *
     * @param builder SQLBuilder 对象
     * @return 执行结果 - 影响条数
     */
    int execute(@Nonnull SQLBuilder builder);

    /**
     * 指执行SQL
     *
     * @param sql SQL
     * @return 执行结果 - 影响条数
     */
    int[] executeBatch(@Nonnull String... sql);

    /**
     * 指执行SQL
     *
     * @param sql         SQL
     * @param paramsArray 数据
     * @return 执行结果 - 影响条数
     */
    int[] executeBatch(@Nonnull String sql, Object[]... paramsArray);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryList(@Nonnull String sql, Mapper<T> mapper, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryList(@Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryList(@Nonnull String sql, @Nonnull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryList(@Nonnull SQLBuilder builder, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(@Nonnull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(@Nonnull SQLBuilder builder);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryListSingle(@Nonnull String sql, @Nonnull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryListSingle(@Nonnull SQLBuilder builder, Class<T> type);

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
    @Nonnull <T> List<T> queryList(int start, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params);

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
    @Nonnull <T> List<T> queryList(int start, int limit, @Nonnull SQLBuilder builder, Mapper<T> mapper);

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
    @Nonnull <T> List<T> queryList(int start, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params);

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
    @Nonnull <T> List<T> queryList(int start, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(int start, int limit, @Nonnull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(int start, int limit, @Nonnull SQLBuilder builder);

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
    @Nonnull <T> List<T> queryListSingle(int start, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params);

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
    @Nonnull <T> List<T> queryListSingle(int start, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type);

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
    @Nonnull <T> List<T> queryList(int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryList(int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper);

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
    @Nonnull <T> List<T> queryList(int limit, String sql, @Nonnull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryList(int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type);

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(int limit, @Nonnull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryListMap(int limit, @Nonnull SQLBuilder builder);


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
    @Nonnull <T> List<T> queryListSingle(int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nonnull <T> List<T> queryListSingle(int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nullable <T> T queryObject(@Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param mapper  映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    @Nullable <T> T queryObject(@Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    @Nullable <T> T queryObject(@Nonnull String sql, @Nonnull Class<T> type, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable <T> T queryObject(@Nonnull SQLBuilder builder, @Nonnull Class<T> type);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryObjectMap(@Nonnull String sql, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryObjectMap(@Nonnull SQLBuilder builder);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    @Nullable <T> T queryObjectSingle(@Nonnull String sql, @Nonnull Class<T> type, Object... params);

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    @Nullable <T> T queryObjectSingle(@Nonnull SQLBuilder builder, @Nonnull Class<T> type);

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
    String queryString(@Nonnull SQLBuilder builder);

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Long queryLong(@Nonnull String sql, Object... params);

    /**
     * 查询 Long 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Long queryLong(@Nonnull SQLBuilder builder);

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Integer queryInt(@Nonnull String sql, Object... params);

    /**
     * 查询 Integer 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Integer queryInt(@Nonnull SQLBuilder builder);

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Short queryShort(@Nonnull String sql, Object... params);

    /**
     * 查询 Short 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Short queryShort(@Nonnull SQLBuilder builder);

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Byte queryByte(@Nonnull String sql, Object... params);

    /**
     * 查询 Byte 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Byte queryByte(@Nonnull SQLBuilder builder);

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Double queryDouble(@Nonnull String sql, Object... params);

    /**
     * 查询 Double 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Double queryDouble(@Nonnull SQLBuilder builder);

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Float queryFloat(@Nonnull String sql, Object... params);

    /**
     * 查询 Float 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Float queryFloat(@Nonnull SQLBuilder builder);

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Boolean queryBoolean(@Nonnull String sql, Object... params);

    /**
     * 查询 Boolean 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Boolean queryBoolean(@Nonnull SQLBuilder builder);

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Timestamp queryTimestamp(@Nonnull String sql, Object... params);

    /**
     * 查询 Timestamp 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Timestamp queryTimestamp(@Nonnull SQLBuilder builder);

    /**
     * 查询 Date 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Date queryDate(@Nonnull String sql, Object... params);

    /**
     * 查询 Date 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Date queryDate(@Nonnull SQLBuilder builder);

    /**
     * 查询 Time 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Time queryTime(@Nonnull String sql, Object... params);

    /**
     * 查询 Time 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nullable
    Time queryTime(@Nonnull SQLBuilder builder);

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
    @Nonnull <T> Page<T> queryPage(int page, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params);

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
    @Nonnull <T> Page<T> queryPage(int page, int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper);

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
    @Nonnull <T> Page<T> queryPage(int page, int limit, String sql, @Nonnull Class<T> type, Object... params);

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
    @Nonnull <T> Page<T> queryPage(int page, int limit, @Nonnull SQLBuilder builder, Class<T> type);

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    Page<Map<String, Object>> queryPageMap(int page, int limit, @Nonnull String sql, Object... params);

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @return 查询结果
     */
    @Nonnull
    Page<Map<String, Object>> queryPageMap(int page, int limit, @Nonnull SQLBuilder builder);

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
    @Nonnull <T> Page<T> queryPageSingle(int page, int limit, @Nonnull String sql, @Nonnull Class<T> type, Object... params);

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
    @Nonnull <T> Page<T> queryPageSingle(int page, int limit, @Nonnull SQLBuilder builder, @Nonnull Class<T> type);

}