package com.mini.core.util;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.Validate.notNull;

public abstract class XAbstract<V, T extends XAbstract<V, T>> {
	private V value;

	protected XAbstract(V value) {
		this.value = value;
	}

	@Nonnull
	protected abstract T getThis();

	protected final void set(V value) {
		this.value = value;
	}

	@Nonnull
	public final V get() {
		notNull(value);
		return value;
	}

	public final V orElse(V v) {
		if (this.isNull()) {
			return v;
		}
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

	@Nonnull
	public final T filter(Predicate<? super V> predicate) {
		if (this.isNull()) return getThis();
		if (!predicate.test(value)) {
			this.set(null);
		}
		return getThis();
	}

	@Nonnull
	public final T map(Function<V, V> mapper) {
		if (this.isNull()) return getThis();
		this.set(mapper.apply(value));
		return getThis();
	}

	@Override
	public final int hashCode() {
		return Objects.hashCode(value);
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

	@Nonnull
	@Override
	public final String toString() {
		return isNull() ? "Null" ://
				value.toString();
	}
}
