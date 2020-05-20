package com.mini.core.web.factory;

import com.mini.core.web.interceptor.ActionInvocation;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.ResourceBundle.getBundle;

@Singleton
public final class DefResourceBundleFactory implements ResourceBundleFactory, EventListener {
	private static final Map<Locale, ResourceBundle> MAP = new ConcurrentHashMap<>();
	
	@NotNull
	@Override
	public final ResourceBundle get(ActionInvocation invocation) {
		Locale locale = Optional.of(invocation.getRequest())
				.map(r -> r.getHeader("Accept-Language"))
				.map(Locale::forLanguageTag)
				.orElse(Locale.getDefault());
		return MAP.computeIfAbsent(locale, key -> { //
			return getBundle("i18n.message", key);
		});
	}
}
