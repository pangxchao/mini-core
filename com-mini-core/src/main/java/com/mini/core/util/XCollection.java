package com.mini.core.util;

import java.util.Collection;

public final class XCollection<T> extends XAbstract<Collection<T>> {
    private XCollection(Collection<T> value) {
        super(value);
    }

    public static <T> XCollection<T> of(Collection<T> array) {
        return new XCollection<>(array);
    }

    public final XArray toArray(T[] array) {
        if (value == null) return XArray.of(array);
        return XArray.of(value.toArray(array));
    }
}
