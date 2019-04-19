package com.mini.util.dao.implement;

import com.mini.util.dao.Paging;
import com.mini.util.lang.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class OdbcDao extends AbstractDao {
    public OdbcDao(Connection connection) {
        super(connection);
    }

    public OdbcDao(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override // TODO： 未实现
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", "select * from (",
                "   select max_count.*, rownum row_number from (", sql, ") max_count where rownum <= ", (paging.getStart() + paging.getRows()),
                ") max_count_rownum where row_number > ", paging.getStart());
    }
}
