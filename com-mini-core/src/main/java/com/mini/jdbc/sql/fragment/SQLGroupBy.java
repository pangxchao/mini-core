package com.mini.jdbc.sql.fragment;

public interface SQLGroupBy<T extends SQLGroupBy<T>> {
    T getSelf();

    DefaultGroupBy getGroupBy();

    /**
     * 获取 GROUP 部分片断
     * @return GROUP 部分片断
     */
    default String groupByToString() {
        return getGroupBy().toString();
    }

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    default T groupBy(SQLFragment fragment) {
        getGroupBy().groupBy(fragment);
        return getSelf();
    }

    /**
     * 添加一个字段分组
     * @param name 字段名称
     * @return 当前对象
     */
    default T groupBy(String name) {
        getGroupBy().groupBy(name);
        return getSelf();
    }
}
