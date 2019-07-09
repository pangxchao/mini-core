package com.mini.callback;

import java.sql.SQLException;
import java.sql.Statement;

@FunctionalInterface
public interface StatementCallback<T> {

    T doStatement(Statement statement) throws SQLException;
}
