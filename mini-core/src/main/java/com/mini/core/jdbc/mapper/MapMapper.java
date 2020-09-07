package com.mini.core.jdbc.mapper;

import com.mini.core.jdbc.util.JdbcUtil;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * MapMapper.java
 *
 * @author xchao
 */
public final class MapMapper implements Mapper<Map<String, Object>> {
    private static final MapMapper INSTANCE = new MapMapper();

    private MapMapper() {
    }

    @Nonnull
    @Override
    public HashMap<String, Object> get(ResultSet rs, int number) throws SQLException {
        HashMap<String, Object> value = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String column = JdbcUtil.lookupColumnName(metaData, i);
            value.putIfAbsent(column, getColumnValue(rs, i));
        }
        return value;
    }

    private Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return JdbcUtil.getObject(rs, index);
    }

    public static Mapper<Map<String, Object>> create() {
        return INSTANCE;
    }
}
