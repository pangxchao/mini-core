package com.mini.util.dao.sql;

import com.mini.util.dao.SQL;
import com.mini.util.dao.sql.where.NestingItem;
import com.mini.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SQLSelect implements SQL, SQLWhere<SQLSelect> {
    private final NestingItem nestingItem = new NestingItem();
    private final List<Object> params = new ArrayList<>();
    private final List<String> keys = new ArrayList<>();
    private final String name;

    public SQLSelect(String name) {
        this.name = name;
    }

    /**
     * 添加查询需要的字段
     * @param key 字段集合
     * @return 当前对象
     */
    public SQLSelect addKeys(String... key) {
        Collections.addAll(keys, key);
        return this;
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public SQLSelect params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }

    @Override
    public String content() {
        return StringUtil.join("", SELECT, StringUtil.join(", ", keys), FROM, name, WHERE, nestingItem.content());
    }

    @Override
    public Object[] params() {
        return params.toArray();
    }

    @Override
    public SQLSelect getSelf() {
        return this;
    }

    @Override
    public NestingItem getNestingItem() {
        return nestingItem;
    }
}
