package com.mini.core.util;

import javax.annotation.Nonnull;
import java.util.Collection;

public final class XCollection<T> extends XAbstract<Collection<T>, XCollection<T>> {
	private XCollection(Collection<T> value) {
		super(value);
	}

	@Nonnull
	@Override
	protected final XCollection<T> getThis() {
		return this;
	}

	public static <T> XCollection<T> of(Collection<T> array) {
		return new XCollection<>(array);
	}

	public final XArray toArray(T[] array) {
		if (isNull()) return XArray.of(array);
		return XArray.of(get().toArray(array));
	}
}
