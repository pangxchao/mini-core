package com.mini.util.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mini.util.lang.StringUtil;

public class SQLBuilder {
    public static final String SELECT = " SELECT ";
    private final StringBuilder content = new StringBuilder();
    private final List<Object> params = new ArrayList<>();

    public SQLBuilder(String... content) {
        this.content(content);
    }

    /**
     * 添加内容
     *
     * @param content
     * @return
     */
    public SQLBuilder content(String... content) {
        for (String str : content) {
            this.content.append(str);
        }
        return this;
    }

    /**
     * 添加参数列表
     *
     * @param param
     * @return
     */
    public SQLBuilder params(Object... param) {
        params.add(Arrays.asList(param));
        return this;
    }

    /**
     * 生成内容列表
     *
     * @return
     */
    public String content() {
        return content.toString();
    }

    /**
     * 获取参数列表
     *
     * @return
     */
    public Object[] params() {
        return params.toArray();
    }

    /**
     * SELECT
     *
     * @return
     */
    public SQLBuilder select() {
        return content(SELECT);
    }

    /**
     * SELECT select1, select2...
     *
     * @return
     */
    public SQLBuilder select(String... select) {
        return select().content(" ", StringUtil.join(", ", select), " ");
    }


}
