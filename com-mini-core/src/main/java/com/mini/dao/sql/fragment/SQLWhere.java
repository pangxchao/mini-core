package com.mini.dao.sql.fragment;

public interface SQLWhere<T extends SQLWhere<T>> {

    T getSelf();

    DefaultWhere getWhere();

    /**
     * 获取WHERE部分片断
     * @return WHERE部分片断
     */
    default String where() {
        return getWhere().content();
    }

    /**
     * 设置多个条件的连接符
     * @param connector 连接符
     * @return 当前对象
     */
    default T connector(String connector) {
        getWhere().connector(connector);
        return getSelf();
    }

    /**
     * 添加一个SQL片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    default T where(SQLFragment fragment) {
        getWhere().where(fragment);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param c     连接符号
     * @param value 字段值
     * @return 当前对象
     */
    default T where(String name, String c, String value) {
        getWhere().where(name, c, value);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @param c    连接符号
     * @return 当前对象
     */
    default T where(String name, String c) {
        return where(name, c, "?");
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @return 当前对象
     */
    default T where(String name) {
        return where(name, "=");
    }

}
