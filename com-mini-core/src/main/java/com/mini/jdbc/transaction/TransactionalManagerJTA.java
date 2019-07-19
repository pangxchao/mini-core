package com.mini.jdbc.transaction;

import com.mini.callback.ConnectionCallback;
import com.mini.jdbc.JdbcTemplate;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.transaction.UserTransaction;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * JPA 事务实现
 * @author xchao
 */
@Singleton
public final class TransactionalManagerJTA implements TransactionalManager {
    private final Provider<UserTransaction> userTransactionProvider;
    private final List<JdbcTemplate> jdbcTemplateList;

    public TransactionalManagerJTA(Provider<UserTransaction> userTransactionProvider, List<JdbcTemplate> jdbcTemplateList) {
        this.userTransactionProvider = userTransactionProvider;
        this.jdbcTemplateList        = jdbcTemplateList;
    }

    private Iterator<JdbcTemplate> getIterator() {
        return jdbcTemplateList.iterator();
    }

    @Override
    public <T> T open(TransactionalManagerCallback<T> callback) throws Throwable {
        UserTransaction userTransaction = null;
        try {
            if (jdbcTemplateList == null) {
                return callback.apply();
            }
            Objects.requireNonNull(userTransactionProvider);
            userTransaction = userTransactionProvider.get();
            userTransaction.begin();
            T r = open(getIterator(), callback);
            userTransaction.commit();
            return r;
        } catch (Exception | Error e) {
            if (userTransaction != null) {
                userTransaction.rollback();
            }
            throw e;
        }
    }

    private <T> T open(Iterator<JdbcTemplate> iterator, TransactionalManagerCallback<T> callback) throws Throwable {
        return iterator.hasNext() ? iterator.next().execute((ConnectionCallback<T>) connection -> {
            // 定义回滚点
            Savepoint point = null;
            try {
                // 设置不自动提交，并设置回调点
                connection.setAutoCommit(false);
                point = connection.setSavepoint();

                // 开启下一个数据连接池的事务
                T result = open(iterator, callback);

                // 提交事务并返回
                connection.commit();
                return result;
            } catch (Throwable e) {
                if (point == null) {
                    connection.rollback();
                    throw new SQLException(e);
                }
                connection.rollback(point);
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        }) : callback.apply();
    }
}
