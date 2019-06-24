package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public final class TimeRow implements IRow<Time> {
    public static final TimeRow INSTANCE = new TimeRow();

    private TimeRow() {}

    @Override
    public Time execute(ResultSet rs, int index) throws SQLException {
        return rs.getTime(index);
    }
}
