package com.mini.core.mvc.validation;

import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.Nonnull;

public class MiniMethodArgumentException extends MethodArgumentNotValidException {
    private final MiniValidationBindException e;

    public MiniMethodArgumentException(MethodArgumentNotValidException exception) {
        super(exception.getParameter(), exception.getBindingResult());
        e = new MiniValidationBindException(getBindingResult());
    }

    @Nonnull
    public final String getFirstErrorMessage() {
        return e.getFirstErrorMessage();
    }
}
