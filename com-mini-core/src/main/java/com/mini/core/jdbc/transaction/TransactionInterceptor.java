package com.mini.core.jdbc.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Objects.requireNonNull;

@Singleton
public final class TransactionInterceptor implements MethodInterceptor {
	private TransactionManager transactionManager;
	
	@Inject
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {
		requireNonNull(transactionManager, "TransactionManager can not be null");
		return transactionManager.open(invocation::proceed);
	}
}
