package com.mini.jdbc.transaction;

import com.mini.callback.ConnectionCallback;
import com.mini.jdbc.JdbcTemplate;

import javax.inject.Singleton;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Iterator;
import java.util.List;

/**
 * JDBC 事务实现
 * @author xchao
 */
@Singleton
public final class TransactionalManagerJdbc implements TransactionalManager {
    private final List<JdbcTemplate> jdbcTemplateList;

    public TransactionalManagerJdbc(List<JdbcTemplate> jdbcTemplateList) {
        this.jdbcTemplateList = jdbcTemplateList;
    }

    @Override
    public <T> T openTransaction(TransactionalManagerCallback<T> callback) throws Throwable {
        if (jdbcTemplateList == null) return callback.apply();
        return openTransaction(jdbcTemplateList.iterator(), callback);
    }

    private <T> T openTransaction(Iterator<JdbcTemplate> iterator, TransactionalManagerCallback<T> callback) throws Throwable {
        return iterator.hasNext() ? iterator.next().execute((ConnectionCallback<T>) connection -> {
            // 定义回滚点
            Savepoint point = null;
            try {
                // 设置不自动提交，并设置回调点
                connection.setAutoCommit(false);
                point = connection.setSavepoint();

                // 开启下一个数据连接池的事务
                T result = openTransaction(iterator, callback);

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
