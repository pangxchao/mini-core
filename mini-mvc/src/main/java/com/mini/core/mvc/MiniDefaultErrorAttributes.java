package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Locale;
import java.util.Map;

import static org.springframework.util.StringUtils.trimWhitespace;

public class MiniDefaultErrorAttributes extends DefaultErrorAttributes {
    private final MessageSource ms;

    public MiniDefaultErrorAttributes(MessageSource messageSource) {
        this.ms = messageSource;
    }

    @SuppressWarnings("DuplicatedCode")
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        final Throwable exception = super.getError(webRequest);

        // MethodArgumentNotValidException 异常处理
        if (exception instanceof MethodArgumentNotValidException) {
            var e = (MethodArgumentNotValidException) exception;
            for (var error : e.getBindingResult().getAllErrors()) {
                map.put(getFieldName(), error.getObjectName());
                map.put("message", error.getDefaultMessage());
                if (error instanceof FieldError) {
                    var field = (FieldError) error;
                    map.put(getFieldName(), error.getObjectName() + "." + field.getField());
                }
                break;
            }
            return map;
        }

        // BindException 异常处理
        if (exception instanceof BindException) {
            var e = (BindException) exception;
            for (var error : e.getAllErrors()) {
                map.put(getFieldName(), error.getObjectName());
                map.put("message", error.getDefaultMessage());
                if (error instanceof FieldError) {
                    var field = (FieldError) error;
                    map.put(getFieldName(), error.getObjectName() + "." + field.getField());
                }
                break;
            }
            return map;
        }

        // ConstraintViolationException 异常处理
        if (exception instanceof ConstraintViolationException) {
            var e = (ConstraintViolationException) exception;
            for (var constraint : e.getConstraintViolations()) {
                map.put(getFieldName(), constraint.getPropertyPath());
                map.put("message", constraint.getMessage());
                break;
            }
            return map;
        }

        // ValidationException 异常处理
        if (exception instanceof ValidationException) {
            var e = (ValidationException) exception;
            map.put("message", e.getMessage());
            return map;
        }

        // 自定义异常消息
        if (exception instanceof ValidateException) {
            Locale locale = webRequest.getLocale();
            var e = (ValidateException) exception;
            map.put(getFieldName(), e.getField());
            map.put(getCodeName(), e.getCode());

            // 空消息
            var message = trimWhitespace(e.getMessage());
            if (!StringUtils.hasText(message)) {
                message = "Bad Request";
            }

            // 国际化消息
            else if (message.startsWith("{") && message.endsWith("}")) {
                String key = message.substring(1, message.length() - 1);
                message = ms.getMessage(key, e.getArgs(), locale);
            }

            // 消息内容
            map.put("message", message);
            return map;
        }

        // 其它消息
        return map;
    }

    protected String getFieldName() {
        return "field";
    }

    protected String getCodeName() {
        return "code";
    }
}
