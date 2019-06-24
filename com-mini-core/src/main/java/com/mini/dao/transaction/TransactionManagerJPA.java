package com.mini.dao.transaction;

import com.mini.dao.IDao;
import com.mini.dao.IDaoTemplate;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;

/**
 * JPA 事务实现
 * @author xchao
 */
public final class TransactionManagerJPA implements TransactionManager {

    private final IDaoTemplate daoTemplate;

    @Inject
    public TransactionManagerJPA(IDaoTemplate daoTemplate) {
        this.daoTemplate = daoTemplate;
    }

    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        try (IDao dao = daoTemplate.getDao()) {
            try {
                dao.setAutoCommit(false);
                dao.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);
                Object result = invocation.proceed();
                dao.commit();
                return result;
            } catch (Exception | Error e) {
                dao.rollback();
                throw e;
            } finally {
                dao.setAutoCommit(true);
            }
        }

    }
}
