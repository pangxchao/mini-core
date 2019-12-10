package com.mini.core.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class XAbstract<V, T extends XAbstract<V, T>> {
    private V value;

    protected XAbstract(V value) {
        this.value = value;
    }

    protected abstract T getThis();

    public final V get() {
        return value;
    }

    public final boolean isNull() {
        return value == null;
    }

    public final boolean isNotNull() {
        return value != null;
    }


    public final void ifPresent(Consumer<? super V> action) {
        if (this.isNull()) return;
        action.accept(value);
    }

    public final void ifPresentOrElse(Consumer<? super V> action, Runnable emptyAction) {
        if (isNull()) emptyAction.run();
        else action.accept(value);
    }

    public final T filter(Predicate<? super V> predicate) {
        if (this.isNull()) return getThis();
        if (!predicate.test(value)) {
            this.value = null;
        }
        return getThis();
    }

    public final T map(Function<V, V> mapper) {
        if (this.isNull()) return getThis();
        value = mapper.apply(value);
        return getThis();
    }

    @Override
    public final int hashCode() {
        if (this.isNull()) {
            return 0;
        }
        return value.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        if (value == null) return false;
        if (!(obj instanceof XAbstract)) {
            return value.equals(obj);
        }
        // 比较当前对象值
        Object o = ((XAbstract) obj).value;
        return value.equals(o);
    }

    @Override
    public final String toString() {
        if (this.isNull()) {
            return "Null";
        }
        return value.toString();
    }
}
