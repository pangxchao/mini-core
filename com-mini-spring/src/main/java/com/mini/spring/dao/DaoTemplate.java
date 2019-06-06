package com.mini.spring.dao;

import com.mini.util.dao.IDao;
import com.mini.util.lang.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public abstract class DaoTemplate extends JdbcTemplate implements IDao {
    // 事务应用计数，支持嵌套事务
    private int transRefCount = 0;

    @Autowired
    public DaoTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Nonnull
    protected Connection getConnection() {
        DataSource dataSource = obtainDataSource();
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public Statement createStatement() throws SQLException {
        return getConnection().createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return getConnection().prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return getConnection().nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        getConnection().setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return getConnection().getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        getConnection().commit();
    }

    @Override
    public void rollback() throws SQLException {
        getConnection().rollback();
    }

    @Override
    public void close() throws SQLException {
        getConnection().close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return getConnection().isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return getConnection().getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        getConnection().setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return getConnection().isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        getConnection().setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return getConnection().getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        getConnection().setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return getConnection().getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return getConnection().getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        getConnection().clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return getConnection().createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return getConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return getConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return getConnection().getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        getConnection().setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        getConnection().setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return getConnection().getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return getConnection().setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return getConnection().setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        getConnection().rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        getConnection().releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        return getConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return getConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        return getConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return getConnection().prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return getConnection().prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return getConnection().prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        return getConnection().createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return getConnection().createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return getConnection().createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return getConnection().createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return getConnection().isClosed();
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        getConnection().setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        getConnection().setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return getConnection().getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return getConnection().getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return getConnection().createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return getConnection().createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        getConnection().setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return getConnection().getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        getConnection().abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        getConnection().setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return getConnection().getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> clazz) throws SQLException {
        return getConnection().unwrap(clazz);
    }

    @Override
    public boolean isWrapperFor(Class<?> clazz) throws SQLException {
        return getConnection().isWrapperFor(clazz);
    }

    @Override
    public boolean transaction(int level, Function.FR1<Boolean, IDao> transaction) throws SQLException {
        // 第一次开启事务
        if (this.transRefCount == 0) {
            this.setAutoCommit(false);
            this.setTransactionIsolation(level);
        }
        // 事务执行阶段
        Savepoint savepoint = null;
        boolean commit = false;
        try {
            this.transRefCount++;
            savepoint = this.setSavepoint();
            commit    = transaction.apply(this);
        } finally {
            this.transRefCount--;
            // 回滚事务到设置的回滚点
            if (!commit && savepoint != null) {
                try {
                    this.rollback(savepoint);
                } catch (SQLException ignored) {}
            }
            // 如果事务计数为0时,则表示所有事务完成
            if (this.transRefCount == 0) {
                boolean rollback = true;
                try {
                    if (commit) {
                        this.commit();
                        rollback = false;
                    }
                } finally {
                    try {
                        if (rollback) {
                            commit = false;
                            this.rollback();
                        }
                    } finally {
                        this.setAutoCommit(true);
                    }
                }
            }
        }
        return commit;
    }
}
