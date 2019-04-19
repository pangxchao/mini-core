package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class RefRow implements IRow<Ref> {
    public static final RefRow INSTANCE = new RefRow();

    private RefRow() {}

    @Override
    public Ref execute(ResultSet rs, int index) throws SQLException {
        return rs.getRef(index);
    }
}
