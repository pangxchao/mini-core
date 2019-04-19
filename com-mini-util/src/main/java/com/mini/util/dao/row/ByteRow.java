package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ByteRow implements IRow<Byte> {
    public static final ByteRow INSTANCE = new ByteRow();

    private ByteRow(){}

    @Override
    public Byte execute(ResultSet rs, int index) throws SQLException {
        return rs.getByte(index);
    }
}
