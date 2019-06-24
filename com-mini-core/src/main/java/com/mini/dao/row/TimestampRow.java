package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public final class TimestampRow implements IRow<Timestamp> {
    public static final TimestampRow INSTANCE = new TimestampRow();

    private TimestampRow() {}

    @Override
    public Timestamp execute(ResultSet rs, int index) throws SQLException {
        return rs.getTimestamp(index);
    }
}
