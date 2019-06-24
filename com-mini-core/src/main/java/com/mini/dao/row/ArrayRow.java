package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ArrayRow implements IRow<Array> {
    public static final ArrayRow INSTANCE = new ArrayRow();

    private ArrayRow() {}

    @Override
    public Array execute(ResultSet rs, int index) throws SQLException {
        return rs.getArray(index);
    }
}
