package com.mini.core.mvc.converters;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;

import static java.time.Instant.ofEpochMilli;

@ConfigurationPropertiesBinding
public class StringToInstantConverter implements Converter<String, Instant> {

    @Override
    public Instant convert(@NotNull String source) {
        return ofEpochMilli(Long.parseLong(source));
    }
}
