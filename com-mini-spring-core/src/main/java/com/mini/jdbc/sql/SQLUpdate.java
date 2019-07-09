package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.*;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLUpdate implements SQL, SQLFragment, SQLFrom<SQLUpdate>, SQLJoins<SQLUpdate>, SQLWhere<SQLUpdate> {
    private final List<String> values = new ArrayList<>();
    private final List<Object> params = new ArrayList<>();
    private final DefaultWhere where = new DefaultWhere();
    private final DefaultJoins joins = new DefaultJoins();
    private final DefaultFrom from = new DefaultFrom();

    /**
     * -添加一个字段和字段值
     * @param key 字段名称
     * @return SQLInsert
     */
    public final SQLUpdate put(String key, String value) {
        values.add(key + " = " + value);
        return this;
    }

    /**
     * -添加一个字段和默认值
     * @param key 字段名称
     * @return SQLInsert
     */
    public final SQLUpdate put(String key) {
        return put(key, "?");
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public final SQLUpdate params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }


    @Override
    public Object[] params() {
        return params.toArray();
    }

    @Override
    public final SQLUpdate getSelf() {
        return this;
    }

    @Override
    public final DefaultFrom getFrom() {
        return this.from;
    }

    @Override
    public final DefaultJoins getJoin() {
        return this.joins;
    }

    @Override
    public final DefaultWhere getWhere() {
        return this.where;
    }

    public final String valuesToString() {
        return StringUtil.join(", ", values);
    }

    @Nonnull
    @Override
    public String content() {
        return StringUtil.join("",
                UPDATE,            // UPDATE 关键字
                fromToString(),    // 修改目标表
                joinToString(),    // 联合表处理
                SET,               // SET 关键字
                valuesToString(),  // 修改字段和值
                whereToString());  // 条件处理

    }

}
