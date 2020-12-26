package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.*;
import com.mini.core.jdbc.builder.fragment.*;
import com.mini.core.jdbc.builder.support.Join;
import com.mini.core.util.holder.ClassHolder;
import com.mini.core.util.holder.FieldHolder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public interface MiniRepository {
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
     * @param table    添加表
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int insert(String table, Consumer<InsertFragment<?>> consumer) {
        return MiniRepository.this.execute(new InsertSql() {{
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
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    default <T> int insertOrUpdate(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return this.insert(getTableName(holder), builder -> { //
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    builder.onKeyFromInsert(column.value());
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    builder.onKeyFromInsert(column.value());
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    builder.onKeyFromInsert(column.value());
                    return;
                }
                // 其它普通字段处理
                if (otherSetHandler(entity, includeNull, builder, field, column)) {
                    builder.onKeyFromInsert(column.value());
                }
            });
        });
    }

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
     * @param consumer 添加字段设置回调
     * @return 执行结果 - 影响条数
     */
    default int replace(String table, Consumer<ReplaceFragment<?>> consumer) {
        return MiniRepository.this.execute(new ReplaceSql() {{
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
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    default <T> int replace(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return this.replace(getTableName(holder), builder -> { //
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    return;
                }
                // 其它普通字段处理
                otherSetHandler(entity, includeNull, builder, field, column);
            });
        });

    }

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
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    default <T> int insert(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return this.insert(getTableName(holder), builder -> { //
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    return;
                }
                // 其它普通字段处理
                otherSetHandler(entity, includeNull, builder, field, column);
            });
        });
    }

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
        return MiniRepository.this.execute(new UpdateSql() {{
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
    @SuppressWarnings({"unchecked", "DuplicatedCode"})
    default <T> int update(T entity, boolean includeNull) {
        Class<? extends T> type = (Class<? extends T>) entity.getClass();
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return this.update(getTableName(holder), builder -> {
            Assert.isTrue(holder.getFields().values().stream().noneMatch(it -> {
                // 为了数据安全，修改时必须有ID字段，否则报错，
                return Objects.nonNull(it.getAnnotation(Id.class));
            }), "No @Id in the " + type.getName());
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                //  有ID注解的字段，修改时不修改其值
                if (field.getAnnotation(Id.class) != null) {
                    return;
                }
                // 处理版本字段 @Version
                if (versionSetHandler(builder, entity, field, column)) {
                    return;
                }
                // 处理创建时间字体 @CreatedDate
                if (createdDateHandler(builder, field, column)) {
                    return;
                }
                // 处理修改时间字段  @LastModifiedDate
                if (lastModifiedDateHandler(builder, field, column)) {
                    return;
                }
                // 其它普通字段处理
                otherSetHandler(entity, includeNull, builder, field, column);
            });
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;
                // 处理ID条件
                if (idWhereHandler(entity, builder, field, column)) {
                    return;
                }
                // 处理版本条件
                versionWhereHandler(builder, entity, field, column);
            });
        });
    }

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
        return MiniRepository.this.execute(new DeleteSql() {{
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
    default <T> List<T> select(int offset, int size, Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return queryList(offset, size, getSelectSql(type, consumer), type);
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
        return queryPage(pageable, getSelectSql(type, consumer), type);
    }


    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type     实体类型
     * @param consumer SQL 回调
     * @return 查询结果
     */
    default <T> List<T> select(Class<T> type, Consumer<SelectFragment<?>> consumer) {
        return queryList(getSelectSql(type, consumer), type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param pageable 分页工具
     * @param type     实体类型
     * @return 查询结果
     */
    default <T> Page<T> select(Pageable pageable, Class<T> type) {
        final SelectSql sql = getSelectSql(type, null);
        return queryPage(pageable, sql, type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param offset 跳过的数据条数
     * @param size   查询结果数量
     * @param type   实体类型
     * @return 查询结果
     */
    default <T> List<T> select(int offset, int size, Class<T> type) {
        final SelectSql sql = getSelectSql(type, null);
        return queryList(offset, size, sql, type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type 实体类型
     * @return 查询结果
     */
    default <T> List<T> select(Class<T> type) {
        var sql = getSelectSql(type, null);
        return queryList(sql, type);
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
        return this.queryObject(getSelectSql(type, consumer), type);
    }

    /**
     * 根据实体和注解，查询实体对应的数据库信息
     *
     * @param type 实体类型
     * @return 查询结果
     */
    @Nullable
    default <T> T selectOne(Class<T> type) {
        var sql = getSelectSql(type, null);
        return queryObject(sql, type);
    }

    // 获取表名称
    private String getTableName(ClassHolder<?> holder) {
        var table = holder.getAnnotation(Table.class);
        Objects.requireNonNull(table);

        if (StringUtils.hasText(table.value())) {
            return table.value();
        }
        return holder.getName();
    }

    private static boolean checkVersionType(Class<?> type) {
        return type == Long.class || type == long.class || type == Integer.class || type == int.class;
    }

    // 处理设置中的版本字段 @Version
    @SuppressWarnings("DuplicatedCode")
    private boolean versionSetHandler(SetFragment<?> builder, Object entity, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(Version.class))) {
            return false;
        }
        Assert.isTrue(checkVersionType(field.getType()), "@Version must be of type long or int");
        Object value = field.getValue(entity), nextValue = 1;
        if (value instanceof Integer) {
            nextValue = ((Integer) value) + 1;
        } else if (value instanceof Long) {
            nextValue = ((Long) value) + 1;
        }
        builder.set(column.value(), nextValue);
        return true;
    }

    // 处理条件中的版本字段 @Version
    @SuppressWarnings({"DuplicatedCode", "UnusedReturnValue"})
    private boolean versionWhereHandler(WhereFragment<?> builder, Object entity, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(Version.class))) {
            return false;
        }
        Assert.isTrue(checkVersionType(field.getType()), "@Version must be of type long or int");
        Optional.ofNullable(field.getValue(entity)).ifPresentOrElse(it -> builder.whereEq(column.value(), it),
                () -> builder.whereIsNull(column.value()));
        return true;
    }

    // 处理创建时间字段 @CreatedDate
    private boolean createdDateHandler(SetFragment<?> builder, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(CreatedDate.class))) {
            return false;
        }
        builder.set(column.value(), new java.util.Date());
        return true;
    }

    // 处理修改时间字段  @LastModifiedDate
    private boolean lastModifiedDateHandler(SetFragment<?> builder, FieldHolder<?> field, Column column) {
        if (Objects.isNull(field.getAnnotation(LastModifiedDate.class))) {
            return false;
        }
        builder.set(column.value(), new java.util.Date());
        return true;
    }

    // 其它普通字段处理
    private <T> boolean otherSetHandler(T entity, boolean includeNull, SetFragment<?> builder, FieldHolder<? extends T> field, Column column) {
        final Object value = field.getValue(entity);
        if (includeNull || Objects.nonNull(value)) {
            builder.set(column.value(), value);
            return true;
        }
        return false;
    }

    // 处理ID条件 @ID
    private <T> boolean idWhereHandler(T entity, WhereFragment<?> builder, FieldHolder<? extends T> field, Column column) {
        if (Objects.isNull(field.getAnnotation(Id.class))) {
            return false;
        }
        Object value = field.getValue(entity);
        builder.whereEq(column.value(), value);
        return true;
    }

    // 获取查询Sql
    private <T> SelectSql getSelectSql(Class<T> type, @Nullable Consumer<SelectFragment<?>> consumer) {
        ClassHolder<? extends T> holder = ClassHolder.create(type);
        return new SelectSql() {{
            holder.getFields().values().forEach(field -> {
                var column = field.getAnnotation(Column.class);
                if (Objects.isNull(column)) return;

                if (StringUtils.hasText(column.value())) {
                    select(column.value(), field.getName());
                } else select(field.getName());

            });
            // 生成查询表名
            this.from(MiniRepository.this.getTableName(holder));
            // 生成联合查询表名
            for (Join join : holder.getAnnotationsByType(Join.class)) {
                join.type().exe(this, join.table(), join.on());
            }
            // 添加其它条件
            if (Objects.nonNull(consumer)) {
                consumer.accept(this);
            }
        }};
    }
}
