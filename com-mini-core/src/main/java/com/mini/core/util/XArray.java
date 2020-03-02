package com.mini.core.util;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.ofNullable;

public final class XArray<T> extends XAbstract<T[], XArray<T>> {
	private XArray(T[] value) {
		super(value);
	}

	@Nonnull
	@Override
	protected final XArray<T> getThis() {
		return this;
	}

	public final XCollection<T> toList() {
		return XCollection.of(ofNullable(get())
				.flatMap(Stream::of)
				.collect(Collectors.toList()));
	}

	public static <T> XArray<T> of(T[] array) {
		return new XArray<>(array);
	}
}
