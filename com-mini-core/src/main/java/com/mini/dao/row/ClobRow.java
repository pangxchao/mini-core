package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClobRow implements IRow<Clob> {
    public static final ClobRow INSTANCE = new ClobRow();

    private ClobRow() {}

    @Override
    public Clob execute(ResultSet rs, int index) throws SQLException {
        return rs.getClob(index);
    }
}
