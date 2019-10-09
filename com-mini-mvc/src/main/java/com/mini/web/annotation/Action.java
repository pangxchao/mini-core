package com.mini.web.annotation;

import java.lang.annotation.*;

import com.mini.web.model.IModel;
import com.mini.web.model.PageModel;

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
    Class<? extends IModel<?>> value() //
            default PageModel.class;

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
