package com.mini.core.jdbc.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.addAll;

/**
 * 数组参数的SQL包装器实现
 *
 * @author pangchao
 */
public final class IndexWrapper implements SQLWrapper<IndexWrapper> {
    private final StringBuilder sql = new StringBuilder();
    private final List<Object> args = new ArrayList<>();

    /**
     * 创建一个空的包装器
     */
    public IndexWrapper() {
    }

    /**
     * 创建一个带SQL片段的包装器
     *
     * @param segment SQL片段
     */
    public IndexWrapper(String segment) {
        sql.append(segment);
    }

    @Override
    public IndexWrapper append(String segment) {
        sql.append(segment);
        return this;
    }

    @Override
    public final String sql() {
        return sql.toString();
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public IndexWrapper add(Collection<?> args) {
        this.args.addAll(args);
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public IndexWrapper add(Object... args) {
        addAll(this.args, args);
        return this;
    }

    /**
     * 添加SQL参数
     *
     * @param args SQL参数
     * @return 当前对象
     */
    public IndexWrapper add(long[] args) {
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
    public IndexWrapper add(int[] args) {
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
    public IndexWrapper add(short[] args) {
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
    public IndexWrapper add(byte[] args) {
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
    public IndexWrapper add(double[] args) {
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
    public IndexWrapper add(float[] args) {
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
    public IndexWrapper add(boolean[] args) {
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
    public IndexWrapper add(char[] args) {
        for (final char arg : args) {
            this.args.add(arg);
        }
        return this;
    }

    /**
     * 获取SQL参数数组
     *
     * @return SQL参数数组
     */
    public final Object[] args() {
        return args.toArray();
    }
}
