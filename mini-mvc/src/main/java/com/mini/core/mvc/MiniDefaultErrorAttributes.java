package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public class MiniDefaultErrorAttributes extends DefaultErrorAttributes {

    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        Object exception = webRequest.getAttribute(ERROR_EXCEPTION, SCOPE_REQUEST);
        if (exception instanceof ValidateException) {
            var e = (ValidateException) exception;
            map.put(getFieldName(), e.getField());
            map.put(getCodeName(), e.getCode());
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
