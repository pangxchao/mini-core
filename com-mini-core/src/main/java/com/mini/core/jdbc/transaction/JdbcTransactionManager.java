package com.mini.core.jdbc.transaction;

import com.mini.core.jdbc.JdbcTemplate;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Iterator;
import java.util.List;

import static com.mini.core.util.ThrowsUtil.hidden;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;

/**
 * JDBC 事务实现
 * @author xchao
 */
@Singleton
public final class JdbcTransactionManager implements TransactionManager {
	private final List<JdbcTemplate> jdbcTemplateList;
	
	@Inject
	public JdbcTransactionManager(@Nonnull List<JdbcTemplate> jdbcTemplateList) {
		this.jdbcTemplateList = jdbcTemplateList;
	}
	
	@Override
	public <T> T open(TransactionManagerCallback<T> callback) throws Throwable {
		return trans(jdbcTemplateList.iterator(), callback);
	}
	
	private <T> T trans(Iterator<JdbcTemplate> iter, TransactionManagerCallback<T> call) throws Throwable {
		return iter.hasNext() ? iter.next().transaction((trans) -> {
			trans.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
			boolean commit = false;
			try {
				trans.startTransaction();
				T t = trans(iter, call);
				commit = true;
				return t;
			} catch (Throwable e) {
				throw hidden(e);
			} finally {
				trans.endTransaction(commit);
			}
		}) : call.apply();
	}
}
