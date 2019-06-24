package com.mini.inject;

import com.google.inject.Binder;
import com.mini.dao.annotation.Transaction;
import com.mini.dao.transaction.TransactionInterceptor;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public final class TransactionModule extends MiniModule {
    @Override
    protected void configures(Binder binder) throws Error {
        bindInterceptor(new TransactionInterceptor());
    }

    // 绑定事务拦截器
    private void bindInterceptor(TransactionInterceptor interceptor) throws Error {
        bindInterceptor(any(), annotatedWith(Transaction.class), interceptor);
    }
}
