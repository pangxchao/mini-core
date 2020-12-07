package com.mini.core.data.builder.fragment;

import com.mini.core.data.builder.statement.SetStatement;
import com.mini.core.data.builder.statement.WhereStatement;

import java.util.function.Consumer;

public interface UpdateFragment<T extends UpdateFragment<T>> extends JoinFragment<T>, SetFragment<T>, WhereFragment<T> {
    T set(Consumer<SetStatement> consumer);

    T setIncrease(String column, Object arg);

    T where(Consumer<WhereStatement> consumer);

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
