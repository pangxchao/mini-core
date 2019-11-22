package com.mini.core.jdbc.transaction;

import com.mini.core.jdbc.JdbcTemplate;
import com.mini.core.jdbc.JdbcTemplate.ConnectionCallback;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JDBC 事务实现
 * @author xchao
 */
@Singleton
public final class TransactionalManagerJdbc implements TransactionalManager {
    private static final TransactionThreadLocal COUNT = new TransactionThreadLocal();
    private final List<JdbcTemplate> jdbcTemplateList;

    @Inject
    public TransactionalManagerJdbc(@Nonnull List<JdbcTemplate> jdbcTemplateList) {
        this.jdbcTemplateList = jdbcTemplateList;
    }

    @Override
    public <T> T openTransaction(TransactionalManagerCallback<T> callback) throws Throwable {
        return transaction(jdbcTemplateList.iterator(), callback);
    }

    private <T> T transaction(Iterator<JdbcTemplate> iterator, TransactionalManagerCallback<T> callback) throws Throwable {
        return iterator.hasNext() ? iterator.next().execute((ConnectionCallback<T>) connection -> {
            try {
                // 事务执行阶段
                Savepoint point = null;
                boolean commit = false;
                T result;
                try {
                    // 第一次开启事务
                    if (COUNT.getCount(connection) <= 0) {
                        connection.setAutoCommit(false);
                    }
                    // 事务计数 +1 并保存到当前线程中
                    COUNT.requestedCount(connection);
                    // 保存回滚点
                    point = connection.setSavepoint();
                    // 调用目标方法，获取返回结果
                    result = transaction(iterator, callback);
                    // 事务正常
                    commit = true;
                    // 返回结果
                    return result;
                } finally {
                    // 事务计数 -1 并保存到当前线程中
                    COUNT.releasedCount(connection);

                    // 回滚事务到设置的回滚点
                    if (!commit && point != null) {
                        try {
                            connection.rollback(point);
                        } catch (SQLException ignored) {}
                    }

                    // 所有事务完成
                    if (COUNT.getCount(connection) <= 0) {
                        boolean rollback = true;
                        try {
                            if (commit) {
                                connection.commit();
                                rollback = false;
                            }
                        } finally {
                            try {
                                if (rollback) {
                                    connection.rollback();
                                }
                            } finally {

                                connection.setAutoCommit(true);
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }) : callback.apply();
    }

    private static final class TransactionThreadLocal extends ThreadLocal<Map<Connection, Integer>> {
        @Override
        protected Map<Connection, Integer> initialValue() {
            return new ConcurrentHashMap<>();
        }

        @Nonnull
        @Override
        public Map<Connection, Integer> get() {
            return super.get();
        }

        public synchronized int getCount(Connection connection) {
            return this.get().getOrDefault(connection, 0);
        }

        public synchronized void requestedCount(Connection connection) {
            int count = this.get().getOrDefault(connection, 0);
            this.get().put(connection, ++count);
        }

        public synchronized void releasedCount(Connection connection) {
            int count = this.get().getOrDefault(connection, 0);
            this.get().put(connection, Math.max(0, --count));
        }
    }
}
