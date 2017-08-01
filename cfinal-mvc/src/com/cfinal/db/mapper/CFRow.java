/**
 * Created the com.cfinal.db.mapper.CFRow.java
 * @created 2016年9月24日 上午11:54:02
 * @version 1.0.0
 */
package com.cfinal.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.CFIRow.java
 * @author XChao
 */
@FunctionalInterface
public interface CFRow<T> {
	public T getRow(CFDB db, ResultSet resultSet) throws SQLException ;
}
