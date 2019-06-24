package com.mini.dao.row;

import com.mini.dao.IRow;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class URLRow implements IRow<URL> {
    public static final URLRow INSTANCE = new URLRow();

    private URLRow() {}

    @Override
    public URL execute(ResultSet rs, int index) throws SQLException {
        return rs.getURL(index);
    }
}
