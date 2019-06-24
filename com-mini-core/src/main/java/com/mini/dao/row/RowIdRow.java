package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;

public final class RowIdRow implements IRow<RowId> {
    public static final RowIdRow INSTANCE = new RowIdRow();

    private RowIdRow() {}

    @Override
    public RowId execute(ResultSet rs, int index) throws SQLException {
        return rs.getRowId(index);
    }
}
