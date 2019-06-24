package com.mini.dao.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Objects;

/**
 * 事务拦截器实现
 * @author xchao
 */
public final class TransactionInterceptor implements MethodInterceptor {

    private TransactionManager txManager;

    /**
     * Sets the value of txManager.
     * @param txManager The value of txManager
     * @return {@Code #this}
     */
    @Inject
    public TransactionInterceptor setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
        return this;
    }

    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Objects.requireNonNull(txManager, "Inject TxManager Error!");
        return txManager.invoke(invocation);
    }
}
