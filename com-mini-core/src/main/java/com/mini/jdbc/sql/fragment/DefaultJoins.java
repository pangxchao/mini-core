package com.mini.jdbc.sql.fragment;

import com.mini.jdbc.sql.SQLSelect;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.mini.jdbc.sql.SQL.*;

public final class DefaultJoins implements SQLFragment {
    private final List<SQLFragment> joins = new ArrayList<>();

    /**
     * 设置 JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    public DefaultJoins join(SQLFragment fragment) {
        joins.add(new DefaultFragment(JOIN, fragment.content()));
        return this;
    }

    /**
     * 设置 JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins join(String content, String alias, String name, String c, String value) {
        return join(new DefaultFragment(content, alias, name, c, value));
    }

    /**
     * 设置 JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins join(SQLSelect content, String alias, String name, String c, String value) {
        return join(content.content(), alias, name, c, value);
    }

    /**
     * 设置 JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins join(String content, String alias, String name, String value) {
        return join(content, alias, name, "=", value);
    }

    /**
     * 设置 JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins join(SQLSelect content, String alias, String name, String value) {
        return join(content, alias, name, "=", value);
    }

    /**
     * 设置 LEFT JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    public DefaultJoins leftJoin(SQLFragment fragment) {
        joins.add(new DefaultFragment(LEFT, JOIN, fragment.content()));
        return this;
    }


    /**
     * 设置 LEFT JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins leftJoin(String content, String alias, String name, String c, String value) {
        return leftJoin(new DefaultFragment(content, alias, name, c, value));
    }

    /**
     * 设置 LEFT JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins leftJoin(SQLSelect content, String alias, String name, String c, String value) {
        return leftJoin(content.content(), alias, name, c, value);
    }

    /**
     * 设置 LEFT JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins leftJoin(String content, String alias, String name, String value) {
        return leftJoin(content, alias, name, "=", value);
    }

    /**
     * 设置 LEFT JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins leftJoin(SQLSelect content, String alias, String name, String value) {
        return leftJoin(content, alias, name, "=", value);
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    public DefaultJoins rightJoin(SQLFragment fragment) {
        joins.add(new DefaultFragment(RIGHT, JOIN, fragment.content()));
        return this;
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins rightJoin(String content, String alias, String name, String c, String value) {
        return rightJoin(new DefaultFragment(content, alias, name, c, value));
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins rightJoin(SQLSelect content, String alias, String name, String c, String value) {
        return rightJoin(content.content(), alias, name, c, value);
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins rightJoin(String content, String alias, String name, String value) {
        return rightJoin(content, alias, name, "=", value);
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins rightJoin(SQLSelect content, String alias, String name, String value) {
        return rightJoin(content, alias, name, "=", value);
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    public DefaultJoins outerJoin(SQLFragment fragment) {
        joins.add(new DefaultFragment(OUTER, JOIN, fragment.content()));
        return this;
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins outerJoin(String content, String alias, String name, String c, String value) {
        return outerJoin(new DefaultFragment(content, alias, name, c, value));
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param c       连接符
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins outerJoin(SQLSelect content, String alias, String name, String c, String value) {
        return outerJoin(content.content(), alias, name, c, value);
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins outerJoin(String content, String alias, String name, String value) {
        return outerJoin(content, alias, name, "=", value);
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    public DefaultJoins outerJoin(SQLSelect content, String alias, String name, String value) {
        return outerJoin(content, alias, name, "=", value);
    }

    @Nonnull
    @Override
    public final String content() {
        if (joins.size() <= 0) return "";
        return text(" ", joins);
    }
}
