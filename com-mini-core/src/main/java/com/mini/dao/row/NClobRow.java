package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class NClobRow implements IRow<Clob> {
    public static final NClobRow INSTANCE = new NClobRow();

    private NClobRow() {}

    @Override
    public Clob execute(ResultSet rs, int index) throws SQLException {
        return rs.getNClob(index);
    }
}
