package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * 自定义错误处理
 *
 * @author pangchao
 */
public class MiniDefaultErrorAttributes extends DefaultErrorAttributes {
    private final MessageSource messageSource;

    public MiniDefaultErrorAttributes(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 获取自定义消息对象
     *
     * @param webRequest 请求对象
     * @param options    消息配置信息
     * @return 自定义消息对象
     */
    @SuppressWarnings("DuplicatedCode")
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        final Throwable exception = super.getError(webRequest);

        // 对对象参数的验证结果
        if (exception instanceof MethodArgumentNotValidException) {
            var e = (MethodArgumentNotValidException) exception;
            final ArrayList<String> messages = new ArrayList<>();
            for (var error : e.getBindingResult().getAllErrors()) {
                if (isNotBlank(error.getDefaultMessage())) {
                    messages.add(error.getDefaultMessage());
                }
            }
            map.put("message", join(messages, ";"));
            return map;
        }

        // 自定义对象验证的结果
        if (exception instanceof ConstraintViolationException) {
            var e = (ConstraintViolationException) exception;
            final ArrayList<String> messages = new ArrayList<>();
            for (var error : e.getConstraintViolations()) {
                if (isNotBlank(error.getMessage())) {
                    messages.add(error.getMessage());
                }
            }
            map.put("message", join(messages, ";"));
            return map;
        }

        // 其它验证框架验证的结果
        if (exception instanceof ValidationException) {
            map.put("message", "Bad Request");
            return map;
        }

        // 普通参数验证结果
        if (exception instanceof BindException) {
            final BindException e = (BindException) exception;
            final ArrayList<String> messages = new ArrayList<>();
            for (var error : e.getBindingResult().getAllErrors()) {
                if (isNotBlank(error.getDefaultMessage())) {
                    messages.add(error.getDefaultMessage());
                }
            }
            map.put("message", join(messages, ";"));
        }

        // 自定义异常消息
        if (exception instanceof ValidateException) {
            var e = (ValidateException) exception;
            map.put("message", e.getMessage(messageSource, webRequest.getLocale()));
            map.put("error", e.getStatus().getReasonPhrase());
            map.put("status", e.getStatus().value());
            map.put(getFieldName(), e.getField());
            map.put(getCodeName(), e.getCode());
            return map;
        }

        // 其它异常信息处理
        map.put("message", "Server Error");
        return map;
    }

    /**
     * 获取国际化消息转换器
     *
     * @return 国际化消息转换器
     */
    protected final MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * 指定错误字段名称
     *
     * @return 字段名称
     */
    protected String getFieldName() {
        return "field";
    }

    /**
     * 指定专用错误码
     *
     * @return 专用错误码
     */
    protected String getCodeName() {
        return "code";
    }
}
