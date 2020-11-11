package com.mini.core.mvc.validation;

import org.springframework.http.HttpStatus;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public final boolean isTrue(boolean expression) {
        if (!expression) throw send();
        return true;
    }

    public final String isPattern(String string, String regex) {
        isTrue(string != null && matches(string, regex));
        return string;
    }

    public final String isNotBlank(String string) {
        isTrue(string != null && !string.isBlank());
        return string;
    }

    public final void isNotEmpty(String string) {
        isTrue(string != null && !string.isEmpty());
    }

    public final <T> T isNotNull(T object) {
        isTrue(object != null);
        return object;
    }

    public final <T> T isNull(T object) {
        isTrue(object == null);
        return null;
    }

    public final String isEmail(String string) {
        isPattern(string, EMAIL);
        return string;
    }

    public final String isPhone(String string) {
        isPattern(string, PHONE);
        return string;
    }

    public final String isMobile(String string) {
        isPattern(string, MOBILE);
        return string;
    }

    public final String isLetter(String string) {
        isPattern(string, LETTER);
        return string;
    }

    public final String isNumber(String string) {
        isPattern(string, NUMBER);
        return string;
    }

    public final String isIdCard(String string) {
        isPattern(string, ID_CARD);
        return string;
    }

    public final String isChinese(String string) {
        isPattern(string, CHINESE);
        return string;
    }

    public final String isRequire(String string) {
        isPattern(string, REQUIRE);
        return string;
    }

    public final String isMobilePhone(String string) {
        if (Objects.isNull(string)) {
            throw send();
        }
        if (string.matches(MOBILE)) {
            return string;
        }
        if (string.matches(PHONE)) {
            return string;
        }
        throw send();
    }

    static final class ValidatorBuilderImpl extends ValidatorBuilder {
        ValidatorBuilderImpl() {
        }
    }
}
