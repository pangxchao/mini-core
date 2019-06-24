package com.mini.dao.implement;


import com.mini.dao.Paging;
import com.mini.util.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class HsqlDao extends AbstractDao {
    public HsqlDao(Connection connection) {
        super(connection);
    }

    public HsqlDao(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", "select limit ", paging.getStart(), "  ", paging.getRows(), " from (", sql, ")");
    }
}
