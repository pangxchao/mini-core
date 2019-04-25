package com.mini.util.dao.sql;

import com.mini.util.dao.IDao;
import com.mini.util.dao.SQL;
import com.mini.util.dao.sql.fragment.SQLFragment;
import com.mini.util.lang.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLInsert implements SQL, SQLFragment {
    private final List<Object> params = new ArrayList<>();
    private final List<String> values = new ArrayList<>();
    private final List<String> keys = new ArrayList<>();
    private final String name;

    public SQLInsert(String name) {
        this.name = name;
    }

    /**
     * -添加一个字段和字段值
     * @param key 字段名称
     * @return SQLInsert
     */
    public final SQLInsert put(String key, String value) {
        values.add(value);
        keys.add(key);
        return this;
    }

    /**
     * -添加一个字段和默认值
     * @param key 字段名称
     * @return SQLInsert
     */
    public final SQLInsert put(String key) {
        return put(key, "?");
    }

    /**
     * -添加参数列表
     * @param param 参数值
     * @return SQLInsert
     */
    public final SQLInsert params(Object... param) {
        params.addAll(Arrays.asList(param));
        return this;
    }

    /**
     * -获取参数列表
     * @return 参数列表
     */
    public final Object[] params() {
        return params.toArray();
    }

    /**
     * 获取添加数据表名称
     * @return 添加数据表名称
     */
    public final String name() {
        return this.name;
    }

    /**
     * 获取添加数据的所有字段
     * @return 添加数据的所有字段
     */
    public final String keys() {
        return StringUtil.join(", ", keys);
    }

    /**
     * 获取添加数据所有字段值
     * @return 添加数据所有字段值
     */
    public final String values() {
        return StringUtil.join(", ", values);
    }

    public String content() {
        return StringUtil.join("", INSERT, INTO, name(), "(", keys(), ") ", VALUES, "(", values(), ")");
    }

    public final int execute(IDao dao) throws SQLException {
        return dao.execute(this);
    }

    public final long automatic(IDao dao) throws SQLException {
        return dao.automatic(this);
    }
}
