package com.mini.jdbc;

import com.mini.callback.*;
import com.mini.jdbc.holder.KeyHolderGeneratedKeys;
import com.mini.jdbc.mapper.IMapper;
import com.mini.jdbc.mapper.IMapperSingle;
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
     *
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
     * 执行 ConnectionCallback 对象
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * @param str      SQL
     * @param callback 回调接口
     * @return 执行结果
     */
    public final int[] batch(String str, PreparedStatementCallback<int[]> callback) {
        return this.execute(connection -> connection.prepareStatement(str), callback);
    }

    /**
     * 批量操作
     *
     * @param builder SQLBuilder 对象
     * @return 执行结果
     */
    public final int[] batch(SQLBuilder builder, PreparedStatementCallback<int[]> callback) {
        return this.batch(builder.toString(), callback);
    }

    /**
     * 执行SQL
     *
     * @param str    SQL
     * @param params 参数
     * @return 执行结果
     */
    public final int execute(String str, Object... params) {
        return this.execute((PreparedStatementCreator) connection -> {
            return connection.prepareStatement(str); //
        }, statement -> JdbcUtil.full(statement, params).executeUpdate());

    }

    /**
     * 执行SQL
     *
     * @param str    SQL
     * @param params 参数
     * @return 执行结果
     */
    public final int execute(KeyHolderGeneratedKeys holder, String str, Object... params) {
        PreparedStatementCallback<Integer> callback = statement -> {
            int result = full(statement, params).executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                holder.set(rs);
            }
            return result;
        };
        return execute(connection -> connection.prepareStatement(str, //
                RETURN_GENERATED_KEYS), callback);
    }

    /**
     * 执行SQL
     *
     * @param builder SQLBuilder 对象
     * @return 执行结果
     */
    public final int execute(SQLBuilder builder) {
        return execute(builder.toString(), builder.toArray());
    }

    /**
     * 执行SQL
     *
     * @param builder SQLBuilder 对象
     * @return ID
     */
    public final int execute(KeyHolderGeneratedKeys holder, SQLBuilder builder) {
        return execute(holder, builder.toString(), builder.toArray());
    }

    /**
     * 查询结果
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final <T> T query(String str, ResultSetCallback<T> callback, Object... params) {
        PreparedStatementCallback<T> preparedStatementCallback = statement -> {
            try (ResultSet rs = full(statement, params).executeQuery()) {
                return callback.doResultSet(rs);
            }
        };
        return execute(c -> c.prepareStatement(str, TYPE_FORWARD_ONLY, //
                CONCUR_READ_ONLY), preparedStatementCallback);
    }

    /**
     * 查询结果
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final <T> T query(SQLBuilder builder, ResultSetCallback<T> callback) {
        return query(builder.toString(), callback, builder.toArray());
    }

    /**
     * 查询结果
     *
     * @param str    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(String str, IMapper<T> m, Object... params) {
        return JdbcTemplate.this.query(str, res -> {
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
     *
     * @param builder SQLBuilder 对象
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(SQLBuilder builder, IMapper<T> m) {
        return query(builder.toString(), m, builder.toArray());
    }

    /**
     * 返回查询分页总条数的SQL
     *
     * @param str SQL
     * @return 查询总条数的SQL
     */
    protected abstract String totals(String str);

    /**
     * 根据分页参数，组装分页查询SQL
     *
     * @param start 查询起始位置
     * @param limit 查询条数
     * @param str   基础查询SQL
     * @return 分页查询SQL
     */
    protected abstract String paging(int start, int limit, String str);

    /**
     * 查询结果
     *
     * @param start  查询起始位置
     * @param limit  查询条数
     * @param str    SQL
     * @param m      解析器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(int start, int limit, String str, IMapper<T> m, Object... params) {
        return query(paging(start, limit, str), m, params);
    }

    /**
     * 查询结果
     *
     * @param start   查询起始位置
     * @param limit   查询条数
     * @param m       解析器
     * @param builder SQL
     * @param <T>     解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(int start, int limit, SQLBuilder builder, IMapper<T> m) {
        return query(start, limit, builder.toString(), m, builder.toArray());
    }

    /**
     * 查询结果
     *
     * @param limit  查询条数
     * @param str    SQL
     * @param m      解析器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(int limit, String str, IMapper<T> m, Object... params) {
        return query(0, limit, str, m, params);
    }

    /**
     * 查询结果
     *
     * @param limit   查询条数
     * @param m       解析器
     * @param builder SQL
     * @param <T>     解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(int limit, SQLBuilder builder, IMapper<T> m) {
        return query(0, limit, builder, m);
    }

    /**
     * 查询结果
     *
     * @param paging paging 分页器
     * @param str    SQL
     * @param m      解析器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, String str, IMapper<T> m, Object... params) {
        paging.setTotal(query(totals(str), rs -> rs.next() ? rs.getInt(1) : 0, params));
        return query(paging(paging.getSkip(), paging.getLimit(), str), m, params);
    }

    /**
     * 查询结果
     *
     * @param paging  分页器
     * @param builder SQL
     * @param m       解析器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    public final <T> List<T> query(Paging paging, SQLBuilder builder, IMapper<T> m) {
        return query(paging, builder.toString(), m, builder.toArray());
    }


    /**
     * 查询结果
     *
     * @param str    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     */
    public final <T> T queryOne(String str, IMapper<T> m, Object... params) {
        return query(paging(0, 1, str), rs -> rs.next() ? m.get(rs, //
                rs.getRow()) : null, params);
    }

    /**
     * 查询结果
     *
     * @param builder SQLBuilder 对象
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    public final <T> T queryOne(SQLBuilder builder, IMapper<T> m) {
        return queryOne(builder.toString(), m, builder.toArray());
    }

    /**
     * 查询单个值
     *
     * @param str    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    public final <T> T queryForObject(String str, Class<T> type, Object... params) {
        return queryOne(str, new IMapperSingle<>(type), params);
    }

    /**
     * 查询单个值
     *
     * @param builder SQL
     * @param type    值的类型
     * @return 查询结果
     */
    public final <T> T queryForObject(SQLBuilder builder, Class<T> type) {
        return queryOne(builder, new IMapperSingle<>(type));
    }

    /**
     * 查询String
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final String queryString(String str, Object... params) {
        return queryForObject(str, String.class, params);
    }


    /**
     * 查询String
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final String queryString(SQLBuilder builder) {
        return queryForObject(builder, String.class);
    }

    /**
     * 查询Long
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Long queryLong(String str, Object... params) {
        return queryForObject(str, Long.class, params);
    }

    /**
     * 查询Long
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Long queryLong(SQLBuilder builder) {
        return queryForObject(builder, Long.class);
    }

    /**
     * 查询Integer
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Integer queryInt(String str, Object... params) {
        return queryForObject(str, Integer.class, params);
    }


    /**
     * 查询Integer
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Integer queryInt(SQLBuilder builder) {
        return queryForObject(builder, Integer.class);
    }


    /**
     * 查询Short
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Short queryShort(String str, Object... params) {
        return queryForObject(str, Short.class, params);
    }

    /**
     * 查询Short
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Short queryShort(SQLBuilder builder) {
        return queryForObject(builder, Short.class);
    }


    /**
     * 查询Byte
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Byte queryByte(String str, Object... params) {
        return queryForObject(str, Byte.class, params);
    }

    /**
     * 查询Byte
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Byte queryByte(SQLBuilder builder) {
        return queryForObject(builder, Byte.class);
    }

    /**
     * 查询Double
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Double queryDouble(String str, Object... params) {
        return queryForObject(str, Double.class, params);
    }

    /**
     * 查询Double
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Double queryDouble(SQLBuilder builder) {
        return queryForObject(builder, Double.class);
    }

    /**
     * 查询Float
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Float queryFloat(String str, Object... params) {
        return queryForObject(str, Float.class, params);
    }

    /**
     * 查询Float
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Float queryFloat(SQLBuilder builder) {
        return queryForObject(builder, Float.class);
    }

    /**
     * 查询Boolean
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Boolean queryBoolean(String str, Object... params) {
        return queryForObject(str, Boolean.class, params);
    }

    /**
     * 查询Boolean
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Boolean queryBoolean(SQLBuilder builder) {
        return queryForObject(builder, Boolean.class);
    }

    /**
     * 查询Timestamp
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Timestamp queryTimestamp(String str, Object... params) {
        return queryForObject(str, Timestamp.class, params);
    }

    /**
     * 查询Timestamp
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Timestamp queryTimestamp(SQLBuilder builder) {
        return queryForObject(builder, Timestamp.class);
    }

    /**
     * 查询Date
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Date queryDate(String str, Object... params) {
        return queryForObject(str, Date.class, params);
    }

    /**
     * 查询Date
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Date queryDate(SQLBuilder builder) {
        return queryForObject(builder, Date.class);
    }

    /**
     * 查询Time
     *
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    public final Time queryTime(String str, Object... params) {
        return queryForObject(str, Time.class);
    }

    /**
     * 查询Time
     *
     * @param builder SQLBuilder 对象
     * @return 查询结果
     */
    public final Time queryTime(SQLBuilder builder) {
        return queryForObject(builder, Time.class);
    }

    /**
     * 当前线程数据库连接池管理
     *
     * @author xchao
     */
    private static final class JdbcThreadLocal extends ThreadLocal<Map<DataSource, ConnectionHolder>> {
        protected Map<DataSource, ConnectionHolder> initialValue() {
            return new ConcurrentHashMap<>();
        }
    }

    /**
     * 当前线程数据库连接管理
     *
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
