package com.mini.core.mvc.validation;

import org.hibernate.validator.internal.engine.messageinterpolation.DefaultLocaleResolver;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import static com.mini.core.util.StringKt.*;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

public class ValidateUtil {
    private static final Logger log = LoggerFactory.getLogger(ValidateUtil.class);

    private final String message;

    private ValidateUtil(String message) {
        this.message = message;
    }

    public ValidationException send() {
        throw new ValidationException(message);
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

    public void isNotNull(String string) {
        isTrue(string != null);
    }

    public void isNull(String string) {
        isTrue(string == null);
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

    public static Validator getValidator(Locale locale) {
        ResourceBundleMessageInterpolator interpolator = new ResourceBundleMessageInterpolator(emptySet(), locale, new DefaultLocaleResolver(), false);
        return Validation.byDefaultProvider().configure().messageInterpolator(interpolator).buildValidatorFactory().getValidator();
    }

    public static Validator getValidator() {
        return getValidator(Locale.getDefault());
    }

    public static ValidateUtil message(String message) {
        return new ValidateUtil(message);
    }

    private static <T> void validate_callback(Set<ConstraintViolation<T>> constraintViolation) {
        ofNullable(constraintViolation).map(Set::iterator).filter(Iterator::hasNext).map(Iterator::next).ifPresent(it -> {
            log.error(it.getMessage(), "[${it.propertyPath}]${it.message}");
            System.out.println(it.getMessageTemplate());
//            System.out.println(it.propertyPath)
//            System.out.println(it.invalidValue)
//            System.out. println(it.constraintDescriptor)
//            System.out. println(it.executableParameters)
//            System.out. println(it.leafBean)
//            System.out.println(it.rootBeanClass)
            throw message(it.getMessage()).send();
        });
    }


    public static <T> void validateParameters(Locale locale, T obj, Method method, Object[] parameterValues, Class<?>... groups) {
        ofNullable(getValidator(locale).forExecutables().validateParameters(obj, method, parameterValues, groups)) //
                .ifPresent(ValidateUtil::validate_callback);
    }

    public static <T> void validateParameters(T obj, Method method, Object[] parameterValues, Class<?>... groups) {
        ofNullable(getValidator().forExecutables().validateParameters(obj, method, parameterValues, groups)) //
                .ifPresent(ValidateUtil::validate_callback);
    }

    public static <T> void validate(Locale locale, T obj, Class<?>... groups) {
        ofNullable(getValidator(locale).validate(obj, groups)).ifPresent(ValidateUtil::validate_callback);
    }

    public static <T> void validate(T obj, Class<?>... groups) {
        ofNullable(getValidator().validate(obj, groups)).ifPresent(ValidateUtil::validate_callback);
    }

}