package com.mini.core.mvc.enums;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class NumberToIEnumConvertFactory implements ConverterFactory<Number, IEnum> {
    @NotNull
    @Override
    public <T extends IEnum> Converter<Number, T> getConverter(@NotNull Class<T> targetType) {
        return new NumberToIEnumConvert<>(targetType);
    }

    private static class NumberToIEnumConvert<T extends IEnum> implements Converter<Number, T> {
        private final Class<T> enumClass;

        public NumberToIEnumConvert(Class<T> enumClass) {
            this.enumClass = enumClass;
        }

        @Override
        public final T convert(@NotNull Number source) {
            final int value = source.intValue();
            for (T enumInstance : enumClass.getEnumConstants()) {
                if (value == enumInstance.getValue()) {
                    return enumInstance;
                }
            }
            throw new IllegalArgumentException("No enum value " + enumClass.getCanonicalName() + "." + value);
        }
    }

}
