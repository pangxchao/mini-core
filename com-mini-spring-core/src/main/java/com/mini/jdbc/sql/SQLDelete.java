package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.*;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLDelete implements SQL, SQLFragment, SQLFrom<SQLDelete>, SQLJoins<SQLDelete>, SQLWhere<SQLDelete> {
    private final List<Object> params = new ArrayList<>();
    private final DefaultWhere where = new DefaultWhere();
    private final DefaultJoins joins = new DefaultJoins();
    private final DefaultFrom from = new DefaultFrom();

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
    public final Object[] params() {
        return params.toArray();
    }

    @Override
    public final SQLDelete getSelf() {
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

    @Nonnull
    @Override
    public String content() {
        return StringUtil.join("",
                DELETE,            // DELETE 关键字
                FROM,              // FROM 关键字
                fromToString(),    // 删除目标表
                joinToString(),    // 联合表处理
                whereToString());  // 条件处理
    }

}
