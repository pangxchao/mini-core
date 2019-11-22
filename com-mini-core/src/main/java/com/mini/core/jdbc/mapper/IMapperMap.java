package com.mini.core.jdbc.mapper;

import com.mini.core.jdbc.util.JdbcUtil;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * MapMapper.java
 * @author xchao
 */
public final class IMapperMap implements IMapper<HashMap<String, Object>> {
    public static final IMapperMap INSTANCE = new IMapperMap();

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
}
