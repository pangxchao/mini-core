package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;

public final class SQLXmlRow implements IRow<SQLXML> {
    public static final SQLXmlRow INSTANCE = new SQLXmlRow();

    private SQLXmlRow() {}

    @Override
    public SQLXML execute(ResultSet rs, int index) throws SQLException {
        return rs.getSQLXML(index);
    }
}
