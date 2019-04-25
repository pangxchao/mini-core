package com.mini.spring.dao;

import com.mini.util.dao.Paging;
import com.mini.util.lang.StringUtil;

import javax.sql.DataSource;

public class MysqlDaoTemplate extends DaoTemplate {
    public MysqlDaoTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", sql, " limit ", paging.getStart(), ", ", paging.getRows());
    }
}
