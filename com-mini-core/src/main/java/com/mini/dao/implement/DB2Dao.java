package com.mini.dao.implement;


import com.mini.dao.Paging;
import com.mini.util.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DB2Dao extends AbstractDao {
    public DB2Dao(Connection connection) {
        super(connection);
    }

    public DB2Dao(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", "select top ", paging.getRows(), " * from ( ",
                "	select row_number() over(order by id) as row_number, * (", sql, ") ",
                ") sql_max_count_table where row_number > " + paging.getStart());
    }


}
