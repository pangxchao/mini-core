package com.mini.spring.dao;

import com.mini.util.dao.Paging;
import com.mini.util.lang.StringUtil;

import javax.sql.DataSource;

public class OracleDaoTemplate extends DaoTemplate {
    public OracleDaoTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", "select * from (",
                "   select max_count.*, rownum row_number from (", sql, ") max_count where rownum <= ", (paging.getStart() + paging.getRows()),
                ") max_count_rownum where row_number > ", paging.getStart());
    }
}
