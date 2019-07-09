package com.mini.jdbc.util;

import com.mini.callback.DatabaseMetaDataCallback;
import com.mini.jdbc.holder.ConnectionHolder;
import com.mini.util.ObjectUtil;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class JdbcUtil {
    private static final ThreadLocal<Map<DataSource, ConnectionHolder>> resources = ThreadLocal.withInitial(ConcurrentHashMap::new);

    /**
     * Obtain a Connection from the given DataSource. Translates SQLExceptions into the Spring hierarchy of unchecked generic data access exceptions,
     * simplifying calling code and making any exception that is thrown more meaningful.
     * @param dataSource the DataSource to obtain Connections from
     * @return a JDBC Connection from the given DataSource
     */
    @Nonnull
    public static Connection getConnection(DataSource dataSource) {
        Connection connection = null;
        try {
            // 获取当前线程的指定数据源的连接状态
            Map<DataSource, ConnectionHolder> map = Objects.requireNonNull(resources.get());
            ConnectionHolder connectionHolder = map.computeIfAbsent(dataSource, ds -> {
                return new ConnectionHolder(); //
            });

            // 如果没有打开的连接，则重新设置连接
            if (!connectionHolder.hasOpenConnection()) {
                connection = dataSource.getConnection();
                connectionHolder.setConnection(connection);
            }
            // 从 ConnectionHolder 对象中获取连接
            connection = connectionHolder.getConnection();
            connectionHolder.requestedConnection();
            return connection;
        } catch (SQLException | RuntimeException e) {
            releaseConnection(connection, dataSource);
            throw new RuntimeException(e);
        }
    }


    /**
     * Close the given Connection, obtained from the given DataSource, if it is not managed externally (that is, not bound to the thread).
     * @param connection the Connection to close if necessary (if this is {@code null}, the call will be ignored)
     * @param dataSource the DataSource that the Connection was obtained from (may be {@code null})
     * @see #getConnection
     */
    public static void releaseConnection(@Nullable Connection connection, @Nullable DataSource dataSource) {
        if (connection == null || dataSource == null) {
            return;
        }
        try {
            // 获取当前线程的指定数据源的连接状态
            Map<DataSource, ConnectionHolder> map = Objects.requireNonNull(resources.get());
            ConnectionHolder connectionHolder = map.computeIfAbsent(dataSource, ds -> {
                return new ConnectionHolder(); //
            });

            Connection holder;
            if (connectionHolder.hasOpenConnection()) {
                holder = connectionHolder.getConnection();
                if (ObjectUtil.equals(holder, connection)) {
                    connectionHolder.releasedConnection();
                    return;
                }
            }
            // 如果连接未关闭，则关闭连接
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 填充 PreparedStatement 参数
     * @param statement PreparedStatement 对象
     * @param params    参数
     * @return PreparedStatement 对象
     */
    public static PreparedStatement full(PreparedStatement statement, Object[] params) throws SQLException {
        for (int i = 0; params != null && i < params.length; i++) {
            statement.setObject((i + 1), params[i]);
        }
        return statement;
    }

    /**
     * Retrieve a JDBC column value from a ResultSet, using the most appropriate value type. The returned value should be a detached value object, not having
     * any ties to the active ResultSet: in particular, it should not be a Blob or Clob object but rather a byte array or String representation, respectively.
     * <p>Uses the {@code getObject(index)} method, but includes additional "hacks"
     * to get around Oracle 10g returning a non-standard object for its TIMESTAMP datatype and a {@code java.sql.Date} for DATE columns leaving out the time
     * portion: These columns will explicitly be extracted as standard {@code java.sql.Timestamp} object.
     * @param rs    is the ResultSet holding the data
     * @param index is the column index
     * @return the value object
     * @throws SQLException if thrown by the JDBC API
     * @see java.sql.Blob
     * @see java.sql.Clob
     * @see java.sql.Timestamp
     */
    @Nullable
    public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
        Object obj = rs.getObject(index);
        String className = obj == null ? null : obj.getClass().getName();
        if (obj instanceof Blob) {
            Blob blob = (Blob) obj;
            return blob.getBytes(1, (int) blob.length());
        }
        if (obj instanceof Clob) {
            Clob clob = (Clob) obj;
            return clob.getSubString(1, (int) clob.length());
        }
        if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
            obj = rs.getTimestamp(index);
        }
        if (className != null && className.startsWith("oracle.sql.DATE")) {
            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
            if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
                obj = rs.getTimestamp(index);
            } else obj = rs.getDate(index);
        }
        if (obj instanceof java.sql.Date) {
            if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
                return rs.getTimestamp(index);
            }
            return obj;
        }
        return obj;
    }


    /**
     * Retrieve a JDBC column value from a ResultSet, using the specified value type.
     * <p>Uses the specifically typed ResultSet accessor methods, falling back to
     * {@link #getResultSetValue(java.sql.ResultSet, int)} for unknown types.
     * <p>Note that the returned value may not be assignable to the specified
     * required type, in case of an unknown type. Calling code needs to deal with this case appropriately, e.g. throwing a corresponding exception.
     * @param rs           is the ResultSet holding the data
     * @param index        is the column index
     * @param requiredType the required value type (may be {@code null})
     * @return the value object (possibly not of the specified required type, with further conversion steps necessary)
     * @throws SQLException if thrown by the JDBC API
     * @see #getResultSetValue(ResultSet, int)
     */
    @Nullable
    public static Object getResultSetValue(ResultSet rs, int index, @Nullable Class<?> requiredType) throws SQLException {
        if (requiredType == null) return getResultSetValue(rs, index);
        // 字符串返回类型
        if (requiredType.isAssignableFrom(String.class)) {
            return rs.getString(index);
        }
        // long/Long 返回类型处理
        if (long.class == requiredType || Long.class == requiredType) {
            return rs.wasNull() ? null : rs.getLong(index);
        }
        // int/Integer 返回类型处理
        if (int.class == requiredType || Integer.class == requiredType) {
            return rs.wasNull() ? null : rs.getInt(index);
        }
        // short/Short 返回类型处理
        if (short.class == requiredType || Short.class == requiredType) {
            return rs.wasNull() ? null : rs.getShort(index);
        }
        // byte/Byte 返回类型处理
        if (byte.class == requiredType || Byte.class == requiredType) {
            return rs.wasNull() ? null : rs.getByte(index);
        }
        // float/Float 返回类型处理
        if (float.class == requiredType || Float.class == requiredType) {
            return rs.wasNull() ? null : rs.getFloat(index);
        }
        // double/Double/Number 返回类型处理
        if (double.class == requiredType || Double.class == requiredType || Number.class == requiredType) {
            return rs.wasNull() ? null : rs.getDouble(index);
        }
        // java.sql.Timestamp/java.sql.Date/LocalDateTime  类型处理
        if (java.sql.Timestamp.class == requiredType || java.util.Date.class == requiredType || LocalDateTime.class == requiredType) {
            return rs.getTimestamp(index);
        }
        // java.sql.Date 类型处理
        if (java.sql.Date.class == requiredType || LocalDate.class == requiredType) {
            return rs.getDate(index);
        }
        // java.sql.Time 类型处理
        if (java.sql.Time.class == requiredType || LocalTime.class == requiredType) {
            return rs.getTime(index);
        }
        // byte[] 类型处理
        if (byte[].class == requiredType) {
            return rs.getBytes(index);
        }
        // Blob 类型处理
        if (Blob.class == requiredType) {
            return rs.getBlob(index);
        }
        // Clob 类型处理
        if (Clob.class == requiredType) {
            return rs.getClob(index);
        }
        // BigDecimal 类型处理
        if (BigDecimal.class == requiredType) {
            return rs.getBigDecimal(index);
        }
        // 其它类型处理
        return rs.getObject(index, requiredType);
    }

    /**
     * Extract database meta-data via the given DatabaseMetaDataCallback.
     * <p>This method will open a connection to the database and retrieve the database meta-data.
     * Since this method is called before the exception translation feature is configured for a datasource, this method can not rely on the SQLException
     * translation functionality.
     * <p>Any exceptions will be wrapped in a MetaDataAccessException. This is a checked exception
     * and any calling code should catch and handle this exception. You can just log the error and hope for the best, but there is probably a more serious error
     * that will reappear when you try to access the database again.
     * @param dataSource the DataSource to extract meta-data for
     * @param callback   callback that will do the actual work
     * @return object containing the extracted information, as returned by the DatabaseMetaDataCallback's {@code processMetaData} method
     */
    public static <T> T extractDatabaseMetaData(DataSource dataSource, DatabaseMetaDataCallback<T> callback) throws SQLException {
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection(dataSource);
            DatabaseMetaData metaData = connection.getMetaData();
            return callback.doDatabaseMetaData(Objects.requireNonNull(metaData));
        } finally {
            JdbcUtil.releaseConnection(connection, dataSource);
        }
    }

    /**
     * Return whether the given JDBC driver supports JDBC 2.0 batch updates.
     * <p>Typically invoked right before execution of a given set of statements:
     * to decide whether the set of SQL statements should be executed through the JDBC 2.0 batch mechanism or simply in a traditional one-by-one fashion.
     * <p>Logs a warning if the "supportsBatchUpdates" methods throws an exception
     * and simply returns {@code false} in that case.
     * @param connection the Connection to check
     * @return whether JDBC 2.0 batch updates are supported
     * @see java.sql.DatabaseMetaData#supportsBatchUpdates()
     */
    public static boolean supportsBatchUpdates(Connection connection) throws SQLException {
        DatabaseMetaData data = connection.getMetaData();
        return data != null && data.supportsBatchUpdates();

    }

    /**
     * Extract a common name for the target database in use even if various drivers/platforms provide varying names at runtime.
     * @param source the name as provided in database meta-data
     * @return the common name to be used (e.g. "DB2" or "Sybase")
     */
    @Nullable
    public static String commonDatabaseName(@Nullable String source) {
        if (source != null && source.startsWith("DB2")) {
            return "DB2";
        }
        if ("MariaDB".equals(source)) {
            return "MySQL";
        }
        if ("Sybase SQL Server".equals(source) || "Adaptive Server Enterprise".equals(source) ||
                "ASE".equals(source) || "sql server".equalsIgnoreCase(source)) {
            return "Sybase";
        }
        return source;
    }

    /**
     * Check whether the given SQL type is numeric.
     * @param sqlType the SQL type to be checked
     * @return whether the type is numeric
     */
    public static boolean isNumeric(int sqlType) {
        return (Types.BIT == sqlType || Types.BIGINT == sqlType || Types.DECIMAL == sqlType ||
                Types.DOUBLE == sqlType || Types.FLOAT == sqlType || Types.INTEGER == sqlType ||
                Types.NUMERIC == sqlType || Types.REAL == sqlType || Types.SMALLINT == sqlType ||
                Types.TINYINT == sqlType);
    }

    /**
     * Determine the column name to use. The column name is determined based on a lookup using ResultSetMetaData.
     * <p>This method implementation takes into account recent clarifications
     * expressed in the JDBC 4.0 specification:
     * <p><i>columnLabel - the label for the column specified with the SQL AS clause.
     * If the SQL AS clause was not specified, then the label is the name of the column</i>.
     * @param metaData    the current meta-data to use
     * @param columnIndex the index of the column for the look up
     * @return the column name to use
     * @throws SQLException in case of lookup failure
     */
    public static String lookupColumnName(ResultSetMetaData metaData, int columnIndex) throws SQLException {
        String name = metaData.getColumnLabel(columnIndex);
        if (!StringUtil.isBlank(name)) return name;
        return metaData.getColumnName(columnIndex);
    }

    ///**
    // * Convert a column name with underscores to the corresponding property name using "camel case". A name like "customer_number" would match a
    // * "customerNumber" property name.
    // * @param name the column name to be converted
    // * @return the name using "camel case"
    // */
    //public static String convertUnderscoreNameToPropertyName(@Nullable String name) {
    //    StringBuilder result = new StringBuilder();
    //    boolean nextIsUpper = false;
    //    if (name != null && name.length() > 0) {
    //        if (name.length() > 1 && name.charAt(1) == '_') {
    //            result.append(Character.toUpperCase(name.charAt(0)));
    //        } else {
    //            result.append(Character.toLowerCase(name.charAt(0)));
    //        }
    //        for (int i = 1; i < name.length(); i++) {
    //            char c = name.charAt(i);
    //            if (c == '_') {
    //                nextIsUpper = true;
    //            } else {
    //                if (nextIsUpper) {
    //                    result.append(Character.toUpperCase(c));
    //                    nextIsUpper = false;
    //                } else {
    //                    result.append(Character.toLowerCase(c));
    //                }
    //            }
    //        }
    //    }
    //    return result.toString();
    //}
}
