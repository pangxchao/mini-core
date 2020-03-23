package com.mini.core.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

import static java.util.Optional.ofNullable;

public class ThrowsUtil {
	@Nonnull
	public static Throwable getLastCause(@Nonnull Throwable throwable) {
		return ofNullable(throwable.getCause())
				.map(ThrowsUtil::getLastCause)
				.orElse(throwable);
	}
	
	@Nullable
	public static Throwable getInvocationTarget(@Nonnull Throwable throwable) {
		if (throwable instanceof InvocationTargetException) {
			var e = (InvocationTargetException) throwable;
			return e.getTargetException();
		}
		return null;
	}
	
	@Nonnull
	public static Throwable getLastInvocationTarget(@Nonnull Throwable throwable) {
		if (throwable instanceof InvocationTargetException) {
			var e = (InvocationTargetException) throwable;
			Throwable ex = e.getTargetException();
			return getLastInvocationTarget(ex);
		}
		return throwable;
	}
	
	public static RuntimeException hidden(@Nonnull Throwable t) {
		return ThrowsUtil.hidden_(t);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Throwable> T hidden_(Throwable t) throws T {
		throw (T) t;
	}
}
