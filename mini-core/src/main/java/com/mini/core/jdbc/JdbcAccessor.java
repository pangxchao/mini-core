package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.BeanMapper;
import com.mini.core.jdbc.mapper.MapMapper;
import com.mini.core.jdbc.mapper.Mapper;
import com.mini.core.jdbc.mapper.SingleMapper;
import com.mini.core.jdbc.model.Page;
import com.mini.core.jdbc.util.JdbcUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import static com.mini.core.jdbc.util.JdbcUtil.full;
import static com.mini.core.util.ThrowsUtil.hidden;
import static java.lang.ThreadLocal.withInitial;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

public abstract class JdbcAccessor implements EventListener, Serializable {
    private static final ThreadLocal<Map<DataSource, Holder>> RESOURCES = withInitial(ConcurrentHashMap::new);
    private static final ThreadLocal<JtaTransaction> JTA = new ThreadLocal<>();
    private final DataSource dataSource;

    public JdbcAccessor(@Nonnull DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Nonnull
    public final DataSource getDataSource() {
        return dataSource;
    }

    @Nonnull
    private Connection getConnection() throws SQLException {
        Holder connection = RESOURCES.get().get(dataSource);
        if (connection == null || connection.isClosed()) {
            connection = JdbcAccessor.create(dataSource);
            RESOURCES.get().put(dataSource, connection);
        }
        // 返回连接并更新连接使用计数，嵌套时不会关闭错误
        return connection.requestedConnection();
    }

    /**
     * 处理普通的 Connection 对象
     *
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public final <T> T execute(ConnectionCallback<T> callback) {
        try (Connection con = this.getConnection()) {
            return callback.apply(con);
        } catch (SQLException e) {
            throw hidden(e);
        }
    }

    /**
     * 处理普通的 DatabaseMetaData 对象
     *
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public final <T> T execute(DatabaseMetaDataCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) (connection) -> {
            return callback.apply(connection.getMetaData()); //
        });
    }

    /**
     * 处理普通的 PreparedStatement 对象
     *
     * @param creator  创建回调函数
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public final <T> T execute(PreparedStatementCreator creator, PreparedStatementCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) (connection) -> {
            return callback.apply(creator.apply(connection)); //
        });
    }

    /**
     * 处理普通的 CallableStatement 对象
     *
     * @param creator  创建回调函数
     * @param callback 执行回调函数
     * @param <T>      返回类型
     * @return 返回类型实例
     */
    public final <T> T execute(CallableStatementCreator creator, CallableStatementCallback<T> callback) {
        return this.execute((ConnectionCallback<T>) (connection) -> {
            return callback.apply(creator.apply(connection)); //
        });
    }

    public final <T> T executeQuery(String str, ResultSetCallback<T> callback, Object... params) {
        return execute((PreparedStatementCreator) con -> con.prepareStatement(str, //
                TYPE_FORWARD_ONLY, CONCUR_READ_ONLY), statement -> {
            try (ResultSet rs = full(statement, params).executeQuery()) {
                return callback.apply(rs);
            }
        });
    }

    protected abstract String paging(int start, int limit, String sql);

    protected abstract String totals(String sql);

    public final int executeUpdate(@Nonnull String sql, Object... params) {
        return execute((PreparedStatementCreator) con -> con.prepareStatement(sql),
                stm -> full(stm, params).executeUpdate());
    }

    public final int executeUpdate(@Nonnull SQLBuilder builder) {
        return executeUpdate(builder.getSql(), builder.getArgs());
    }

    public final int[] batchUpdate(@Nonnull String... sql) {
        return this.execute((ConnectionCallback<int[]>) (connection) -> {
            Statement statement = connection.createStatement();
            for (String s : sql) statement.addBatch(s);
            return statement.executeBatch();
        });
    }

    public final int[] batchUpdate(@Nonnull String sql, Object[]... paramsArray) {
        return this.execute((ConnectionCallback<int[]>) (connection) -> {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Object[] objects : paramsArray) {
                JdbcUtil.full(statement, objects);
                statement.addBatch();
            }
            return statement.executeBatch();
        });
    }

    @Nonnull
    public final <T> List<T> executeQueryList(@Nonnull String sql, Mapper<T> m, Object... params) {
        return JdbcAccessor.this.executeQuery(sql, rs -> {
            final List<T> result = new ArrayList<>();
            while (Objects.nonNull(rs) && rs.next()) {
                result.add(m.get(rs, rs.getRow()));
            }
            return result;
        }, params);
    }

    @Nonnull
    public final <T> List<T> executeQueryList(@Nonnull SQLBuilder builder, Mapper<T> mapper) {
        return executeQueryList(builder.getSql(), mapper, builder.getArgs());
    }

