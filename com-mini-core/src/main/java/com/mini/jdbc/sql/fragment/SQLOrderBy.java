package com.mini.jdbc.sql.fragment;

import static com.mini.jdbc.sql.SQL.ASC;

public interface SQLOrderBy<T extends SQLOrderBy<T>> {

    T getSelf();

    DefaultOrderBy getOrderBy();

    /**
     * 获取 ORDER 部分片断
     * @return ORDER 部分片断
     */
    default String orderByToString() {
        return getOrderBy().toString();
    }

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    default T orderBy(SQLFragment fragment) {
        getOrderBy().orderBy(fragment);
        return getSelf();
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @param type ASC-正序; DESC-倒序
     * @return 当前对象
     */
    default T orderBy(String name, String type) {
        getOrderBy().orderBy(name, type);
        return getSelf();
    }

    /**
     * 添加一个字段正序排序
     * @param name 字段名称
     * @return 当前对象
     */
    default T orderBy(String name) {
        return orderBy(name, ASC);
    }
}
