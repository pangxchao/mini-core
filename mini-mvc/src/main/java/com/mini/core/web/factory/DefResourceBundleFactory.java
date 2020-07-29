package com.mini.core.web.factory;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.ResourceBundle.getBundle;

@Singleton
public final class DefResourceBundleFactory implements ResourceBundleFactory, EventListener {
	private static final Map<Locale, ResourceBundle> MAP = new ConcurrentHashMap<>();
	
	@NotNull
	@Override
	public final ResourceBundle get(@Nonnull HttpServletRequest request) {
		Locale locale = Optional.of(request).map(r -> r.getHeader("Accept-Language"))
				.map(Locale::forLanguageTag).orElse(Locale.getDefault());
		return MAP.computeIfAbsent(locale, key -> { //
			return getBundle("i18n.message", key);
		});
	}
}
