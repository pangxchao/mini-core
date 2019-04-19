package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DateRow implements IRow<Date> {
    public static final DateRow INSTANCE = new DateRow();

    private DateRow() {}

    @Override
    public Date execute(ResultSet rs, int index) throws SQLException {
        return rs.getDate(index);
    }
}
