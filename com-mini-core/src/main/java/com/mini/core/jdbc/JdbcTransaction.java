package com.mini.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public interface JdbcTransaction {

	/**
	 * 设置事务隔离级别
	 * @param level 隔离级别
	 */
	void setTransactionIsolation(int level) throws SQLException;

	/**
	 * 开启一个数据库事务
	 * @see Connection#setAutoCommit(boolean)
	 * @see Connection#rollback(Savepoint)
	 * @see Connection#rollback()
	 * @see Connection#commit()
	 */
	void startTransaction() throws SQLException;

	/**
	 * 事务中保存一个回滚点
	 * @return 回滚点
	 */
	Savepoint setSavepoint() throws SQLException;

	/**
	 * 事务中保存一个回滚点
	 * @param name 保存点名称
	 * @return 回滚点
	 */
	Savepoint setSavepoint(String name) throws SQLException;

	/**
	 * 回滚事务到指定回滚点
	 * @param point 指定回滚保存点
	 * @see Connection#setAutoCommit(boolean)
	 * @see Connection#rollback(Savepoint)
	 * @see Connection#rollback()
	 * @see Connection#commit()
	 */
	void transactionRollback(Savepoint point) throws SQLException;

	/**
	 * 结束一个数据库事务
	 * @param commit true-提交
	 * @see Connection#setAutoCommit(boolean)
	 * @see Connection#rollback(Savepoint)
	 * @see Connection#rollback()
	 * @see Connection#commit()
	 */
	void endTransaction(boolean commit) throws SQLException;
}
