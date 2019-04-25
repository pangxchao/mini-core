package com.mini.util.dao.sql.fragment;

import com.mini.util.dao.sql.SQLSelect;

public interface SQLFrom<T extends SQLFrom<T>> {

    T getSelf();

    DefaultFrom getFrom();


    /**
     * 获取 FROM 部分片断
     * @return FROM 部分片断
     */
    default String from() {
        return getFrom().content();
    }

    /**
     * 设置 FROM 对象
     * @param fragment FROM 对象
     * @return 当前对象
     */
    default T from(SQLFragment fragment) {
        getFrom().from(fragment);
        return getSelf();
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @param alias   别名
     * @return 当前对象
     */
    default T from(String content, String alias) {
        getFrom().from(content, alias);
        return getSelf();
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @return 当前对象
     */
    default T from(String content) {
        getFrom().from(content);
        return getSelf();
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    default T from(SQLSelect select, String alias) {
        return from(select.content(), alias);
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    default T from(SQLSelect select) {
        return from(select.content());
    }
}
