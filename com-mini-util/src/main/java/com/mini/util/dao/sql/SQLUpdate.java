package com.mini.util.dao.sql;

import com.mini.util.dao.SQL;
import com.mini.util.dao.sql.where.NestingItem;
import com.mini.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SQLUpdate implements SQL, SQLWhere<SQLUpdate> {
    private final NestingItem nestingItem = new NestingItem();
    private final List<String> key_values = new ArrayList<>();
    private final List<Object> params = new ArrayList<>();
    private final String name;

    public SQLUpdate(String name) {
        this.name = name;
    }

    /**
     * -添加一个字段和字段值
     * @param key 字段名称
     * @return SQLInsert
     */
    public SQLUpdate put(String key, String value) {
        key_values.add(key + " = " + value);
        return this;
    }

    /**
     * -添加一个字段和默认值
     * @param key 字段名称
     * @return SQLInsert
     */
    public SQLUpdate put(String key) {
        return put(key, "?");
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public SQLUpdate params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }


    @Override
    public String content() {
        return StringUtil.join("", UPDATE, name, SET, StringUtil.join(", ", key_values), WHERE, nestingItem.content());
    }

    @Override
    public Object[] params() {
        return params.toArray();
    }

    @Override
    public SQLUpdate getSelf() {
        return this;
    }

    @Override
    public NestingItem getNestingItem() {
        return nestingItem;
    }
}
