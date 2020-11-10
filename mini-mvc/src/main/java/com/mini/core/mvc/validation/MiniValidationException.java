package com.mini.core.mvc.validation;

import org.jetbrains.annotations.NotNull;

import javax.validation.ValidationException;

import static java.util.Optional.ofNullable;

public class MiniValidationException extends ValidationException {
    public MiniValidationException(@NotNull ValidationException exception) {
        super(exception.getMessage(), exception.getCause());
    }

    @NotNull
    public final String getErrorMessage() throws RuntimeException {
        return ofNullable(getMessage()).orElse("Bad Request");
    }
}
