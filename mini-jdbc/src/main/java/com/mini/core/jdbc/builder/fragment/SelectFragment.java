package com.mini.core.jdbc.builder.fragment;

import com.mini.core.jdbc.builder.statement.HavingStatement;
import com.mini.core.jdbc.builder.statement.WhereStatement;

import java.util.Collection;
import java.util.function.Consumer;

public interface SelectFragment<T extends SelectFragment<T>> extends JoinFragment<T>, WhereFragment<T> {
    T where(Consumer<WhereStatement> consumer);

    /**
     * 添加“AND”连接符
     *
     * @return {@code this}
     */
    T and();

    /**
     * 添加“OR”连接符
     *
     * @return {@code this}
     */
    T or();

    T groupBy(String... columns);

    T having(Consumer<HavingStatement> consumer);

    /**
     * “=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingEqNative(String column, String target, Object... args);

    /**
     * “=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingEq(String column, Object arg);

    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingNotEqNative(String column, String target, Object... args);


    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingNotEq(String column, Object arg);

    /**
     * 字段必须为空
     *
     * @param column 字段名称
     * @return {@code this}
     */
    T havingIsNull(String column);

    /**
     * 字段必须不为空
     *
     * @param column 字段名称
     * @return {@code this}
     */
    T havingIsNotNull(String column);


    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingGtNative(String column, String target, Object... args);

    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingGt(String column, Object arg);


    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingLtNative(String column, String target, Object... args);

    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingLt(String column, Object arg);


    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingGteNative(String column, String target, Object... args);

    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingGte(String column, Object arg);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingLteNative(String column, String target, Object... args);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingLte(String column, Object arg);

    /**
     * “IN”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingInNative(String column, String target, Object... args);


    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O, C extends Collection<O>> T havingIn(String column, C args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O> T havingIn(String column, O[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, long[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, int[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, short[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, byte[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, double[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, float[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, boolean[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingIn(String column, char[] args);

    /**
     * “NOT IN”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingNotInNative(String column, String target, Object... args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O, C extends Collection<O>> T havingNotIn(String column, C args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O> T havingNotIn(String column, O[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, long[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, int[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, short[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, byte[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, double[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, float[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, boolean[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T havingNotIn(String column, char[] args);

    /**
     * “LIKE”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingLikeNative(String column, String target, Object... args);

    /**
     * "LIKE" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingLike(String column, Object arg);

    /**
     * "LIKE %_%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingContain(String column, Object arg);

    /**
     * "LIKE _%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingStartWith(String column, Object arg);

    /**
     * "LIKE %_" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingEndWith(String column, Object arg);

    /**
     * “LIKE”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T havingNotLikeNative(String column, String target, Object... args);

    /**
     * "LIKE" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingNotLike(String column, Object arg);

    /**
     * "LIKE %_%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingNotContain(String column, Object arg);

    /**
     * "LIKE _%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingNotStartWith(String column, Object arg);

    /**
     * "LIKE %_" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T havingNotEndWith(String column, Object arg);

    /**
     * “MATCH(?)  AGAINST(target)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T havingMatchNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T havingMatch(String[] columns, Object arg);

    /**
     * “MATCH(?)  AGAINST(target)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T havingNotMatchNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T havingNotMatch(String[] columns, Object arg);

    /**
     * “MATCH(?) AGAINST(target IN BOOLEAN MODE)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T havingMatchInBoolNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T havingMatchInBool(String[] columns, Object arg);

    /**
     * “MATCH(?) AGAINST(target IN BOOLEAN MODE)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T havingNotMatchInBoolNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T havingNotMatchInBool(String[] columns, Object arg);

    /**
     * “BETWEEN ? AND ?”条件
     *
     * @param column    字段名称
     * @param targetMin 最小目标字符串
     * @param targetMax 最大目标字符串
     * @param args      目标字符串中的参数值
     * @return {@code this}
     */
    T havingBetweenNative(String column, String targetMin, String targetMax, Object... args);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column 字段名称
     * @param min    最小参数值
     * @param max    最大参数值
     * @return {@code this}
     */
    T havingBetween(String column, Object min, Object max);

    /**
     * “BETWEEN ? AND ?”条件
     *
     * @param column    字段名称
     * @param targetMin 最小目标字符串
     * @param targetMax 最大目标字符串
     * @param args      目标字符串中的参数值
     * @return {@code this}
     */
    T havingNotBetweenNative(String column, String targetMin, String targetMax, Object... args);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column 字段名称
     * @param min    最小参数值
     * @param max    最大参数值
     * @return {@code this}
     */
    T havingNotBetween(String column, Object min, Object max);

    T orderBy(String... columns);

    T orderByAsc(String... columns);

    T orderByDesc(String... columns);
}
