package com.mini.core.data.builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * SQL构建器
 *
 * @author xchao
 */
@SuppressWarnings({"UnusedReturnValue", "unchecked"})
public abstract class BaseSql<T extends BaseSql<T>> implements Serializable {

    public final T IF_NOT_BLANK(Consumer<String> consumer, String arg) {
        if (Objects.nonNull(arg) && !arg.isBlank()) {
            consumer.accept(arg);
        }
        return (T) this;
    }

    public final T IF_NOT_NULL(Consumer<Object> consumer, Object arg) {
        if (Objects.nonNull(arg)) {
            consumer.accept(arg);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<Object[]> consumer, Object... args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<long[]> consumer, long[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<int[]> consumer, int[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<short[]> consumer, short[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<byte[]> consumer, byte[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<double[]> consumer, double[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<float[]> consumer, float[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<boolean[]> consumer, boolean[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T IF_NOT_EMPTY(Consumer<char[]> consumer, char[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public abstract String getSql();

}