package com.mini.inject.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Implemented {
    /**
     * 实现于哪个类
     * @return 实现于哪个类
     */
    Class<?> value();

    /**
     * 注入名称
     * @return 注入名称
     */
    String name();

}
