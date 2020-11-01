package com.mini.core.mvc.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ValidateException extends RuntimeException {
    private final HttpStatus status;

    public ValidateException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    @NotNull
    public final HttpStatus getStatus() {
        if (this.status == null) {
            return BAD_REQUEST;
        }
        return status;
    }
}
