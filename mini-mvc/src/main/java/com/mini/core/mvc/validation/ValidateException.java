package com.mini.core.mvc.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ValidateException extends RuntimeException {
    private final HttpStatus status;
    private final Object[] args;
    private final Integer code;
    private final String field;

    public ValidateException(String message, HttpStatus status, Integer code, Object[] args, String field) {
        super(message);
        this.status = status;
        this.field = field;
        this.code = code;
        this.args = args;
    }

    @NotNull
    public final HttpStatus getStatus() {
        if (this.status == null) {
            return BAD_REQUEST;
        }
        return status;
    }

    public final Object[] getArgs() {
        return args;
    }

    public final Integer getCode() {
        return code;
    }

    public final String getField() {
        return field;
    }
}
