package com.mini.jdbc.mapper;

import com.mini.jdbc.util.JdbcUtil;
import com.mini.jdbc.util.SQLValue;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * MapMapper.java
 * @author xchao
 */
public final class IMapperMap implements IMapper<SQLValue> {
    public static final IMapperMap INSTANCE = new IMapperMap();

    @Override
    public SQLValue get(ResultSet rs, int number) throws SQLException {
        SQLValue value = new SQLValue();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String column = JdbcUtil.lookupColumnName(metaData, i);
            value.putIfAbsent(column, getColumnValue(rs, i));
        }
        return value;
    }

    private Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return JdbcUtil.getResultSetValue(rs, index);
    }
}
