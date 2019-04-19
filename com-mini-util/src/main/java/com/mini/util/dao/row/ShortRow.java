package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ShortRow implements IRow<Short> {
    public static final ShortRow INSTANCE = new ShortRow();

    private ShortRow() {}

    @Override
    public Short execute(ResultSet rs, int index) throws SQLException {
        return rs.getShort(index);
    }
}
