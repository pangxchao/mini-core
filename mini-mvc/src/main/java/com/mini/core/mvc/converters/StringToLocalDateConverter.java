package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.lang.Long.parseLong;
import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;

@ConfigurationPropertiesBinding
public class StringToLocalDateConverter extends AbstractDateConverter<LocalDate> {

    @Override
    public LocalDate convert(@NotNull String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        try {
            var format = ofPattern(getDateFormat());
            return LocalDate.parse(source, format);
        } catch (DateTimeParseException ignored) {
        }
        var instant = ofEpochMilli(parseLong(source));
        return ofInstant(instant, systemDefault());
    }
}
