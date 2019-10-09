package com.mini.callback;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface CallableStatementCreator {
	CallableStatement get(Connection connection) throws SQLException;
}
