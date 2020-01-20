package com.mini.plugin.util;

import org.jetbrains.annotations.NotNull;

public class ThrowsUtil {
	
	public static RuntimeException hidden(@NotNull Throwable t) {
		return ThrowsUtil.hidden_(t);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Throwable> T hidden_(Throwable t) throws T {
		throw (T) t;
	}
}