    @Nonnull
    public final <T> List<T> executeQueryList(int start, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        return executeQueryList(paging(start, limit, sql), mapper, params);
    }

    @Nonnull
    public final <T> List<T> executeQueryList(int start, int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return executeQueryList(start, limit, builder.getSql(), mapper, builder.getArgs());
    }

    @Nullable
    public final <T> T executeQueryObject(String sql, Mapper<T> mapper, Object... params) {
        return this.executeQuery(this.paging(0, 1, sql), rs -> {
            if (!rs.next()) mapper.get(rs, rs.getRow());
            return null;
        }, params);
    }

    @Nullable
    public final <T> T executeQueryObject(@Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return executeQueryObject(builder.getSql(), mapper, builder.getArgs());
    }

    @Nonnull
    protected final <T> Mapper<T> getSingleColumnMapper(@Nonnull Class<T> requiredType) {
        return SingleMapper.create(requiredType);
    }

    @Nonnull
    protected final <T> Mapper<T> getBeanPropertyMapper(@Nonnull Class<T> requiredType) {
        return BeanMapper.create(requiredType);
    }

    @Nonnull
    protected final Mapper<Map<String, Object>> getColumnMapMapper() {
        return MapMapper.create();
    }


    @Nonnull
    public final <T> Page<T> executeQueryPage(int page, int limit, @Nonnull String sql, @Nonnull Mapper<T> mapper, Object... params) {
        final Page<T> result = new Page<>(page, limit);
        result.setRows(executeQueryList(paging(result.getStart(), result.getLimit(), sql), mapper, params));
        Integer total = executeQueryObject(totals(sql), getSingleColumnMapper(Integer.class), params);
        result.setTotal(Optional.ofNullable(total).orElse(0));
        return result;
    }

    @Nonnull
    public final <T> Page<T> executeQueryPage(int page, int limit, @Nonnull SQLBuilder builder, @Nonnull Mapper<T> mapper) {
        return executeQueryPage(page, limit, builder.getSql(), mapper, builder.getArgs());
    }

    /**
     * 开启一个数据库事务
     *
     * @param callback 事务过程
     * @return 返回类型实例
     */
    public final <T> T transaction(JdbcTransactionCallback<T> callback) {
        try (Holder holder = (Holder) this.getConnection()) {
            var trans = holder.getTransaction();
            return callback.trans(trans);
        } catch (SQLException e) {
            throw hidden(e);
        }
    }

    /**
     * 开启一个数据库事务
     *
     * @param holder   全局事务管理
     * @param callback 事务过程
     * @return 返回类型实例
     */
    public static <T> T transaction(UserTransaction holder, JtaTransactionCallback<T> callback) {
        try {
            var trans = Optional.ofNullable(JTA.get()).orElseGet(() -> {
                JTA.set(new JtaTransaction() {
                    private int transactionCount = 0;

                    @Override
                    public void startTransaction() throws Exception {
                        if (this.transactionCount > 0) {
                            this.transactionCount++;
                            return;
                        }
                        this.transactionCount = 1;
                        holder.begin();
                    }

                    @Override
                    public void endTransaction(boolean trans_commit) throws Exception {
                        if (--this.transactionCount <= 0) {
                            boolean rollback = true;
                            try {
                                if (trans_commit) {
                                    holder.commit();
                                    rollback = false;
                                }
                            } finally {
                                if (rollback) {
                                    holder.rollback();
                                }
                            }
                        }
                    }
                });
                return JTA.get();
            });
            return callback.trans(trans);
        } catch (SQLException e) {
            throw hidden(e);
        }
    }

    // 创建 Holder Connection 对象
    private static Holder create(DataSource dataSource) throws SQLException {
        return new Holder(dataSource.getConnection());
    }

    private static class Holder implements Connection {
        private final Connection connection;
        private int transactionCount = 0;
        private int referenceCount;

        public Holder(Connection connection) {
            this.connection = connection;
            referenceCount = 0;
        }

        public Holder requestedConnection() {
            this.referenceCount++;
            return this;
        }

