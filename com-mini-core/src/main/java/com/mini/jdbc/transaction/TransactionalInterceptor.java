package com.mini.jdbc.transaction;

import java.util.Objects;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Singleton
public final class TransactionalInterceptor implements MethodInterceptor {
	private TransactionalManager transactionManager;

	/**
	 * The value of transactionManager
	 * @param transactionManager The value of transactionManager
	 */
	@Inject
	public void setTransactionManager(@Nullable TransactionalManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Objects.requireNonNull(transactionManager, "TransactionManager can not be null");
		return transactionManager.openTransaction(invocation::proceed);
	}
}
