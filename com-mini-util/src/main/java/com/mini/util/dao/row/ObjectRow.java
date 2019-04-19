package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ObjectRow implements IRow<Object> {
    public static final ObjectRow INSTANCE = new ObjectRow();

    private ObjectRow() {}

    @Override
    public Object execute(ResultSet rs, int index) throws SQLException {
        return rs.getObject(index);
    }
}
