package com.mini.core.mvc.enums;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

public class ValueToIEnumConvertFactory implements ConverterFactory<String, IEnum> {
    @NotNull
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(@NotNull Class<T> targetType) {
        return new ValueToIEnumConvert<>(targetType);
    }

    public static class ValueToIEnumConvert<T extends IEnum> implements Converter<String, T> {
        private final Class<T> enumClass;

        public ValueToIEnumConvert(Class<T> enumClass) {
            this.enumClass = enumClass;
        }

        @Override
        public final T convert(@NotNull String source) {
            if (!StringUtils.hasText(source)) {
                return null;
            }
            // 读取枚举值生成枚举对象
            final int value = Integer.parseInt(source);
            for (T enumInstance : enumClass.getEnumConstants()) {
                if (value == enumInstance.getValue()) {
                    return enumInstance;
                }
            }
            // 枚举值错误，无法创建枚举对象
            var message = "No enum value " + enumClass.getCanonicalName() + "." + value;
            throw new IllegalArgumentException(message);
        }
    }

}
