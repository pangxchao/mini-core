package com.mini.core.mvc.validation;

import com.mini.core.mvc.validation.ValidatorBuilder.ValidatorBuilderImpl;
import org.springframework.http.HttpStatus;

public final class ValidatorUtil {
    public static ValidatorBuilder status(HttpStatus status) {
        return new ValidatorBuilderImpl().status(status);
    }

    public static ValidatorBuilder message(String message) {
        return new ValidatorBuilderImpl().message(message);
    }

    public static ValidatorBuilder code(Integer code) {
        return new ValidatorBuilderImpl().code(code);
    }
}