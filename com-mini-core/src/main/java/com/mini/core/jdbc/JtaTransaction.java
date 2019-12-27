package com.mini.core.jdbc;

import javax.transaction.UserTransaction;

public interface JtaTransaction {
	/**
	 * 开启一个数据库事务
	 * @see UserTransaction#begin()
	 * @see UserTransaction#commit()
	 * @see UserTransaction#rollback()
	 */
	void startTransaction() throws Exception;


	/**
	 * 结束一个数据库事务
	 * @param commit true-提交
	 * @see UserTransaction#begin()
	 * @see UserTransaction#commit()
	 * @see UserTransaction#rollback()
	 */
	void endTransaction(boolean commit) throws Exception;
}
