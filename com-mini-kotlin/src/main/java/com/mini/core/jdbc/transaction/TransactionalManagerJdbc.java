package com.mini.core.jdbc.transaction;

import com.mini.core.jdbc.JdbcTemplate;
import com.mini.core.logger.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Iterator;
import java.util.List;

import static com.mini.core.logger.LoggerFactory.getLogger;

/**
 * JDBC 事务实现
 * @author xchao
 */
@Singleton
public final class TransactionalManagerJdbc implements TransactionalManager {
    private static final Logger logger = getLogger(TransactionalManagerJdbc.class);
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
        return iterator.hasNext() ? iterator.next().connection(connection -> {
            // 第一次开启事务
            boolean setAutoCommit = false;
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
                setAutoCommit = true;
            }
            // 事务执行阶段
            Savepoint savepoint = null;
            boolean commit = false;
            T result;
            try {
                // 设置回滚点
                savepoint = connection.setSavepoint();
                // 调用目标方法
                result = transaction(iterator, callback);
                // 提交事务
                commit = true;
            } finally {
                // 不提交的情况，回滚事务到设置的回滚点
                if (!commit && savepoint != null) {
                    try {
                        connection.rollback(savepoint);
                    } catch (SQLException e) {
                        logger.error(e);
                    }
                }
                //  事务完成
                if (setAutoCommit) {
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
            return result;
        }) : callback.apply();
    }
}
