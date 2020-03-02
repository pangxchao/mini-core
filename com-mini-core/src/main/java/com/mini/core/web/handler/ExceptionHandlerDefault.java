package com.mini.core.web.handler;

import com.mini.core.web.model.IModel;
import com.mini.core.web.util.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public final class ExceptionHandlerDefault implements ExceptionHandler {
	private static final Logger LOGGER = getLogger(ExceptionHandlerDefault.class);

	@Override
	public int handlerOnExecute() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean supportException(Throwable exception) {
		return true;
	}

	@Override
	public void handler(IModel<?> model, Throwable exception, HttpServletRequest request,
			HttpServletResponse response) {
		String message = StringUtils.defaultIfBlank(exception.getMessage(), "Service Error!");
		model.setStatus(ResponseCode.INTERNAL_SERVER_ERROR).setMessage(message);
		LOGGER.error(exception.getMessage(), exception);
	}
}
