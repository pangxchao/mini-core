package com.mini.core.jdbc.transaction;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.UserTransaction;

import static com.mini.core.jdbc.JdbcAccessor.transaction;
import static com.mini.core.util.ThrowsUtil.hidden;
import static java.util.Objects.requireNonNull;

/**
 * JTA 事务实现
 * @author xchao
 */
@Singleton
public final class JtaTransactionManager implements TransactionManager {
	private final UserTransaction transaction;
	
	@Inject
	public JtaTransactionManager(@Nullable UserTransaction transaction) {
		this.transaction = transaction;
	}
	
	@Override
	public <T> T open(TransactionManagerCallback<T> callback) {
		var userTrans = requireNonNull(transaction);
		return transaction(userTrans, trans -> {
			try {
				boolean commit = false;
				try {
					trans.startTransaction();
					T t = callback.apply();
					commit = true;
					return t;
				} finally {
					trans.endTransaction(commit);
				}
			} catch (Throwable e) {
				throw hidden(e);
			}
		});
	}
}
