package com.mini.jdbc.sql.fragment;

import com.mini.jdbc.sql.SQLSelect;

public interface SQLJoin<T extends SQLJoin<T>> {
    T getSelf();

    DefaultJoin getJoin();

    /**
     * 获取 JOIN 部分片断
     * @return JOIN 部分片断
     */
    default String joinToString() {
        return getJoin().toString();
    }

    /**
     * 设置 JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    default T join(SQLFragment fragment) {
        getJoin().join(fragment);
        return getSelf();
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
    default T join(String content, String alias, String name, String c, String value) {
        getJoin().join(content, alias, name, c, value);
        return getSelf();
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
    default T join(SQLSelect content, String alias, String name, String c, String value) {
        getJoin().join(content, alias, name, c, value);
        return getSelf();
    }

    /**
     * 设置 JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T join(String content, String alias, String name, String value) {
        getJoin().join(content, alias, name,   value);
        return getSelf();
    }

    /**
     * 设置 JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T join(SQLSelect content, String alias, String name, String value) {
        getJoin().join(content, alias, name,   value);
        return getSelf();
    }

    /**
     * 设置 LEFT JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    default T leftJoin(SQLFragment fragment) {
        getJoin().leftJoin(fragment);
        return getSelf();
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
    default T leftJoin(String content, String alias, String name, String c, String value) {
        getJoin().leftJoin(content, alias, name, c, value);
        return getSelf();
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
    default T leftJoin(SQLSelect content, String alias, String name, String c, String value) {
        getJoin().leftJoin(content, alias, name, c, value);
        return getSelf();
    }

    /**
     * 设置 LEFT JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T leftJoin(String content, String alias, String name, String value) {
        getJoin().leftJoin(content, alias, name,   value);
        return getSelf();
    }

    /**
     * 设置 LEFT JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T leftJoin(SQLSelect content, String alias, String name, String value) {
        getJoin().leftJoin(content, alias, name,   value);
        return getSelf();
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    default T rightJoin(SQLFragment fragment) {
        getJoin().rightJoin(fragment);
        return getSelf();
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
    default T rightJoin(String content, String alias, String name, String c, String value) {
        getJoin().rightJoin(content, alias, name, c, value);
        return getSelf();
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
    default T rightJoin(SQLSelect content, String alias, String name, String c, String value) {
        getJoin().rightJoin(content, alias, name, c, value);
        return getSelf();
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T rightJoin(String content, String alias, String name, String value) {
        getJoin().rightJoin(content, alias, name,   value);
        return getSelf();
    }

    /**
     * 设置 RIGHT JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T rightJoin(SQLSelect content, String alias, String name, String value) {
        getJoin().rightJoin(content, alias, name,   value);
        return getSelf();
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param fragment FROM 对象
     * @return {@Code this}
     */
    default T outerJoin(SQLFragment fragment) {
        getJoin().outerJoin(fragment);
        return getSelf();
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
    default T outerJoin(String content, String alias, String name, String c, String value) {
        getJoin().outerJoin(content, alias, name, c, value);
        return getSelf();
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
    default T outerJoin(SQLSelect content, String alias, String name, String c, String value) {
        getJoin().outerJoin(content, alias, name, c, value);
        return getSelf();
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param content 连接目标表/子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T outerJoin(String content, String alias, String name, String value) {
        getJoin().outerJoin(content, alias, name, value);
        return getSelf();
    }

    /**
     * 设置 OUTER JOIN  内容
     * @param content 连接目标子查询
     * @param alias   表别名/子查询别名
     * @param name    连接条件主表字段
     * @param value   连接条件子表字段
     * @return {@Code this}
     */
    default T outerJoin(SQLSelect content, String alias, String name, String value) {
        getJoin().outerJoin(content, alias, name, value);
        return getSelf();
    }
}
