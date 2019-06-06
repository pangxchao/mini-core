package com.mini.util.ioc.annotation;

import java.lang.annotation.*;

/**
 * 指定Bean实现类
 * @author xchao
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    /**
     * Bean名称
     * @return 名称
     */
    String value() default "";
}
