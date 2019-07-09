package com.mini.jdbc.template;

import com.mini.jdbc.Paging;
import com.mini.util.StringUtil;

public class MysqlTemplate extends DaoTemplate {

    @Override
    protected String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    protected String paging(Paging paging, String sql) {
        return StringUtil.join("", sql, " limit ", paging.getStart(), ", ", paging.getRows());
    }
}
