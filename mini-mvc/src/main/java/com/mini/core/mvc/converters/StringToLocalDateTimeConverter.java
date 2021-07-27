package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static java.lang.Long.parseLong;
import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;

@ConfigurationPropertiesBinding
public class StringToLocalDateTimeConverter extends AbstractDateConverter<LocalDateTime> {

    @Override
    public LocalDateTime convert(@NotNull String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        try {
            var format = ofPattern(getDateTimeFormat());
            return LocalDateTime.parse(source, format);
        } catch (DateTimeParseException ignored) {
        }
        var instant = ofEpochMilli(parseLong(source));
        return ofInstant(instant, systemDefault());
    }
}
