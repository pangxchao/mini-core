package com.mini.core.data.builder.fragment;

import org.jetbrains.annotations.NotNull;

public interface InsertFragment<T extends InsertFragment<T>> extends SetFragment<T> {

    T onKeyNative(String column, String value, Object... args);

    T onKey(String column, Object arg);

    /**
     * 修改成 Insert 设置的值
     *
     * @param column 字段名称
     * @return {@code this}
     */
    T onKeyFromInsert(@NotNull String column);

    /**
     * 值自增处理
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T onKeyIncrease(@NotNull String column, Number arg);
}
