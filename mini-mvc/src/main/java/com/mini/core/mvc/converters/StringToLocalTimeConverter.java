package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static java.lang.Long.parseLong;
import static java.time.Instant.ofEpochMilli;
import static java.time.LocalTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;

@ConfigurationPropertiesBinding
public class StringToLocalTimeConverter extends AbstractDateConverter<LocalTime> {
    public StringToLocalTimeConverter(DataConvertersFormat convertersFormat) {
        super(convertersFormat);
    }

    @Override
    public LocalTime convert(@NotNull String source) {
        try {
            var format = ofPattern(getDateTimeFormat());
            return LocalTime.parse(source, format);
        } catch (DateTimeParseException ignored) {
        }
        var instant = ofEpochMilli(parseLong(source));
        return ofInstant(instant, systemDefault());
    }
}
