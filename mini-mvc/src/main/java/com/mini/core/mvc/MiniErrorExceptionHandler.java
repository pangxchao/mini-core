package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import lombok.SneakyThrows;
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

/**
 * 自定义错误处理类，防止自定义错误打印异常日志
 *
 * @author pangchao
 */
@ControllerAdvice
public class MiniErrorExceptionHandler {

    /**
     * 对对象参数的验证异常类处理器
     *
     * @param exception MethodArgumentNotValidException 对象
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     */
    @SneakyThrows
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public void validate(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        request.getRequestDispatcher("/error").forward(request, response);
    }

    /**
     * 自定义对象验证的异常类处理器
     *
     * @param exception ConstraintViolationException 对象
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     */
    @SneakyThrows
    @ExceptionHandler({ConstraintViolationException.class})
    public void validate(ConstraintViolationException exception, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        request.getRequestDispatcher("/error").forward(request, response);
    }

    /**
     * 其它验证框架验证的异常类处理器
     *
     * @param exception ValidationException 对象
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     */
    @SneakyThrows
    @ExceptionHandler({ValidationException.class})
    public void validate(ValidationException exception, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        request.getRequestDispatcher("/error").forward(request, response);
    }

    /**
     * 其它验证框架验证的异常类处理器
     *
     * @param exception BindException 对象
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     */
    @SneakyThrows
    @ExceptionHandler({BindException.class})
    public void validate(BindException exception, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        request.getRequestDispatcher("/error").forward(request, response);
    }

    /**
     * 自定义错误处理异常类处理器
     *
     * @param exception ValidateException 对象
     * @param request   HttpServletRequest 对象
     * @param response  HttpServletResponse 对象
     */
    @SneakyThrows
    @ExceptionHandler({ValidateException.class})
    public void validate(ValidateException exception, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, exception.getStatus().value());
        request.getRequestDispatcher("/error").forward(request, response);
    }
}
