package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import com.mini.core.util.ThrowableKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class MiniMessageValidationHandler {
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 异常处理
     *
     * @param e        异常信息
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     * @param locale   区域信息
     */
    @SuppressWarnings("SameParameterValue")
    protected void handler(ValidateException e, HttpServletRequest request, HttpServletResponse response, Locale locale) {
        try {
            request.setAttribute(RequestDispatcher.ERROR_MESSAGE, e.getMessage(messageSource, locale));
            request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, e.getStatus().value());
            request.removeAttribute(DefaultErrorAttributes.class.getName() + ".ERROR");
            request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, e);
            var dispatcher = request.getRequestDispatcher("/error");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException exception) {
            throw ThrowableKt.hidden(exception);
        }
    }

    /**
     * 自定义异常
     *
     * @param exception 异常信息
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param locale    区域信息
     */
    @ExceptionHandler(value = {ValidateException.class})
    public void validate(ValidateException exception, HttpServletRequest request, HttpServletResponse response, Locale locale) {
        MiniMessageValidationHandler.this.handler(requireNonNull(exception), request, response, locale);
    }

    /**
     * 方法参数验证异常
     *
     * @param exception 异常信息
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param locale    区域信息
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public void constraint(ConstraintViolationException exception, HttpServletRequest request, HttpServletResponse response, Locale locale) {
        ofNullable(exception).map(ConstraintViolationException::getConstraintViolations).map(Set::iterator).filter(Iterator::hasNext) //
                .map(Iterator::next).ifPresentOrElse(it -> validate(new ValidateException(it, BAD_REQUEST), request,    //
                response, locale), () -> validate(new ValidateException("Bad Request", BAD_REQUEST, null),  //
                request, response, locale));
    }

    /**
     * 实体验证异常
     *
     * @param exception 异常信息
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param locale    区域信息
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public void method(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response, Locale locale) {
        ofNullable(exception).map(MethodArgumentNotValidException::getBindingResult).map(Errors::getAllErrors).map(List::iterator) //
                .filter(Iterator::hasNext).map(Iterator::next).ifPresentOrElse(it -> validate(new ValidateException(it, //
                BAD_REQUEST), request, response, locale), () -> validate(new ValidateException("Bad Request",   //
                BAD_REQUEST, null), request, response, locale));
    }

    /**
     * 方法参数验证异常
     *
     * @param exception 异常信息
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param locale    区域信息
     */
    @ExceptionHandler(value = {ValidationException.class})
    public void validation(ValidationException exception, HttpServletRequest request, HttpServletResponse response, Locale locale) {
        validate(new ValidateException(requireNonNull(exception).getMessage(), BAD_REQUEST, null), request, response, locale);
    }


    /**
     * 实体验证异常
     *
     * @param exception 异常信息
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     * @param locale    区域信息
     */
    @ExceptionHandler(value = {BindException.class})
    public void bind(BindException exception, HttpServletRequest request, HttpServletResponse response, Locale locale) {
        ofNullable(exception).map(BindException::getAllErrors).map(List::iterator).filter(Iterator::hasNext) //
                .map(Iterator::next).ifPresentOrElse(it -> validate(new ValidateException(it, BAD_REQUEST), //
                request, response, locale), () -> validate(new ValidateException("Bad Request",     //
                BAD_REQUEST, null), request, response, locale));
    }
}
