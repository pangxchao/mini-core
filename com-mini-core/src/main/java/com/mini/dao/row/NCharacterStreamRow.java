package com.mini.dao.row;

import com.mini.dao.IRow;

import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class NCharacterStreamRow implements IRow<Reader> {
    public static final NCharacterStreamRow INSTANCE = new NCharacterStreamRow();

    private NCharacterStreamRow() {}

    @Override
    public Reader execute(ResultSet rs, int index) throws SQLException {
        return rs.getNCharacterStream(index);
    }
}
