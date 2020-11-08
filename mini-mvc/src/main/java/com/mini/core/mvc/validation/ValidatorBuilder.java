package com.mini.core.mvc.validation;

import org.springframework.http.HttpStatus;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mini.core.util.StringKt.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@SuppressWarnings("UnusedReturnValue")
public abstract class ValidatorBuilder {
    private final List<Object> args = new ArrayList<>();
    private HttpStatus status = BAD_REQUEST;
    private String message;
    private Integer code;

    private ValidatorBuilder() {
    }

    public final ValidatorBuilder message(String message) {
        this.message = message;
        return this;
    }

    public final ValidatorBuilder status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public final ValidatorBuilder code(Integer code) {
        this.code = code;
        return this;
    }

    public final ValidatorBuilder args(Object... args) {
        Collections.addAll(this.args, args);
        return this;
    }

    public final ValidationException send() {
        throw new ValidateException(message, status, code, args.toArray());
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

    static final class ValidatorBuilderImpl extends ValidatorBuilder {
        ValidatorBuilderImpl() {
        }
    }
}
