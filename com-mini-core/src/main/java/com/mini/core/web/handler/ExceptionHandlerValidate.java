package com.mini.core.web.handler;

import com.mini.core.validate.ValidateException;
import com.mini.core.web.model.IModel;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public final class ExceptionHandlerValidate implements ExceptionHandler {
    @Override
    public int handlerOnExecute() {
        return 0;
    }

    @Override
    public boolean supportException(Throwable throwable) {
        return throwable instanceof ValidateException;
    }

    @Override
    public void handler(IModel<?> model, Throwable exception, HttpServletRequest request, HttpServletResponse response) {
        ValidateException e = (ValidateException) exception;
        model.setStatus(e.getStatus()).setMessage(e.getMessage());
    }
}
