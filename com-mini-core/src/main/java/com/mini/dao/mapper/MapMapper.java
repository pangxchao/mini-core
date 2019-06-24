package com.mini.dao.mapper;

import com.mini.dao.IRow;
import com.mini.dao.SQLValue;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class MapMapper extends AbstractMapper<SQLValue> {
    public static final MapMapper INSTANCE = new MapMapper();

    private MapMapper() {}

    @Override
    public SQLValue execute(ResultSet rs, int number) throws SQLException {
        SQLValue value = new SQLValue();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String clazz = metaData.getColumnClassName(i);
            IRow<?> row = getRow(clazz);
            if (row == null) continue;

            String key = metaData.getColumnLabel(i);
            value.put(key, row.execute(rs, i));
        }
        return value;
    }
}
