package com.mini.util.dao.sql.fragment;

import com.mini.util.dao.SQL;
import com.mini.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class DefaultHaving implements SQLFragment{
    private final List<SQLFragment> having = new ArrayList<>();
    private String connector = SQL.AND;

    /**
     * 设置多个条件的连接符
     * @param connector 连接符
     * @return 当前对象
     */
    public DefaultHaving connector(String connector) {
        this.connector = connector;
        return this;
    }

    /**
     * 添加一个SQL片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    public DefaultHaving having(SQLFragment fragment) {
        this.having.add(fragment);
        return this;
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param c     连接符号
     * @param value 字段值
     * @return 当前对象
     */
    public DefaultHaving having(String name, String c, String value) {
        return having(new DefaultFragment(name, c, value));
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param value 字段值
     * @return 当前对象
     */
    public DefaultHaving having(String name, String value) {
        return having(name, "=", value);
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @return 当前对象
     */
    public DefaultHaving having(String name) {
        return having(name, "?");
    }

    @Override
    public String content() {
        return "(" + StringUtil.join(connector, having.stream().map(SQLFragment::content).toArray()) + ")";
    }
}
