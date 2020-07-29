package com.mini.core.jdbc.transaction;

@FunctionalInterface
public interface TransactionManager {
	<T> T open(TransactionManagerCallback<T> callback) throws Throwable;
	
	@FunctionalInterface
	interface TransactionManagerCallback<T> {
		T apply() throws Throwable;
	}
}
