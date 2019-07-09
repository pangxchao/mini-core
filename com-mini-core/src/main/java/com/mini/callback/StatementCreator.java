package com.mini.callback;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@FunctionalInterface
public interface StatementCreator {


    Statement get(Connection connection) throws SQLException;
}
