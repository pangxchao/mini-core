package com.mini.core.web.handler;

import com.mini.core.web.model.IModel;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.util.ResponseCode;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

import static com.mini.core.util.LanguageUtil.getMessage;
import static com.mini.core.util.ThrowsUtil.hidden;
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
	public boolean supportException(@Nonnull Throwable exception) {
		return true;
	}
	
	@Override
	public void handler(@Nonnull IModel<?> model, @Nonnull Throwable e, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
		try {
			ResourceBundle bundle = configures.getResourceBundleFactory().get(request);
			String defaultMessage = defaultIfBlank(e.getMessage(), "Service Error");
			String code = String.valueOf(ResponseCode.INTERNAL_SERVER_ERROR);
			model.setMessage(getMessage(code, defaultMessage, bundle));
			model.setStatus(ResponseCode.INTERNAL_SERVER_ERROR);
			log.error(e.getMessage(), e);
		} catch (Exception | Error ex) {
			throw hidden(ex);
		}
	}
}
