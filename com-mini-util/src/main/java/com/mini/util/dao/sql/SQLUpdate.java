package com.mini.util.dao.sql;

import com.mini.util.dao.IDao;
import com.mini.util.dao.SQL;
import com.mini.util.dao.sql.fragment.*;
import com.mini.util.lang.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLUpdate implements SQL, SQLFragment, SQLFrom<SQLUpdate>, SQLJoin<SQLUpdate>, SQLWhere<SQLUpdate> {
    private final List<String> values = new ArrayList<>();
    private final List<Object> params = new ArrayList<>();
    private final DefaultWhere where = new DefaultWhere();
    private final DefaultFrom from = new DefaultFrom();
    private final DefaultJoin join = new DefaultJoin();

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
    public final DefaultJoin getJoin() {
        return this.join;
    }

    @Override
    public final DefaultWhere getWhere() {
        return this.where;
    }

    @Override
    public final String from() {
        return SQLFrom.super.from();
    }

    public final String values() {
        return StringUtil.join(", ", values);
    }

    @Override
    public final String join() {
        return SQLJoin.super.join();
    }

    @Override
    public final String where() {
        return SQLWhere.super.where();
    }

    @Override
    public String content() {
        StringBuilder builder = new StringBuilder();
        builder.append(UPDATE).append(from());
        // 联合表处理
        String join = SQLUpdate.this.join();
        if (StringUtil.isNotBlank(join)) {
            builder.append(JOIN);
            builder.append(join);
        }
        builder.append(SET).append(values());
        // 条件处理
        String where = SQLUpdate.this.where();
        if (StringUtil.isNotBlank(where)) {
            builder.append(WHERE);
            builder.append(where());
        }
        return builder.toString();
    }

    public final int execute(IDao dao) throws SQLException {
        return dao.execute(this);
    }
}
