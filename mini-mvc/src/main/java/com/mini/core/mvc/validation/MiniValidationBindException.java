package com.mini.core.mvc.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

import static java.util.Optional.of;

public class MiniValidationBindException extends BindException {

    public MiniValidationBindException(@Nonnull BindingResult bindingResult) {
        super(bindingResult);
    }

    public MiniValidationBindException(@Nonnull BindException exception) {
        super(exception.getBindingResult());
    }

    @Nonnull
    public final String getFirstErrorMessage() throws RuntimeException, Error {
        return of(getBindingResult()).map(Errors::getFieldErrors).map(List::iterator)
                .filter(Iterator::hasNext).map(Iterator::next)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(it -> !it.isBlank()).orElse("Bad Request");
    }
}
