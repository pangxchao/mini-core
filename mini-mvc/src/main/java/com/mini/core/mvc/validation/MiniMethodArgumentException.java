package com.mini.core.mvc.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.MethodArgumentNotValidException;


public class MiniMethodArgumentException extends MethodArgumentNotValidException {
    private final MiniValidationBindException e;

    public MiniMethodArgumentException(MethodArgumentNotValidException exception) {
        super(exception.getParameter(), exception.getBindingResult());
        e = new MiniValidationBindException(getBindingResult());
    }

    @NotNull
    public final String getFirstErrorMessage() {
        return e.getFirstErrorMessage();
    }
}
