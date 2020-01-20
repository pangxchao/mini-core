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
public final class JdbcTransManager implements TransManager {
	private final List<JdbcTemplate> jdbcTemplateList;
	
	@Inject
	public JdbcTransManager(@Nonnull List<JdbcTemplate> jdbcTemplateList) {
		this.jdbcTemplateList = jdbcTemplateList;
	}
	
	@Override
	public <T> T open(TransManagerCallback<T> callback) throws Throwable {
		return trans(jdbcTemplateList.iterator(), callback);
	}
	
	private <T> T trans(Iterator<JdbcTemplate> iter, TransManagerCallback<T> call) throws Throwable {
		return iter.hasNext() ? iter.next().transaction((trans) -> {
			trans.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
			boolean commit = false;
			try {
				// 开始事务并保存一个回滚点
				trans.startTransaction();
				trans.setSavepoint();
				
				// 调用下一个事物或者目标方法
				T t = trans(iter, call);
				commit = true;
				return t;
			} catch (Throwable e) {
				throw hidden(e);
			} finally {
				// 结束当前事务 (true-提交)
				trans.endTransaction(commit);
			}
		}) : call.apply();
	}
}
