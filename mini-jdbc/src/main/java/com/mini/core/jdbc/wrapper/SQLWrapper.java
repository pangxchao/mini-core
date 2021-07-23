package com.mini.core.jdbc.wrapper;

public interface SQLWrapper<Child extends SQLWrapper<Child>> {
    /**
     * 追加SQL片段
     *
     * @param segment SQL片段
     * @return 当前对象
     */
    Child append(String segment);

    /**
     * 获取SQL语句
     *
     * @return SQL语句
     */
    String sql();
}
