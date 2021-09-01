package com.mini.core.mvc.validation;

import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 自定义错误消息构建器
 *
 * @author pangchao
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class ValidatorBuilder {
    private final List<Object> args = new ArrayList<>();
    private HttpStatus status = BAD_REQUEST;
    private String message;
    private Integer code;
    private String field;

    /**
     * 默认构造器
     */
    private ValidatorBuilder() {
    }

    /**
     * 设置HTTP状态码
     *
     * @param status HTTP状态码
     * @return 自定义消息构建器
     */
    @Nonnull
    public final ValidatorBuilder status(HttpStatus status) {
        this.status = status;
        return this;
    }

    /**
     * 设置消息内容
     * <p>
     * {message}表示取国际化消息属性文件名称
     * </p>
     *
     * @param message 消息内容
     * @return 自定义消息构建器
     */
    @Nonnull
    public final ValidatorBuilder message(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置专用错误码
     *
     * @param code 用错误码
     * @return 自定义消息构建器
     */
    @Nonnull
    public final ValidatorBuilder code(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * 国际化消息参数
     *
     * @param args 国际化消息参数
     * @return 自定义消息构建器
     */
    @Nonnull
    public final ValidatorBuilder args(Object... args) {
        Collections.addAll(this.args, args);
        return this;
    }

    /**
     * 设置错误字段名称
     *
     * @param field 错误字段名称
     * @return 自定义消息构建器
     */
    @Nonnull
    public final ValidatorBuilder field(String field) {
        this.field = field;
        return this;
    }

    /**
     * 直接发头错误消息
     *
     * @return 自定义错误消息异常类
     */
    @Nonnull
    public final ValidateException send() {
        throw new ValidateException(message, status, code, args.toArray(), field);
    }

    /**
     * 验证表达式必须为TRUE
     * <p>
     * 验证失败时发送错误异常信息
     * </p>
     *
     * @param expression 表达式
     * @return 表达式结果
     */
    public final boolean isTrue(boolean expression) {
        if (!expression) throw send();
        return true;
    }

    /**
     * 验证字符串必须符合指定正则表达式
     * <p>
     * 验证失败时发送错误异常信息
     * </p>
     *
     * @param string 字符串
     * @param regex  正则表达式
     * @return 字符串
     */
    @Nonnull
    public final String isPattern(@Nullable String string, String regex) {
        isTrue(string != null && string.matches(regex));
        return requireNonNull(string);
    }

    /**
     * 验证字符串必须不为空并且不能只包含空白字符
     * <p>
     * 验证失败时发送错误异常信息
     * </p>
     *
     * @param string 字符串
     * @return 字符串
     */
    @Nonnull
    public final String isNotBlank(@Nullable String string) {
        isTrue(string != null && !string.isBlank());
        return requireNonNull(string);
    }

    /**
     * 验证字符串必须不为空并且长度不为0
     * <p>
     * 验证失败时发送错误异常信息
     * </p>
     *
     * @param string 字符串
     * @return 字符串
     */
    @Nonnull
    public final String isNotEmpty(@Nullable String string) {
        this.isTrue(string != null && !string.isEmpty());
        return requireNonNull(string);
    }

    /**
     * 验证对象必须不能为空
     * <p>
     * 验证失败时发送错误异常信息
     * </p>
     *
     * @param object 对象信息
     * @return 对象信息
     */
    @Nonnull
    public final <T> T isNotNull(@Nullable T object) {
        isTrue(Objects.nonNull(object));
        return requireNonNull(object);
    }

    /**
     * 验证对象必须为空
     * <p>
     * 验证失败时发送错误异常信息
     * </p>
     *
     * @param object 对象信息
     * @return 对象信息
     */
    @Nullable
    public final <T> T isNull(@Nullable T object) {
        isTrue(Objects.isNull(object));
        return null;
    }

    /**
     * 默认实现类
     */
    static final class ValidatorBuilderImpl extends ValidatorBuilder {
        ValidatorBuilderImpl() {
        }
    }
}
