package com.mini.util.dao.sql.fragment;

import com.mini.util.lang.StringUtil;

public final class DefaultFragment implements SQLFragment {
    private final String content;

    public DefaultFragment(String... content) {
        this.content = StringUtil.join(" ", content);
    }

    @Override
    public String content() {
        return content;
    }
}
