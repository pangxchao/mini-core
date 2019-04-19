package com.mini.util.dao.sql;

import com.mini.util.dao.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SQLBuilder implements SQL {
    private final StringBuilder content = new StringBuilder();
    private final List<Object> params = new ArrayList<>();

    /**
     * -带参数构造方法
     * @param strings 内容
     */
    public SQLBuilder(String... strings) {
        this.content(strings);
    }

    /**
     * -添加内容
     * @param strings 内容
     * @return #SqlBuilder
     */
    public SQLBuilder content(String... strings) {
        for (String s : strings) {
            content.append(s);
        }
        return this;
    }

    /**
     * -添加参数列表
     * @param param 参数
     * @return #SqlBuilder
     */
    public SQLBuilder params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }

    /**
     * -生成内容列表
     * @return SQL
     */
    public String content() {
        return content.toString();
    }

    /**
     * -获取参数列表
     * @return 参数列表
     */
    public Object[] params() {
        return params.toArray();
    }
}
