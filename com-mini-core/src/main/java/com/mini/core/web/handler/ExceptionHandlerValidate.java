package com.mini.core.web.handler;

import com.mini.core.validation.ValidationException;
import com.mini.core.web.model.IModel;
import com.mini.core.web.support.config.Configures;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

import static com.mini.core.util.LanguageUtil.getMessage;
import static com.mini.core.util.ThrowsUtil.hidden;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;


@Singleton
public final class ExceptionHandlerValidate implements ExceptionHandler {
	private static final Logger log = getLogger(ExceptionHandlerDefault.class);
	@Inject
	private Configures configures;
	
	@Override
	public int handlerOnExecute() {
		return 0;
	}
	
	@Override
	public boolean supportException(@Nonnull Throwable throwable) {
		return throwable instanceof ValidationException;
	}
	
	@Override
	public void handler(@Nonnull IModel<?> model, @Nonnull Throwable exception, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
		try {
			ResourceBundle bundle = configures.getResourceBundleFactory().get(request);
			ValidationException e = (ValidationException) exception;
			// 设置消息和状态码
			var message = getMessage(e.getKey(), e.getMessage(), bundle, e.getArgs().toArray());
			model.setStatus(e.getStatus()).setMessage(message);
			// 输出调试日志
			log.debug(format("%s(%s)", request.getRequestURI(), e.getMessage()));
		} catch (Exception | Error e) {
			throw hidden(e);
		}
	}
}
