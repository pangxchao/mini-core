package com.mini.jdbc.sql.fragment;

import static com.mini.jdbc.sql.SQL.ASC;

public interface SQLOrder<T extends SQLOrder<T>> {

    T getSelf();

    DefaultOrder getOrder();

    /**
     * 获取 ORDER 部分片断
     * @return ORDER 部分片断
     */
    default String orderToString() {
        return getOrder().content();
    }

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    default T order(SQLFragment fragment) {
        getOrder().order(fragment);
        return getSelf();
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @param type ASC-正序; DESC-倒序
     * @return 当前对象
     */
    default T order(String name, String type) {
        getOrder().order(name, type);
        return getSelf();
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @return 当前对象
     */
    default T order(String name) {
        return order(name, ASC);
    }
}
