package com.mini.core.mvc.validation;

import com.mini.core.mvc.validation.ValidatorBuilder.ValidatorBuilderImpl;
import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.ResourceBundle.getBundle;
import static javax.validation.Validation.byDefaultProvider;

public final class ValidatorUtil {
    private static String BUNDLE_NAME = "org.hibernate.validator.ValidationMessages";

    public static void setBundleName(@Nonnull String bundleName) {
        ValidatorUtil.BUNDLE_NAME = bundleName;
    }

    @Nonnull
    public static Validator getValidator(@Nullable Locale locale) {
        final Locale mLocale = locale == null ? Locale.getDefault() : locale;
        final DefaultLocaleResolver resolver = new DefaultLocaleResolver();
        return byDefaultProvider().configure().messageInterpolator( //
                new ResourceBundleMessageInterpolator(emptySet(),  //
                        mLocale, resolver, false)
        ).buildValidatorFactory().getValidator();
    }

    public static Validator getValidator() {
        return getValidator(null);
    }

    @Nonnull
    private static String getBindingResultMessage(@Nullable BindingResult bindingResult) {
        return Optional.ofNullable(bindingResult)
                .map(Errors::getFieldErrors)
                .map(List::iterator)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }

    @Nonnull
    public static String methodArgumentNotValidMessage(MethodArgumentNotValidException exception) {
        return Optional.ofNullable(exception)
                .map(it -> exception.getBindingResult())
                .map(ValidatorUtil::getBindingResultMessage)
                .orElse("Bad Request");
    }

    @Nonnull
    public static <T> String getConstraintViolationSetMessage(Set<ConstraintViolation<T>> set) {
        return Optional.ofNullable(set).map(Set::iterator)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .map(ConstraintViolation::getMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }

    @Nonnull
    public static <T> String constraintViolationMessage(ConstraintViolationException exception) {
        return Optional.ofNullable(exception)
                .map(ConstraintViolationException::getConstraintViolations)
                .map(Set.class::cast)
                .map(ValidatorUtil::getConstraintViolationSetMessage)
                .orElse("Bad Request");
    }

    @Nonnull
    public static String validateMessage(ValidateException exception, Locale locale) {
        return Optional.ofNullable(exception).map(Throwable::getMessage).map(msg ->
                Optional.ofNullable(locale).map(it -> getBundle(BUNDLE_NAME, it))
                        .filter(it -> msg.startsWith("{") && msg.endsWith("}"))
                        .filter(it -> it.containsKey(msg))
                        .map(it -> it.getString(msg))
                        .orElse(msg))
                .orElse("Bad Request");
    }

    @Nonnull
    public static String validationMessage(ValidationException exception) {
        return Optional.ofNullable(exception).map(Throwable::getMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }

    @Nonnull
    public static String validateMessage(ValidateException exception) {
        return validateMessage(exception, null);
    }

    @Nonnull
    public static String bindMessage(BindException exception) {
        return Optional.ofNullable(exception)
                .map(BindException::getAllErrors)
                .map(List::iterator)
                .map(errors -> errors.hasNext() ? errors.next() : null)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(it -> !it.isBlank())
                .orElse("Bad Request");
    }

    public static ValidatorBuilder status(HttpStatus status) {
        return new ValidatorBuilderImpl().status(status);
    }

    public static ValidatorBuilder message(String message) {
        return new ValidatorBuilderImpl().message(message);
    }

    public static ValidatorBuilder code(Integer code) {
        return new ValidatorBuilderImpl().code(code);
    }
}