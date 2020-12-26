package com.mini.core.jdbc.builder.fragment;

import java.util.Collection;

@SuppressWarnings("UnusedReturnValue")
public interface WhereFragment<T extends WhereFragment<T>> {
    /**
     * “=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereEqNative(String column, String target, Object... args);

    /**
     * “=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereEq(String column, Object arg);

    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereNotEqNative(String column, String target, Object... args);


    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereNotEq(String column, Object arg);

    /**
     * 字段必须为空
     *
     * @param column 字段名称
     * @return {@code this}
     */
    T whereIsNull(String column);

    /**
     * 字段必须不为空
     *
     * @param column 字段名称
     * @return {@code this}
     */
    T whereIsNotNull(String column);


    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereGtNative(String column, String target, Object... args);

    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereGt(String column, Object arg);


    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereLtNative(String column, String target, Object... args);

    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereLt(String column, Object arg);


    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereGteNative(String column, String target, Object... args);

    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereGte(String column, Object arg);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereLteNative(String column, String target, Object... args);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereLte(String column, Object arg);

    /**
     * “IN”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereInNative(String column, String target, Object... args);


    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O, C extends Collection<O>> T whereIn(String column, C args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O> T whereIn(String column, O[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, long[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, int[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, short[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, byte[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, double[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, float[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, boolean[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereIn(String column, char[] args);

    /**
     * “NOT IN”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereNotInNative(String column, String target, Object... args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O, C extends Collection<O>> T whereNotIn(String column, C args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O> T whereNotIn(String column, O[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, long[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, int[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, short[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, byte[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, double[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, float[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, boolean[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T whereNotIn(String column, char[] args);

    /**
     * “LIKE”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereLikeNative(String column, String target, Object... args);

    /**
     * "LIKE" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereLike(String column, Object arg);

    /**
     * "LIKE %_%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereContain(String column, Object arg);

    /**
     * "LIKE _%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereStartWith(String column, Object arg);

    /**
     * "LIKE %_" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereEndWith(String column, Object arg);

    /**
     * “LIKE”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T whereNotLikeNative(String column, String target, Object... args);

    /**
     * "LIKE" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereNotLike(String column, Object arg);

    /**
     * "LIKE %_%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereNotContain(String column, Object arg);

    /**
     * "LIKE _%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereNotStartWith(String column, Object arg);

    /**
     * "LIKE %_" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T whereNotEndWith(String column, Object arg);

    /**
     * “MATCH(?)  AGAINST(target)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T whereMatchNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T whereMatch(String[] columns, Object arg);

    /**
     * “MATCH(?)  AGAINST(target)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T whereNotMatchNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T whereNotMatch(String[] columns, Object arg);

    /**
     * “MATCH(?) AGAINST(target IN BOOLEAN MODE)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T whereMatchInBoolNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T whereMatchInBool(String[] columns, Object arg);

    /**
     * “MATCH(?) AGAINST(target IN BOOLEAN MODE)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T whereNotMatchInBoolNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T whereNotMatchInBool(String[] columns, Object arg);

    /**
     * “BETWEEN ? AND ?”条件
     *
     * @param column    字段名称
     * @param targetMin 最小目标字符串
     * @param targetMax 最大目标字符串
     * @param args      目标字符串中的参数值
     * @return {@code this}
     */
    T whereBetweenNative(String column, String targetMin, String targetMax, Object... args);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column 字段名称
     * @param min    最小参数值
     * @param max    最大参数值
     * @return {@code this}
     */
    T whereBetween(String column, Object min, Object max);

    /**
     * “BETWEEN ? AND ?”条件
     *
     * @param column    字段名称
     * @param targetMin 最小目标字符串
     * @param targetMax 最大目标字符串
     * @param args      目标字符串中的参数值
     * @return {@code this}
     */
    T whereNotBetweenNative(String column, String targetMin, String targetMax, Object... args);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column 字段名称
     * @param min    最小参数值
     * @param max    最大参数值
     * @return {@code this}
     */
    T whereNotBetween(String column, Object min, Object max);
}
