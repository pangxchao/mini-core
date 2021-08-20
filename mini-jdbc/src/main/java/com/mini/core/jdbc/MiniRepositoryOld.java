package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.*;
import com.mini.core.jdbc.builder.fragment.*;
import com.mini.core.util.holder.FieldHolder;
import org.springframework.context.ApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public interface MiniRepositoryOld extends MiniJdbcTemplate {
    /**
     * 执行SQL
     *
     * @param sql       SQL
     * @param params    参数
     * @param keyHolder 自增ID获取
     * @return 执行结果 - 影响条数
     */
    int execute(String sql, @Nullable Object[] params, GeneratedKeyHolder keyHolder);

    /**
     * 执行SQL
     *
     * @param sql       SQL和参数
     * @param keyHolder 自增ID获取
     * @return 执行结果 - 影响条数
     */
    default int execute(AbstractSql<?> sql, GeneratedKeyHolder keyHolder) {
        return execute(sql.getSql(), sql.getArgs(), keyHolder);
    }

    /**
     * 执行SQL
     *
     * @param sql SQL和参数
     * @return 执行结果 - 影响条数
     */
    default int execute(AbstractSql<?> sql) {
        return update(sql.getSql(), sql.getArgs());
    }


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
    default <T> T queryObject(String sql, @Nullable Object[] params, RowMapper<T> mapper) {
        return ofNullable(queryList(0, 1, sql, params, mapper)).map(List::iterator)
                .filter(Iterator::hasNext).map(Iterator::next).orElse(null);
    }

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
     * 添加数据
     *
     * @param table     添加表
     * @param keyHolder 自增ID获取
     * @param consumer  添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int insert(String table, GeneratedKeyHolder keyHolder, Consumer<InsertFragment<?>> consumer) {
        return MiniRepositoryOld.this.execute(new InsertSql() {{
            consumer.accept(insertInto(table));
        }}, keyHolder);
    }

    /**
     * 添加数据
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int insert(String table, Consumer<InsertFragment<?>> consumer) {
        return MiniRepositoryOld.this.execute(new InsertSql() {{
            consumer.accept(insertInto(table));
        }});
    }

    /**
     * 添加实体信息，主键或者唯一索引冲突时修改
     *
     * @param entity      实体信息
     * @param includeNull true-SQL语句中包含实体字段的null值
     * @param <T>         实体类型
     * @return 执行结果
     */
    <T> int insertOrUpdate(T entity, boolean includeNull);

    /**
     * 添加实体信息，主键或者唯一索引冲突时修改
     * <p>SQL语句中默认包含Null值</p>
     *
     * @param entity 实体信息
     * @param <T>    实体类型
     * @return 执行结果
     */
    default <T> int insertOrUpdate(T entity) {
        return insertOrUpdate(entity, false);
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param table    添加表
     *                 keyHolder 自增ID获取
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int replace(String table, GeneratedKeyHolder keyHolder, Consumer<ReplaceFragment<?>> consumer) {
        return MiniRepositoryOld.this.execute(new ReplaceSql() {{
            consumer.accept(replaceInto(table));
        }}, keyHolder);
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int replace(String table, Consumer<ReplaceFragment<?>> consumer) {
        return MiniRepositoryOld.this.execute(new ReplaceSql() {{
            consumer.accept(replaceInto(table));
        }});
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param entity      实体信息
     * @param includeNull true-SQL语句中包含实体字段的null值
     * @param <T>         实体类型
     * @return 执行结果
     */

    <T> int replace(T entity, boolean includeNull);

    /**
     * 添加数据-如果唯一索引重复则替换
     * <p>SQL语句中不包含实体字段中的null值</p>
     *
     * @param entity 实体信息
     * @param <T>    实体类型
     * @return 执行结果
     */
    default <T> int replace(T entity) {
        return replace(entity, false);
    }

    /**
     * 添加数据
     *
     * @param entity      实体信息
     * @param includeNull true-SQL语句中包含实体字段的null值
     * @param <T>         实体类型
     * @return 执行结果
     */
    <T> int insert(T entity, boolean includeNull);

    /**
     * 添加数据-如果唯一索引重复则替换
     * <p>SQL语句中不包含实体字段中的null值</p>
     *
     * @param entity 实体信息
     * @param <T>    实体类型
     * @return 执行结果
     */
    default <T> int insert(T entity) {
        return insert(entity, false);
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int update(String table, Consumer<UpdateFragment<?>> consumer) {
        return MiniRepositoryOld.this.execute(new UpdateSql() {{
            consumer.accept(this.update(table));
        }});
    }

    /**
     * 根据ID修改数据
     *
     * @param entity      实体信息
     * @param includeNull true-SQL语句中包含实体字段的null值
     * @param <T>         实体类型
     * @return 执行结果
     */
    <T> int update(T entity, boolean includeNull);

    /**
     * 根据ID修改数据
     * <p>SQL语句中不包含实体字段中的null值</p>
     *
     * @param entity 实体信息
     * @param <T>    实体类型
     * @return 执行结果
     */
    default <T> int update(T entity) {
        return update(entity, false);
    }

    /**
     * 添加数据-如果唯一索引重复则替换
     *
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int delete(String table, Consumer<DeleteFragment<?>> consumer) {
        return MiniRepositoryOld.this.execute(new DeleteSql() {{
            consumer.accept(delete().from(table));
        }});
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param offset   跳过的数据条数
     * @param size     查询结果数量
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    <T> List<T> select(int offset, int size, Class<T> type, Consumer<SelectFragment<?>> consumer);


    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param pageable 分页工具
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    <T> Page<T> select(Pageable pageable, Class<T> type, Consumer<SelectFragment<?>> consumer);


    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    <T> List<T> select(Class<T> type, Consumer<SelectFragment<?>> consumer);

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param pageable 分页工具
     * @param type     实体类型
     * @return 查询结果
     */
    <T> Page<T> select(Pageable pageable, Class<T> type);

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param offset 跳过的数据条数
     * @param size   查询结果数量
     * @param type   实体类型
     * @return 查询结果
     */
    <T> List<T> select(int offset, int size, Class<T> type);

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type 实体类型
     * @return 查询结果
     */
    <T> List<T> select(Class<T> type);

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    @Nullable
    <T> T selectOne(Class<T> type, Consumer<SelectFragment<?>> consumer);

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type 实体类型
     * @return 查询结果
     */
    @Nullable
    <T> T selectOne(Class<T> type);

    class AutoMiniId extends ApplicationEvent {
        private final GeneratedKeyHolder holder;

        public AutoMiniId(Object source, GeneratedKeyHolder holder) {
            super(source);
            this.holder = holder;
        }

        public GeneratedKeyHolder getHolder() {
            return holder;
        }

        public final Class<?> getType() {
            return getSource().getClass();
        }
    }

    class MiniId extends ApplicationEvent {
        private final FieldHolder<?> field;

        public MiniId(FieldHolder<?> field, Object entity) {
            super(entity);
            this.field = field;
        }

        public FieldHolder<?> getField() {
            return field;
        }
    }
}
