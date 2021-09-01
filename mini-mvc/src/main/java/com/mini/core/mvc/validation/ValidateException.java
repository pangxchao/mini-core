package com.mini.core.mvc.validation;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 自定义错误消息异常类
 *
 * @author pangchao
 */
public final class ValidateException extends RuntimeException {
    private final HttpStatus status;
    private final boolean isI18n;
    private final Object[] args;
    private final Integer code;
    private final String field;
    private final String name;

    /**
     * 构造方法
     *
     * @param message 消息内容 {message}表示国际化消息属性名称
     * @param status  HTTP状态码
     * @param code    专用错误码
     * @param args    国际
     * @param field   错误字段名称
     */
    public ValidateException(String message, HttpStatus status, Integer code, Object[] args, String field) {
        super(StringUtils.isBlank(message) ? "Bad Request" : StringUtils.strip(message));
        this.isI18n = startsWith(getMessage(), "{") && endsWith(getMessage(), "}");
        this.name = isI18n ? stripEnd(stripStart(message, "{"), "}") : "";
        this.status = status;
        this.field = field;
        this.code = code;
        this.args = args;
    }

    /**
     * 获取消息状态码
     *
     * @return 消息状态码
     */
    @NotNull
    public HttpStatus getStatus() {
        if (this.status == null) {
            return BAD_REQUEST;
        }
        return status;
    }

    /**
     * 获取国际化消息参数
     *
     * @return 国际化消息参数
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * 自定义专用错误码
     *
     * @return 专用错误码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 自定义错误字段名称
     *
     * @return 错误字段名称
     */
    public String getField() {
        return field;
    }

    /**
     * 获取国际化消息内容
     *
     * @param messageSource 国际化消息配置源信息
     * @param locale        语言信息
     * @return 获取结果
     */
    public String getMessage(@Nonnull MessageSource messageSource, Locale locale) {
        if (ValidateException.this.isI18n && StringUtils.isNoneBlank(name)) {
            return messageSource.getMessage(name, getArgs(), locale);
        }
        return getMessage();
    }
}
