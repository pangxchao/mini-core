package com.mini.web.annotation;

import java.lang.annotation.*;

import javax.annotation.Nonnull;

/**
 * 控制器类注解
 * @author xchao
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    /**
     * 访问地址
     * @return 访问地址
     */
    @Nonnull
    String url() default "";

    /**
     * 视图路径
     * @return 视图路径
     */
    @Nonnull
    String path() default "";
}
