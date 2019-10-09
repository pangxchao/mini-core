package com.mini.callback;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetCallback<T> {

	T doResultSet(ResultSet rs) throws SQLException;
}
