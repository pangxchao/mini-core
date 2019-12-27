package com.mini.core.jdbc;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.sql.*;
import java.util.EventListener;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import static java.lang.ThreadLocal.withInitial;

public abstract class JdbcAccessor implements EventListener, Serializable {
	private static final ThreadLocal<Map<DataSource, Holder>> RESOURCES //
		= withInitial(ConcurrentHashMap::new);
	private static final ThreadLocal<JtaHolderTransaction> JTA //
		= new ThreadLocal<>();
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
	 * @param callback 执行回调函数
	 * @param <T>      返回类型
	 * @return 返回类型实例
	 */
	public final <T> T execute(ConnectionCallback<T> callback) {
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
	public final <T> T execute(DatabaseMetaDataCallback<T> callback) {
		return this.execute((ConnectionCallback<T>) (connection) -> {
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
	public final <T> T execute(PreparedStatementCreator creator, PreparedStatementCallback<T> callback) {
		return this.execute((ConnectionCallback<T>) (connection) -> {
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
	public final <T> T execute(CallableStatementCreator creator, CallableStatementCallback<T> callback) {
		return this.execute((ConnectionCallback<T>) (connection) -> {
			return callback.apply(creator.apply(connection)); //
		});
	}

	/**
	 * 开启一个数据库事务
	 * @param callback 事务过程
	 * @return 返回类型实例
	 */
	public final <T> T transaction(JdbcTransactionCallback<T> callback) throws Throwable {
		try (Holder holder = (Holder) this.getConnection()) {
			var trans = new JdbcHolderTransaction(holder);
			return callback.trans(trans);
		}
	}

	/**
	 * 开启一个数据库事务
	 * @param holder   全局事务管理
	 * @param callback 事务过程
	 * @return 返回类型实例
	 */
	public static <T> T transaction(UserTransaction holder, JtaTransactionCallback<T> callback) throws Throwable {
		var trans = Optional.ofNullable(JTA.get()).orElseGet(() -> {
			JTA.set(new JtaHolderTransaction(holder));
			return JTA.get();
		});
		return callback.trans(trans);
	}

	// 创建 Holder Connection 对象
	private static Holder create(DataSource dataSource) throws SQLException {
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

	private static final class JdbcHolderTransaction implements JdbcTransaction {
		private int transactionCount = 0;
		private final Holder holder;
		private Savepoint point;

		public JdbcHolderTransaction(Holder holder) {
			this.holder = holder;
		}

		public final void setTransactionIsolation(int level) throws SQLException {
			holder.setTransactionIsolation(level);
		}

		public final void startTransaction() throws SQLException {
			if (this.transactionCount > 0) {
				this.transactionCount++;
				return;
			}
			holder.setAutoCommit(false);
			this.transactionCount = 1;
		}

		public final Savepoint setSavepoint() throws SQLException {
			this.point = holder.setSavepoint();
			return this.point;
		}

		@SuppressWarnings("unused")
		public final Savepoint setSavepoint(String name) throws SQLException {
			this.point = holder.setSavepoint(name);
			return this.point;
		}

		@SuppressWarnings("unused")
		public final void transactionRollback(Savepoint point) throws SQLException {
			holder.rollback(point);
		}

		public final void endTransaction(boolean trans_commit) throws SQLException {
			// 事务计数 -1
			this.transactionCount--;
			// 回滚事务到设置的回滚点
			if (!trans_commit && point != null) {
				try {
					holder.rollback(point);
				} catch (SQLException ignored) { }
			}
			// 所有事务完成处理
			if (this.transactionCount <= 0) {
				boolean rollback = true;
				try {
					if (trans_commit) {
						holder.commit();
						rollback = false;
					}
				} finally {
					try {
						if (rollback) {
							holder.rollback();
						}
					} finally {
						holder.setAutoCommit(true);
					}
				}
			}
		}
	}

	private static final class JtaHolderTransaction implements JtaTransaction {
		private final UserTransaction holder;
		private int transactionCount = 0;

		private JtaHolderTransaction(UserTransaction transaction) {
			this.holder = transaction;
		}

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
			// 事务计数 -1
			this.transactionCount--;
			// 所有事务完成处理
			if (this.transactionCount <= 0) {
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
	}

	@FunctionalInterface
	public interface ConnectionCallback<T> {
		T apply(Connection con) throws SQLException;
	}

	@FunctionalInterface
	public interface JdbcTransactionCallback<T> {
		T trans(JdbcTransaction trans) throws Throwable;
	}

	@FunctionalInterface
	public interface JtaTransactionCallback<T> {
		T trans(JtaTransaction trans) throws Throwable;
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
