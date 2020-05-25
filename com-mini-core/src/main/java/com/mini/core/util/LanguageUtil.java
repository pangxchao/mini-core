package com.mini.core.util;

import javax.annotation.Nonnull;
import java.util.EventListener;
import java.util.ResourceBundle;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public final class LanguageUtil implements EventListener {
	
	public static String getMessage(@Nonnull String key, String message, ResourceBundle bundle, Object... args) {
		return format(ofNullable(bundle).filter(b -> b.containsKey(key)).map(b -> b.getString(key)) //
				.orElse(defaultIfEmpty(message, "")), args);
	}
	
	public static String getMessage(@Nonnull String key, ResourceBundle bundle, Object... args) {
		return getMessage(key, null, bundle, args);
	}
}
