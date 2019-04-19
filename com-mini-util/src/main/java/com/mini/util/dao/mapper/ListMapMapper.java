package com.mini.util.dao.mapper;

import com.mini.util.dao.IRow;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ListMapMapper extends AbstractMapper<List<Map<String, Object>>> {
    public static final ListMapMapper INSTANCE = new ListMapMapper();

    private ListMapMapper() {}

    @Override
    public List<Map<String, Object>> execute(ResultSet rs, int number) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String clazz = metaData.getColumnClassName(i);
                IRow<?> row = getRow(clazz);
                if (row == null) continue;

                String key = metaData.getColumnLabel(i);
                map.put(key, row.execute(rs, i));
            }
            list.add(map);
        }
        return list;
    }
}
