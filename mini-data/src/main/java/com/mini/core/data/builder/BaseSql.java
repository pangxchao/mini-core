package com.mini.core.data.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * SQL构建器
 *
 * @author xchao
 */
@SuppressWarnings({"UnusedReturnValue", "unchecked"})
public abstract class BaseSql<T extends BaseSql<T>> implements Serializable {

    public final T IF_NOT_EMPTY(Collection<Object> args, Consumer<Collection<Object>> consumer) {
        if (Objects.nonNull(args) && !args.isEmpty()) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Object[] args, Consumer<Object[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(long[] args, Consumer<long[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(int[] args, Consumer<int[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(short[] args, Consumer<short[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(byte[] args, Consumer<byte[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(double[] args, Consumer<double[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(float[] args, Consumer<float[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(boolean[] args, Consumer<boolean[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(char[] args, Consumer<char[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_BLANK(String arg, Consumer<String> consumer) {
        if (Objects.nonNull(arg) && !arg.isBlank()) {
            consumer.accept(arg);
        }
        return (T) this;
    }

    public final T IF_NOT_NULL(Object arg, Consumer<Object> consumer) {
        if (Objects.nonNull(arg)) {
            consumer.accept(arg);
        }
        return (T) this;
    }

    public final T IF(boolean expression, Runnable runnable) {
        if (expression) runnable.run();
        return (T) this;
    }

    public abstract String getSql();

}