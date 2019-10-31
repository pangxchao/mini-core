package com.mini.web.annotation;

import com.mini.web.model.factory.ModelType;

import java.lang.annotation.*;

import static com.mini.web.model.factory.ModelType.PAGE;

/**
 * 控制器方法注解
 * @author xchao
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /**
     * 返回数据类型
     * @return 数据类型
     */
    ModelType value() default PAGE;

    /**
     * 访问地址
     * @return 访问地址
     */
    String[] url() default {};

    /**
     * 视图路径
     * @return 视图路径
     */
    String path() default "";

    /**
     * 默认访问路径的后缀
     * @return 访问路径的后缀
     */
    String suffix() default ".htm";

    /**
     * 该方法支持的请求类型
     * @return 请求类型数组
     */
    Method[] method() default {
            Method.GET,
            Method.POST,
            Method.PUT,
            Method.DELETE
    };

    /**
     * 方法类型
     * @author xchao
     */
    enum Method {
        GET, HEAD, POST, PUT, DELETE, OPTIONS, TRACE
    }
}
