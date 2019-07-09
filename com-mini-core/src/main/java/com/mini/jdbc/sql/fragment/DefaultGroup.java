package com.mini.jdbc.sql.fragment;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class DefaultGroup implements SQLFragment {
    private final List<SQLFragment> groups = new ArrayList<>();

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    public DefaultGroup group(SQLFragment fragment) {
        this.groups.add(fragment);
        return this;
    }

    /**
     * 添加一个字段分组
     * @param name 字段名称
     * @return 当前对象
     */
    public DefaultGroup group(String name) {
        return group(new DefaultFragment(name));
    }

    @Nonnull
    @Override
    public final String content() {
        if (groups.size() <= 0) return "";
        return text(", ", groups);
    }
}
