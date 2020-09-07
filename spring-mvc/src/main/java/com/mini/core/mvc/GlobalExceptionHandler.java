
package com.mini.core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.ResourceBundle.getBundle;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     *
     * @param exception 验证不通过时的异常信息
     * @return 返回数据
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ofNullable(exception).map(it -> it.getBindingResult().getFieldErrors().iterator())
                .map(it -> it.hasNext() ? it.next() : null).map(it -> {
                    System.out.println(it.getDefaultMessage());
                    System.out.println(it.getRejectedValue());
                    System.out.println(it.getField());
                    return status(BAD_REQUEST).body(it.getDefaultMessage());
                }).orElse(status(BAD_REQUEST).body("Bad Request"));
    }

    /**
     * 处理请求单个参数不满足校验规则的异常信息
     *
     * @param exception 验证不通过时的异常信息
     * @return 返回数据
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException exception) {
        return ofNullable(exception).map(it -> it.getConstraintViolations().iterator())
                .map(it -> it.hasNext() ? it.next() : null).map(it -> {
                    log.error(it.getMessage(), format("[%s]%s", it.getPropertyPath(), it.getMessage()));
                    System.out.println(it.getMessageTemplate());
                    System.out.println(it.getPropertyPath());
                    System.out.println(it.getInvalidValue());
                    System.out.println(it.getConstraintDescriptor());
                    System.out.println(it.getExecutableReturnValue());
                    System.out.println(it.getLeafBean());
                    System.out.println(it.getRootBean());
                    return status(BAD_REQUEST).body(it.getMessage());
                }).orElse(status(BAD_REQUEST).body("Bad Request"));
    }

    /**
     * 自定义验证错误处理
     *
     * @param exception 验证错误信息
     * @param locale    本地参数信息
     * @return 返回数据
     */
    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<String> validationExceptionHandler(ValidationException exception, Locale locale) {
        return Optional.ofNullable(exception).map(Throwable::getMessage).map(message -> {
            if (!message.isBlank() && message.startsWith("{") && message.endsWith("}")) {
                ResourceBundle bundle = getBundle("ValidationMessages", locale);
                return status(BAD_REQUEST).body(bundle.getString(message));
            }
            return status(BAD_REQUEST).body(message);
        }).orElse(status(BAD_REQUEST).body("Bad Request"));
    }
}