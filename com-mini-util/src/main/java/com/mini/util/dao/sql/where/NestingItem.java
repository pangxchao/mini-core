package com.mini.util.dao.sql.where;

import com.mini.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class NestingItem implements SQLWhereItem {

    private String connector = " and ";
    private final List<SQLWhereItem> items = new ArrayList<>();

    public NestingItem setConnector(String connector) {
        this.connector = connector;
        return this;
    }

    /**
     * 添加一个条件
     * @param item 条件对象
     * @return 当前对象
     */
    public NestingItem addItem(SQLWhereItem item) {
        this.items.add(item);
        return this;
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @return 当前对象
     */
    public NestingItem addItem(String name) {
        return addItem(new NormalItem().setName(name));
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param value 字段值
     * @return 当前对象
     */
    public NestingItem addItem(String name, String value) {
        return addItem(new NormalItem().setName(name).setName(value));
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param c     连接符号
     * @param value 字段值
     * @return 当前对象
     */
    public NestingItem addItem(String name, String c, String value) {
        return addItem(new NormalItem().setName(name).setConnector(c).setValue(value));
    }

    @Override
    public String content() {
        return "(" + StringUtil.join(connector, items.stream().map(SQLWhereItem::content).toArray()) + ")";
    }
}
