package com.mini.jdbc.sql.fragment;

import com.mini.jdbc.sql.SQLSelect;

import javax.annotation.Nonnull;

import static com.mini.jdbc.sql.SQL.EMPTY;
import static com.mini.jdbc.sql.SQL.FROM;

public final class DefaultFrom implements SQLFragment {
    private SQLFragment content;

    /**
     * 设置 FROM 对象
     * @param fragment FROM 对象
     * @return 当前对象
     */
    public DefaultFrom from(SQLFragment fragment) {
        this.content = fragment;
        return this;
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @param alias   别名
     * @return 当前对象
     */
    public DefaultFrom from(String content, String alias) {
        return from(new DefaultFragment(content, alias));
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @return 当前对象
     */
    public DefaultFrom from(String content) {
        return from(new DefaultFragment(content));
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    public DefaultFrom from(SQLSelect select, String alias) {
        return from(select.toString(), alias);
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    public DefaultFrom from(SQLSelect select) {
        return from(select.toString());
    }

    @Nonnull
    @Override
    public final String toString() {
        if (content == null) return EMPTY;
        return FROM + content.toString();
    }
}
