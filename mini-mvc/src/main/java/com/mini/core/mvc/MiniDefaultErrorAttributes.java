package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static javax.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public class MiniDefaultErrorAttributes extends DefaultErrorAttributes {

    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        Object exception = webRequest.getAttribute(ERROR_EXCEPTION, SCOPE_REQUEST);
        if (exception instanceof ValidateException) {
            var e = (ValidateException) exception;
            map.put(getFieldName(), e.getField());
            map.put(getCodeName(), e.getCode());
            addErrorMessage(map, webRequest);
        }
        return map;
    }

    protected String getFieldName() {
        return "field";
    }

    protected String getCodeName() {
        return "code";
    }

    protected void addErrorMessage(Map<String, Object> errorAttributes, WebRequest webRequest) {
        Object message = webRequest.getAttribute(ERROR_MESSAGE, SCOPE_REQUEST);
        errorAttributes.put("message", message);
    }
}
