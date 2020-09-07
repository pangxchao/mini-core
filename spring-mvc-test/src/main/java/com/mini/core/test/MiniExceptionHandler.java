package com.mini.core.test;

import com.mini.core.mvc.GlobalExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Locale;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class MiniExceptionHandler extends GlobalExceptionHandler {

    @Override
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return super.methodArgumentNotValidException(exception);
    }

    @Override
    @ResponseBody
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException exception) {
        return super.constraintViolationExceptionHandler(exception);
    }

    @Override
    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<String> validationExceptionHandler(ValidationException exception, Locale locale) {
        return super.validationExceptionHandler(exception, locale);
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> exceptionHandler(Exception exception, Locale locale) {
        System.out.println("----------------------------------------------");
        exception.printStackTrace();
        return status(BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<String> bindExceptionHandler(BindException exception, Locale locale) {
        System.out.println("----------------------------------------------");
        exception.printStackTrace();
//        isAjaxRequest
        return status(BAD_REQUEST).body(exception.getMessage());
    }
}
