package com.mini.jdbc.util;

import com.mini.util.StringUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.*;

public final class JdbcUtil {

    /**
     * 填充 PreparedStatement 参数
     *
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
     *
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
     *
     * @param rs           is the ResultSet holding the data
     * @param index        is the column index
     * @param requiredType the required value type (may be {@code null})
     * @return the value object (possibly not of the specified required type, with further conversion steps necessary)
     * @throws SQLException if thrown by the JDBC API
     * @see #getResultSetValue(ResultSet, int)
     */
    @Nullable
    public static Object getResultSetValue(ResultSet rs, int index, @Nullable Class<?> requiredType) throws SQLException {
        if (requiredType == null) {
            return getResultSetValue(rs, index);
        }
        // long 类型处理
        if (long.class == requiredType) {
            return rs.getLong(index);
        }
        // int 类型处理
        if (int.class == requiredType) {
            return rs.getInt(index);
        }
        // short 类型处理
        if (short.class == requiredType) {
            return rs.getShort(index);
        }
        // byte 类型处理
        if (byte.class == requiredType) {
            return rs.getByte(index);
        }
        // double 类型处理
        if (double.class == requiredType) {
            return rs.getDouble(index);
        }
        // float 类型处理
        if (float.class == requiredType) {
            return rs.getFloat(index);
        }
        // boolean 类型处理
        if (boolean.class == requiredType) {
            return rs.getBoolean(index);
        }
        // char 类型处理
        if (char.class == requiredType) {
            return rs.getInt(index);
        }
        // 空值处理
        if (rs.wasNull()) {
            return null;
        }
        // String 类型处理
        if (String.class == requiredType) {
            return rs.getString(index);
        }
        // CharSequence 类型处理
        if (CharSequence.class == requiredType) {
            return rs.getString(index);
        }
        //  Long 类型处理
        if (Long.class == requiredType) {
            return rs.getLong(index);
        }
        //  Integer 类型处理
        if (Integer.class == requiredType) {
            return rs.getInt(index);
        }
        //  Short 类型处理
        if (Short.class == requiredType) {
            return rs.getShort(index);
        }
        //  Byte 类型处理
        if (Byte.class == requiredType) {
            return rs.getByte(index);
        }
        //  Float 类型处理
        if (Float.class == requiredType) {
            return rs.getFloat(index);
        }
        //  Double 类型处理
        if (Double.class == requiredType) {
            return rs.getDouble(index);
        }
        // Number 类型处理
        if (Number.class == requiredType) {
            return rs.getDouble(index);
        }
        // java.sql.Date 类型处理
        if (java.util.Date.class == requiredType) {
            return rs.getTimestamp(index);
        }
        // java.sql.Timestamp类型处理
        if (Timestamp.class == requiredType) {
            return rs.getTimestamp(index);
        }
        // java.sql.Time 类型处理
        if (Time.class == requiredType) {
            return rs.getTime(index);
        }
        // java.sql.Date 类型处理
        if (Date.class == requiredType) {
            return rs.getDate(index);
        }
        // BigDecimal 类型处理
        if (BigDecimal.class == requiredType) {
            return rs.getBigDecimal(index);
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
        // 其它类型处理
        return rs.getObject(index, requiredType);
    }


    /**
     * Return whether the given JDBC driver supports JDBC 2.0 batch updates.
     * <p>Typically invoked right before execution of a given set of statements:
     * to decide whether the set of SQL statements should be executed through the JDBC 2.0 batch mechanism or simply in a traditional one-by-one fashion.
     * <p>Logs a warning if the "supportsBatchUpdates" methods throws an exception
     * and simply returns {@code false} in that case.
     *
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
     *
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
     *
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
     *
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
}
