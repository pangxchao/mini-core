package com.mini.util.dao.sql.fragment;

import com.mini.util.dao.sql.SQLSelect;

public interface SQLJoin<T extends SQLJoin<T>> {
    T getSelf();

    DefaultJoin getJoin();

    /**
     * 获取 JOIN 部分片断
     * @return JOIN 部分片断
     */
    default String join() {
        return getJoin().content();
    }

    /**
     * 设置 FROM 对象
     * @param fragment FROM 对象
     * @return 当前对象
     */
    default T join(SQLFragment fragment) {
        getJoin().join(fragment);
        return getSelf();
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @param alias   别名
     * @return 当前对象
     */
    default T join(String content, String alias) {
        getJoin().join(content, alias);
        return getSelf();
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @return 当前对象
     */
    default T join(String content) {
        getJoin().join(content);
        return getSelf();
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    default T join(SQLSelect select, String alias) {
        return join(select.content(), alias);
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    default T join(SQLSelect select) {
        return join(select.content());
    }

    /**
     * 设置 ON 的连接符
     * @param connector 连接符
     * @return 当前对象
     */
    default T onConnector(String connector) {
        getJoin().connector(connector);
        return getSelf();
    }

    /**
     * 添加一个SQL片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    default T on(SQLFragment fragment) {
        getJoin().on(fragment);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param c     连接符号
     * @param value 字段值
     * @return 当前对象
     */
    default T on(String name, String c, String value) {
        getJoin().on(name, c, value);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param value 字段值
     * @return 当前对象
     */
    default T on(String name, String value) {
        return on(name, "=", value);
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @return 当前对象
     */
    default T on(String name) {
        return on(name, "?");
    }
}
