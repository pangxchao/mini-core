package com.mini.callback;

import java.sql.CallableStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface CallableStatementCallback<T> {

    T doCallableStatement(CallableStatement statement) throws SQLException;
}
