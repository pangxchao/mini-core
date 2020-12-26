package com.mini.core.jdbc.builder.fragment;

public interface InsertFragment<T extends InsertFragment<T>> extends SetFragment<T> {

    T onKeyNative(String column, String value, Object... args);

    T onKey(String column, Object arg);

    /**
     * 修改成 Insert 设置的值
     *
     * @param column 字段名称
     * @return {@code this}
     */
    @SuppressWarnings("UnusedReturnValue")
    T onKeyFromInsert(String column);

    /**
     * 值自增处理
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T onKeyIncrease(String column, Number arg);
}
