package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.Long.parseLong;
import static java.time.format.DateTimeFormatter.ofPattern;

@ConfigurationPropertiesBinding
public class StringToJavaUtilDateConverter extends AbstractDateConverter<java.util.Date> {

    @Override
    public java.util.Date convert(@NotNull String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        try {
            DateTimeFormatter format = ofPattern(getDateTimeFormat());
            LocalDateTime date = LocalDateTime.parse(source, format);
            return java.sql.Timestamp.valueOf(date);
        } catch (DateTimeParseException ignored) {
        }
        try {
            DateTimeFormatter format = ofPattern(getDateTimeFormat());
            LocalDate date = LocalDate.parse(source, format);
            return java.sql.Date.valueOf(date);
        } catch (DateTimeParseException ignored) {
        }
        return new java.util.Date(parseLong(source));
    }
}
