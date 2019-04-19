package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class AsciiStreamRow implements IRow<InputStream> {
    public static final AsciiStreamRow INSTANCE = new AsciiStreamRow();

    private AsciiStreamRow() {}

    @Override
    public InputStream execute(ResultSet rs, int index) throws SQLException {
        return rs.getAsciiStream(index);
    }
}
