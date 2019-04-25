package com.mini.util.dao.sql.fragment;

import com.mini.util.dao.SQL;
import com.mini.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class DefaultOrder implements SQLFragment {
    private final List<SQLFragment> order = new ArrayList<>();

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    public DefaultOrder order(SQLFragment fragment) {
        this.order.add(fragment);
        return this;
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @param type ASC-正序; DESC-倒序
     * @return 当前对象
     */
    public DefaultOrder order(String name, String type) {
        return order(new DefaultFragment(name, type));
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @return 当前对象
     */
    public DefaultOrder order(String name) {
        return order(name, SQL.ASC);
    }

    @Override
    public String content() {
        return StringUtil.join(", ", order.stream().map(SQLFragment::content));
    }
}
