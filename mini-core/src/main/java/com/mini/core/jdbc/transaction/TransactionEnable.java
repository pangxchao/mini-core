package com.mini.core.jdbc.transaction;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionEnable {
}
