package com.mini.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

/**
 * 控制器类注解
 * @author xchao
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

    @AliasFor(annotation = Component.class)
    String value() default "";

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
