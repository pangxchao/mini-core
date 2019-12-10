package com.mini.core.util;

public abstract class XAbstract<T> {
    protected T value;

    protected XAbstract(T value) {
        this.value = value;
    }

    public final T getValue() {
        return value;
    }

    public final boolean isNull() {
        return value == null;
    }

    @Override
    public final int hashCode() {
        if (this.isNull()) {
            return 0;
        }

        return value.hashCode();
    }

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
