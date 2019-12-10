package com.mini.core.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class XArray<T> extends XAbstract<T[], XArray<T>> {
    private XArray(T[] value) {
        super(value);
    }

    @Override
    protected final XArray<T> getThis() {
        return this;
    }

    public static <T> XArray<T> of(T[] array) {
        return new XArray<>(array);
    }

    public final XCollection<T> toList() {
        return XCollection.of(Stream.ofNullable(get())
                .flatMap(Stream::of)
                .collect(Collectors.toList()));
    }
}
