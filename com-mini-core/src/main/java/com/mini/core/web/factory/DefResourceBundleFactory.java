package com.mini.core.web.factory;

import com.mini.core.web.interceptor.ActionInvocation;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.util.EventListener;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.ResourceBundle.getBundle;

@Singleton
public final class DefResourceBundleFactory implements ResourceBundleFactory, EventListener {
	private static final Map<Locale, ResourceBundle> MAP = new ConcurrentHashMap<>();
	
	@NotNull
	@Override
	public final ResourceBundle get(ActionInvocation invocation) {
		HttpServletRequest request = invocation.getRequest();
		var language = request.getHeader("Accept-Language");
		Locale locale = Locale.forLanguageTag(language);
		return MAP.computeIfAbsent(locale, key -> {
			return getBundle("i18n.message", key); //
		});
	}
}
