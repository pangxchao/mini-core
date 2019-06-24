package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class BytesRow implements IRow<byte[]> {
    public static final BytesRow INSTANCE = new BytesRow();

    private BytesRow(){}

    @Override
    public byte[] execute(ResultSet rs, int index) throws SQLException {
        return rs.getBytes(index);
    }
}
