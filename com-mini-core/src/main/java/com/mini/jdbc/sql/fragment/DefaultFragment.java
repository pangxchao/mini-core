package com.mini.jdbc.sql.fragment;

import com.mini.util.StringUtil;

import javax.annotation.Nonnull;

public final class DefaultFragment implements SQLFragment {
    private final String content;

    public DefaultFragment(String... content) {
        this.content = StringUtil.join(" ", content);
    }

    @Nonnull
    @Override
    public final String toString() {
        return content;
    }
}
