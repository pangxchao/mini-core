package com.mini.core.jdbc.transaction;

@FunctionalInterface
public interface TransManager {
	<T> T open(TransManagerCallback<T> callback) throws Throwable;

	@FunctionalInterface
	interface TransManagerCallback<T> {
		T apply() throws Throwable;
	}
}
