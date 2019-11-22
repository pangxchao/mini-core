package com.mini.web.handler;

import com.mini.logger.Logger;
import com.mini.util.StringUtil;
import com.mini.web.model.IModel;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.logger.LoggerFactory.getLogger;

@Singleton
public final class ExceptionHandlerDefault implements ExceptionHandler<Throwable> {
    private static final Logger LOGGER = getLogger(ExceptionHandlerDefault.class);

    @Override
    public int handlerOnExecute() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void handler(IModel<?> model, Throwable exception, HttpServletRequest request, HttpServletResponse response) {
        String message = StringUtil.def(exception.getMessage(), "Service ERROR!");
        model.setStatus(INTERNAL_SERVER_ERROR).setMessage(message);
        LOGGER.error(exception);
    }
}
