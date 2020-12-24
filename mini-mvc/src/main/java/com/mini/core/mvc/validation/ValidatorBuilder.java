package com.mini.core.mvc.validation;

import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.mini.core.util.StringKt.*;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@SuppressWarnings("UnusedReturnValue")
public abstract class ValidatorBuilder {
    private final List<Object> args = new ArrayList<>();
    private HttpStatus status = BAD_REQUEST;
    private String message;
    private Integer code;

    private ValidatorBuilder() {
    }

    @Nonnull
    public final ValidatorBuilder message(String message) {
        this.message = message;
        return this;
    }

    @Nonnull
    public final ValidatorBuilder status(HttpStatus status) {
        this.status = status;
        return this;
    }

    @Nonnull
    public final ValidatorBuilder code(Integer code) {
        this.code = code;
        return this;
    }

    @Nonnull
    public final ValidatorBuilder args(Object... args) {
        Collections.addAll(this.args, args);
        return this;
    }

    @Nonnull
    public final ValidateException send() {
        throw new ValidateException(message, status, code, args.toArray());
    }

    public final boolean isTrue(boolean expression) {
        if (!expression) throw send();
        return true;
    }

    @Nonnull
    public final String isPattern(@Nullable String string, String regex) {
        this.isTrue(string != null && matches(string, regex));
        return requireNonNull(string);
    }

    @Nonnull
    public final String isNotBlank(@Nullable String string) {
        isTrue(string != null && !string.isBlank());
        return requireNonNull(string);
    }

    @Nonnull
    public final String isNotEmpty(@Nullable String string) {
        this.isTrue(string != null && !string.isEmpty());
        return requireNonNull(string);
    }

    @Nonnull
    public final <T> T isNotNull(@Nullable T object) {
        isTrue(Objects.nonNull(object));
        return requireNonNull(object);
    }

    @Nullable
    public final <T> T isNull(@Nullable T object) {
        isTrue(Objects.isNull(object));
        return null;
    }

    @Nonnull
    public final String isEmail(@Nullable String string) {
        return isPattern(string, EMAIL);
    }

    @Nonnull
    public final String isLetter(@Nullable String string) {
        return isPattern(string, LETTER);
    }

    @Nonnull
    public final String isNumber(@Nullable String string) {
        return isPattern(string, NUMBER);
    }

    @Nonnull
    public final String isIdCard(@Nullable String string) {
        return isPattern(string, ID_CARD);
    }

    @Nonnull
    public final String isChinese(@Nullable String string) {
        return isPattern(string, CHINESE);
    }

    @Nonnull
    public final String isRequire(@Nullable String string) {
        return isPattern(string, REQUIRE);
    }

    static final class ValidatorBuilderImpl extends ValidatorBuilder {
        ValidatorBuilderImpl() {
        }
    }
}
