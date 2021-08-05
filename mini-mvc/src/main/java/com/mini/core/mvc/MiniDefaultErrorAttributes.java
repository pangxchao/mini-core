package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.Map;

import static org.springframework.util.StringUtils.trimWhitespace;

public class MiniDefaultErrorAttributes extends DefaultErrorAttributes {
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @SuppressWarnings("DuplicatedCode")
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        final Throwable exception = super.getError(webRequest);
        // 自定义异常消息
        if (exception instanceof ValidateException) {
            Locale locale = webRequest.getLocale();
            var e = (ValidateException) exception;

            // 重新设置错误状态码
            map.put("error", e.getStatus().getReasonPhrase());
            map.put("status", e.getStatus().value());

            // 设置错误码和错误字段
            map.put(getFieldName(), e.getField());
            map.put(getCodeName(), e.getCode());

            // 空消息
            var message = trimWhitespace(e.getMessage());
            if (!StringUtils.hasText(message)) {
                message = "Bad Request";
            }

            // 国际化消息
            else if (messageSource != null && message.startsWith("{") && message.endsWith("}")) {
                String key = message.substring(1, message.length() - 1);
                message = messageSource.getMessage(key, e.getArgs(), locale);
            }

            // 消息内容
            map.put("message", message);
        }
        return map;
    }

    protected String getFieldName() {
        return "field";
    }

    protected String getCodeName() {
        return "code";
    }
}
