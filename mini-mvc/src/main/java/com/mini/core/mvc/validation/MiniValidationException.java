package com.mini.core.mvc.validation;

import javax.annotation.Nonnull;
import javax.validation.ValidationException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class MiniValidationException extends ValidationException {
    public MiniValidationException(@Nonnull ValidationException exception) {
        super(exception.getMessage(), exception.getCause());
    }

    @Nonnull
    public final String getErrorMessage() throws RuntimeException {
        return ofNullable(getMessage()).orElse("Bad Request");
    }
}
