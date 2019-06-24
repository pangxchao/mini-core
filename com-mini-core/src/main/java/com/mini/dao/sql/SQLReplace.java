package com.mini.dao.sql;

import com.mini.dao.sql.fragment.SQLFragment;
import com.mini.util.StringUtil;

public class SQLReplace extends SQLInsert implements SQLFragment{

    public SQLReplace(String name) {
        super(name);
    }

    public String content() {
        return StringUtil.join("", REPLACE, INTO, name(), "(", keys(), ") ", VALUES, "(", values(), ")");
    }
}
