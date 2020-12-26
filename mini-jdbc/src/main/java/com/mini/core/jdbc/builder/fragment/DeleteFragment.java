package com.mini.core.jdbc.builder.fragment;

public interface DeleteFragment<T extends DeleteFragment<T>> extends JoinFragment<T>, WhereFragment<T> {
    /**
     * 添加“AND”连接符
     *
     * @return {@code this}
     */
    T and();

    /**
     * 添加“OR”连接符
     *
     * @return {@code this}
     */
    T or();
}
