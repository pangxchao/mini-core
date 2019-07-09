package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.*;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SQLSelect implements SQL, SQLFragment, SQLFrom<SQLSelect>, SQLJoins<SQLSelect>, SQLWhere<SQLSelect>, SQLGroup<SQLSelect>, SQLHaving<SQLSelect>,
        SQLOrder<SQLSelect> {
    private final DefaultHaving having = new DefaultHaving();
    private final DefaultWhere where = new DefaultWhere();
    private final List<Object> params = new ArrayList<>();
    private final DefaultGroup group = new DefaultGroup();
    private final DefaultOrder order = new DefaultOrder();
    private final DefaultJoins joins = new DefaultJoins();
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
    public Object[] params() {
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
    public final DefaultJoins getJoin() {
        return this.joins;
    }

    @Override
    public final DefaultWhere getWhere() {
        return this.where;
    }

    @Override
    public DefaultGroup getGroup() {
        return this.group;
    }

    @Override
    public DefaultHaving getHaving() {
        return this.having;
    }

    @Override
    public DefaultOrder getOrder() {
        return this.order;
    }

    /**
     * 获取查询字段片断
     * @return 查询字段片断
     */
    public final String keysToString() {
        return StringUtil.join(", ", keys);
    }

    @Nonnull
    @Override
    public String content() {
        return StringUtil.join("",
                SELECT,            // SELECT 关键字
                keysToString(),    // 查询字段
                FROM,              // FROM 关键字
                fromToString(),    // 查询目标表
                joinToString(),    // 联合表处理
                whereToString(),   // 条件处理
                groupToString(),   // 分组处理
                havingToString(),  // 结果过虑
                orderToString());  // 处理排序
    }
}
