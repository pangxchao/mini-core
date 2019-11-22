package com.mini.core.jdbc.transaction;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.transaction.UserTransaction;

/**
 * JTA 事务实现
 * @author xchao
 */
@Singleton
public final class TransactionalManagerJTA implements TransactionalManager {
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();
    private final Provider<UserTransaction> provider;

    @Inject
    public TransactionalManagerJTA(@Nonnull Provider<UserTransaction> provider) {
        this.provider = provider;
    }

    @Override
    public <T> T openTransaction(TransactionalManagerCallback<T> callback) throws Throwable {
        try {
            // 开启事务
            boolean setAutoCommit = false;
            if (THREAD_LOCAL.get() == null) {
                provider.get().begin();
                THREAD_LOCAL.set(true);
                setAutoCommit = true;
            }
            // 调用事务目标
            T result = callback.apply();
            // 如果最后一次退出事务管理，提交事务
            if (setAutoCommit) {
                provider.get().commit();
                THREAD_LOCAL.set(null);
            }
            // 返回
            return result;
        } catch (Throwable e) {
            // 程序异常时，回滚事务
            if (provider.get() != null) {
                provider.get().rollback();
            }
            throw e;
        }
    }
}
