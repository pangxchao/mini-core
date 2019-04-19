package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BinaryStreamRow implements IRow<InputStream> {
    public static final BinaryStreamRow INSTANCE = new BinaryStreamRow();

    private BinaryStreamRow() {}

    @Override
    public InputStream execute(ResultSet rs, int index) throws SQLException {
        return rs.getBinaryStream(index);
    }
}
