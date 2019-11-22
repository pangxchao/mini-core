package com.mini.web.annotation;

import java.lang.annotation.*;

/**
 * 拦截器清除
 * @author xchao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Clear {
    Target value() default Target.ALL;

    enum Target {
        METHOD,
        TYPE,
        ALL
    }
}
