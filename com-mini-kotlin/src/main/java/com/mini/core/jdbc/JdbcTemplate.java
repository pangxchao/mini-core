package com.mini.core.jdbc;

import com.mini.core.jdbc.mapper.IMapper;
import com.mini.core.jdbc.model.Paging;
import com.mini.core.util.ObjectUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mini.core.jdbc.util.JdbcUtil.full;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.requireNonNull;

/**
 * 数据库操作对象
 * @author xchao
 */
public abstract class JdbcTemplate implements JdbcInterface {
    // 连接池连接线程池
    private static final ThreadLocal<Map<DataSource, ConnectionHolder>> //
            resources = ThreadLocal.withInitial(HashMap::new);
    // 数据库连接池
    private final DataSource dataSource;

    public JdbcTemplate(@Nonnull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库连接池对象
     * @return 数据库连接池对象
     */
    @Nonnull
    public DataSource getDataSource() {
        return dataSource;
    }

    // 打开当前线程连接
    @Nonnull
    private static Connection getConnection(@Nonnull DataSource dataSource) {
        Connection connection = null;
        try {
            // 获取当前线程的指定数据源的连接状态
            Map<DataSource, ConnectionHolder> map = requireNonNull(resources.get());
            ConnectionHolder connectionHolder = map.computeIfAbsent(dataSource, ds -> {
                return new ConnectionHolder(); //
            });

            // 如果没有打开的连接，则重新设置连接
            if (!connectionHolder.hasOpenConnection()) {
                connection = dataSource.getConnection();
                connectionHolder.setConnection(connection);
            }

            // 从 ConnectionHolder 对象中获取连接
            connection = connectionHolder.getConnection();
            connectionHolder.requestedConnection();
            return connection;
        } catch (SQLException | RuntimeException e) {
            releaseConnection(dataSource, connection);
            throw new RuntimeException(e);
        }
    }

    // 关闭当前线程连接
    private static void releaseConnection(@Nonnull DataSource dataSource, @Nullable Connection connection) {
        // 连接为空无需关闭
        if (connection == null) {
            return;
        }
        try {
            // 获取当前线程的指定数据源的连接状态
            Map<DataSource, ConnectionHolder> map = requireNonNull(resources.get());
            ConnectionHolder connectionHolder = map.computeIfAbsent(dataSource, ds -> {
                return new ConnectionHolder(); //
            });

            // 从connectionHolder中获取连接
            Connection holder;
            if (connectionHolder.hasOpenConnection()) {
                holder = connectionHolder.getConnection();
                if (ObjectUtil.equals(holder, connection)) {
                    connectionHolder.releasedConnection();
                    return;
                }
            }

            // 如果连接未关闭，则关闭连接
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理普通的 Connection 对象
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     * @throws SQLException SQL错误
     */
    public <T> T connection(ConnectionThrowableCallback<T> callback) throws Throwable {
        Connection connection = null;
        try {
            connection = getConnection(dataSource);
            return callback.apply(connection);
        } finally {
            releaseConnection(dataSource, connection);
        }
    }

    /**
     * 处理普通的 Connection 对象
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(ConnectionCallback<T> callback) {
        Connection connection = null;
        try {
            connection = getConnection(dataSource);
            return callback.apply(connection);
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(dataSource, connection);
        }
    }

    /**
     * 处理普通的 DatabaseMetaData 对象
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(DatabaseMetaDataCallback<T> callback) {
        return execute((ConnectionCallback<T>) connection -> {//
            return callback.apply(connection.getMetaData());
        });
    }

    /**
     * 处理普通的 PreparedStatement 对象
     * @param creator  创建回调函数
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(PreparedStatementCreator creator, PreparedStatementCallback<T> callback) {
        return execute((ConnectionCallback<T>) connection -> { //
            return callback.apply(creator.apply(connection));
        });
    }

    /**
     * 处理普通的 CallableStatement 对象
     * @param creator  创建回调函数
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public <T> T execute(CallableStatementCreator creator, CallableStatementCallback<T> callback) {
        return execute((ConnectionCallback<T>) connection -> { //
            return callback.apply(creator.apply(connection));
        });
    }

    /**
     * 指执行SQL
     * @param str    SQL
     * @param setter 预处理设置器
     * @return 执行结果
     */
    public int[] executeBatch(String str, JdbcInterface.PreparedStatementSetter setter) {
        return execute((PreparedStatementCreator) con -> con.prepareStatement(str), stm -> {
            for (int i = 0, len = setter.batchSize; i < len; i++) {
                setter.setValues(stm, i);
                stm.addBatch();
            }
            return stm.executeBatch();
        });
    }

    /**
     * 执行SQL
     * @param str    SQL
     * @param params 参数
     * @return 执行结果
     */
    public int execute(String str, Object... params) {
        return execute((PreparedStatementCreator) con -> {
            return con.prepareStatement(str); //
        }, stm -> full(stm, params).executeUpdate());
    }

    /**
     * 执行SQL
     * @param holder 执行返回数据
     * @param str    SQL
     * @param params 参数
     * @return 执行结果
     */
    public int execute(JdbcInterface.HolderGenerated holder, String str, Object... params) {
        return execute((PreparedStatementCreator) con -> con.prepareStatement(str, //
                RETURN_GENERATED_KEYS), stm -> {
            int result = full(stm, params).executeUpdate();
            try (ResultSet rs = stm.getGeneratedKeys()) {
                holder.setValue(rs);
            }
            return result;
        });
    }

    /**
     * 查询结果
     * @param str      查询SQL
     * @param callback 查询回调方法
     * @param params   查询SQL中的参数
     * @param <T>      查询结果返回类型
     * @return 查询结果
     */
    public <T> T query(String str, ResultSetCallback<T> callback, Object... params) {
        return execute((PreparedStatementCreator) con -> con.prepareStatement(str, //
                TYPE_FORWARD_ONLY, CONCUR_READ_ONLY), statement -> {
            try (ResultSet rs = full(statement, params).executeQuery()) {
                return callback.apply(rs);
            }
        });
    }

    /**
     * 返回查询分页总条数的SQL
     * @param str SQL
     * @return 查询总条数的SQL
     */
    @Nonnull
    protected abstract String totals(String str);

    /**
     * 根据分页参数，组装分页查询SQL
     * @param start 查询起始位置
     * @param limit 查询条数
     * @param str   基础查询SQL
     * @return 分页查询SQL
     */
    @Nonnull
    protected abstract String paging(int start, int limit, String str);

    @Nonnull
    public final <T> List<T> queryList(int start, int limit, String str, IMapper<T> m, Object... params) {
        return queryList(paging(start, limit, str), m, params);
    }

    @Nonnull
    public final <T> List<T> queryList(Paging paging, String str, IMapper<T> m, Object... params) {
        paging.setTotal(query(totals(str), (rs -> rs.next() ? rs.getInt(1) : 0), params));
        return queryList(paging(paging.getSkip(), paging.getLimit(), str), m, params);
    }

    @Nullable
    public final <T> T queryObject(String str, IMapper<T> m, Object... params) {
        return query(paging(0, 1, str), rs -> rs.next() ? m.get( //
                rs, rs.getRow()) : null, params);
    }

    @FunctionalInterface
    public interface ConnectionThrowableCallback<T> {
        T apply(Connection con) throws Throwable;
    }

    @FunctionalInterface
    public interface ConnectionCallback<T> {
        T apply(Connection con) throws SQLException;
    }

    @FunctionalInterface
    public interface DatabaseMetaDataCallback<T> {
        T apply(DatabaseMetaData con) throws SQLException;
    }

    @FunctionalInterface
    public interface PreparedStatementCreator {
        PreparedStatement apply(Connection con) throws SQLException;
    }

    @FunctionalInterface
    public interface PreparedStatementCallback<T> {
        T apply(PreparedStatement stm) throws SQLException;
    }

    @FunctionalInterface
    public interface CallableStatementCreator {
        CallableStatement apply(Connection con) throws SQLException;
    }

    @FunctionalInterface
    public interface CallableStatementCallback<T> {
        T apply(CallableStatement stm) throws SQLException;
    }

    /**
     * 当前线程数据库连接管理
     * @author xchao
     */
    private static final class ConnectionHolder {
        private Connection connection;
        private int referenceCount = 0;

        private void setConnection(Connection connection) {
            this.connection = connection;
        }

        private Connection getConnection() {
            return connection;
        }

        private void requestedConnection() {
            this.referenceCount++;
        }

        private void releasedConnection() throws SQLException {
            this.referenceCount--;
            if (referenceCount <= 0) {
                closeConnection();
            }
        }

        private boolean hasOpenConnection() throws SQLException {
            return connection != null && !connection.isClosed();
        }

        private void closeConnection() throws SQLException {
            if (connection.isClosed()) return;
            this.connection.close();
        }
    }
}
