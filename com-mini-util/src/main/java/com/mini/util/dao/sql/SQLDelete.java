package com.mini.util.dao.sql;

import com.mini.util.dao.IDao;
import com.mini.util.dao.SQL;
import com.mini.util.dao.sql.fragment.*;
import com.mini.util.lang.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLDelete implements SQL, SQLFragment, SQLFrom<SQLDelete>, SQLJoin<SQLDelete>, SQLWhere<SQLDelete> {
    private final List<Object> params = new ArrayList<>();
    private final DefaultWhere where = new DefaultWhere();
    private final DefaultFrom from = new DefaultFrom();
    private final DefaultJoin join = new DefaultJoin();

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
        builder.append(DELETE).append(FROM).append(from());
        // 联合表处理
        String join = SQLDelete.this.join();
        if (!StringUtil.isBlank(join)) {
            String before = this.before();
            if (!StringUtil.isBlank(before)) {
                builder.append(before);
            }
            builder.append(JOIN);
            builder.append(join);
        }
        // 条件处理
        String where = SQLDelete.this.where();
        if (!StringUtil.isBlank(where)) {
            builder.append(WHERE);
            builder.append(where());
        }
        return builder.toString();
    }

    public final int execute(IDao dao) throws SQLException {
        return dao.execute(this);
    }
}
