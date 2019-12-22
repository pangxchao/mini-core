package com.mini.core.jdbc;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.*;
import java.util.EventListener;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public abstract class JdbcAccessor implements EventListener {
	private static final JdbcThreadLocal resources = new JdbcThreadLocal();
	private static final TransThreadLocal TRANS = new TransThreadLocal();
	private final DataSource dataSource;

	public JdbcAccessor(@Nonnull DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Nonnull
	public final DataSource getDataSource() {
		return dataSource;
	}

	@Nonnull
	protected final Connection getConnection() throws SQLException {
		Holder connection = resources.get().get(dataSource);
		if (connection == null || connection.isClosed()) {
			connection = this.createHolder(dataSource);
			resources.get().put(dataSource, connection);
		}
		// 返回连接并更新连接使用计数，嵌套时不会关闭错误
		return connection.requestedConnection();
	}

	/**
	 * 处理普通的 Connection 对象
	 * @param callback 执行回调函数
	 * @param <T>      返回类型
	 * @return 返回类型实例
	 */
	public final <T> T execute(JdbcTemplate.ConnectionCallback<T> callback) {
		try (Connection con = this.getConnection()) {
			return callback.apply(con);
		} catch (SQLException | RuntimeException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 处理普通的 DatabaseMetaData 对象
	 * @param callback 执行回调函数
	 * @param <T>      返回类型
	 * @return 返回类型实例
	 */
	public final <T> T execute(JdbcTemplate.DatabaseMetaDataCallback<T> callback) {
		return this.execute((JdbcTemplate.ConnectionCallback<T>) (connection) -> {
			return callback.apply(connection.getMetaData()); //
		});
	}

	/**
	 * 处理普通的 PreparedStatement 对象
	 * @param creator  创建回调函数
	 * @param callback 执行回调函数
	 * @param <T>      返回类型
	 * @return 返回类型实例
	 */
	public final <T> T execute(JdbcTemplate.PreparedStatementCreator creator, JdbcTemplate.PreparedStatementCallback<T> callback) {
		return this.execute((JdbcTemplate.ConnectionCallback<T>) (connection) -> {
			return callback.apply(creator.apply(connection)); //
		});
	}

	/**
	 * 处理普通的 CallableStatement 对象
	 * @param creator  创建回调函数
	 * @param callback 执行回调函数
	 * @param <T>      返回类型
	 * @return 返回类型实例
	 */
	public final <T> T execute(JdbcTemplate.CallableStatementCreator creator, JdbcTemplate.CallableStatementCallback<T> callback) {
		return this.execute((JdbcTemplate.ConnectionCallback<T>) (connection) -> {
			return callback.apply(creator.apply(connection)); //
		});
	}

	/**
	 * 开启一个数据库事务
	 * @see Connection#setAutoCommit(boolean)
	 * @see Connection#rollback(Savepoint)
	 * @see Connection#rollback()
	 * @see Connection#commit()
	 */
	public final void startTransaction() {
		TRANS.getTrans(dataSource, dataSourceKey -> {
			try {
				return new JdbcTransaction(getConnection());
			} catch (SQLException | RuntimeException e) {
				throw new RuntimeException(e);
			}
		}).open();
	}

	public final void endTransaction(boolean commit) {
		ofNullable(TRANS.getTrans(dataSource)) //
			.ifPresent(trans -> {
				try (trans) {
					if (commit) {
						trans.commit();
					}
				}
			});
	}

	private Holder createHolder(DataSource dataSource) throws SQLException {
		return new Holder(dataSource.getConnection());
	}

	private static class Holder implements Connection {
		private final Connection connection;
		private int referenceCount;

		public Holder(Connection connection) {
			this.connection = connection;
			referenceCount  = 0;
		}

		public Holder requestedConnection() {
			this.referenceCount++;
			return this;
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
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void rollback(Savepoint arg0) throws SQLException {
			connection.rollback(arg0);
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
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
		public synchronized <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
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

	//
	private static class JdbcThreadLocal extends ThreadLocal<Map<DataSource, Holder>> {
		@Override
		protected Map<DataSource, Holder> initialValue() {
			return new ConcurrentHashMap<>();
		}

		@Nonnull
		@Override
		public Map<DataSource, Holder> get() {
			return super.get();
		}
	}

	// 数据库事务类
	private static final class JdbcTransaction implements AutoCloseable {
		private boolean commit = false;
		private final Connection conn;
		private boolean trans = true;
		private int transCount = 0;

		public JdbcTransaction(@Nonnull Connection conn) {
			this.conn = conn;
		}

		public final void close() {
			endTrans(commit);
			commit = false;
		}

		protected final void open() {
			this.startTrans();
			commit = false;
		}

		protected final void commit() {
			commit = true;
		}

		protected final void startTrans() {
			try {
				// 事务计数+1
				if (this.transCount > 0) {
					++this.transCount;
					return;
				}
				// 开启数据库事务
				conn.setAutoCommit(false);
				transCount = 1;
				trans      = true;
			} catch (SQLException | RuntimeException e) {
				throw new RuntimeException(e);
			}
		}

		protected final void endTrans(boolean transCommit) {
			try {
				// 事务计数 -1
				if (this.transCount > 1) {
					--this.transCount;
					if (!transCommit) {
						trans = false;
					}
					return;
				}

				// 提交或者回滚事物
				try {
					this.transCount = 0;
					if (commit && trans) {
						conn.commit();
					} else conn.rollback();
				} finally {
				    // 重新设置为自动提交
					conn.setAutoCommit(true);
				}
			} catch (SQLException | RuntimeException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static final class TransThreadLocal extends ThreadLocal<Map<DataSource, JdbcTransaction>> {
		@Override
		protected Map<DataSource, JdbcTransaction> initialValue() {
			return new ConcurrentHashMap<>();
		}

		@Nonnull
		@Override
		public Map<DataSource, JdbcTransaction> get() {
			return super.get();
		}

		@Nonnull
		public JdbcTransaction getTrans(DataSource dataSource, Function<DataSource, JdbcTransaction> m) {
			return get().computeIfAbsent(dataSource, m);
		}

		public JdbcTransaction getTrans(DataSource dataSource) {
			return get().get(dataSource);
		}
	}


	@FunctionalInterface
	public interface ConnectionCallback<T> {
		T apply(Connection con) throws SQLException;
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
}
