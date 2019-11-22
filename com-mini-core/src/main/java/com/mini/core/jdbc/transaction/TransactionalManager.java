package com.mini.core.jdbc.transaction;

@FunctionalInterface
public interface TransactionalManager {
    <T> T openTransaction(TransactionalManagerCallback<T> callback) throws Throwable;

    @FunctionalInterface
    interface TransactionalManagerCallback<T> {
        T apply() throws Throwable;
    }
}
