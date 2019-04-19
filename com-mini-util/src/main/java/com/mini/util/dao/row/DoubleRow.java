package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DoubleRow implements IRow<Double> {
    public static final DoubleRow INSTANCE = new DoubleRow();

    private DoubleRow() {}

    @Override
    public Double execute(ResultSet rs, int index) throws SQLException {
        return rs.getDouble(index);
    }
}
