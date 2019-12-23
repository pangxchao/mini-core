package com.mini.code.util;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class AbstractBuilder<T> {

	protected abstract T getThis();

	public final T ifAdd(boolean bool, Consumer<T> consumer) {
		if (bool) consumer.accept(getThis());
		return getThis();
	}

	public final <U> T forAdd(Collection<U> collection, BiConsumer<T, U> consumer) {
		collection.forEach(val -> consumer.accept(getThis(), val));
		return getThis();
	}
}
