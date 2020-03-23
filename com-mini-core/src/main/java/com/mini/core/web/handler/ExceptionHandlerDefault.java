package com.mini.core.web.handler;

import com.mini.core.util.ThrowsUtil;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.util.ResponseCode;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public final class ExceptionHandlerDefault implements ExceptionHandler {
	private static final Logger log = getLogger(ExceptionHandlerDefault.class);
	@Inject
	private Configures configures;
	
	@Override
	public int handlerOnExecute() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean supportException(Throwable exception) {
		return true;
	}
	
	@Override
	public void handler(ActionInvocation invocation, Throwable e, HttpServletRequest request, HttpServletResponse response) {
		try {
			ResourceBundle bundle = configures.getResourceBundleFactory().get(invocation);
			invocation.getModel().setStatus(ResponseCode.INTERNAL_SERVER_ERROR);
			String code = String.valueOf(ResponseCode.INTERNAL_SERVER_ERROR);
			String message = bundle.containsKey(code) ? bundle.getString(code)
					: defaultIfBlank(e.getMessage(), "Service Error");
			invocation.getModel().setMessage(message);
			log.error(e.getMessage(), e);
		} catch (Exception | Error ex) {
			throw ThrowsUtil.hidden(ex);
		}
	}
}
