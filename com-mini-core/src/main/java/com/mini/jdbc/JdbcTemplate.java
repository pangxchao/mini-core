package com.mini.jdbc;

import com.mini.callback.*;
import com.mini.jdbc.holder.KeyHolderGeneratedKeys;
import com.mini.jdbc.mapper.IMapper;
import com.mini.jdbc.mapper.IMapperSingle;
import com.mini.jdbc.sql.SQL;
import com.mini.jdbc.util.JdbcUtil;
import com.mini.jdbc.util.Paging;
import com.mini.util.ObjectUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.jdbc.util.JdbcUtil.full;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public abstract class JdbcTemplate {
    private static final JdbcThreadLocal resources = new JdbcThreadLocal();
    private final DataSource dataSource;

    public JdbcTemplate(@Nonnull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Gets the value of dataSource.
     * @return The value of dataSource
     */
    @Nonnull
    public final DataSource getDataSource() {
        return dataSource;
    }

    // 打开当前线程连接
    @Nonnull
    private static Connection getConnection(DataSource dataSource) {
        Connection connection = null;
        try {
            // 获取当前线程的指定数据源的连接状态
            Map<DataSource, ConnectionHolder> map = Objects.requireNonNull(resources.get());
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
            releaseConnection(connection, dataSource);
            throw new RuntimeException(e);
        }
    }

    // 关闭当前线程连接
    private static void releaseConnection(@Nullable Connection connection, @Nullable DataSource dataSource) {
        if (connection == null || dataSource == null) {
            return;
        }
        try {
            // 获取当前线程的指定数据源的连接状态
            Map<DataSource, ConnectionHolder> map = Objects.requireNonNull(resources.get());
            ConnectionHolder connectionHolder = map.computeIfAbsent(dataSource, ds -> {
                return new ConnectionHolder(); //
            });

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
     * 执行 ConnectionCallback 对象
     * @param callback ConnectionCallback 对象
     * @param <T>      结果类型
     * @return 执行结果
     */
    public final <T> T execute(ConnectionCallback<T> callback) {
        Connection connection = null;
        try {
            connection = getConnection(dataSource);
            return callback.doConnection(connection);
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection, dataSource);
        }
    }

    /**
     * 执行 DatabaseMetaDataCallback 对象
     * @param callback DatabaseMetaDataCallback 对象
     * @param <T>      结果类型
     * @return 执行结果
     */
    public <T> T execute(DatabaseMetaDataCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) connection -> {
            DatabaseMetaData metaData = connection.getMetaData();
            return callback.doDatabaseMetaData(metaData);
        });
    }

    /**
     * 执行 StatementCallback 对象
     * @param creator  StatementCreator 创建器
     * @param callback StatementCallback 对象
     * @param <T>      结果类型
     * @return 执行结果
     */
    public final <T> T execute(StatementCreator creator, StatementCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) connection -> {
            Statement statement = creator.get(connection);
            return callback.doStatement(statement);
        });
    }

    /**
     * 执行 PreparedStatementCallback 对象
     * @param creator  PreparedStatementCreator 创建器
     * @param callback PreparedStatementCallback 对象
     * @param <T>      结果类型
     * @return 执行结果
     */
    public final <T> T execute(PreparedStatementCreator creator, PreparedStatementCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) connection -> {
            PreparedStatement statement = creator.get(connection);
            return callback.doPreparedStatement(statement);
        });
    }

    /**
     * 执行 PreparedStatementCallback 对象
     * @param creator  CallableStatementCreator 创建器
     * @param callback CallableStatementCallback 对象
     * @param <T>      结果类型
     * @return 执行结果
     */
    public final <T> T execute(CallableStatementCreator creator, CallableStatementCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) connection -> {
            CallableStatement statement = creator.get(connection);
            return callback.doCallableStatement(statement);
        });
    }

    /**
     * 批量操作
     * @param sql      SQL
     * @param callback 回调接口
     * @return 执行结果
     */
    public final int[] batch(String sql, PreparedStatementCallback<int[]> callback) {
        return this.execute(connection -> connection.prepareStatement(sql), callback);
    }

    /**
     * 批量操作
     * @param sql SQL
     * @return 执行结果
     */
    public final int[] batch(SQL sql, PreparedStatementCallback<int[]> callback) {
        return batch(sql.toString(), callback);
    }

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    public final int execute(String sql, Object... params) {
        return this.execute((PreparedStatementCreator) connection -> {
            return connection.prepareStatement(sql); //
        }, statement -> JdbcUtil.full(statement, params).executeUpdate());

    }

    /**
     * 执行SQL
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果
     */
    public final int execute(KeyHolderGeneratedKeys holder, String sql, Object... params) {
        PreparedStatementCallback<Integer> callback = statement -> {
            int result = full(statement, params).executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                holder.set(rs);
            }
            return result;
        };
        return execute(connection -> connection.prepareStatement(sql, RETURN_GENERATED_KEYS), callback);
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return 执行结果
     */
    public final int execute(SQL sql) {
        return execute(sql.toString(), sql.toArray());
    }

    /**
     * 执行SQL
     * @param sql SQL
     * @return ID
     */
    public final int execute(KeyHolderGeneratedKeys holder, SQL sql) {
        return execute(holder, sql.toString(), sql.toArray());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final <T> T query(String sql, ResultSetCallback<T> callback, Object... params) {
        PreparedStatementCreator creator = c -> c.prepareStatement(sql, TYPE_FORWARD_ONLY, CONCUR_READ_ONLY);
        PreparedStatementCallback<T> preparedStatementCallback = statement -> {
            try (ResultSet rs = full(statement, params).executeQuery()) {
                return callback.doResultSet(rs);
            }
        };
        return execute(creator, preparedStatementCallback);
    }

    /**
     * 查询结果
     * @param sql SQL
     * @return 查询结果
     */
    public final <T> T query(SQL sql, ResultSetCallback<T> callback) {
        return query(sql.toString(), callback, sql.toArray());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(String sql, IMapper<T> m, Object... params) {
        return JdbcTemplate.this.query(sql, res -> {
            List<T> result = new ArrayList<>();
            while (res != null && res.next()) {
                int number = res.getRow();
                T t = m.get(res, number);
                result.add(t);
            }
            return result;
        }, params);
    }

    /**
     * 查询结果
     * @param sql SQL
     * @param m   映射器
     * @param <T> 解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(SQL sql, IMapper<T> m) {
        return query(sql.toString(), m, sql.toArray());
    }

    /**
     * 查询结果
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> T queryOne(String sql, IMapper<T> m, Object... params) {
        return JdbcTemplate.this.query(sql, res -> {
            if (res != null && res.next()) {
                int number = res.getRow();
                return m.get(res, number);
            }
            return null;
        }, params);
    }

    /**
     * 查询结果
     * @param sql SQL
     * @param m   映射器
     * @param <T> 解析器类型
     * @return 查询结果
     */
    public final <T> T queryOne(SQL sql, IMapper<T> m) {
        return queryOne(sql.toString(), m, sql.toArray());
    }


    /**
     * 查询单个值
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    public final <T> T queryForObject(String sql, Class<T> type, Object... params) {
        return queryOne(sql, new IMapperSingle<>(type), params);
    }

    /**
     * 查询单个值
     * @param sql  SQL
     * @param type 值的类型
     * @return 查询结果
     */
    public final <T> T queryForObject(SQL sql, Class<T> type) {
        return queryOne(sql, new IMapperSingle<>(type));
    }

    /**
     * 查询String
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final String queryString(String sql, Object... params) {
        return queryForObject(sql, String.class, params);
    }


    /**
     * 查询String
     * @param sql SQL
     * @return 查询结果
     */
    public final String queryString(SQL sql) {
        return queryForObject(sql, String.class);
    }

    /**
     * 查询Long
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Long queryLong(String sql, Object... params) {
        return queryForObject(sql, Long.class, params);
    }

    /**
     * 查询Long
     * @param sql SQL
     * @return 查询结果
     */
    public final Long queryLong(SQL sql) {
        return queryForObject(sql, Long.class);
    }

    /**
     * 查询Integer
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Integer queryInt(String sql, Object... params) {
        return queryForObject(sql, Integer.class, params);
    }


    /**
     * 查询Integer
     * @param sql SQL
     * @return 查询结果
     */
    public final Integer queryInt(SQL sql) {
        return queryForObject(sql, Integer.class);
    }


    /**
     * 查询Short
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Short queryShort(String sql, Object... params) {
        return queryForObject(sql, Short.class, params);
    }

    /**
     * 查询Short
     * @param sql SQL
     * @return 查询结果
     */
    public final Short queryShort(SQL sql) {
        return queryForObject(sql, Short.class);
    }


    /**
     * 查询Byte
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Byte queryByte(String sql, Object... params) {
        return queryForObject(sql, Byte.class, params);
    }

    /**
     * 查询Byte
     * @param sql SQL
     * @return 查询结果
     */
    public final Byte queryByte(SQL sql) {
        return queryForObject(sql, Byte.class);
    }

    /**
     * 查询Double
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Double queryDouble(String sql, Object... params) {
        return queryForObject(sql, Double.class, params);
    }

    /**
     * 查询Double
     * @param sql SQL
     * @return 查询结果
     */
    public final Double queryDouble(SQL sql) {
        return queryForObject(sql, Double.class);
    }

    /**
     * 查询Float
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Float queryFloat(String sql, Object... params) {
        return queryForObject(sql, Float.class, params);
    }

    /**
     * 查询Float
     * @param sql SQL
     * @return 查询结果
     */
    public final Float queryFloat(SQL sql) {
        return queryForObject(sql, Float.class);
    }

    /**
     * 查询Boolean
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Boolean queryBoolean(String sql, Object... params) {
        return queryForObject(sql, Boolean.class, params);
    }

    /**
     * 查询Boolean
     * @param sql SQL
     * @return 查询结果
     */
    public final Boolean queryBoolean(SQL sql) {
        return queryForObject(sql, Boolean.class);
    }

    /**
     * 查询Timestamp
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Timestamp queryTimestamp(String sql, Object... params) {
        return queryForObject(sql, Timestamp.class, params);
    }

    /**
     * 查询Timestamp
     * @param sql SQL
     * @return 查询结果
     */
    public final Timestamp queryTimestamp(SQL sql) {
        return queryForObject(sql, Timestamp.class);
    }

    /**
     * 查询Date
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Date queryDate(String sql, Object... params) {
        return queryForObject(sql, Date.class, params);
    }

    /**
     * 查询Date
     * @param sql SQL
     * @return 查询结果
     */
    public final Date queryDate(SQL sql) {
        return queryForObject(sql, Date.class);
    }

    /**
     * 查询Time
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Time queryTime(String sql, Object... params) {
        return queryForObject(sql, Time.class);
    }

    /**
     * 查询Time
     * @param sql SQL
     * @return 查询结果
     */
    public final Time queryTime(SQL sql) {
        return queryForObject(sql, Time.class);
    }

    /**
     * 查询结果
     * @param paging paging 分页器
     * @param sql    SQL
     * @param m      解析器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, String sql, IMapper<T> m, Object... params) {
        paging.setTotal(this.queryInt(this.totals(sql), params));
        return query(paging(paging, sql), m, params);
    }

    /**
     * 查询结果
     * @param paging 分页器
     * @param sql    SQL
     * @param m      解析器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, SQL sql, IMapper<T> m) {
        return query(paging, sql.toString(), m, sql.toArray());
    }

    /**
     * 返回查询分页总条数的SQL
     * @param sql SQL
     * @return 查询总条数的SQL
     */
    protected abstract String totals(String sql);

    /**
     * 根据分页参数，组装分页查询SQL
     * @param paging 分页参数
     * @param sql    基础查询SQL
     * @return 分页查询SQL
     */
    protected abstract String paging(Paging paging, String sql);


    /**
     * 当前线程数据库连接池管理
     * @author xchao
     */
    private static final class JdbcThreadLocal extends ThreadLocal<Map<DataSource, ConnectionHolder>> {
        protected Map<DataSource, ConnectionHolder> initialValue() {
            return new ConcurrentHashMap<>();
        }
    }

    /**
     * 当前线程数据库连接管理
     * @author xchao
     */
    private static final class ConnectionHolder {
        private Connection connection;
        private int referenceCount = 0;

        public ConnectionHolder setConnection(Connection connection) {
            this.connection = connection;
            return this;
        }

        public Connection getConnection() {
            return connection;
        }

        public void requestedConnection() {
            this.referenceCount++;
        }

        public void releasedConnection() throws SQLException {
            this.referenceCount--;
            if (referenceCount <= 0) {
                closeConnection();
            }
        }

        public boolean hasOpenConnection() throws SQLException {
            return connection != null && !connection.isClosed();
        }

        private void closeConnection() throws SQLException {
            if (connection.isClosed()) return;
            this.connection.close();
        }
    }
}
