/**
 * Created the com.cfinal.db.CFDBBatch.java
 * @created 2017年3月2日 上午11:52:48
 * @version 1.0.0
 */
package com.cfinal.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * com.cfinal.db.CFDBBatch.java
 * @author XChao
 */
@FunctionalInterface
public interface CFDBBatch {
	public void sets(PreparedStatement statement, int index) throws SQLException;
}
