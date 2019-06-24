package com.mini.dao.sql.fragment;

import com.mini.util.StringUtil;

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
