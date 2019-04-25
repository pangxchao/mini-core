package com.mini.util.dao;

import com.mini.util.dao.sql.SQLBuilder;
import com.mini.util.lang.ArraysUtil;
import com.mini.util.lang.Function;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public interface IDao extends Connection {
    /**
     * 预处理对象填充
     * @param statement 预处理对象
     * @param params    参数
     * @throws SQLException 执行错误
     */
    default void fullPreparedStatement(PreparedStatement statement, Object[] params) throws SQLException {
        for (int i = 0; params != null && i < params.length; i++) {
            statement.setObject((i + 1), params[i]);
        }
    }

    /**
     * 批量操作
     * @param sql    SQL
     * @param length 数据长度
     * @param batch  回调接口
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    default int[] batch(String sql, int length, Function.FR1<Object[], Integer> batch) throws SQLException {
        int[] result = new int[0];
        try (PreparedStatement statement = this.prepareStatement(sql)) {
            for (int index = 0; index < length; index++) {
                fullPreparedStatement(statement, batch.apply(index));
                // 添加 batch 数据
                statement.addBatch();

                // 提交 batch 数据
                if (index % 500 != 499) continue;
                result = ArraysUtil.concat(result, statement.executeBatch());
            }
            return ArraysUtil.concat(result, statement.executeBatch());
        }
    }

    /**
     * 批量操作
     * @param sql    SQL
     * @param params 参数
     * @return SQL执行结果
     * @throws SQLException 执行错误
     */
    default int[] batch(String sql, Object[][] params) throws SQLException {
        return batch(sql, params.length, index -> params[index]);
    }

    /**
     * 批量操作
     * @param sql        SQL
     * @param dataLength 数据长度
     * @param batch      回调接口
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    default int[] batch(SQLBuilder sql, int dataLength, Function.FR1<Object[], Integer> batch) throws SQLException {
        return batch(sql.content(), dataLength, batch);
    }

    /**
     * 批量操作
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    default int[] batch(SQLBuilder sql, Object[][] params) throws SQLException {
        return batch(sql.content(), params);
    }

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    default int execute(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = this.prepareStatement(sql)) {
            this.fullPreparedStatement(statement, params);
            return statement.executeUpdate();
        }
    }

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return ID
     * @throws SQLException 执行错误
     */
    default long automatic(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = this.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            this.fullPreparedStatement(statement, params);
            statement.executeUpdate();

            // 读取自动生成的ID
            try (ResultSet rs = statement.getGeneratedKeys()) {
                return rs.next() ? rs.getLong(1) : 0;
            }
        }
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     * @throws SQLException 执行错误
     */
    default int execute(SQL sql) throws SQLException {
        return execute(sql.content(), sql.params());
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return ID
     * @throws SQLException 执行错误
     */
    default long automatic(SQL sql) throws SQLException {
        return automatic(sql.content(), sql.params());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default <T> List<T> query(String sql, IMapper<T> mapper, Object... params) throws SQLException {
        try (PreparedStatement statement = prepareStatement(sql, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY)) {
            IDao.this.fullPreparedStatement(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<T> result = new ArrayList<>();
                while (resultSet != null && resultSet.next()) {
                    int number = resultSet.getRow();
                    T t = mapper.execute(resultSet, number);
                    result.add(t);
                }
                return result;
            }
        }
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default <T> List<T> query(SQL sql, IMapper<T> mapper) throws SQLException {
        return query(sql.content(), mapper, sql.params());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default <T> T queryOne(String sql, IMapper<T> mapper, Object... params) throws SQLException {
        try (PreparedStatement statement = prepareStatement(sql, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY)) {
            IDao.this.fullPreparedStatement(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    int number = resultSet.getRow();
                    return mapper.execute(resultSet, number);
                }
                return null;
            }
        }
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param mapper 映射器
     * @param <T>    解析器类型
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default <T> T queryOne(SQL sql, IMapper<T> mapper) throws SQLException {
        return queryOne(sql.content(), mapper, sql.params());
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default String queryString(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getString(1), params);
    }

    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default String queryString(SQL sql) throws SQLException {
        return queryString(sql.content(), sql.params());
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default long queryLong(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getLong(1), params);
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default long queryLong(SQL sql) throws SQLException {
        return queryLong(sql.content(), sql.params());
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default int queryInt(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getInt(1), params);
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default int queryInt(SQL sql) throws SQLException {
        return queryInt(sql.content(), sql.params());
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default short queryShort(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getShort(1), params);
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default short queryShort(SQL sql) throws SQLException {
        return queryShort(sql.content(), sql.params());
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default byte queryByte(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getByte(1), params);
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default byte queryByte(SQL sql) throws SQLException {
        return queryByte(sql.content(), sql.params());
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default double queryDouble(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getDouble(1), params);
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default double queryDouble(SQL sql) throws SQLException {
        return queryDouble(sql.content(), sql.params());
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default float queryFloat(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getFloat(1), params);
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default float queryFloat(SQL sql) throws SQLException {
        return queryFloat(sql.content(), sql.params());
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default boolean queryBoolean(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getBoolean(1), params);
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default boolean queryBoolean(SQL sql) throws SQLException {
        return queryBoolean(sql.content(), sql.params());
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default Timestamp queryTimestamp(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getTimestamp(1), params);
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default Timestamp queryTimestamp(SQL sql) throws SQLException {
        return queryTimestamp(sql.content(), sql.params());
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default Date queryDate(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getDate(1), params);
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default Date queryDate(SQL sql) throws SQLException {
        return queryDate(sql.content(), sql.params());
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default Time queryTime(String sql, Object... params) throws SQLException {
        return queryOne(sql, (rs, n) -> rs.getTime(1), params);
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     * @throws SQLException 执行错误
     */
    default Time queryTime(SQL sql) throws SQLException {
        return queryTime(sql.content(), sql.params());
    }


    /**
     * 查询结果
     * @param paging paging 分页器
     * @param sql    SQL
     * @param row    解析器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> query(Paging paging, String sql, IMapper<T> row, Object... params) throws SQLException {
        paging.setTotal(this.queryInt(this.totals(sql), params));
        return query(paging(paging, sql), row, params);
    }

    /**
     * 查询结果
     * @param paging 分页器
     * @param sql    SQL
     * @param row    解析器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    default <T> List<T> query(Paging paging, SQL sql, IMapper<T> row) throws SQLException {
        return query(paging, sql.content(), row, sql.params());
    }

    /**
     * 事件执行方法
     * @param transaction 事务回调
     * @return true-提交； false-回滚
     */
    default boolean transaction(Function.FR1<Boolean, IDao> transaction) throws SQLException {
        return transaction(TRANSACTION_REPEATABLE_READ, transaction);
    }

    /**
     * 事件执行方法
     * @param level       事务级别
     * @param transaction 事务回调
     * @return true-提交； false-回滚
     */
    boolean transaction(int level, Function.FR1<Boolean, IDao> transaction) throws SQLException;

    /**
     * 返回查询分页总条数的SQL
     * @param sql SQL
     * @return 查询总条数的SQL
     */
    String totals(String sql);

    /**
     * 根据分页参数，组装分页查询SQL
     * @param paging 分页参数
     * @param sql    基础查询SQL
     * @return 分页查询SQL
     */
    String paging(Paging paging, String sql);
}
