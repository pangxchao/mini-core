package com.mini.core.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

import static java.util.Optional.ofNullable;

public final class XThrows extends XAbstract<Throwable, XThrows> {

	private XThrows(Throwable value) {
		super(value);
	}

	@Nonnull
	@Override
	protected XThrows getThis() {
		return this;
	}

	@Nonnull
	public final XThrows cause() {
		return map(Throwable::getCause);
	}

	@Nonnull
	public final XThrows lastCause() {
		return map(this::getLastCause);
	}

	@Nonnull
	public final XThrows invocationTarget() {
		return map(this::getInvocationTarget);
	}

	@Nonnull
	public final XThrows lastInvocationTarget() {
		return map(this::getLastInvocationTarget);
	}

	public static XThrows of(Throwable e) {
		return new XThrows(e);
	}

	@Nonnull
	private Throwable getLastCause(@Nonnull Throwable throwable) {
		return ofNullable(throwable.getCause())
			.map(this::getLastCause)
			.orElse(throwable);
	}

	@Nullable
	private Throwable getInvocationTarget(@Nonnull Throwable throwable) {
		if (throwable instanceof InvocationTargetException) {
			var e = (InvocationTargetException) throwable;
			return e.getTargetException();
		}
		return null;
	}

	@Nonnull
	private Throwable getLastInvocationTarget(@Nonnull Throwable throwable) {
		if (throwable instanceof InvocationTargetException) {
			var e = (InvocationTargetException) throwable;
			Throwable ex = e.getTargetException();
			return getLastInvocationTarget(ex);
		}
		return throwable;
	}

	public static RuntimeException sneaky(@Nonnull Throwable t) {
		return XThrows.sneaky0(t);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Throwable> T sneaky0(Throwable t) throws T {
		throw (T) t;
	}
}
