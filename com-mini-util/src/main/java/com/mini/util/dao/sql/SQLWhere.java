package com.mini.util.dao.sql;

import com.mini.util.dao.sql.where.NestingItem;
import com.mini.util.dao.sql.where.SQLWhereItem;

public interface SQLWhere<T extends SQLWhere<T>> {

    T getSelf();

    NestingItem getNestingItem();

    /**
     * 设置每个Item的连接符
     * @param connector 连接符
     * @return 当前对象
     */
    default T setConnector(String connector) {
        getNestingItem().setConnector(connector);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param item 条件对象
     * @return 当前对象
     */
    default T addItem(SQLWhereItem item) {
        getNestingItem().addItem(item);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @return 当前对象
     */
    default T addItem(String name) {
        getNestingItem().addItem(name);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param value 字段值
     * @return 当前对象
     */
    default T addItem(String name, String value) {
        getNestingItem().addItem(name, value);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param c     连接符号
     * @param value 字段值
     * @return 当前对象
     */
    default T addItem(String name, String c, String value) {
        getNestingItem().addItem(name, c, value);
        return getSelf();
    }
}
