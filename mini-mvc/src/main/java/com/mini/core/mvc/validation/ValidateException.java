package com.mini.core.mvc.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ValidateException extends RuntimeException {
    private final HttpStatus status;
    private final Integer code;
    private final Object[] args;

    public ValidateException(String message, HttpStatus status, Integer code, Object[] args) {
        super(message);
        this.status = status;
        this.code = code;
        this.args = args;
    }

    public final String getMessage(@Nullable MessageSource source, @Nullable Locale locale) {
        final String message = Optional.ofNullable(getMessage()).orElse("Bad Request");
        if (source == null || !message.startsWith("{") || !message.endsWith("}")) {
            return message;
        }
        locale = locale == null ? Locale.getDefault() : locale;
        return source.getMessage(message, args, locale);
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
}
