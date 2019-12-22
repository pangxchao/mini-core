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
public final class JtaTransManager implements TransManager {
    private static final TransactionThreadLocal COUNT = new TransactionThreadLocal();
    private final Provider<UserTransaction> provider;

    @Inject
    public JtaTransManager(@Nonnull Provider<UserTransaction> provider) {
        this.provider = provider;
    }

    @Override
    public <T> T open(TransManagerCallback<T> callback) throws Throwable {
        boolean commit = false;
        try {
            // 第一次开启事务
            if (COUNT.getCount() <= 0) {
                provider.get().begin();
            }
            // 事务计数 +1 并保存到当前线程中
            COUNT.requestedCount();
            // 调用目标方法，获取返回结果
            T result = callback.apply();
            // 事务正常
            commit = true;
            // 返回
            return result;
        } finally {
            // 事务计数 -1 并保存到当前线程中
            COUNT.releasedCount();
            // 事务异常时，回滚事务
            if (!commit) {
                try {
                    provider.get().rollback();
                } catch (Exception ignored) {}
            }
            // 所有事务完成
            if (COUNT.getCount() <= 0) {
                boolean rollback = true;
                try {
                    if (commit) {
                        provider.get().commit();
                        rollback = false;
                    }
                } finally {
                    if (rollback) {
                        provider.get().rollback();
                    }
                }
            }
        }
    }

    private static final class TransactionThreadLocal extends ThreadLocal<Integer> {
        @Override
        protected Integer initialValue() {
            return 0;
        }

        @Nonnull
        @Override
        public synchronized Integer get() {
            return super.get();
        }

        public synchronized int getCount() {
            return this.get();
        }

        public synchronized void requestedCount() {
            this.set(this.getCount() + 1);
        }

        public synchronized void releasedCount() {
            this.set(this.getCount() - 1);
        }
    }
}
