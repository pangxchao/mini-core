package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.*;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SQLSelect implements SQL, SQLFragment, SQLFrom<SQLSelect>, SQLJoin<SQLSelect>, SQLWhere<SQLSelect>, SQLGroupBy<SQLSelect>, SQLHaving<SQLSelect>,
        SQLOrderBy<SQLSelect> {
    private final DefaultOrderBy orderBy = new DefaultOrderBy();
    private final DefaultHaving having = new DefaultHaving();
    private final DefaultWhere where = new DefaultWhere();
    private final List<Object> params = new ArrayList<>();
    private final DefaultGroupBy groupBy = new DefaultGroupBy();
    private final DefaultJoin joins = new DefaultJoin();
    private final List<String> keys = new ArrayList<>();
    private final DefaultFrom from = new DefaultFrom();

    /**
     * 添加查询需要的字段
     * @param key 字段集合
     * @return 当前对象
     */
    public final SQLSelect keys(String... key) {
        Collections.addAll(keys, key);
        return this;
    }

    /**
     * 添加单个查询字段和别名
     * @param key   查询字段
     * @param alisa 别名
     * @return 当前对象
     */
    public final SQLSelect key(String key, String alisa) {
        return keys(StringUtil.join("", key, AS, alisa));
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public final SQLSelect params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }

    @Override
    public Object[] toArray() {
        return params.toArray();
    }


    @Override
    public final SQLSelect getSelf() {
        return this;
    }

    @Override
    public final DefaultFrom getFrom() {
        return this.from;
    }

    @Override
    public final DefaultJoin getJoin() {
        return this.joins;
    }

    @Override
    public final DefaultWhere getWhere() {
        return this.where;
    }

    @Override
    public DefaultGroupBy getGroupBy() {
        return this.groupBy;
    }

    @Override
    public DefaultHaving getHaving() {
        return this.having;
    }

    @Override
    public DefaultOrderBy getOrderBy() {
        return this.orderBy;
    }

    /**
     * 获取查询字段片断
     * @return 查询字段片断
     */
    public final String selectToString() {
        return SELECT + toText(", ", keys);
    }

    @Nonnull
    @Override
    public String toString() {
        return StringUtil.join("",
                selectToString(),   // select 片断
                fromToString(),    // from 片断
                joinToString(),    // join 片断
                whereToString(),   // where 片断
                groupByToString(), // group By 片断
                havingToString(),  // having 片断
                orderByToString());// order by 矁
    }
}
