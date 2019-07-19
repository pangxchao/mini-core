package com.mini.jdbc.sql.fragment;

public interface SQLHaving<T extends SQLHaving<T>> {

    T getSelf();

    DefaultHaving getHaving();

    /**
     * 获取 HAVING 部分片断
     * @return HAVING 部分片断
     */
    default String havingToString() {
        return getHaving().toString();
    }

    /**
     * 设置多个条件的连接符
     * @param connector 连接符
     * @return 当前对象
     */
    default T havingConnector(String connector) {
        getHaving().connector(connector);
        return getSelf();
    }

    /**
     * 添加一个SQL片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    default T having(SQLFragment fragment) {
        getHaving().having(fragment);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param c     连接符号
     * @param value 字段值
     * @return 当前对象
     */
    default T having(String name, String c, String value) {
        getHaving().having(name, c, value);
        return getSelf();
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param value 字段值
     * @return 当前对象
     */
    default T having(String name, String value) {
        return having(name, "=", value);
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @return 当前对象
     */
    default T having(String name) {
        return having(name, "?");
    }
}
