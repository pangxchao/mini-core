package com.mini.util.dao.mapper;

import com.mini.util.dao.IRow;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class MapMapper extends AbstractMapper<Map<String, Object>> {
    public static final MapMapper INSTANCE = new MapMapper();

    private MapMapper() {}

    @Override
    public Map<String, Object> execute(ResultSet rs, int number) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        if (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String clazz = metaData.getColumnClassName(i);
                IRow<?> row = getRow(clazz);
                if (row == null) continue;

                String key = metaData.getColumnLabel(i);
                map.put(key, row.execute(rs, i));
            }
            return map;
        }
        return null;
    }
}
