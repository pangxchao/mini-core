package com.mini.core.jdbc.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.addAll;

public final class SQLWrapper {
    private final StringBuilder sql = new StringBuilder();
    private final List<Object> args = new ArrayList<>();

    /**
     * 创建一个空的包装器
     */
    public SQLWrapper() {
    }

    /**
     * 创建一个带SQL片段的包装器
     *
     * @param segment SQL片段
     */
    public SQLWrapper(String segment) {
        sql.append(segment);
    }

    /**
     * 追加SQL片段
     *
     * @param segment SQL片段
     * @return SQL包装器
     */
    public SQLWrapper append(String segment) {
        sql.append(segment);
        return this;
    }


    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(Collection<?> args) {
        this.args.addAll(args);
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(Object... args) {
        addAll(this.args, args);
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(long[] args) {
        for (final long arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(int[] args) {
        for (final int arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(short[] args) {
        for (final short arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(byte[] args) {
        for (final byte arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(double[] args) {
        for (final double arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(float[] args) {
        for (final float arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(boolean[] args) {
        for (final boolean arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public SQLWrapper add(char[] args) {
        for (final char arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 获取SQL语句
     *
     * @return SQL语句
     */
    public String sql() {
        return sql.toString();
    }

    /**
     * 获取SQL参数数组
     *
     * @return SQL参数数组
     */
    public Object[] args() {
        return args.toArray();
    }

}
