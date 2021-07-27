package com.mini.core.mvc.converters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nonnull;

import static java.util.Optional.ofNullable;

public abstract class AbstractDateConverter<T> implements Converter<String, T> {
    private WebMvcProperties webMvcProperties;

    @Autowired(required = false)
    public void setWebMvcProperties(WebMvcProperties webMvcProperties) {
        this.webMvcProperties = webMvcProperties;
    }

    @Nonnull
    public final String getDateTimeFormat() {
        return ofNullable(webMvcProperties).map(WebMvcProperties::getFormat)
                .map(WebMvcProperties.Format::getDateTime)
                .orElse("yyyy-MM-dd HH[:mm[:ss]]");
    }

    @Nonnull
    public final String getDateFormat() {
        return ofNullable(webMvcProperties).map(WebMvcProperties::getFormat)
                .map(WebMvcProperties.Format::getDateTime)
                .orElse("yyyy-MM-dd");
    }

    @Nonnull
    public final String getTimeFormat() {
        return ofNullable(webMvcProperties).map(WebMvcProperties::getFormat)
                .map(WebMvcProperties.Format::getDateTime)
                .orElse("HH[:mm[:ss]]");
    }
}
