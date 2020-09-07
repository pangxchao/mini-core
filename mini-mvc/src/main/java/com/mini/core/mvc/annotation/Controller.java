package com.mini.core.mvc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * 控制器类注解
 *
 * @author xchao
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    /**
     * 控制器名称
     *
     * @return 名称
     */
    @AliasFor(annotation = Component.class)
    String name() default "";

    /**
     * 访问地址
     *
     * @return 访问地址
     */
    @Nonnull
    String url() default "";

    /**
     * 视图路径
     *
     * @return 视图路径
     */
    @Nonnull
    String path() default "";
}
