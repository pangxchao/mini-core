package com.mini.jdbc.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

@Singleton
public final class TransactionalInterceptor implements MethodInterceptor {
    private TransactionalManager transactionManager;

    /**
     * The value of transactionManager
     * @param transactionManager The value of transactionManager
     */
    @Inject
    public void setTransactionManager(TransactionalManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Objects.requireNonNull(transactionManager, "TransactionManager can not be null");
        return transactionManager.open(invocation::proceed);
    }
}
