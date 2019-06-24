package com.mini.dao.sql.fragment;

import com.mini.dao.SQL;
import com.mini.dao.sql.SQLSelect;
import com.mini.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class DefaultJoin implements SQLFragment {
    private final List<SQLFragment> ones = new ArrayList<>();
    private String connector = SQL.AND;
    private SQLFragment content;
    private String before;

    /**
     * LEFT JOIN 前缀
     * @return 当前对象
     */
    public DefaultJoin left() {
        this.before = SQL.LEFT;
        return this;
    }

    /**
     * RIGHT JOIN 前缀
     * @return 当前对象
     */
    public DefaultJoin right() {
        this.before = SQL.RIGHT;
        return this;
    }

    /**
     * OUTER JOIN 前缀
     * @return 当前对象
     */
    public DefaultJoin outer() {
        this.before = SQL.OUTER;
        return this;
    }

    /**
     * 设置 FROM 对象
     * @param fragment FROM 对象
     * @return 当前对象
     */
    public DefaultJoin join(SQLFragment fragment) {
        this.content = fragment;
        return this;
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @param alias   别名
     * @return 当前对象
     */
    public DefaultJoin join(String content, String alias) {
        return join(new DefaultFragment(content, alias));
    }

    /**
     * 设置 FROM 对象内容
     * @param content FROM 对象
     * @return 当前对象
     */
    public DefaultJoin join(String content) {
        return join(new DefaultFragment(content));
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    public DefaultJoin join(SQLSelect select, String alias) {
        return join(select.content(), alias);
    }

    /**
     * 设置 FROM 对象
     * @param select FROM 对象
     * @return 当前对象
     */
    public DefaultJoin join(SQLSelect select) {
        return join(select.content());
    }

    /**
     * 设置 ON 的连接符
     * @param connector 连接符
     * @return 当前对象
     */
    public DefaultJoin connector(String connector) {
        this.connector = connector;
        return this;
    }

    /**
     * 添加一个SQL片断
     * @param fragment 片断内容
     * @return 当前对象
     */
    public DefaultJoin on(SQLFragment fragment) {
        this.ones.add(fragment);
        return this;
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param c     连接符号
     * @param value 字段值
     * @return 当前对象
     */
    public DefaultJoin on(String name, String c, String value) {
        return on(new DefaultFragment(name, c, value));
    }

    /**
     * 添加一个条件
     * @param name  字段名称
     * @param value 字段值
     * @return 当前对象
     */
    public DefaultJoin on(String name, String value) {
        return on(name, "=", value);
    }

    /**
     * 添加一个条件
     * @param name 字段名称
     * @return 当前对象
     */
    public DefaultJoin on(String name) {
        return on(name, "?");
    }

    /**
     * 获取JOIN 前缀
     * @return LEFT/RIGHT/OUTER
     */
    public String before() {
        return before;
    }

    @Override
    public String content() {
        if (content == null) return null;
        return StringUtil.join(" ", content.content(), "(", StringUtil.join(connector, ones.stream().map(SQLFragment::content).toArray()), ")");
    }
}
