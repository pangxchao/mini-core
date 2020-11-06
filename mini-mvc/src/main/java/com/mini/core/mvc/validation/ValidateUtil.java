package com.mini.core.mvc.validation;

import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.*;

import static com.mini.core.util.StringKt.*;
import static java.util.Collections.emptySet;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static javax.validation.Validation.byDefaultProvider;

public class ValidateUtil {
    private static String bundleName = "org.hibernate.validator.ValidationMessages";
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message;
    private Integer code;

    private ValidateUtil(HttpStatus status, String message, Integer code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    private ValidateUtil(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    private ValidateUtil(HttpStatus status, Integer code) {
        this.status = status;
        this.code = code;
    }

    private ValidateUtil(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    private ValidateUtil(HttpStatus status) {
        this.status = status;
    }

    private ValidateUtil(String message) {
        this.message = message;
    }

    private ValidateUtil(Integer code) {
        this.code = code;
    }

    private ValidateUtil() {
    }

    @SuppressWarnings("UnusedReturnValue")
    public ValidateUtil message(String message) {
        this.message = message;
        return this;
    }

    public ValidateUtil status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ValidateUtil code(Integer code) {
        this.code = code;
        return this;
    }

    public ValidationException send() {
        throw new ValidateException(message, status, code);
    }

    public void isTrue(boolean expression) {
        if (!expression) throw send();
    }

    public void isPattern(String string, String regex) {
        isTrue(string != null && matches(string, regex));
    }

    public void isNotBlank(String string) {
        isTrue(string != null && !string.isBlank());
    }

    public void isNotEmpty(String string) {
        isTrue(string != null && !string.isEmpty());
    }

    public void isNotNull(Object object) {
        isTrue(object != null);
    }

    public void isNull(Object object) {
        isTrue(object == null);
    }

    public void isEmail(String string) {
        isPattern(string, EMAIL);
    }

    public void isPhone(String string) {
        isPattern(string, PHONE);
    }

    public void isMobile(String string) {
        isPattern(string, MOBILE);
    }

    public void isMobilePhone(String string) {
        isTrue(string != null && (string.matches(MOBILE) || string.matches(PHONE)));
    }

    public void isLetter(String string) {
        isPattern(string, LETTER);
    }

    public void isNumber(String string) {
        isPattern(string, NUMBER);
    }

    public void isChinese(String string) {
        isPattern(string, CHINESE);
    }

    public void isIdCard(String string) {
        isPattern(string, ID_CARD);
    }

    public void isRequire(String string) {
        isPattern(string, REQUIRE);
    }

    @NotNull
    public static Validator getValidator(@Nullable Locale locale) {
        final Locale l = locale == null ? Locale.getDefault() : locale;
        final DefaultLocaleResolver resolver = new DefaultLocaleResolver();
        var i = new ResourceBundleMessageInterpolator(emptySet(), l, resolver, false);
        return byDefaultProvider().configure().messageInterpolator(i).buildValidatorFactory().getValidator();
    }

    public static Validator getValidator() {
        return getValidator(null);
    }

    public <T> ValidateUtil validateParameters(@Nullable Locale locale, T obj, Method method, Object[] parameterValues, Class<?>... groups) {
        of(getValidator(locale)).map(Validator::forExecutables).map(it -> it.validateParameters(obj, method, parameterValues, groups))
                .map(ValidateUtil::getConstraintViolationSetMessage).ifPresent(this::message);
        return this;
    }

    public <T> ValidateUtil validateParameters(T obj, Method method, Object[] parameterValues, Class<?>... groups) {
        return validateParameters(Locale.getDefault(), obj, method, parameterValues, groups);
    }

    public <T> ValidateUtil validate(@Nullable Locale locale, T obj, Class<?>... groups) {
        Optional.of(getValidator(locale)).map(it -> it.validate(obj, groups))
                .map(ValidateUtil::getConstraintViolationSetMessage)
                .ifPresent(this::message);
        return this;
    }

    public <T> ValidateUtil validate(T obj, Class<?>... groups) {
        return validate(Locale.getDefault(), obj, groups);
    }

    public static void setBundleName(@NotNull String bundleName) {
        ValidateUtil.bundleName = bundleName;
    }

    public static ValidateUtil build(HttpStatus status, String message, Integer code) {
        return new ValidateUtil(status, message, code);
    }

    public static ValidateUtil build(HttpStatus status, String message) {
        return new ValidateUtil(status, message);
    }

    public static ValidateUtil build(HttpStatus status, Integer code) {
        return new ValidateUtil(status, code);
    }

    public static ValidateUtil build(String message, Integer code) {
        return new ValidateUtil(message, code);
    }

    public static ValidateUtil build(HttpStatus status) {
        return new ValidateUtil(status);
    }

    public static ValidateUtil build(String message) {
        return new ValidateUtil(message);
    }

    public static ValidateUtil build(Integer code) {
        return new ValidateUtil(code);
    }

    public static ValidateUtil build() {
        return new ValidateUtil();
    }

    @NotNull
    private static String getBindingResultMessage(@Nullable BindingResult bindingResult) {
        return ofNullable(bindingResult).map(Errors::getFieldErrors)
                .map(List::iterator).filter(Iterator::hasNext).map(Iterator::next)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }

    @NotNull
    public static String methodArgumentNotValidMessage(MethodArgumentNotValidException exception) {
        return ofNullable(exception).map(it -> exception.getBindingResult())
                .map(ValidateUtil::getBindingResultMessage)
                .orElse("Bad Request");
    }

    @NotNull
    private static <T> String getConstraintViolationSetMessage(Set<ConstraintViolation<T>> set) {
        return ofNullable(set).map(Set::iterator).filter(Iterator::hasNext)
                .map(Iterator::next).map(ConstraintViolation::getMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }

    @NotNull
    public static <T> String constraintViolationMessage(ConstraintViolationException exception) {
        return ofNullable(exception).map(ConstraintViolationException::getConstraintViolations)
                .map(Set.class::cast).map(ValidateUtil::getConstraintViolationSetMessage)
                .orElse("Bad Request");
    }

    @NotNull
    public static String validateMessage(ValidateException exception, Locale locale) {
        return Optional.ofNullable(exception).map(Throwable::getMessage).map(message ->
                ofNullable(locale).map(it -> ResourceBundle.getBundle(bundleName, it))
                        .filter(it -> message.startsWith("{") && message.endsWith("}"))
                        .filter(it -> it.containsKey(message))
                        .map(it -> it.getString(message))
                        .orElse(message))
                .orElse("Bad Request");
    }

    @NotNull
    public static String validationMessage(ValidationException exception) {
        return ofNullable(exception).map(Throwable::getMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }

    @NotNull
    public static String validateMessage(ValidateException exception) {
        return validateMessage(exception, null);
    }

    @NotNull
    public static String bindMessage(BindException exception) {
        return ofNullable(exception).map(BindException::getAllErrors).map(List::iterator)
                .map(errors -> errors.hasNext() ? errors.next() : null)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }
}