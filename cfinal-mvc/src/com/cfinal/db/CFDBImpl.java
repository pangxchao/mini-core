/**
 * Created the com.cfinal.db.impls.CFDBImpl.java
 * @created 2016年9月22日 下午3:28:42
 * @version 1.0.0
 */
package com.cfinal.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfinal.db.mapper.CFAbstractRow;
import com.cfinal.db.mapper.CFBeanRow;
import com.cfinal.db.mapper.CFMapRow;
import com.cfinal.db.mapper.CFRow;
import com.cfinal.db.mapper.cell.CFCell;
import com.cfinal.db.paging.CFPaging;
import com.cfinal.util.lang.CFArrays;
import com.cfinal.util.logger.CFLog;

/**
 * 数据库连接实现
 * @author XChao
 */
public final class CFDBImpl implements CFDB {
	private Connection connection;
	// 事务级别
	private int transLevel = 0;
	// 事务应用计数，支持嵌套事务
	private int transRefCount = 0;

	public CFDBImpl(Connection conn) {
		this.connection = conn;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public DatabaseMetaData getMetaData() {
		try {
			return this.getConnection().getMetaData();
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}

	public void fullPreparedStatement(PreparedStatement statement, Object... params) throws SQLException {
		if(params == null || params.length == 0) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			statement.setObject((i + 1), params[i]);
		}
	}

	public int[] batch(String sql, int dataLength, CFDBBatch batchStatement) {
		PreparedStatement preparedStatement = null;
		try {
			int[] result = new int[0];
			preparedStatement = this.getConnection().prepareStatement(sql);
			for (int i = 0; i < dataLength; i++) {
				batchStatement.sets(preparedStatement, i);
				preparedStatement.addBatch();
				if(i % 500 == 499) {
					int[] temp = preparedStatement.executeBatch();
					result = CFArrays.concat(result, temp);
				}
			}
			int[] temp = preparedStatement.executeBatch();
			result = CFArrays.concat(result, temp);
			return result;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(preparedStatement);
		}
	}

	public int[] batch(String sql, Object[]... params) {
		return this.batch(sql, (params == null ? 0 : params.length), (statement, index) -> {
			this.fullPreparedStatement(statement, params[index]);
		});
	}

	public int[] batch(CFSql sql, int dataLength, CFDBBatch batchStatement) {
		return this.batch(sql.getSQL(), dataLength, batchStatement);
	}

	public int[] batch(CFSql sql, Object[]... params) {
		return this.batch(sql, (params == null ? 0 : params.length), (statement, index) -> {
			this.fullPreparedStatement(statement, params[index]);
		});
	}

	public int execute(String sql, Object... params) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = this.getConnection().prepareStatement(sql);
			this.fullPreparedStatement(preparedStatement, params);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(preparedStatement);
		}
	}

	public long executeGen(String sql, Object... params) {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			this.fullPreparedStatement(preparedStatement, params);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) {
				return resultSet.getLong(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public int execute(CFSql sql) {
		return this.execute(sql.getSQL(), sql.getParams());
	}

	public long executeGen(CFSql sql) {
		return this.executeGen(sql.getSQL(), sql.getParams());
	}

	public int insert(String tname, String keys, Object... params) {
		CFSql sql = new CFSql("insert into ").append(tname).append("(").append(keys).append(") values(");
		return this.execute(sql.append(CFSql.getInsertParam(keys)).append(")", params));
	}

	public long insertGen(String tname, String keys, Object... params) {
		CFSql sql = new CFSql("insert into ").append(tname).append("(").append(keys).append(") values(");
		return this.executeGen(sql.append(CFSql.getInsertParam(keys)).append(")", params));
	}

	public int replace(String tname, String keys, Object... params) {
		CFSql sql = new CFSql("replace into ").append(tname).append("(").append(keys).append(") values(");
		return this.execute(sql.append(CFSql.getInsertParam(keys)).append(")", params));
	}

	public long replaceGen(String tname, String keys, Object... params) {
		CFSql sql = new CFSql("replace into ").append(tname).append("(").append(keys).append(") values(");
		return this.executeGen(sql.append(CFSql.getInsertParam(keys)).append(")", params));
	}

	public int update(String tname, String keys, String wheres, Object... params) {
		CFSql sql = new CFSql("update ").append(tname).append(" set ");
		sql.append(CFSql.getUpdateParam(keys));
		if(StringUtils.isNotBlank(wheres)) {
			sql.append(" where ").append(CFSql.getWheresParam(wheres));
		}
		return this.execute(sql.append(" ", params));
	}

	public int delete(String tname, String wheres, Object... params) {
		CFSql sql = new CFSql("delete from ").append(tname);
		if(StringUtils.isNotBlank(wheres)) {
			sql.append(" where ").append(CFSql.getWheresParam(wheres));
		}
		if(params != null && params.length > 0) {
			sql.append(" ", params);
		}
		return this.execute(sql);
	}

	public ResultSet executeQuery(String sql, Object... params) {
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = this.getConnection().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
			this.fullPreparedStatement(prepareStatement, params);
			return prepareStatement.executeQuery();
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}

	public ResultSet executeQuery(CFSql sql) {
		return this.executeQuery(sql.getSQL(), sql.getParams());
	}

	public ResultSet executeQuery(CFPaging paging, String sql, Object... params) {
		PreparedStatement preparedStatement = null;
		try {
			paging.setTotal(this.queryInt(paging.getMaxSql(sql), params));
			preparedStatement = this.getConnection().prepareStatement(paging.paging(sql));
			this.fullPreparedStatement(preparedStatement, params);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}

	public ResultSet executeQuery(CFPaging paging, CFSql sql) {
		return this.executeQuery(paging, sql.getSQL(), sql.getParams());
	}

	public JSONArray analysis(ResultSet resultSet) {
		JSONArray result = new JSONArray();
		try {
			CFAbstractRow<JSONObject> mapper = new CFMapRow();
			this.getMapperByResultSet(resultSet, mapper);
			while (resultSet.next()) {
				result.add(mapper.getRow(this, resultSet));
			}
			return result;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public JSONArray analysis(ResultSet resultSet, Map<String, CFCell<?>> cells) {
		JSONArray result = new JSONArray();
		try {
			CFAbstractRow<JSONObject> mapper = new CFMapRow();
			this.getMapperByResultSet(resultSet, mapper, cells);
			while (resultSet.next()) {
				result.add(mapper.getRow(this, resultSet));
			}
			return result;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public <T> List<T> analysis(ResultSet resultSet, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		try {
			CFAbstractRow<T> mapper = new CFBeanRow<T>(this, clazz);
			this.getMapperByResultSet(resultSet, mapper);
			while (resultSet.next()) {
				result.add(mapper.getRow(this, resultSet));
			}
			return result;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public <T> List<T> analysis(ResultSet resultSet, Class<T> clazz, Map<String, CFCell<?>> cells) {
		List<T> result = new ArrayList<T>();
		try {
			CFAbstractRow<T> mapper = new CFBeanRow<T>(this, clazz);
			this.getMapperByResultSet(resultSet, mapper, cells);
			while (resultSet.next()) {
				result.add(mapper.getRow(this, resultSet));
			}
			return result;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public <T> List<T> analysis(ResultSet resultSet, CFRow<T> mapper) {
		List<T> result = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				result.add(mapper.getRow(this, resultSet));
			}
			return result;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public JSONObject analysisOne(ResultSet resultSet) {
		try {
			CFAbstractRow<JSONObject> mapper = new CFMapRow();
			this.getMapperByResultSet(resultSet, mapper);
			if(resultSet.next()) {
				return mapper.getRow(this, resultSet);
			}
			return null;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public JSONObject analysisOne(ResultSet resultSet, Map<String, CFCell<?>> cells) {
		try {
			CFAbstractRow<JSONObject> mapper = new CFMapRow();
			this.getMapperByResultSet(resultSet, mapper, cells);
			if(resultSet.next()) {
				return mapper.getRow(this, resultSet);
			}
			return null;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public <T> T analysisOne(ResultSet resultSet, Class<T> clazz) {
		try {
			CFAbstractRow<T> mapper = new CFBeanRow<T>(this, clazz);
			this.getMapperByResultSet(resultSet, mapper);
			if(resultSet.next()) {
				return mapper.getRow(this, resultSet);
			}
			return null;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public <T> T analysisOne(ResultSet resultSet, Class<T> clazz, Map<String, CFCell<?>> cells) {
		try {
			CFAbstractRow<T> mapper = new CFBeanRow<T>(this, clazz);
			this.getMapperByResultSet(resultSet, mapper, cells);
			if(resultSet.next()) {
				return mapper.getRow(this, resultSet);
			}
			return null;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public <T> T analysisOne(ResultSet resultSet, CFRow<T> mapper) {
		try {
			if(resultSet.next()) {
				return mapper.getRow(this, resultSet);
			}
			return null;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public JSONArray query(CFSql sql) {
		return this.analysis(this.executeQuery(sql), sql.getCells());
	}

	public JSONArray query(String sql, Object... params) {
		return this.query(new CFSql(sql, params));
	}

	public <T> List<T> query(Class<T> clazz, CFSql sql) {
		return this.analysis(this.executeQuery(sql), clazz, sql.getCells());
	}

	public <T> List<T> query(Class<T> clazz, String sql, Object... params) {
		return this.query(clazz, new CFSql(sql, params));
	}

	public <T> List<T> query(CFRow<T> mapper, CFSql sql) {
		return this.analysis(this.executeQuery(sql), mapper);
	}

	public <T> List<T> query(CFRow<T> mapper, String sql, Object... params) {
		return this.query(mapper, new CFSql(sql, params));
	}

	public JSONArray query(CFPaging paging, CFSql sql) {
		return this.analysis(this.executeQuery(paging, sql), sql.getCells());
	}

	public JSONArray query(CFPaging paging, String sql, Object... params) {
		return this.query(paging, new CFSql(sql, params));
	}

	public <T> List<T> query(CFPaging paging, Class<T> clazz, CFSql sql) {
		return this.analysis(this.executeQuery(paging, sql), clazz, sql.getCells());
	}

	public <T> List<T> query(CFPaging paging, Class<T> clazz, String sql, Object... params) {
		return this.query(paging, clazz, new CFSql(sql, params));
	}

	public <T> List<T> query(CFPaging paging, CFRow<T> mapper, CFSql sql) {
		return this.analysis(this.executeQuery(paging, sql), mapper);
	}

	public <T> List<T> query(CFPaging paging, CFRow<T> mapper, String sql, Object... params) {
		return this.query(paging, mapper, new CFSql(sql, params));
	}

	public JSONObject queryOne(CFSql sql) {
		return this.analysisOne(this.executeQuery(sql), sql.getCells());
	}

	public JSONObject queryOne(String sql, Object... params) {
		return this.queryOne(new CFSql(sql, params));
	}

	public <T> T queryOne(Class<T> clazz, CFSql sql) {
		return this.analysisOne(this.executeQuery(sql), clazz, sql.getCells());
	}

	public <T> T queryOne(Class<T> clazz, String sql, Object... params) {
		return this.queryOne(clazz, new CFSql(sql, params));
	}

	public <T> T queryOne(CFRow<T> mapper, CFSql sql) {
		return this.analysisOne(this.executeQuery(sql), mapper);
	}

	public <T> T queryOne(CFRow<T> mapper, String sql, Object... params) {
		return this.queryOne(mapper, new CFSql(sql, params));
	}

	public String queryString(CFSql sql) {
		return this.queryString(sql.getSQL(), sql.getParams());
	}

	public String queryString(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public long queryLong(CFSql sql) {
		return this.queryLong(sql.getSQL(), sql.getParams());
	}

	public long queryLong(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getLong(1);
			}
			return 0L;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public int queryInt(CFSql sql) {
		return this.queryInt(sql.getSQL(), sql.getParams());
	}

	public int queryInt(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public short queryShort(CFSql sql) {
		return this.queryShort(sql.getSQL(), sql.getParams());
	}

	public short queryShort(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getShort(1);
			}
			return (short) 0;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public byte queryByte(CFSql sql) {
		return this.queryByte(sql.getSQL(), sql.getParams());
	}

	public byte queryByte(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getByte(1);
			}
			return (byte) 0;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public double queryDouble(CFSql sql) {
		return this.queryDouble(sql.getSQL(), sql.getParams());
	}

	public double queryDouble(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getDouble(1);
			}
			return 0.0D;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public float queryFloat(CFSql sql) {
		return this.queryFloat(sql.getSQL(), sql.getParams());
	}

	public float queryFloat(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getFloat(1);
			}
			return 0.0F;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public Date queryDate(CFSql sql) {
		return this.queryDate(sql.getSQL(), sql.getParams());
	}

	public Date queryDate(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getTimestamp(1);
			}
			return null;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public boolean queryBoolean(CFSql sql) {
		return this.queryBoolean(sql.getSQL(), sql.getParams());
	}

	public boolean queryBoolean(String sql, Object... params) {
		ResultSet resultSet = null;
		try {
			resultSet = this.executeQuery(sql, params);
			if(resultSet.next()) {
				return resultSet.getBoolean(1);
			}
			return false;
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(resultSet);
		}
	}

	public boolean transaction(CFDBTransaction transaction) {
		return this.transaction(Connection.TRANSACTION_REPEATABLE_READ, transaction);
	}

	public boolean transaction(int transLevel, CFDBTransaction transaction) {
		if(this.transRefCount == 0) { // 第一次开启事务
			try { // 开启事务阶段
				connection.setAutoCommit(false);
				this.transLevel = transLevel;
				connection.setTransactionIsolation(this.transLevel);
			} catch (SQLException e) {
				throw new CFDBException(e);
			}
			// 嵌套事务中的事务级别不能高于初始级别
		} else if(transLevel > this.transLevel) {
			throw new CFDBException("Nest transnationt level is high." + this.transLevel + "<" + transLevel);
		}
		transRefCount++;
		// 事务执行阶段
		Savepoint savepoint = null;
		boolean commit = false;
		try {
			savepoint = connection.setSavepoint();
			commit = transaction.transaction();
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			// 事务提交 或者回滚阶段
			this.transRefCount--;
			if(commit == false && savepoint != null) { // 回滚事务到设置的回滚点
				try {
					connection.rollback(savepoint);
				} catch (SQLException e) {
					CFLog.error("Transaction rollback error.");
				}
			}
			if(this.transRefCount == 0) { // 如果事务计数为0 时, 则表示所有事务提交或者回滚完成
				boolean roolback = true;
				try {
					this.transLevel = 0;
					if(commit == true) {
						this.getConnection().commit();
						roolback = false;
						connection.setAutoCommit(true);
					}
				} catch (SQLException e) {
					throw new CFDBException(e);
				} finally {
					if(roolback) {
						try {
							commit = false;
							connection.rollback();
							connection.setAutoCommit(true);
						} catch (SQLException e) {
							throw new CFDBException(e);
						}
					}
				}
			}
		}
		return commit;
	}

	/**
	 * 根据 ResultSet, Sql 初始化RowMapper数据
	 * @param resultSet
	 * @param mapper
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private <T> CFAbstractRow<T> getMapperByResultSet(ResultSet resultSet, CFAbstractRow<T> mapper)
		throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			mapper.putCell(rsmd.getColumnLabel(i), rsmd.getColumnClassName(i));
		}
		return mapper;
	}

	/**
	 * 根据 ResultSet, Sql 初始化RowMapper数据
	 * @param resultSet
	 * @param mapper
	 * @param cells
	 * @return
	 * @throws SQLException
	 */
	private <T> CFAbstractRow<T> getMapperByResultSet(ResultSet resultSet, CFAbstractRow<T> mapper,
		Map<String, CFCell<?>> cells) throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			mapper.putCell(rsmd.getColumnLabel(i), rsmd.getColumnClassName(i));
		}
		for (String hkey : cells.keySet()) {
			mapper.putCell(hkey, cells.get(hkey));
		}
		return mapper;
	}
}
