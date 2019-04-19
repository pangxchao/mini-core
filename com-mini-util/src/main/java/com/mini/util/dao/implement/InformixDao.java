package com.mini.util.dao.implement;


import com.mini.util.dao.Paging;
import com.mini.util.lang.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class InformixDao extends AbstractDao {
    public InformixDao(Connection connection) {
        super(connection);
    }

    public InformixDao(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", "select skip " + paging.getStart() + " first " + paging.getRows() + " (", sql, ")");
    }
}
