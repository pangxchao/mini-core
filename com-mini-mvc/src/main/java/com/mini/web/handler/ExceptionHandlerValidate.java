package com.mini.web.handler;

import com.mini.core.validate.ValidateException;
import com.mini.web.model.IModel;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public final class ExceptionHandlerValidate implements ExceptionHandler<ValidateException> {
    @Override
    public int handlerOnExecute() {
        return 0;
    }

    @Override
    public Class<ValidateException> getExceptionClass() {
        return ValidateException.class;
    }

    @Override
    public void handler(IModel model, Throwable exception, HttpServletRequest request, HttpServletResponse response) {
        ValidateException e = (ValidateException) exception;
        model.setStatus(e.getStatus()).setMessage(e.getMessage());
    }
}
