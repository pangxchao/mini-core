package com.mini.core.jdbc;

import com.mini.core.jdbc.wrapper.SQLWrapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Nonnull;
import java.util.*;

import static java.util.Optional.ofNullable;

/**
 * Mini JDBC 操作接口
 *
 * @author pangchao
 */
public interface MiniJdbcTemplate extends JdbcOperations {

    /**
     * 判断当前连接是否存在指定表
     *
     * @param tableName 表名
     * @return 查询结棍
     */
    default boolean hasTable(String tableName) {
        return Objects.requireNonNullElse(execute((ConnectionCallback<Boolean>) connection -> {
            try (var rs = connection.getMetaData().getTables(connection.getCatalog(),
                    connection.getSchema(), tableName, null)) {
                return rs.next();
            }
        }), false);
    }

    /**
     * 判断当前连接是否存在指定表的指定字段
     *
     * @param tableName  表名
     * @param columnName 字段名
     * @return 查询结果
     */
    default boolean hasColumn(String tableName, String columnName) {
        return Objects.requireNonNullElse(execute((ConnectionCallback<Boolean>) connection -> {
            try (var rs = connection.getMetaData().getColumns(connection.getCatalog(),
                    connection.getSchema(), tableName, columnName)) {
                return rs.next();
            }
        }), false);
    }

    /**
     * 执行SQL
     *
     * @param wrapper SQL包装器
     * @return 执行结果 - 影响条数
     */
    default int update(@Nonnull SQLWrapper wrapper) {
        return update(wrapper.sql(), wrapper.args());
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(String sql, RowMapper<T> mapper, Object... params) {
        return this.query(sql, mapper, params);
    }

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @param mapper  映射器
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(SQLWrapper wrapper, RowMapper<T> mapper) {
        return queryList(wrapper.sql(), mapper, wrapper.args());
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> queryList(String sql, Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @param type    类型类对象
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> queryList(SQLWrapper wrapper, Class<T> type) {
        return queryList(wrapper.sql(), type, wrapper.args());
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    List<Map<String, Object>> queryMapList(String sql, Object... params);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default List<Map<String, Object>> queryMapList(SQLWrapper wrapper) {
        return queryMapList(wrapper.sql(), wrapper.args());
    }

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    <T> List<T> querySingleList(String sql, Class<T> type, Object... params);

    /**
     * 查询列表
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default <T> List<T> querySingleList(SQLWrapper wrapper, Class<T> type) {
        return querySingleList(wrapper.sql(), type, wrapper.args());
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default <T> Optional<T> queryOne(String sql, RowMapper<T> mapper, Object... params) {
        return ofNullable(query(sql, params, rs -> rs.next() ? mapper.mapRow(rs, 0) : null));
    }

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param mapper  映射器
     * @return 查询结果
     */
    @Nonnull
    default <T> Optional<T> queryOne(SQLWrapper wrapper, RowMapper<T> mapper) {
        return queryOne(wrapper.sql(), mapper, wrapper.args());
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    <T> Optional<T> queryOne(String sql, Class<T> type, Object... params);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param type    值的类型
     * @return 查询结果
     */
    @Nonnull
    default <T> Optional<T> queryOne(SQLWrapper wrapper, Class<T> type) {
        return queryOne(wrapper.sql(), type, wrapper.args());
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    Optional<Map<String, Object>> queryMapOne(String sql, Object... params);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Map<String, Object>> queryMapOne(SQLWrapper wrapper) {
        return queryMapOne(wrapper.sql(), wrapper.args());
    }

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    <T> Optional<T> querySingleOne(String sql, Class<T> type, Object... params);

    /**
     * 查询对象
     *
     * @param wrapper SQL包装器
     * @param type    值的类型
     * @return 查询结果
     */
    @Nonnull
    default <T> Optional<T> querySingleOne(SQLWrapper wrapper, Class<T> type) {
        return querySingleOne(wrapper.sql(), type, wrapper.args());
    }

    /**
     * 查询 String 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<String> queryString(String sql, Object... params) {
        return querySingleOne(sql, String.class, params);
    }

    /**
     * 查询 String 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<String> queryString(SQLWrapper wrapper) {
        return querySingleOne(wrapper, String.class);
    }

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Long> queryLong(String sql, Object... params) {
        return querySingleOne(sql, Long.class, params);
    }

    /**
     * 查询 Long 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Long> queryLong(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Long.class);
    }

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Integer> queryInt(String sql, Object... params) {
        return querySingleOne(sql, Integer.class, params);
    }

    /**
     * 查询 Integer 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Integer> queryInt(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Integer.class);
    }

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Short> queryShort(String sql, Object... params) {
        return querySingleOne(sql, Short.class, params);
    }

    /**
     * 查询 Short 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Short> queryShort(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Short.class);
    }

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Byte> queryByte(String sql, Object... params) {
        return querySingleOne(sql, Byte.class, params);
    }

    /**
     * 查询 Byte 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Byte> queryByte(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Byte.class);
    }

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Double> queryDouble(String sql, Object... params) {
        return querySingleOne(sql, Double.class, params);
    }

    /**
     * 查询 Double 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Double> queryDouble(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Double.class);
    }

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Float> queryFloat(String sql, Object... params) {
        return querySingleOne(sql, Float.class, params);
    }

    /**
     * 查询 Float 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Float> queryFloat(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Float.class);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Boolean> queryBoolean(String sql, Object... params) {
        return querySingleOne(sql, Boolean.class, params);
    }

    /**
     * 查询 Boolean 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Boolean> queryBoolean(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Boolean.class);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    @Nonnull
    default Optional<Date> queryDate(String sql, Object... params) {
        return querySingleOne(sql, Date.class, params);
    }

    /**
     * 查询 Timestamp 对象
     *
     * @param wrapper SQL包装器
     * @return 查询结果
     */
    @Nonnull
    default Optional<Date> queryDate(SQLWrapper wrapper) {
        return querySingleOne(wrapper, Date.class);
    }
}
