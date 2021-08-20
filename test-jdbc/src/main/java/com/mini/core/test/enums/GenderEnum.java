package com.mini.core.test.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mini.core.mvc.enums.IEnum;

public enum GenderEnum implements IEnum {
    UN_KNOW(0),
    GIRL(2),
    BOY(1),
    ;

    @JsonValue
    @EnumValue
    private final int value;

    GenderEnum(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
