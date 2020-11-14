package com.mini.core.data.builder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;

public abstract class IndexedSql<T extends IndexedSql<T>> extends BaseSql<T> {
    private final List<Object> args = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public final T ARGS(Object... args) {
        addAll(this.args, args);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(long[] args) {
        for (long arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(int[] args) {
        for (int arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(short[] args) {
        for (short arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(byte[] args) {
        for (byte arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(double[] args) {
        for (double arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(float[] args) {
        for (float arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(boolean[] args) {
        for (boolean arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public final T ARGS(char[] args) {
        for (char arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final List<Object> getArgs() {
        return this.args;
    }
}
