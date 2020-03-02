package com.mini.core.web.interceptor;

import javax.inject.Singleton;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.ResourceBundle.getBundle;

/**
 * Web Action 拦截器
 * @author xchao
 */
@Singleton
public final class I18nActionInterceptor implements ActionInterceptor {
	private static final Map<Locale, ResourceBundle> BUNDLE  //
			= new ConcurrentHashMap<>();

	public Object invoke(ActionInvocation invocation) throws Throwable {
		var language = invocation.getRequest().getHeader("Accept-Language");
		final Locale locale = Locale.forLanguageTag(language); //
		var bundle = BUNDLE.computeIfAbsent(locale, key -> //
				getBundle("i18n.message", key)); //
		invocation.getModel().setResourceBundle(bundle);
		return invocation.invoke();
	}
}
