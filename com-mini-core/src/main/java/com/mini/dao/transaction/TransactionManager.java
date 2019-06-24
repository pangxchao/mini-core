package com.mini.dao.transaction;

import org.aopalliance.intercept.MethodInvocation;

public interface TransactionManager {
     Object invoke(MethodInvocation invocation) throws Throwable ;
}
