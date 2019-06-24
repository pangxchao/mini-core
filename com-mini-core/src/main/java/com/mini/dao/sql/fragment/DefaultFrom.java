package com.mini.dao.sql.fragment;

import com.mini.dao.sql.SQLSelect;

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
        return from(select.content(), alias);
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    public DefaultFrom from(SQLSelect select) {
        return from(select.content());
    }

    @Override
    public String content() {
        if (content == null) return null;
        return content.content();
    }
}
