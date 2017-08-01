/**
 * Created the com.cfinal.db.CFDBException.java
 * @created 2017年3月2日 上午11:52:48
 * @version 1.0.0
 */
package com.cfinal.db;

import java.sql.SQLException;

/**
 * 数据库操作异常
 * @author XChao
 */
public class CFDBException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CFDBException(String message) {
		super(message);
	}
	
	public CFDBException(SQLException e) {
		super(e);
	}

	public CFDBException(String message, SQLException e) {
		super(message, e);
	}
}
