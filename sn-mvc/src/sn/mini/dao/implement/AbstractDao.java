/**
 * Created the sn.mini.dao.implement.AbstractDao.java
 * @created 2017年9月22日 下午12:23:36
 * @version 1.0.0
 */
package sn.mini.dao.implement;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.DataSource;

import sn.mini.dao.DBException;
import sn.mini.dao.IDao;
import sn.mini.dao.Paging;
import sn.mini.dao.Sql;
import sn.mini.dao.mapper.row.IRow;
import sn.mini.dao.util.DaoUtil;
import sn.mini.util.json.JSONArray;
import sn.mini.util.json.JSONObject;
import sn.mini.util.lang.ArraysUtil;
import sn.mini.util.logger.Log;

/**
 * sn.mini.dao.implement.AbstractDao.java
 * @author XChao
 */
public abstract class AbstractDao implements IDao {
	private final Connection connection; // 数据库连接
	private int transRefCount = 0; // 事务应用计数，支持嵌套事务

	public AbstractDao(Connection connection) {
		this.connection = connection;
	}

	public AbstractDao(DataSource dataSource) throws SQLException {
		this(dataSource.getConnection());
	}

	@Override
	public Statement createStatement() throws SQLException {
		return this.connection.createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.connection.prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.connection.prepareCall(sql);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		return this.connection.nativeSQL(sql);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.connection.setAutoCommit(autoCommit);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return this.connection.getAutoCommit();
	}

	@Override
	public void commit() throws SQLException {
		this.connection.commit();
	}

	@Override
	public void rollback() throws SQLException {
		this.connection.rollback();
	}

	@Override
	public void close() throws SQLException {
		this.connection.close();
		Log.trace("********* dao close *******");
	}

	@Override
	public boolean isClosed() throws SQLException {
		return this.connection != null && this.connection.isClosed();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return this.connection.getMetaData();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		this.connection.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return this.connection.isReadOnly();
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		this.connection.setCatalog(catalog);
	}

