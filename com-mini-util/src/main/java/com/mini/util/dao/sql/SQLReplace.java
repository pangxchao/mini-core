package com.mini.util.dao.sql;

import com.mini.util.dao.sql.fragment.SQLFragment;
import com.mini.util.lang.StringUtil;

public class SQLReplace extends SQLInsert implements SQLFragment{

    public SQLReplace(String name) {
        super(name);
    }

    public String content() {
        return StringUtil.join("", REPLACE, INTO, name(), "(", keys(), ") ", VALUES, "(", values(), ")");
    }
}
