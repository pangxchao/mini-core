package com.mini.core.jdbc.transaction;

import com.mini.core.jdbc.JdbcTemplate;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Iterator;
import java.util.List;

import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;

/**
 * JDBC 事务实现
 * @author xchao
 */
@Singleton
public final class JdbcTransManager implements TransManager {
	private final List<JdbcTemplate> jdbcTemplateList;

	@Inject
	public JdbcTransManager(@Nonnull List<JdbcTemplate> jdbcTemplateList) {
		this.jdbcTemplateList = jdbcTemplateList;
	}

	@Override
	public <T> T open(TransManagerCallback<T> callback) throws Throwable {
		return transaction(jdbcTemplateList.iterator(), callback);
	}

	private <T> T transaction(Iterator<JdbcTemplate> iterator, TransManagerCallback<T> callback) throws Throwable {
		return iterator.hasNext() ? iterator.next().transaction((trans) -> {
			trans.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
			boolean commit = false;
			try {
				// 开始事务并保存一个回滚点
				trans.startTransaction();
				trans.setSavepoint();

				// 调用下一个事物或者目标方法
				T t = transaction(iterator, callback);
				commit = true;
				return t;
			} finally {
				trans.endTransaction(commit);
			}
		}) : callback.apply();
	}
}
