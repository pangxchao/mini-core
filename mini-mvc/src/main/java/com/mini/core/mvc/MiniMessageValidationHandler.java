package com.mini.core.mvc;

import com.mini.core.mvc.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Locale;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@ControllerAdvice
public class MiniMessageValidationHandler {
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @SuppressWarnings("SameParameterValue")
    protected <T extends Exception> void handler(T exception, String message, HttpStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, status.value());
        request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception);
        request.setAttribute(RequestDispatcher.ERROR_MESSAGE, message);
        var dispatcher = request.getRequestDispatcher("/error");
        dispatcher.forward(request, response);
    }

    @ExceptionHandler(value = {ValidateException.class})
    public void validate(ValidateException exception, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
        handler(requireNonNull(exception), exception.getMessage(messageSource, locale), exception.getStatus(), request, response);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public void constraint(ConstraintViolationException exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MiniConstraintViolationException e = new MiniConstraintViolationException(requireNonNull(exception));
        handler(exception, e.getFirstErrorMessage(), HttpStatus.BAD_REQUEST, request, response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public void method(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MiniMethodArgumentException e = new MiniMethodArgumentException(Objects.requireNonNull(exception));
        handler(exception, e.getFirstErrorMessage(), HttpStatus.BAD_REQUEST, request, response);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public void validation(ValidationException exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final MiniValidationException e = new MiniValidationException(Objects.requireNonNull(exception));
        handler(exception, e.getErrorMessage(), HttpStatus.BAD_REQUEST, request, response);
    }

    @ExceptionHandler(value = {BindException.class})
    public void bind(BindException exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MiniValidationBindException e = new MiniValidationBindException(requireNonNull(exception));
        handler(exception, e.getMessage(), HttpStatus.BAD_REQUEST, request, response);
    }
}
