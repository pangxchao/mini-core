package com.mini.core.mvc.validation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ValidateException extends RuntimeException {
    private final HttpStatus status;
    private final Object[] args;
    private final Integer code;

    public ValidateException(String message, HttpStatus status, Integer code, Object[] args) {
        super(message);
        this.status = status;
        this.code = code;
        this.args = args;
    }

    public ValidateException(String message, HttpStatus status) {
        this(message, status, null, new Object[0]);
    }

    public ValidateException(Set<ConstraintViolation<?>> set, HttpStatus status) {
        this(ofNullable(set).map(Set::iterator).filter(Iterator::hasNext)
                .map(Iterator::next).map(ConstraintViolation::getMessage)
                .filter(message -> !message.isBlank())
                .orElse("Bad Request"), status);
    }

    public ValidateException(BindingResult bindingResult, HttpStatus status) {
        this(ofNullable(bindingResult).map(Errors::getAllErrors).map(List::iterator)
                .filter(Iterator::hasNext).map(Iterator::next)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Bad Request"), status);
    }

    public final String getMessage(@Nullable MessageSource source, Locale locale) {
        String msg = ofNullable(getMessage()).orElse("Bad Request").strip();
        if (source == null || !msg.startsWith("{") || !msg.endsWith("}")) {
            return msg;
        }
        String message = msg.substring(1, msg.length() - 1);
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
