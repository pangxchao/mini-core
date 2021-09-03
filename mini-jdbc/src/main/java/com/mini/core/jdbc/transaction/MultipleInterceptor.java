package com.mini.core.jdbc.transaction;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;

@Aspect
@Component
public class MultipleInterceptor {
    private final ApplicationContext applicationContext;

    @Autowired
    public MultipleInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 环绕通知，
     *
     * @param proceedingJoinPoint 方法调用参数
     * @return 返回目标方法返回对象
     */
    @SuppressWarnings("DuplicatedCode")
    @Around(value = "@annotation(com.mini.core.jdbc.transaction.MultipleTransactional)")
    public Object transactionalMultiAspectAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        requireNonNull(proceedingJoinPoint, "TransactionMultiInterceptor Error: ProceedingJoinPoint is null");
        final MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        requireNonNull(methodSignature, "TransactionMultiInterceptor Error: MethodSignature is null");
        final Method method = requireNonNull(methodSignature.getMethod());
        // 获取目标方法上的 TransactionalMulti 注解
        var transactionalMulti = method.getAnnotation(MultipleTransactional.class);
        if (transactionalMulti == null) {
            return proceedingJoinPoint.proceed();
        }
        // 获取注解需要开启的事务的DataSourceTransactionManager对象列表
        var transactionManagerList = of(stream(transactionalMulti.value())
                .map(this::getBean).collect(Collectors.toList()))
                .filter(it -> it.size() > 0)
                .orElse(getBeanList());
        // 获取所有事务的状态
        final List<TransactionParam> paramList = transactionManagerList.stream().map(it -> {
            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            definition.setPropagationBehavior(transactionalMulti.propagation().value());
            definition.setIsolationLevel(transactionalMulti.isolation().value());
            return new TransactionParam(it, it.getTransaction(definition));
        }).collect(Collectors.toList());
        // 默认不提交
        boolean commit = false;
        final Object result;
        try {
            // 调用目标方法并获得返回值
            result = proceedingJoinPoint.proceed();
            commit = true;
        } finally {
            // 默认强制回滚
            boolean rollback = true;
            try {
                if (commit) {
                    paramList.forEach(it -> {
                        var status = it.getTransactionStatus();
                        it.getTransactionManager().commit(status);
                    });
                    rollback = false;
                }
            } finally {
                if (rollback) {
                    paramList.forEach(it -> {
                        try {
                            var status = it.getTransactionStatus();
                            it.getTransactionManager().rollback(status);
                        } catch (Throwable ignored) {
                        }
                    });
                }
            }
        }
        return result;
    }

    private PlatformTransactionManager getBean(String beanName) {
        final var mClass = PlatformTransactionManager.class;
        return applicationContext.getBean(beanName, mClass);
    }

    private List<PlatformTransactionManager> getBeanList() {
        final var mClass = PlatformTransactionManager.class;
        var arr = applicationContext.getBeansOfType(mClass);
        return new ArrayList<>(arr.values());
    }

    private static class TransactionParam {
        private final PlatformTransactionManager transactionManager;
        private final TransactionStatus transactionStatus;

        public TransactionParam(PlatformTransactionManager transactionManager, TransactionStatus transactionStatus) {
            this.transactionManager = transactionManager;
            this.transactionStatus = transactionStatus;
        }

        public PlatformTransactionManager getTransactionManager() {
            return transactionManager;
        }

        public TransactionStatus getTransactionStatus() {
            return transactionStatus;
        }
    }

}



