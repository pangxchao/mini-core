
package com.mini.util.dao.implement;


import com.mini.util.dao.Paging;
import com.mini.util.lang.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class SQLServerDao extends AbstractDao {
    public SQLServerDao(Connection connection) {
        super(connection);
    }

    public SQLServerDao(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", "select top " + paging.getRows() + " * from ( ",
                "	select row_number() over(order by id) as row_number, * (", sql, ")",
                ") sql_max_count where row_number > " + paging.getStart());
    }
}
