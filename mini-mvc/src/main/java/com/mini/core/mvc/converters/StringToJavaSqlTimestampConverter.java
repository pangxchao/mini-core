package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.Long.parseLong;
import static java.time.format.DateTimeFormatter.ofPattern;

@ConfigurationPropertiesBinding
public class StringToJavaSqlTimestampConverter extends AbstractDateConverter<java.sql.Timestamp> {
    public StringToJavaSqlTimestampConverter(DataConvertersFormat convertersFormat) {
        super(convertersFormat);
    }

    @Override
    public java.sql.Timestamp convert(@NotNull String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        try {
            DateTimeFormatter format = ofPattern(this.getDateTimeFormat());
            LocalDateTime date = LocalDateTime.parse(source, format);
            return java.sql.Timestamp.valueOf(date);
        } catch (DateTimeParseException ignored) {
        }
        return new java.sql.Timestamp(parseLong(source));
    }
}
