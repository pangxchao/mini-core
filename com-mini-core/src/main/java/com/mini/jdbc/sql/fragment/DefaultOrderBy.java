package com.mini.jdbc.sql.fragment;

import com.mini.jdbc.sql.SQL;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.mini.jdbc.sql.SQL.ORDER_BY;

public final class DefaultOrderBy implements SQLFragment {
    private final List<SQLFragment> order = new ArrayList<>();

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    public DefaultOrderBy orderBy(SQLFragment fragment) {
        this.order.add(fragment);
        return this;
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @param type ASC-正序; DESC-倒序
     * @return 当前对象
     */
    public DefaultOrderBy orderBy(String name, String type) {
        return orderBy(new DefaultFragment(name, type));
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @return 当前对象
     */
    public DefaultOrderBy orderBy(String name) {
        return orderBy(name, SQL.ASC);
    }

    @Nonnull
    @Override
    public final String toString() {
        if (order.size() <= 0) return SQL.EMPTY;
        return ORDER_BY + toText(", ", order);
    }
}
