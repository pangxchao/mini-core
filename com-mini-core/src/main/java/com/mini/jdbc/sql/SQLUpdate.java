package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.*;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLUpdate implements SQL, SQLFragment, SQLJoin<SQLUpdate>, SQLWhere<SQLUpdate> {
    private final List<String> values = new ArrayList<>();
    private final List<Object> params = new ArrayList<>();
    private final DefaultWhere where = new DefaultWhere();
    private final DefaultJoin joins = new DefaultJoin();
    private final String table;

    public SQLUpdate(String table) {
        this.table = table;
    }

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
    public Object[] toArray() {
        return params.toArray();
    }

    @Override
    public final SQLUpdate getSelf() {
        return this;
    }


    @Override
    public final DefaultJoin getJoin() {
        return this.joins;
    }

    @Override
    public final DefaultWhere getWhere() {
        return this.where;
    }

    public final String updateToString() {
        return UPDATE + table + " ";
    }

    public final String setToString() {
        return SET + toText(", ", values);
    }

    @Nonnull
    @Override
    public String toString() {
        return StringUtil.join("",
                updateToString(),   // update 片断
                joinToString(),    // join 片断
                setToString(),     // set 片断
                whereToString());  // where 片断

    }

}
