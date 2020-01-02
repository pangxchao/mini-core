package com.mini.core.jdbc.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Objects.requireNonNull;

@Singleton
public final class TransInterceptor implements MethodInterceptor {
	private TransManager transManager;

	/**
	 * The value of transManager
	 * @param transManager The value of transManager
	 */
	@Inject
	public void setTransManager(@Nullable TransManager transManager) {
		this.transManager = transManager;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		requireNonNull(transManager, "TransManager can not be null");
		return transManager.open(invocation::proceed);
	}
}