        protected JdbcTransaction getTransaction() {
            return new JdbcTransaction() {
                private Savepoint point;

                public final void setTransactionIsolation(int level) throws SQLException {
                    Holder.this.setTransactionIsolation(level);
                }

                public final void startTransaction() throws SQLException {
                    if (Holder.this.transactionCount > 0) {
                        Holder.this.transactionCount++;
                        return;
                    }
                    Holder.this.setAutoCommit(false);
                    Holder.this.transactionCount = 1;
                    this.point = setSavepoint();
                }

                public final void endTransaction(boolean commit) throws SQLException {
                    try {
                        Holder.this.transactionCount--;
                        if (!commit && point != null) {
                            rollback(point);
                        }
                    } finally {
                        if (transactionCount <= 0) {
                            boolean rollback = true;
                            try {
                                if (commit) {
                                    commit();
                                    rollback = false;
                                }
                            } finally {
                                if (rollback) {
                                    rollback();
                                }
                            }
                        }
                    }
                }
            };
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public void close() throws SQLException {
            if (--referenceCount <= 0) {
                connection.close();
            }
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setHoldability(int arg0) throws SQLException {
            connection.setHoldability(arg0);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String arg0) throws SQLException {
            return connection.setSavepoint(arg0);
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public Statement createStatement(int arg0, int arg1, int arg2) throws SQLException {
            return connection.createStatement(arg0, arg1, arg2);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3) throws SQLException {
            return connection.prepareCall(arg0, arg1, arg2, arg3);
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3) throws SQLException {
            return connection.prepareStatement(arg0, arg1, arg2, arg3);
        }

        @Override
        public PreparedStatement prepareStatement(String arg0, int arg1) throws SQLException {
            return connection.prepareStatement(arg0, arg1);
        }

        @Override
        public PreparedStatement prepareStatement(String arg0, int[] arg1) throws SQLException {
            return connection.prepareStatement(arg0, arg1);
        }

        @Override
        public PreparedStatement prepareStatement(String arg0, String[] arg1) throws SQLException {
            return connection.prepareStatement(arg0, arg1);
        }

        @Override
        public void releaseSavepoint(Savepoint arg0) throws SQLException {
            connection.releaseSavepoint(arg0);
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public synchronized boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        @SuppressWarnings("SpellCheckingInspection")
        public synchronized <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        @SuppressWarnings("SpellCheckingInspection")
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }

        @Override
        public void beginRequest() throws SQLException {
            connection.beginRequest();
        }

        @Override
        public void endRequest() throws SQLException {
            connection.endRequest();
        }

        @Override
        public boolean setShardingKeyIfValid(ShardingKey shardingKey, ShardingKey connectionShardingKey, int timeout) throws SQLException {
            return connection.setShardingKeyIfValid(shardingKey, connectionShardingKey, timeout);
        }

        @Override
        public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout) throws SQLException {
            return connection.setShardingKeyIfValid(shardingKey, timeout);
        }

        @Override
        public void setShardingKey(ShardingKey shardingKey, ShardingKey connectionShardingKey) throws SQLException {
            connection.setShardingKey(shardingKey, connectionShardingKey);
        }

        @Override
        public void setShardingKey(ShardingKey shardingKey) throws SQLException {
            connection.setShardingKey(shardingKey);
        }
    }


    @FunctionalInterface
    public interface ConnectionCallback<T> {
        T apply(Connection con) throws SQLException;
    }

    @FunctionalInterface
    public interface JdbcTransactionCallback<T> {
        T trans(JdbcTransaction trans) throws SQLException;
    }

    @FunctionalInterface
    public interface JtaTransactionCallback<T> {
        T trans(JtaTransaction trans) throws SQLException;
    }

    @FunctionalInterface
    public interface DatabaseMetaDataCallback<T> {
        T apply(DatabaseMetaData metaData) throws SQLException;
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
     * 查询结果集回调处理
     *
     * @param <T> 返回类型
     * @author xchao
     */
    @FunctionalInterface
    interface ResultSetCallback<T> {
        T apply(ResultSet rs) throws SQLException;
    }

    // JDBC 事务接口
    public interface JdbcTransaction {
        /**
         * 设置事务隔离级别
         *
         * @param level 隔离级别
         */
        void setTransactionIsolation(int level) throws SQLException;

        /**
         * 开启一个数据库事务
         *
         * @see Connection#setAutoCommit(boolean)
         * @see Connection#rollback(Savepoint)
         * @see Connection#rollback()
         * @see Connection#commit()
         */
        void startTransaction() throws SQLException;

        /**
         * 结束一个数据库事务
         *
         * @param commit true-提交
         * @see Connection#setAutoCommit(boolean)
         * @see Connection#rollback(Savepoint)
         * @see Connection#rollback()
         * @see Connection#commit()
         */
        void endTransaction(boolean commit) throws SQLException;

    }

    // JTA 事务接口
    public interface JtaTransaction {
        /**
         * 开启一个数据库事务
         *
         * @see UserTransaction#begin()
         * @see UserTransaction#commit()
         * @see UserTransaction#rollback()
         */
        void startTransaction() throws Exception;

        /**
         * 结束一个数据库事务
         *
         * @param commit true-提交
         * @see UserTransaction#begin()
         * @see UserTransaction#commit()
         * @see UserTransaction#rollback()
         */
        void endTransaction(boolean commit) throws Exception;
    }

}
