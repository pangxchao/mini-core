package com.mini.plugin.builder;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class AbstractBuilder<T> {

	protected abstract T getThis();

	public final T ifAdd(boolean bool, Consumer<T> consumer) {
		if (bool) consumer.accept(getThis());
		return getThis();
	}
	
	public final T ifElseAdd(boolean bool, Consumer<T> ifConsumer, Consumer<T> elseConsumer) {
		if(bool) ifConsumer.accept(getThis());
		else elseConsumer.accept(getThis());
		return getThis();
	}

	public final <U> T forAdd(Collection<U> collection, BiConsumer<T, U> consumer) {
		collection.forEach(val -> consumer.accept(getThis(), val));
		return getThis();
	}
}
