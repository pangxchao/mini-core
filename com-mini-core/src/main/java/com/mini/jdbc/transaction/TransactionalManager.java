package com.mini.jdbc.transaction;

@FunctionalInterface
public interface TransactionalManager {
    <T> T open(TransactionalManagerCallback<T> callback) throws Throwable;

    @FunctionalInterface
    interface TransactionalManagerCallback<T> {
        T apply() throws Throwable;
    }
}
