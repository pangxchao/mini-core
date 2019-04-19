package com.mini.util.dao.sql;

import com.mini.util.dao.IDao;
import com.mini.util.dao.SQL;
import com.mini.util.lang.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SQLReplace implements SQL {
    private final List<Object> params = new ArrayList<>();
    private final List<String> values = new ArrayList<>();
    private final List<String> keys = new ArrayList<>();
    private final String name;

    public SQLReplace(String name) {
        this.name = name;
    }

    /**
     * -添加一个字段和字段值
     * @param key 字段名称
     * @return SQLInsert
     */
    public SQLReplace put(String key, String value) {
        values.add(value);
        keys.add(key);
        return this;
    }

    /**
     * -添加一个字段和默认值
     * @param key 字段名称
     * @return SQLInsert
     */
    public SQLReplace put(String key) {
        return put(key, "?");
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public SQLReplace params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }

    /**
     * -生成内容列表
     * @return SQL
     */
    public String content() {
        return StringUtil.join("", REPLACE, INTO, name, "(", StringUtil.join(", ", keys), ") ", VALUES, "(", StringUtil.join(", ", values), ")");
    }

    /**
     * -获取参数列表
     * @return 参数列表
     */
    public Object[] params() {
        return params.toArray();
    }

    public int execute(IDao dao) throws SQLException {
        return dao.execute(this);
    }

    public long automatic(IDao dao) throws SQLException {
        return dao.automatic(this);
    }
}
