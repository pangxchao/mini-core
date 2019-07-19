package com.mini.jdbc.sql;

import com.mini.jdbc.sql.fragment.SQLFragment;

import javax.annotation.Nonnull;
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
    public final Object[] toArray() {
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
        return toText(", ", keys);
    }

    /**
     * 获取添加数据所有字段值
     * @return 添加数据所有字段值
     */
    public final String values() {
        return toText(", ", values);
    }

    @Nonnull
    public String toString() {
        return toText("", INSERT, INTO, name(), "(", keys(), ") ",//
                VALUES, "(", values(), ")");
    }

}
