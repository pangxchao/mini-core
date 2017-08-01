/**
 * Created the com.cfinal.db.mapper.cell.CFCell.java
 * @created 2016年9月24日 上午11:49:55
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFCell.java
 * @author XChao
 */
@FunctionalInterface
public interface CFCell<T> {
	public T getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException;
}
