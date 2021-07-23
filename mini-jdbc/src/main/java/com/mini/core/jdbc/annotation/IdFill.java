package com.mini.core.jdbc.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdFill {
    /**
     * ID自动填充方式
     *
     * @return 填充方式
     */
    FillType value() default FillType.INPUT;

    enum FillType {
        AUTO, INPUT, UUID, MINI
    }
}
