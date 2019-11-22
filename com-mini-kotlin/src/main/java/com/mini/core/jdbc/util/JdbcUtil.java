package com.mini.core.jdbc.util;

import com.mini.core.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
}
