package com.mini.jdbc.sql.fragment;

import com.mini.jdbc.sql.SQL;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.mini.jdbc.sql.SQL.EMPTY;

public final class DefaultGroupBy implements SQLFragment {
    private final List<SQLFragment> groups = new ArrayList<>();

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    public DefaultGroupBy groupBy(SQLFragment fragment) {
        this.groups.add(fragment);
        return this;
    }

    /**
     * 添加一个字段分组
     * @param name 字段名称
     * @return 当前对象
     */
    public DefaultGroupBy groupBy(String name) {
        return groupBy(new DefaultFragment(name));
    }

    @Nonnull
    @Override
    public final String toString() {
        if (groups.size() <= 0) return EMPTY;
        return toText(", ", groups);
    }
}
