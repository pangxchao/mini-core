package com.mini.core.data;

import com.mini.core.data.builder.*;
import com.mini.core.data.builder.fragment.*;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface MiniBaseRepository {
    /**
     * 执行SQL
     *
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果 - 影响条数
     */
    int execute(String sql, @Nullable Object[] params);

    /**
     * 执行SQL
     *
     * @param sql SQL和参数
     * @return 执行结果 - 影响条数
     */
    default int execute(AbstractSql<?> sql) {
        return execute(sql.getSql(), sql.getArgs());
    }

    /**
     * 指执行SQL
     *
     * @param sql SQL
     * @return 执行结果 - 影响条数
     */
    int[] executeBatch(String... sql);

    /**
     * 指执行SQL
     *
     * @param sql        SQL
     * @param paramsList 数据
     * @return 执行结果 - 影响条数
     */
    int[] executeBatch(String sql, List<Object[]> paramsList);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    <T> List<T> queryList(String sql, @Nullable Object[] params, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryList(sql.getSql(), sql.getArgs(), mapper);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    <T> List<T> queryList(String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(AbstractSql<?> sql, Class<T> type) {
        return queryList(sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    <T> List<T> queryList(long offset, int size, String sql, @Nullable Object[] params, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryList(offset, size, sql.getSql(), sql.getArgs(), mapper);
    }

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    <T> List<T> queryList(long offset, int size, String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return queryList(offset, size, sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询列表
     *
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(int size, String sql, @Nullable Object[] params, RowMapper<T> mapper) {
        return queryList(0, size, sql, params, mapper);
    }

    /**
     * 查询列表
     *
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryList(0, size, sql, mapper);
    }

    /**
     * 查询列表
     *
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(int size, String sql, @Nullable Object[] params, Class<T> type) {
        return queryList(0, size, sql, params, type);
    }

    /**
     * 查询列表
     *
     * @param size 获取指定的条数
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryList(int size, AbstractSql<?> sql, Class<T> type) {
        return queryList(0, size, sql, type);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    List<Map<String, Object>> queryListMap(String sql, @Nullable Object[] params);

    /**
     * 查询列表
     *
     * @param sql SQL
     * @return 查询结果
     */
    default List<Map<String, Object>> queryListMap(AbstractSql<?> sql) {
        return queryListMap(sql.getSql(), sql.getArgs());
    }

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    List<Map<String, Object>> queryListMap(long offset, int size, String sql, @Nullable Object[] params);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @return 查询结果
     */
    default List<Map<String, Object>> queryListMap(long offset, int size, AbstractSql<?> sql) {
        return queryListMap(offset, size, sql.getSql(), sql.getArgs());
    }

    /**
     * 查询列表
     *
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default List<Map<String, Object>> queryListMap(int size, String sql, @Nullable Object[] params) {
        return queryListMap(0, size, sql, params);
    }

    /**
     * 查询列表
     *
     * @param size 获取指定的条数
     * @param sql  SQL
     * @return 查询结果
     */
    default List<Map<String, Object>> queryListMap(int size, AbstractSql<?> sql) {
        return queryListMap(0, size, sql);
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    <T> List<T> queryListSingle(String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryListSingle(AbstractSql<?> sql, Class<T> type) {
        return queryListSingle(sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    <T> List<T> queryListSingle(long offset, int size, String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param offset 跳过的数据条数
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryListSingle(long offset, int size, AbstractSql<?> sql, Class<T> type) {
        return queryListSingle(offset, size, sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询列表
     *
     * @param size   获取指定的条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryListSingle(int size, String sql, @Nullable Object[] params, Class<T> type) {
        return queryListSingle(0, size, sql, params, type);
    }

    /**
     * 查询列表
     *
     * @param size 获取指定的条数
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    default <T> List<T> queryListSingle(int size, AbstractSql<?> sql, Class<T> type) {
        return queryListSingle(0, size, sql, type);
    }

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
    <T> T queryObject(String sql, @Nullable Object[] params, RowMapper<T> mapper);

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    @Nullable
    default <T> T queryObject(AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryObject(sql.getSql(), sql.getArgs(), mapper);
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
    <T> T queryObject(String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询对象
     *
     * @param sql  SQL
     * @param type 值的类型
     * @return 查询结果
     */
    @Nullable
    default <T> T queryObject(AbstractSql<?> sql, Class<T> type) {
        return queryObject(sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    Map<String, Object> queryObjectMap(String sql, @Nullable Object[] params);

    /**
     * 查询对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Map<String, Object> queryObjectMap(AbstractSql<?> sql) {
        return queryObjectMap(sql.getSql(), sql.getArgs());
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
    <T> T queryObjectSingle(String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询对象
     *
     * @param sql  SQL
     * @param type 值的类型
     * @return 查询结果
     */
    @Nullable
    default <T> T queryObjectSingle(AbstractSql<?> sql, Class<T> type) {
        return queryObjectSingle(sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询 String 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default String queryString(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, String.class);
    }

    /**
     * 查询 String 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default String queryString(AbstractSql<?> sql) {
        return queryObjectSingle(sql, String.class);
    }

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Long.class);
    }

    /**
     * 查询 Long 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Long queryLong(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Long.class);
    }

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Integer.class);
    }

    /**
     * 查询 Integer 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Integer queryInt(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Integer.class);
    }

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Short.class);
    }

    /**
     * 查询 Short 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Short queryShort(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Short.class);
    }

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Byte.class);
    }

    /**
     * 查询 Byte 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Byte queryByte(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Byte.class);
    }

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Double.class);
    }

    /**
     * 查询 Double 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Double queryDouble(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Double.class);
    }

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Float.class);
    }

    /**
     * 查询 Float 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Float queryFloat(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Float.class);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Boolean.class);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Boolean queryBoolean(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Boolean.class);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Timestamp queryTimestamp(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Timestamp.class);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Timestamp queryTimestamp(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Timestamp.class);
    }

    /**
     * 查询 Date 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Date.class);
    }

    /**
     * 查询 Date 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Date queryDate(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Date.class);
    }

    /**
     * 查询 Time 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nullable
    default Time queryTime(String sql, @Nullable Object[] params) {
        return queryObjectSingle(sql, params, Time.class);
    }

    /**
     * 查询 Time 对象
     *
     * @param sql SQL
     * @return 查询结果
     */
    @Nullable
    default Time queryTime(AbstractSql<?> sql) {
        return queryObjectSingle(sql, Time.class);
    }

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
    <T> Page<T> queryPage(Pageable pageable, String sql, @Nullable Object[] params, RowMapper<T> mapper);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param mapper   映射器
     * @param <T>      解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPage(Pageable pageable, AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryPage(pageable, sql.getSql(), sql.getArgs(), mapper);
    }

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
    <T> Page<T> queryPage(Pageable pageable, String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param type     类型类对象
     * @param <T>      解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPage(Pageable pageable, AbstractSql<?> sql, Class<T> type) {
        return queryPage(pageable, sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询列表
     *
     * @param page   页码数，第一页为0
     * @param size   分页数据 每页条数
     * @param sql    SQL
     * @param params 参数
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPage(int page, int size, String sql, @Nullable Object[] params, RowMapper<T> mapper) {
        return queryPage(PageRequest.of(page, size), sql, params, mapper);
    }

    /**
     * 查询列表
     *
     * @param page   页码数，第一页为0
     * @param size   分页数据 每页条数
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPage(int page, int size, AbstractSql<?> sql, RowMapper<T> mapper) {
        return queryPage(PageRequest.of(page, size), sql, mapper);
    }

    /**
     * 查询列表
     *
     * @param page   页码数，第一页为0
     * @param size   分页数据 每页条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPage(int page, int size, String sql, @Nullable Object[] params, Class<T> type) {
        return queryPage(PageRequest.of(page, size), sql, params, type);
    }

    /**
     * 查询列表
     *
     * @param page 页码数，第一页为0
     * @param size 分页数据 每页条数
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPage(int page, int size, AbstractSql<?> sql, Class<T> type) {
        return queryPage(PageRequest.of(page, size), sql, type);
    }

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param params   参数
     * @return 查询结果
     */
    Page<Map<String, Object>> queryPageMap(Pageable pageable, String sql, @Nullable Object[] params);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @return 查询结果
     */
    default Page<Map<String, Object>> queryPageMap(Pageable pageable, AbstractSql<?> sql) {
        return queryPageMap(pageable, sql.getSql(), sql.getArgs());
    }

    /**
     * 查询列表
     *
     * @param page   页码数，第一页为0
     * @param size   分页数据 每页条数
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    default Page<Map<String, Object>> queryPageMap(int page, int size, String sql, @Nullable Object[] params) {
        return queryPageMap(PageRequest.of(page, size), sql, params);
    }

    /**
     * 查询列表
     *
     * @param page 页码数，第一页为0
     * @param size 分页数据 每页条数
     * @param sql  SQL
     * @return 查询结果
     */
    default Page<Map<String, Object>> queryPageMap(int page, int size, AbstractSql<?> sql) {
        return queryPageMap(PageRequest.of(page, size), sql);
    }

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
    <T> Page<T> queryPageSingle(Pageable pageable, String sql, @Nullable Object[] params, Class<T> type);

    /**
     * 查询列表
     *
     * @param pageable 分页数据
     * @param sql      SQL
     * @param type     类型类对象
     * @param <T>      解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPageSingle(Pageable pageable, AbstractSql<?> sql, Class<T> type) {
        return queryPageSingle(pageable, sql.getSql(), sql.getArgs(), type);
    }

    /**
     * 查询列表
     *
     * @param page   页码数，第一页为0
     * @param size   分页数据 每页条数
     * @param sql    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPageSingle(int page, int size, String sql, @Nullable Object[] params, Class<T> type) {
        return queryPageSingle(PageRequest.of(page, size), sql, params, type);
    }

    /**
     * 查询列表
     *
     * @param page 页码数，第一页为0
     * @param size 分页数据 每页条数
     * @param sql  SQL
     * @param type 类型类对象
     * @param <T>  解析器类型
     * @return 查询结果
     */
    default <T> Page<T> queryPageSingle(int page, int size, AbstractSql<?> sql, Class<T> type) {
        return queryPageSingle(PageRequest.of(page, size), sql, type);
    }

    /**
     * 添加数据
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int insert(String table, Consumer<InsertFragment<?>> consumer) {
        return this.execute(new InsertSql() {{
            this.insertInto(table);
            consumer.accept(this);
        }});
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int replace(String table, Consumer<SetFragment<?>> consumer) {
        return this.execute(new ReplaceSql() {{
            this.replaceInto(table);
            consumer.accept(this);
        }});
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int delete(String table, Consumer<DeleteFragment<?>> consumer) {
        return this.execute(new DeleteSql() {{
            this.delete().from(table);
            consumer.accept(this);
        }});
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int update(String table, Consumer<UpdateFragment<?>> consumer) {
        return this.execute(new UpdateSql() {{
            consumer.accept(update(table));
        }});
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param pageable 分页工具
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    default <T> Page<T> select(Pageable pageable, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return this.queryPage(pageable, new SelectSql(type) {{
            consumer.accept(this);
        }}, type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param page     分页-页码数，0为第一页
     * @param size     分页-每页条数
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    default <T> Page<T> select(int page, int size, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return this.queryPage(page, size, new SelectSql(type) {{
            consumer.accept(this);
        }}, type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    default <T> List<T> select(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return this.queryList(new SelectSql(type) {{
            consumer.accept(this);
        }}, type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param pageable 分页工具
     * @param type     实体类型
     * @return 查询结果
     */
    default <T> Page<T> select(Pageable pageable, Class<T> type) {
        return queryPage(pageable, new SelectSql(type), type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param page 分页-页码数，0为第一页
     * @param size 分页-每页条数
     * @param type 实体类型
     * @return 查询结果
     */
    default <T> Page<T> select(int page, int size, Class<T> type) {
        return queryPage(page, size, new SelectSql(type), type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type 实体类型
     * @return 查询结果
     */
    default <T> List<T> select(Class<T> type) {
        return queryList(new SelectSql(type), type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    @Nullable
    default <T> T selectOne(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return this.queryObject(new SelectSql(type) {{
            consumer.accept(this);
        }}, type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type 实体类型
     * @return 查询结果
     */
    @Nullable
    default <T> T selectOne(Class<T> type) {
        return queryObject(new SelectSql(type), type);
    }
}
