package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.Long.parseLong;
import static java.time.format.DateTimeFormatter.ofPattern;

@ConfigurationPropertiesBinding
public class StringToJavaSqlDateConverter extends AbstractDateConverter<java.sql.Date> {
    public StringToJavaSqlDateConverter(DataConvertersFormat convertersFormat) {
        super(convertersFormat);
    }

    @Override
    public java.sql.Date convert(@NotNull String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        try {
            DateTimeFormatter format = ofPattern(getDateFormat());
            LocalDate date = LocalDate.parse(source, format);
            return java.sql.Date.valueOf(date);
        } catch (DateTimeParseException ignored) {
        }
        return new java.sql.Date(parseLong(source));
    }
}
