package com.mini.core.data.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * SQL构建器
 *
 * @author xchao
 */
@SuppressWarnings({"UnusedReturnValue", "unchecked"})
public abstract class BaseSql<T extends BaseSql<T>> implements Serializable {

    public final T IF_NOT_EMPTY(Collection<Object> args, Runnable runnable) {
        if (Objects.nonNull(args) && !args.isEmpty()) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Object[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(long[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(int[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(short[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(byte[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(double[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(float[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(boolean[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(char[] args, Runnable runnable) {
        if (Objects.nonNull(args) && args.length > 0) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_BLANK(String arg, Runnable runnable) {
        if (Objects.nonNull(arg) && !arg.isBlank()) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF_NOT_NULL(Object arg, Runnable runnable) {
        if (Objects.nonNull(arg)) {
            runnable.run();
        }
        return (T) this;
    }

    public final T IF(boolean expression, Runnable runnable) {
        if (expression) runnable.run();
        return (T) this;
    }

    public abstract String getSql();

}