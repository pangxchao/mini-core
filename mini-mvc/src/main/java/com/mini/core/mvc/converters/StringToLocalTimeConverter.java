package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static java.lang.Long.parseLong;
import static java.time.Instant.ofEpochMilli;
import static java.time.LocalTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;

@ConfigurationPropertiesBinding
public class StringToLocalTimeConverter extends AbstractDateConverter<LocalTime> {

    @Override
    public LocalTime convert(@NotNull String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        try {
            var format = ofPattern(getDateTimeFormat());
            return LocalTime.parse(source, format);
        } catch (DateTimeParseException ignored) {
        }
        var instant = ofEpochMilli(parseLong(source));
        return ofInstant(instant, systemDefault());
    }
}
