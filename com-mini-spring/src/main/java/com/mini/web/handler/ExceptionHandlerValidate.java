package com.mini.web.handler;

import com.mini.validate.ValidateException;
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
    public void handler(IModel model, ValidateException exception, HttpServletRequest request, HttpServletResponse response) {
        model.setStatus(exception.getStatus()).setMessage(exception.getMessage());
    }
}
