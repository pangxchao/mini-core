package com.mini.core.holder.model;

import java.lang.annotation.*;

/**
 * 指定哪些字段需要在指定参数构造器中出现
 * @author xchao
 * @see ConstructorArgsReq
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface ConstructorArgsReqField {
}
