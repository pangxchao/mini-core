package com.mini.core.jdbc.transaction;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
}
