package com.mini.core.mvc.validation;

import com.mini.core.mvc.validation.ValidatorBuilder.ValidatorBuilderImpl;
import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;

public final class ValidatorUtil {
    @Nonnull
    public static ValidatorBuilder status(HttpStatus status) {
        return new ValidatorBuilderImpl().status(status);
    }

    @Nonnull
    public static ValidatorBuilder message(String message) {
        return new ValidatorBuilderImpl().message(message);
    }

    @Nonnull
    public static ValidatorBuilder code(Integer code) {
        return new ValidatorBuilderImpl().code(code);
    }

    @Nonnull
    public static ValidatorBuilder field(String field) {
        return new ValidatorBuilderImpl().field(field);
    }
}