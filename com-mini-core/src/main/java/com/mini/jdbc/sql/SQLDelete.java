package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.*;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLDelete implements SQL, SQLFragment, SQLJoin<SQLDelete>, SQLWhere<SQLDelete> {
    private final List<Object> params = new ArrayList<>();
    private final DefaultWhere where = new DefaultWhere();
    private final DefaultJoin joins = new DefaultJoin();
    private final String table;

    public SQLDelete(String table) {
        this.table = table;
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public final SQLDelete params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }

    @Override
    public final Object[] toArray() {
        return params.toArray();
    }

    @Override
    public final SQLDelete getSelf() {
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

    public final String deleteFromToString() {
        return DELETE + FROM + table;
    }

    @Nonnull
    @Override
    public String toString() {
        return StringUtil.join("",
                deleteFromToString(), // delete from 片断
                joinToString(),       // join 片断
                whereToString());     // where 片断
    }

}
