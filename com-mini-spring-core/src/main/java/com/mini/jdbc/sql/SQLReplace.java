package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.SQLFragment;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;

public class SQLReplace extends SQLInsert implements SQLFragment{

    public SQLReplace(String name) {
        super(name);
    }

    @Nonnull
    public String content() {
        return StringUtil.join("", REPLACE, INTO, name(), "(", keys(), ") ", VALUES, "(", values(), ")");
    }
}