	@Override
	public String getCatalog() throws SQLException {
		return this.connection.getCatalog();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		this.connection.setTransactionIsolation(level);
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return this.connection.getTransactionIsolation();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.connection.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		this.connection.clearWarnings();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.connection.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
		throws SQLException {
		return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.connection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.connection.getTypeMap();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.connection.setTypeMap(map);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		this.connection.setHoldability(holdability);
	}

	@Override
	public int getHoldability() throws SQLException {
		return this.connection.getHoldability();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return this.connection.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return this.connection.setSavepoint(name);
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		this.connection.rollback(savepoint);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		this.connection.releaseSavepoint(savepoint);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
		throws SQLException {
		return this.connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException {
		return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException {
		return this.connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return this.connection.prepareStatement(sql, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return this.connection.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return this.connection.prepareStatement(sql, columnNames);
	}

	@Override
	public Clob createClob() throws SQLException {
		return this.connection.createClob();
	}

	@Override
	public Blob createBlob() throws SQLException {
		return this.connection.createBlob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		return this.connection.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return this.connection.createSQLXML();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return this.connection.isClosed();
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		this.connection.setClientInfo(name, value);
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		this.connection.setClientInfo(properties);
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		return this.connection.getClientInfo(name);
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return this.connection.getClientInfo();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return this.connection.createArrayOf(typeName, elements);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return this.connection.createStruct(typeName, attributes);
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		this.connection.setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException {
		return this.connection.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		this.connection.abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		this.connection.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return this.connection.getNetworkTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.connection.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.connection.isWrapperFor(iface);
	}

	@Override
	public void fullPreparedStatement(PreparedStatement statement, Object... params) {
		try {
			for (int i = 0; params != null && i < params.length; i++) {
				statement.setObject((i + 1), params[i]);
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	@Override
	public int[] batch(String sql, int length, Batch batch) {
		int[] result = new int[0]; // 创建返回的初始对象
		try (PreparedStatement statement = this.prepareStatement(sql)) {
			for (int index = 0; index < length; index++) {
				this.fullPreparedStatement(statement, batch.values(index));
				statement.addBatch(); // 将当前statement中的数据提交到内存在
				if(index % 500 == 499) { // 当前内存中数据有 500到时，提交到数据库，并返回成功标识
					result = ArraysUtil.concat(result, statement.executeBatch());
				}
			}
			return ArraysUtil.concat(result, statement.executeBatch());
		} catch (SQLException e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	@Override
	public int[] batch(String sql, Object[][] params) {
		return this.batch(sql, (params == null ? 0 : params.length), (index) -> {
			return params[index];
		});
	}

	@Override
	public int[] batch(Sql sql, int length, Batch batch) {
		return this.batch(sql.sqlString(), length, batch);
	}

	@Override
	public int[] batch(Sql sql, Object[][] params) {
		return this.batch(sql.sqlString(), params);
	}

	@Override
	public int execute(String sql, Object... params) {
		try (PreparedStatement preparedStatement = this.prepareStatement(sql)) {
			this.fullPreparedStatement(preparedStatement, params);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	@Override
	public long executeGen(String sql, Object... params) {
		try (PreparedStatement preparedStatement = this.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			this.fullPreparedStatement(preparedStatement, params);
			preparedStatement.executeUpdate();
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				return resultSet.next() ? resultSet.getLong(1) : 0;
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	@Override
	public int execute(Sql sql) {
		return this.execute(sql.sqlString(), sql.params());
	}

	@Override
	public long executeGen(Sql sql) {
		return this.executeGen(sql.sqlString(), sql.params());
	}

	@Override
	public int insert(String tname, String[] keys, Object... params) {
		return this.execute(Sql.createInsert(tname, keys).sqlString(), params);
	}

	@Override
	public long insertGen(String tname, String[] keys, Object... params) {
		return this.executeGen(Sql.createInsert(tname, keys).sqlString(), params);
	}

	@Override
	public int replace(String tname, String[] keys, Object... params) {
		return this.execute(Sql.createReplace(tname, keys).sqlString(), params);
	}

	@Override
	public long replaceGen(String tname, String[] keys, Object... params) {
		return this.execute(Sql.createReplace(tname, keys).sqlString(), params);
	}

	@Override
	public int update(String tname, String[] keys, String[] wheres, Object... params) {
		return this.execute(Sql.createUpdate(tname, keys, wheres).sqlString(), params);
	}

	@Override
	public int delete(String tname, String[] wheres, Object... params) {
		return this.execute(Sql.createDelete(tname, wheres).sqlString(), params);
	}

	@Override
	public <T> T executeQuery(IRow<T> row, String sql, Object... params) {
		try (PreparedStatement prepareStatement = this.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
			ResultSet.CONCUR_READ_ONLY)) {
			this.fullPreparedStatement(prepareStatement, params);
			try (ResultSet resultSet = prepareStatement.executeQuery()) {
				return row.getRow(resultSet);
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T executeQuery(IRow<T> row, Sql sql) {
		return this.executeQuery(row, sql.sqlString(), sql.params());
	}

	@Override
	public <T> T executeQuery(IRow<T> row, Paging paging, String sql, Object... params) {
		paging.setTotal(this.queryInt(this.totals(sql), params));
		try (PreparedStatement preparedStatement = this.prepareStatement(this.paging(paging, sql))) {
			this.fullPreparedStatement(preparedStatement, params);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return row.getRow(resultSet);
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T executeQuery(IRow<T> row, Paging paging, Sql sql) {
		return this.executeQuery(row, paging, sql.sqlString(), sql.params());
	}

	@Override
	public JSONArray query(Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, sql.cells());
		}, sql);
	}

	@Override
	public JSONArray query(String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet);
		}, sql, params);
	}

	@Override
	public <T> List<T> query(Class<T> clazz, Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, clazz, sql.cells());
		}, sql);
	}

	@Override
	public <T> List<T> query(Class<T> clazz, String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, clazz);
		}, sql, params);
	}

	@Override
	public <T> List<T> query(IRow<T> mapper, Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, mapper);
		}, sql);
	}

	@Override
	public <T> List<T> query(IRow<T> mapper, String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, mapper);
		}, sql, params);
	}

	@Override
	public JSONArray query(Paging paging, Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, sql.cells());
		}, paging, sql);
	}

	@Override
	public JSONArray query(Paging paging, String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet);
		}, paging, sql, params);
	}

	@Override
	public <T> List<T> query(Paging paging, Class<T> clazz, Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, clazz, sql.cells());
		}, paging, sql);
	}

	@Override
	public <T> List<T> query(Paging paging, Class<T> clazz, String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, clazz);
		}, paging, sql, params);
	}

	@Override
	public <T> List<T> query(Paging paging, IRow<T> mapper, Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, mapper);
		}, paging, sql);
	}

	@Override
	public <T> List<T> query(Paging paging, IRow<T> mapper, String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysis(resultSet, mapper);
		}, paging, sql, params);
	}

	@Override
	public JSONObject queryOne(Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysisOne(resultSet, sql.cells());
		}, sql);
	}

	@Override
	public JSONObject queryOne(String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysisOne(resultSet);
		}, sql, params);
	}

	@Override
	public <T> T queryOne(Class<T> clazz, Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysisOne(resultSet, clazz, sql.cells());
		}, sql);
	}

	@Override
	public <T> T queryOne(Class<T> clazz, String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysisOne(resultSet, clazz);
		}, sql, params);
	}

	@Override
	public <T> T queryOne(IRow<T> mapper, Sql sql) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysisOne(resultSet, mapper);
		}, sql);
	}

	@Override
	public <T> T queryOne(IRow<T> mapper, String sql, Object... params) {
		return this.executeQuery(resultSet -> {
			return DaoUtil.analysisOne(resultSet, mapper);
		}, sql, params);
	}

	@Override
	public String queryString(Sql sql) {
		return this.queryString(sql.sqlString(), sql.params());
	}

	@Override
	public String queryString(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getString(1) : null), sql, params);
	}

	@Override
	public long queryLong(Sql sql) {
		return this.queryLong(sql.sqlString(), sql.params());
	}

	@Override
	public long queryLong(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getLong(1) : 0L), sql, params);
	}

	@Override
	public int queryInt(Sql sql) {
		return this.queryInt(sql.sqlString(), sql.params());
	}

	@Override
	public int queryInt(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getInt(1) : 0), sql, params);
	}

	@Override
	public short queryShort(Sql sql) {
		return this.queryShort(sql.sqlString(), sql.params());
	}

	@Override
	public short queryShort(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getShort(1) : (short) 0), sql, params);
	}

	@Override
	public byte queryByte(Sql sql) {
		return this.queryByte(sql.sqlString(), sql.params());
	}

	@Override
	public byte queryByte(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getByte(1) : (byte) 0), sql, params);
	}

	@Override
	public double queryDouble(Sql sql) {
		return this.queryDouble(sql.sqlString(), sql.params());
	}

	@Override
	public double queryDouble(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getDouble(1) : 0D), sql, params);
	}

	@Override
	public float queryFloat(Sql sql) {
		return this.queryFloat(sql.sqlString(), sql.params());
	}

	@Override
	public float queryFloat(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getFloat(1) : 0F), sql, params);
	}

	@Override
	public Date queryDate(Sql sql) {
		return this.queryDate(sql.sqlString(), sql.params());
	}

	@Override
	public Date queryDate(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getTimestamp(1) : null), sql, params);
	}

	@Override
	public boolean queryBoolean(Sql sql) {
		return this.queryBoolean(sql.sqlString(), sql.params());
	}

	@Override
	public boolean queryBoolean(String sql, Object... params) {
		return this.executeQuery(resultSet -> (resultSet.next() ? resultSet.getBoolean(1) : false), sql, params);
	}

	@Override
	public boolean transaction(Transaction transaction) {
		return this.transaction(Connection.TRANSACTION_REPEATABLE_READ, transaction);
	}

	@Override
	public boolean transaction(int transLevel, Transaction transaction) {
		try {
			if(this.transRefCount == 0) { // 第一次开启事务
				this.setAutoCommit(false);
				this.setTransactionIsolation(transLevel);
			}
			// 事务执行阶段
			Savepoint savepoint = null;
			boolean commit = false;
			try {
				this.transRefCount = this.transRefCount + 1;
				savepoint = this.setSavepoint();
				commit = transaction.transaction();
			} finally {
				this.transRefCount = this.transRefCount - 1;
				if(commit == false && savepoint != null) { // 回滚事务到设置的回滚点
					try {
						this.rollback(savepoint);
					} catch (SQLException e) {
						Log.error("Transaction rollback error.");
					}
				}
				if(this.transRefCount == 0) { // 如果事务计数为0 时, 则表示所有事务提交或者回滚完成
					boolean roolback = true;
					try {
						if(commit == true) {
							this.commit();
							roolback = false;
						}
					} finally {
						try {
							if(roolback) {
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
		} catch (SQLException e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	protected abstract String totals(String sql);

	protected abstract String paging(Paging paging, String sql);
}
