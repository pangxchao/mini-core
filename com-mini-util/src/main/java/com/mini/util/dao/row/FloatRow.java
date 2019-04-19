package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class FloatRow implements IRow<Float> {
    public static final FloatRow INSTANCE = new FloatRow();

    private FloatRow() {}

    @Override
    public Float execute(ResultSet rs, int index) throws SQLException {
        return rs.getFloat(index);
    }
}
