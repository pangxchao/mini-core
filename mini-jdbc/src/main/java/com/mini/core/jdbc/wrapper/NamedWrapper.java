package com.mini.core.jdbc.wrapper;

import java.util.HashMap;
import java.util.Map;

public final class NamedWrapper implements SQLWrapper<NamedWrapper> {
    private final StringBuilder sql = new StringBuilder();
    private final Map<String, Object> args = new HashMap<>();

    /**
     * 创建一个空的包装器
     */
    public NamedWrapper() {
    }

    /**
     * 创建一个带SQL片段的包装器
     *
     * @param segment SQL片段
     */
    public NamedWrapper(String segment) {
        sql.append(segment);
    }

    @Override
    public NamedWrapper append(String segment) {
        sql.append(segment);
        return this;
    }

    @Override
    public final String sql() {
        return sql.toString();
    }

    /**
     * 添加一个SQL参数
     *
     * @param name  参数名称
     * @param value 参数值
     * @return 当前对象
     */
    public NamedWrapper put(String name, Object value) {
        this.args.put(name, value);
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public NamedWrapper put(Map<String, ?> args) {
        this.args.putAll(args);
        return this;
    }

    /**
     * 获取所有SQL参数
     *
     * @return SQL参数
     */
    public Map<String, ?> args() {
        return this.args;
    }
}
