package com.mini.core.data.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.deepToString;
import static java.util.Collections.addAll;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public abstract class AbstractSql<T extends AbstractSql<T>> {
    private final List<Object> args = new ArrayList<>();

    public final <O, C extends Collection<O>> T args(C args) {
        this.args.addAll(args);
        return (T) this;
    }

    public final T args(Object... args) {
        addAll(this.args, args);
        return (T) this;
    }

    public final T args(long[] args) {
        for (long arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final T args(int[] args) {
        for (int arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final T args(short[] args) {
        for (short arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final T args(byte[] args) {
        for (byte arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final T args(double[] args) {
        for (double arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final T args(float[] args) {
        for (float arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final T args(boolean[] args) {
        for (boolean arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final T args(char[] args) {
        for (char arg : args) {
            this.args.add(arg);
        }
        return (T) this;
    }

    public final Object[] getArgs() {
        return args.toArray();
    }

    public abstract String getSql();

    @Override
    public final String toString() {
        return String.format("%s\n%s", getSql(), deepToString(getArgs()));
    }
}
