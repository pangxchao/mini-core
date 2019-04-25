package com.mini.util.dao.sql.fragment;

public interface SQLGroup<T extends SQLGroup<T>> {
    T getSelf();

    DefaultGroup getGroup();

    /**
     * 获取 GROUP 部分片断
     * @return GROUP 部分片断
     */
    default String group() {
        return getGroup().content();
    }

    /**
     * 添加一个 GROUP 片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    default T group(SQLFragment fragment) {
        getGroup().group(fragment);
        return getSelf();
    }

    /**
     * 添加一个字段分组
     * @param name 字段名称
     * @return 当前对象
     */
    default T group(String name) {
        getGroup().group(name);
        return getSelf();
    }
}
