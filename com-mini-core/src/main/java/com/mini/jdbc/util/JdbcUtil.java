package com.mini.jdbc.util;

import com.mini.util.StringUtil;

import java.math.BigDecimal;
import java.sql.*;

public final class JdbcUtil {

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
     * 根据  ResultSetMetaData 对象和字段序号查询字段别名或者名称
     * @param metaData    ResultSetMetaData 对象
     * @param columnIndex 字段序号
     * @return 字段别名或者名称
     */
    public static String lookupColumnName(ResultSetMetaData metaData, int columnIndex) throws SQLException {
        String name = metaData.getColumnLabel(columnIndex);
        if (!StringUtil.isBlank(name)) return name;
        return metaData.getColumnName(columnIndex);
    }

    public static String getString(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    public static String getString(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getString(columnLabel);
    }

    public static boolean getBoolean(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBoolean(columnIndex);
    }

    public static boolean getBoolean(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBoolean(columnLabel);
    }

    public static Boolean getOBoolean(ResultSet rs, int columnIndex) throws SQLException {
        boolean value = rs.getBoolean(columnIndex);
        return rs.wasNull() ? null : value;
    }

    public static Boolean getOBoolean(ResultSet rs, String columnLabel) throws SQLException {
        boolean value = rs.getBoolean(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static byte getByte(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getByte(columnIndex);
    }

    public static byte getByte(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getByte(columnLabel);
    }

    public static Byte getOByte(ResultSet rs, int columnIndex) throws SQLException {
        byte value = rs.getByte(columnIndex);
        return rs.wasNull() ? null : value;
    }

    public static Byte getOByte(ResultSet rs, String columnLabel) throws SQLException {
        byte value = rs.getByte(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static short getShort(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getShort(columnIndex);
    }

    public static short getShort(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getShort(columnLabel);
    }

    public static Short getOShort(ResultSet rs, int columnIndex) throws SQLException {
        short value = rs.getShort(columnIndex);
        return rs.wasNull() ? null : value;
    }

    public static Short getOShort(ResultSet rs, String columnLabel) throws SQLException {
        short value = rs.getShort(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static int getInt(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex);
    }

    public static int getInt(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getInt(columnLabel);
    }

    public static Integer getInteger(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : value;
    }

    public static Integer getInteger(ResultSet rs, String columnLabel) throws SQLException {
        int value = rs.getInt(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static long getLong(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getLong(columnIndex);
    }

    public static long getLong(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getLong(columnLabel);
    }

    public static Long getOLong(ResultSet rs, int columnIndex) throws SQLException {
        long value = rs.getLong(columnIndex);
        return rs.wasNull() ? null : value;
    }

    public static Long getOLong(ResultSet rs, String columnLabel) throws SQLException {
        long value = rs.getLong(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static float getFloat(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getFloat(columnIndex);
    }

    public static float getFloat(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getFloat(columnLabel);
    }

    public static Float getOFloat(ResultSet rs, int columnIndex) throws SQLException {
        float value = rs.getFloat(columnIndex);
        return rs.wasNull() ? null : value;
    }

    public static Float getOFloat(ResultSet rs, String columnLabel) throws SQLException {
        float value = rs.getFloat(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static double getDouble(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getDouble(columnIndex);
    }

    public static double getDouble(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getDouble(columnLabel);
    }

    public static Double getODoubl(ResultSet rs, int columnIndex) throws SQLException {
        double value = rs.getDouble(columnIndex);
        return rs.wasNull() ? null : value;
    }

    public static Double getODoubl(ResultSet rs, String columnLabel) throws SQLException {
        double value = rs.getDouble(columnLabel);
        return rs.wasNull() ? null : value;
    }

    public static byte[] getBytes(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBytes(columnIndex);
    }

    public static byte[] getBytes(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBytes(columnLabel);
    }

    public static java.sql.Date getDate(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getDate(columnIndex);
    }

    public static java.sql.Date getDate(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getDate(columnLabel);
    }

    public static java.sql.Time getTime(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getTime(columnIndex);
    }

    public static java.sql.Time getTime(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getTime(columnLabel);
    }

    public static java.sql.Timestamp getTimestamp(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getTimestamp(columnIndex);
    }

    public static java.sql.Timestamp getTimestamp(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getTimestamp(columnLabel);
    }

    public static java.io.InputStream getAsciiStream(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getAsciiStream(columnIndex);
    }

    public static java.io.InputStream getAsciiStream(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getAsciiStream(columnLabel);
    }

    public static java.io.InputStream getBinaryStream(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBinaryStream(columnIndex);
    }

    public static java.io.InputStream getBinaryStream(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBinaryStream(columnLabel);
    }

    public static Object getObject(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getObject(columnIndex);
    }

    public static Object getObject(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getObject(columnLabel);
    }

    public static java.io.Reader getCharacterStream(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getCharacterStream(columnIndex);
    }

    public static java.io.Reader getCharacterStream(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getCharacterStream(columnLabel);
    }

    public static BigDecimal getBigDecimal(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBigDecimal(columnIndex);
    }

    public static BigDecimal getBigDecimal(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBigDecimal(columnLabel);
    }

    public static Ref getRef(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getRef(columnIndex);
    }

    public static Ref getRef(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getRef(columnLabel);
    }

    public static Blob getBlob(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBlob(columnIndex);
    }

    public static Blob getBlob(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getBlob(columnLabel);
    }

    public static Clob getClob(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getClob(columnIndex);
    }

    public static Clob getClob(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getClob(columnLabel);
    }

    public static Array getArray(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getArray(columnIndex);
    }

    public static Array getArray(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getArray(columnLabel);
    }

    public static java.net.URL getURL(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getURL(columnIndex);
    }

    public static java.net.URL getURL(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getURL(columnLabel);
    }

    public static RowId getRowId(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getRowId(columnIndex);
    }

    public static RowId getRowId(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getRowId(columnLabel);
    }

    public static SQLXML getSQLXML(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getSQLXML(columnIndex);
    }

    public static SQLXML getSQLXML(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getSQLXML(columnLabel);
    }

    public static String getNString(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getNString(columnIndex);
    }

    public static String getNString(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getNString(columnLabel);
    }

    public static java.io.Reader getNCharacterStream(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getNCharacterStream(columnIndex);
    }

    public static java.io.Reader getNCharacterStream(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getNCharacterStream(columnLabel);
    }

    public static <T> T getObject(ResultSet rs, int columnIndex, Class<T> type) throws SQLException {
        return rs.getObject(columnIndex, type);
    }

    public static <T> T getObject(ResultSet rs, String columnLabel, Class<T> type) throws SQLException {
        return rs.getObject(columnLabel, type);
    }
}
