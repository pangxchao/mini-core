package com.mini.callback;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionCallback<T> {

    T doConnection(Connection connection) throws SQLException;
}
