package com.mini.jdbc.template;

import com.mini.jdbc.Paging;
import com.mini.util.StringUtil;

public class OracleTemplate extends DaoTemplate {

    @Override
    protected String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    protected String paging(Paging paging, String sql) {
        return StringUtil.join("", "select * from (",
                "   select max_count.*, rownum row_number from (", sql, ") max_count where rownum <= ", (paging.getStart() + paging.getRows()),
                ") max_count_rownum where row_number > ", paging.getStart());
    }
}
