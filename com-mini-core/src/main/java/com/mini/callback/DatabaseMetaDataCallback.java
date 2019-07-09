package com.mini.callback;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;


@FunctionalInterface
public interface DatabaseMetaDataCallback<T> {

    T doDatabaseMetaData(DatabaseMetaData metaData) throws SQLException;
}
