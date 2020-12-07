package com.mini.core.data.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static java.util.Arrays.deepToString;
import static java.util.Collections.addAll;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public abstract class AbstractSql<T extends AbstractSql<T>> {
    private final List<Object> args = new ArrayList<>();

    public final <O, C extends Collection<O>> T isNotEmpty(C args, Consumer<C> consumer) {
        if (Objects.nonNull(args) && !args.isEmpty()) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final <O> T isNotEmpty(O[] args, Consumer<O[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(long[] args, Consumer<long[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(int[] args, Consumer<int[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(short[] args, Consumer<short[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(byte[] args, Consumer<byte[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(double[] args, Consumer<double[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(float[] args, Consumer<float[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(boolean[] args, Consumer<boolean[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }

    public final T isNotEmpty(char[] args, Consumer<char[]> consumer) {
        if (Objects.nonNull(args) && args.length > 0) {
            consumer.accept(args);
        }
        return (T) this;
    }


    public final T isNotBlank(String arg, Consumer<String> consumer) {
        if (Objects.nonNull(arg) && !arg.isBlank()) {
            consumer.accept(arg);
        }
        return (T) this;
    }

    public final <O> T isNotNull(O arg, Consumer<O> consumer) {
        if (Objects.nonNull(arg)) {
            consumer.accept(arg);
        }
        return (T) this;
    }

    public final T isTrue(Boolean expression, Consumer<Boolean> consumer) {
        if (expression != null && expression) {
            consumer.accept(true);
        }
        return (T) this;
    }

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
