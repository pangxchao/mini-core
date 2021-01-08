package com.mini.core.mvc.converters;


import org.springframework.core.convert.converter.Converter;

public abstract class AbstractDateConverter<T> implements Converter<String, T> {
    private final DataConvertersFormat format;

    public AbstractDateConverter(DataConvertersFormat convertersFormat) {
        this.format = convertersFormat;
    }

    protected String getDateTimeFormat() {
        return format.getDateTimeFormat();
    }

    protected String getDateFormat() {
        return format.getDateFormat();
    }

    protected String getTimeFormat() {
        return format.getTimeFormat();
    }
}
