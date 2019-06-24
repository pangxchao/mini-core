package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class NStringRow implements IRow<String> {
    public static final NStringRow INSTANCE = new NStringRow();

    private NStringRow() {}

    @Override
    public String execute(ResultSet rs, int index) throws SQLException {
        return rs.getNString(index);
    }
}
