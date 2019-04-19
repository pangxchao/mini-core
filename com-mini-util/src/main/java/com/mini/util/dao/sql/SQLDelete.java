package com.mini.util.dao.sql;

import com.mini.util.dao.IDao;
import com.mini.util.dao.SQL;
import com.mini.util.dao.sql.where.NestingItem;
import com.mini.util.lang.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SQLDelete implements SQL, SQLWhere<SQLDelete> {
    private final NestingItem nestingItem = new NestingItem();
    private final List<Object> params = new ArrayList<>();
    private final String name;

    public SQLDelete(String name) {
        this.name = name;
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public SQLDelete params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }

    @Override
    public String content() {
        return StringUtil.join("", DELETE, FROM, name, WHERE, nestingItem.content());
    }

    @Override
    public Object[] params() {
        return params.toArray();
    }

    @Override
    public SQLDelete getSelf() {
        return this;
    }

    @Override
    public NestingItem getNestingItem() {
        return nestingItem;
    }

    public int execute(IDao dao) throws SQLException {
        return dao.execute(this);
    }
}
