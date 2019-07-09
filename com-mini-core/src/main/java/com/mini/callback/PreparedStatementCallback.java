package com.mini.callback;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementCallback<T> {

    T doPreparedStatement(PreparedStatement statement) throws SQLException;
}
