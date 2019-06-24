package com.mini.dao.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface DBMapping {
    String value() default "";

    DES des() default DES.NONE;

    enum DES {
        NONE(1), PK(1), FK(2), PFK(3);
        private final int value;

        DES(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;

        }
    }
}
