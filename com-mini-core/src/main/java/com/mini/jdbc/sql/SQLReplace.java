package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.SQLFragment;

import javax.annotation.Nonnull;

public class SQLReplace extends SQLInsert implements SQLFragment {

    public SQLReplace(String name) {
        super(name);
    }

    @Nonnull
    public String toString() {
        return toText("", REPLACE, INTO, name(), "(", keys(), ") ", //
                VALUES, "(", values(), ")");
    }
}
