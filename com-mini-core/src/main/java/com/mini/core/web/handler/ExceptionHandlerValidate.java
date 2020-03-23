package com.mini.core.web.handler;

import com.mini.core.util.ThrowsUtil;
import com.mini.core.validate.ValidateException;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

@Singleton
public final class ExceptionHandlerValidate implements ExceptionHandler {
	
	@Inject
	private Configures configures;
	
	@Override
	public int handlerOnExecute() {
		return 0;
	}
	
	@Override
	public boolean supportException(Throwable throwable) {
		return throwable instanceof ValidateException;
	}
	
	@Override
	public void handler(ActionInvocation invocation, Throwable exception, HttpServletRequest request, HttpServletResponse response) {
		try {
			ResourceBundle bundle = configures.getResourceBundleFactory().get(invocation);
			ValidateException e = (ValidateException) exception;
			// 获取消息内容
			var message = bundle.containsKey(e.getKey())
					? bundle.getString(e.getKey())
					: e.getMessage();
			// 格式化消息参数
			message = String.format(message, e.getArgs().toArray());
			invocation.getModel().setStatus(e.getStatus());
			invocation.getModel().setMessage(message);
		} catch (Exception | Error e) {
			throw ThrowsUtil.hidden(e);
		}
	}
}
