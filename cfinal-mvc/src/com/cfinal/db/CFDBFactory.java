/**
 * Created the com.cfinal.db.CFDBFactory.java
 * @created 2017年3月2日 上午11:52:48
 * @version 1.0.0
 */
package com.cfinal.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import com.cfinal.db.thread.CFDBThreadLocal;
import com.cfinal.util.logger.CFLog;

/**
 * 数据库连接工厂类
 * @author XChao
 */
public class CFDBFactory {
	// 保存存所有数据源信息
	private static final Map<String, DataSource> data_sources = new HashMap<>();
	// 保存当前线程的所有数据库连接
	private static CFDBThreadLocal db_connection = new CFDBThreadLocal();

	/**
	 * 根据数据源名称 创建一个数据库
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static CFDB create(String name) {
		CFDB db = null;
		try {
			db = db_connection.get().get(name);
			if(db == null || isclosed(db)) {
				DataSource dataSource = data_sources.get(name);
				if(dataSource == null) {
					return null;
				}
				db = new CFDBImpl(dataSource.getConnection());
				db_connection.get().put(name, db);
			}
		} catch (SQLException e) {
			throw new CFDBException("Create db fail, dataSource name is " + name, e);
		}
		return db;
	}

	/**
	 * 添加一个数据源
	 * @param name
	 * @param dataSource
	 */
	public static void addDataSource(String name, DataSource dataSource) {
		data_sources.put(name, dataSource);
	}

	/**
	 * 获取所有连接池名称
	 * @return
	 */
	public static Set<String> keySet() {
		return data_sources.keySet();
	}

	/**
	 * 获取当前线程的所有DB连接
	 * @return
	 */
	public static Map<String, CFDB> getThreadDB() {
		return db_connection.get();
	}

	/**
	 * 判断DB是否关闭
	 * @param db
	 * @return
	 */
	public static boolean isclosed(CFDB db) {
		try {
			return db.getConnection().isClosed();
		} catch (Exception e) {
			CFLog.error("DB isClosed error. ", e);
		}
		return true;
	}

	/**
	 * 判断一个数据库连接是否关闭
	 * @param connection
	 * @return
	 */
	public static boolean isclosed(Connection connection) {
		try {
			return connection.isClosed();
		} catch (Exception e) {
			CFLog.error("Connection isClosed error. ", e);
		}
		return true;
	}

	/**
	 * close com.xcc.db.IDB
	 * @param db
	 */
	public static void close(CFDB db) {
		if(db != null && !isclosed(db)) {
			CFDBFactory.close(db.getConnection());
		}
	}

	/**
	 * close java.sql.Connection
	 * @param connection
	 */
	public static void close(Connection connection) {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			CFLog.error("Connection close fail. ", e);
		}
	}

	/**
	 * close java.sql.Statement
	 * @param statement
	 */
	public static void close(Statement statement) {
		try {
			if(statement != null && !statement.isClosed()) {
				statement.close();
			}
		} catch (SQLException e) {
			CFLog.error("Statement close fail. ", e);
		}
	}

	/**
	 * close java.sql.ResultSet, close java.sql.Statement<br/>
	 * 该方法关闭ResultSet之前,会先检查Statement是否关闭, 如果没有关闭,会关闭Statement
	 * @param resultSet
	 */
	public static void close(ResultSet resultSet) {
		try {
			if(resultSet != null && !resultSet.isClosed()) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					CFLog.error("ResultSet close fail. ", e);
				}
				Statement statement = null;
				try { // 这里获取Statement可能出异常，不用管
					statement = resultSet.getStatement();
				} catch (SQLException e) {
				}
				close(statement); // 关闭 Statement
			}
		} catch (SQLException e) {
			CFLog.error("ResultSet close fail. ", e);
		}
	}
}
